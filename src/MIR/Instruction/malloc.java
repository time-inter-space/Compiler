package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;
import MIR.Entity.*;
import Util.Type;

public class malloc extends statement {
    public register lhs;
    public IRType type;
    public entity sz;
    public malloc(register lhs, Type type, entity sz) {
        this.lhs = lhs;
        this.type = new IRType(type);
        this.sz = sz;
    }
    @Override public void print(PrintStream out) {
        lhs.print(out);
        out.print(" = call ptr @malloc(");
        type.print(out);
        out.print(" ");
        sz.print(out);
        out.print(")");
    }
}
