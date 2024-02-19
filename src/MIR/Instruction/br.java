package MIR.Instruction;

import MIR.Entity.entity;

import java.io.PrintStream;

import MIR.*;

public class br extends terminalStmt {
    public entity cond;
    public block trueBlock, falseBlock;
    public br(entity cond, block trueBlock, block falseBlock) {
        this.cond = cond;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }
    @Override public void print(PrintStream out) {
        out.print("br i1 ");
        cond.print(out);
        out.print(", label %" + trueBlock.label + ", label %" + falseBlock.label);
    }
    @Override public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
