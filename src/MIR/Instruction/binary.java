package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;
import MIR.Entity.*;
import Util.Type;

public class binary extends statement {
    public IRType type;
    public String op;
    public register lhs;
    public entity op1, op2;
    public binary(Type type, register lhs, entity op1, entity op2, String op) {
        this.type = new IRType(type);
        this.lhs = lhs;
        this.op1 = op1;
        this.op2 = op2;
        this.op = op;
    }
    @Override public void print(PrintStream out) {
        lhs.print(out);
        out.print(" = " + op + " ");
        type.print(out);
        out.print(" ");
        op1.print(out);
        out.print(", ");
        op2.print(out);
    }
}
