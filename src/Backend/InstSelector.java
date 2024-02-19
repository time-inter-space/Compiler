package Backend;

import java.util.HashMap;

import Assembly.*;
import Assembly.Instruction.*;
import Assembly.Operand.*;
import MIR.*;
import MIR.Entity.*;
import MIR.Instruction.*;
import Util.*;

public class InstSelector implements IRVisitor {
    globalScope gScope;
    AsmRoot rt;
    AsmFunction currentFn = null;
    AsmBlock currentBlock = null, retBlock = null;
    HashMap<block, AsmBlock> blockMap = new HashMap<>();
    int fnCnt = 0;

    public InstSelector(globalScope gScope, AsmRoot rt) {
        this.gScope = gScope;
        this.rt = rt;
    }

    AsmReg getReg(entity it) {
        if (it.asmReg == null) {
            if (it instanceof intConst)
                it.asmReg = new AsmImmReg(Integer.parseInt(((intConst)it).val));
            else it.asmReg = new AsmMemReg(4);
        }
        return it.asmReg;
    }

    @Override public void visit(root it) {
        for (globalVar var : it.vars) {
            if (var.strConst == null) {
                var.asmReg = new AsmGlobalVar(var);
                rt.vars.add((AsmGlobalVar)var.asmReg);
            }
            else {
                var.asmReg = new AsmGlobalStr(var);
                rt.strs.add((AsmGlobalStr)var.asmReg);
            }
        }
        for (function fn : it.fns) {
            currentFn = new AsmFunction(fn.fnName);
            rt.fns.add(currentFn);
            fn.accept(this);
            fnCnt++;
        }
    }

    @Override public void visit(function it) {
        retBlock = new AsmBlock(".DARK" + Integer.toString(fnCnt) + "_"
            + Integer.toString(it.blocks.size()));
        blockMap.clear();
        int mx = 0;
        for (int i = 0; i < it.blocks.size(); i++) {
            block blk = it.blocks.get(i);
            blockMap.put(blk, new AsmBlock(
                ".DARK" + Integer.toString(fnCnt) + "_" + Integer.toString(i)));
            for (statement stmt : blk.stmts)
                if (stmt instanceof call)
                    mx = Math.max(mx, ((call)stmt).params.size());
        }
        currentFn.paramSize = Math.max(mx - 8, 0) * 4;
        for (int i = 0; i < it.params.size(); i++) {
            localVar param = it.params.get(i);
            if (i < 8) param.asmReg = new AsmBaseReg("a" + Integer.toString(i));
            else param.asmReg = new AsmMemReg(4, i);
        }
        for (int i = 0; i < it.blocks.size(); i++) {
            block blk = it.blocks.get(i);
            currentBlock = blockMap.get(blk);
            if (i == 0) currentBlock.push_back(new AsmStoreInst(new AsmBaseReg("ra"),
                new AsmBaseReg("sp"), new AsmImm(currentFn.paramSize)));
            blk.accept(this);
            currentFn.blocks.add(currentBlock);
        }
        currentFn.stackSize = (currentFn.paramSize + currentFn.allocaSize
            + AsmMemReg.cnt * 4 + 15 + 4) / 16 * 16;
        AsmBaseReg sp = new AsmBaseReg("sp");
        currentFn.blocks.get(0).push_front(new AsmImmInst(
            "addi", sp, sp, new AsmImm(-currentFn.stackSize)));
        retBlock.push_back(new AsmImmInst(
            "addi", sp, sp, new AsmImm(currentFn.stackSize)));
        retBlock.push_back(new AsmRetInst());
        currentFn.blocks.add(retBlock);
    }

    @Override public void visit(block it) {
        it.stmts.forEach(stmt -> stmt.accept(this));
    }
    
    @Override public void visit(alloca it) {
        currentBlock.push_back(new AsmImmInst("addi", getReg(it.lhs),
            new AsmBaseReg("sp"), new AsmImm(currentFn.paramSize + currentFn.allocaSize)));
        currentFn.allocaSize += 4;
    }

    @Override public void visit(binary it) {
        String op = it.op;
        if (op.equals("sdiv")) op = "div";
        if (op.equals("srem")) op = "rem";
        if (op.equals("shl")) op = "sll";
        if (op.equals("ashr")) op = "sra";
        currentBlock.push_back(new AsmCalcInst(op, getReg(it.lhs), getReg(it.op1), getReg(it.op2)));
    }

    @Override public void visit(br it) {
        currentBlock.push_back(new AsmBrInst("beq", getReg(it.cond),
            new AsmBaseReg("zero"), blockMap.get(it.falseBlock)));
        currentBlock.push_back(new AsmJumpInst(blockMap.get(it.trueBlock)));
    }

    @Override public void visit(call it) {
        for (int i = 0; i < it.params.size(); i++) {
            entity param = it.params.get(i);
            if (i < 8) currentBlock.push_back(new AsmMvInst(
                new AsmBaseReg("a" + Integer.toString(i)), getReg(param)));
            else currentBlock.push_back(new AsmStoreInst(
                getReg(param), new AsmBaseReg("sp"), new AsmImm((i - 8) * 4)));
        }
        currentBlock.push_back(new AsmCallInst(it.name));
        if (it.lhs != null) currentBlock.push_back(new AsmMvInst(getReg(it.lhs), new AsmBaseReg("a0")));
    }

    @Override public void visit(getelementptr it) {
        AsmReg id = getReg(it.num);
        AsmMemReg reg = new AsmMemReg(4);
        currentBlock.push_back(new AsmImmInst("slli", reg, id, new AsmImm(2)));
        currentBlock.push_back(new AsmCalcInst("add", getReg(it.lhs), getReg(it.obj), reg));
    }

    @Override public void visit(icmp it) {
        AsmMemReg reg = new AsmMemReg(4);
        if (it.op.equals("eq")) {
            currentBlock.push_back(new AsmCalcInst("sub", reg, getReg(it.op1), getReg(it.op2)));
            currentBlock.push_back(new AsmCmpzInst("seqz", getReg(it.lhs), reg));
        }
        else if (it.op.equals("ne")) {
            currentBlock.push_back(new AsmCalcInst("sub", reg, getReg(it.op1), getReg(it.op2)));
            currentBlock.push_back(new AsmCmpzInst("snez", getReg(it.lhs), reg));
        }
        else if (it.op.equals("slt")) {
            currentBlock.push_back(new AsmCalcInst("slt", getReg(it.lhs), getReg(it.op1), getReg(it.op2)));
        }
        else if (it.op.equals("sle")) {
            currentBlock.push_back(new AsmCalcInst("slt", reg, getReg(it.op2), getReg(it.op1)));
            currentBlock.push_back(new AsmCmpzInst("seqz", getReg(it.lhs), reg));
        }
        else if (it.op.equals("sgt")) {
            currentBlock.push_back(new AsmCalcInst("slt", getReg(it.lhs), getReg(it.op2), getReg(it.op1)));
        }
        else {
            currentBlock.push_back(new AsmCalcInst("slt", reg, getReg(it.op1), getReg(it.op2)));
            currentBlock.push_back(new AsmCmpzInst("seqz", getReg(it.lhs), reg));
        }
    }

    @Override public void visit(jump it) {
        currentBlock.push_back(new AsmJumpInst(blockMap.get(it.destination)));
    }

    @Override public void visit(load it) {
        currentBlock.push_back(new AsmLoadInst(getReg(it.var), getReg(it.ptr), new AsmImm(0)));
    }

    @Override public void visit(ret it) {
        if (!it.type.name.equals("void")) currentBlock.push_back(
            new AsmMvInst(new AsmBaseReg("a0"), getReg(it.val)));
        currentBlock.push_back(new AsmLoadInst(new AsmBaseReg("ra"),
            new AsmBaseReg("sp"), new AsmImm(currentFn.paramSize)));
        currentBlock.push_back(new AsmJumpInst(retBlock));
    }

    @Override public void visit(select it) {}

    @Override public void visit(store it) {
        currentBlock.push_back(new AsmStoreInst(getReg(it.var), getReg(it.ptr), new AsmImm(0)));
    }
}
