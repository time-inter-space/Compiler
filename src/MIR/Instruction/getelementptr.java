package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;
import MIR.Entity.*;
import Util.Type;

public class getelementptr extends statement {
    public register lhs;
    public IRType type;
    public entity obj;
    public entity num;
    public getelementptr(register lhs, Type type, entity obj, entity num) {
        this.lhs = lhs;
        this.type = new IRType(type);
        this.obj = obj;
        this.num = num;
    }
    @Override public void print(PrintStream out) {
        lhs.print(out);
        out.print(" = getelementptr ");
        type.print(out);
        out.print(", ptr ");
        obj.print(out);
        out.print(", i32 ");
        num.print(out);
    }
}
