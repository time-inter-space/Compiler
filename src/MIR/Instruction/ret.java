package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;
import MIR.Entity.*;

public class ret extends terminalStmt {
    public IRType type;
    public entity val;
    public ret(IRType type, entity val) {
        this.type = type;
        this.val = val;
    }
    @Override public void print(PrintStream out) {
        out.print("ret ");
        type.print(out);
        if (!type.name.equals("void")) {
            out.print(" ");
            val.print(out);
        }
    }
    @Override public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
