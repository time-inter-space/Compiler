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
    public boolean isDotExpr;
    public getelementptr(register lhs, Type type, entity obj, entity num, boolean isDotExpr) {
        this.lhs = lhs;
        this.type = new IRType(type);
        this.obj = obj;
        this.num = num;
        this.isDotExpr = isDotExpr;
    }
    @Override public void print(PrintStream out) {
        lhs.print(out);
        out.print(" = getelementptr ");
        out.print(type.className);
        out.print(", ptr ");
        obj.print(out);
        if (isDotExpr) out.print(", i32 0");
        out.print(", i32 ");
        num.print(out);
    }
}
