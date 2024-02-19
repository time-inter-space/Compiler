package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;

public class jump extends terminalStmt {
    public block destination;
    public jump(block destination) {
        this.destination = destination;
    }
    @Override public void print(PrintStream out) {
        out.print("br label %" + destination.label);
    }
    @Override public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
