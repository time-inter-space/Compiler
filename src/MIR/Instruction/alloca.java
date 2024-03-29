package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;
import MIR.Entity.*;

public class alloca extends statement {
    public entity lhs;
    public alloca(entity lhs) {
        this.lhs = lhs;
    }
    @Override public void print(PrintStream out) {
        lhs.print(out);
        out.print(" = alloca ");
        lhs.type.print(out);
    }
    @Override public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
