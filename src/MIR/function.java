package MIR;

import Util.Type;
import MIR.Entity.*;

import java.io.PrintStream;
import java.util.ArrayList;

public class function {
    public IRType type;
    public String fnName;
    public ArrayList<localVar> params = new ArrayList<>();
    public ArrayList<block> blocks = new ArrayList<>();
    public function(Type type, classDef currentClass, String fnName) {
        this.type = new IRType(type);
        if (currentClass == null) this.fnName = fnName;
        else this.fnName = currentClass.className + "." + fnName;
    }
    public void print(PrintStream out) {
        out.print("define dso_local ");
        type.print(out);
        out.print(" @" + fnName + "(");
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) out.print(", ");
            params.get(i).printWithType(out);
        }
        out.println(") {");
        for (int i = 0; i < blocks.size(); i++)
            blocks.get(i).label = String.valueOf(i);
        blocks.forEach(block -> block.print(out));
        out.println("}");
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
