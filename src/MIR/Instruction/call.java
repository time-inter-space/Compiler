package MIR.Instruction;

import AST.*;
import MIR.*;
import MIR.Entity.*;

import java.io.PrintStream;
import java.util.ArrayList;

public class call extends statement {
    public register lhs;
    public IRType type;
    public String name;
    public ArrayList<entity> params = new ArrayList<>();
    public call(register lhs, ExprNode dotObj, FnExprNode expr) {
        this.lhs = lhs;
        type = new IRType(expr.type);
        if (dotObj == null) name = expr.fnName;
        else {
            if (dotObj.type.dim > 0 && expr.fnName.equals("size"))
                name = "builtin.array.size";
            else if (dotObj.type.name.equals("string"))
                name = "builtin.string." + expr.fnName;
            else name = "" + dotObj.type.name + "." + expr.fnName;
            params.add(dotObj.val);
        }
        expr.params.forEach(param -> params.add(param.val));
        if (expr.fnName.equals("builtin.new.array")) params.add(new intConst("4"));
    }
    @Override public void print(PrintStream out) {
        if (lhs != null) {
            lhs.print(out);
            out.print(" = ");
        }
        out.print("call ");
        type.print(out);
        out.print(" @" + name + "(");
        for (int i = 0; i < params.size(); i++) {
            entity param = params.get(i);
            param.type.print(out);
            out.print(" ");
            param.print(out);
            if (i + 1 < params.size()) out.print(", ");
        }
        out.print(")");
    }
    @Override public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
