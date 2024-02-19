package Backend;

import java.util.LinkedList;

import Assembly.*;
import Assembly.Instruction.*;
import Assembly.Operand.*;

public class RegAlloc {
    AsmRoot rt;
    int stackSize, memBeg;
    LinkedList<AsmInst> workList;

    public RegAlloc(AsmRoot rt) {
        this.rt = rt;
    }

    void allocSrc(AsmBaseReg reg, AsmReg src) {
        if (src instanceof AsmImmReg)
            workList.add(new AsmLiInst(reg, new AsmImm(((AsmImmReg)src).num)));
        else if (src instanceof AsmMemReg) {
            AsmMemReg mem = (AsmMemReg)src; 
            int offset;
            if (mem.id != -1) offset = memBeg + mem.id * 4;
            else offset = stackSize + mem.paramId * 4;
            workList.add(new AsmLoadInst(reg, new AsmBaseReg("sp"), new AsmImm(offset)));
        }
        else if (src instanceof AsmGlobalStr || src instanceof AsmGlobalVar) {
            String name;
            if (src instanceof AsmGlobalStr) name = ((AsmGlobalStr)src).name;
            else name = ((AsmGlobalVar)src).name;
            workList.add(new AsmLuiInst(reg, new AsmRelocationFn("hi", name)));
            workList.add(new AsmImmInst("addi", reg, reg, new AsmRelocationFn("lo", name)));
        }
    }

    void allocDest(AsmBaseReg reg, AsmReg dest) {
        if (dest instanceof AsmMemReg) {
            AsmMemReg mem = (AsmMemReg)dest;
            int offset;
            if (mem.id != -1) offset = memBeg + mem.id * 4;
            else offset = stackSize + mem.paramId * 4;
            workList.add(new AsmStoreInst(reg, new AsmBaseReg("sp"), new AsmImm(offset)));
        }
    }

    void visitBlock(AsmBlock block) {
        workList = new LinkedList<>();
        for (AsmInst inst : block.insts) {
            AsmBaseReg t0 = new AsmBaseReg("t0");
            AsmBaseReg t1 = new AsmBaseReg("t1");
            if (inst.rs1 != null && !(inst.rs1 instanceof AsmBaseReg)) {
                allocSrc(t1, inst.rs1);
                inst.rs1 = t1;
            }
            if (inst.rs2 != null && !(inst.rs2 instanceof AsmBaseReg)) {
                allocSrc(t0, inst.rs2);
                inst.rs2 = t0;
            }
            workList.add(inst);
            if (inst.rd != null && !(inst.rd instanceof AsmBaseReg)) {
                allocDest(t0, inst.rd);
                inst.rd = t0;
            }
        }
        block.insts = workList;
    }

    public void work() {
        for (AsmFunction fn : rt.fns) {
            stackSize = fn.stackSize;
            memBeg = fn.paramSize + fn.allocaSize;
            fn.blocks.forEach(block -> visitBlock(block));
        }
    }
}
