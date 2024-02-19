package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;
import MIR.Entity.*;
import Util.Type;

public class select extends statement {
    IRType type;
    register lhs;
    entity cond, val1, val2;
    public select(Type type, register lhs, entity cond, entity val1, entity val2) {
        this.type = new IRType(type);
        this.lhs = lhs;
        this.cond = cond;
        this.val1 = val1;
        this.val2 = val2;
    }
    @Override public void print(PrintStream out) {
        lhs.print(out);
        out.print(" = select i1 ");
        cond.print(out);
        out.print(", ");
        val1.type.print(out);
        out.print(" ");
        val1.print(out);
        out.print(", ");
        val2.type.print(out);
        out.print(" ");
        val2.print(out);
    }
    @Override public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
