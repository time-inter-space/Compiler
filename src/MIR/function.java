package MIR;

import Util.Type;
import MIR.Entity.*;

import java.io.PrintStream;
import java.util.ArrayList;

public class function {
    public IRType type;
    public String fnName;
    public ArrayList<variable> params = new ArrayList<>();
    public ArrayList<block> blocks = new ArrayList<>();
    public function(Type type, classDef currentClass, String fnName) {
        this.type = new IRType(type);
        if (currentClass == null) this.fnName = "@" + fnName;
        else this.fnName = "@" + currentClass.className + "." + fnName;
    }
    public void print(PrintStream out) {
        out.print("define ");
        type.print(out);
        out.print(" " + fnName + "(");
        for (int i = 0; i < params.size(); i++) {
            if (i > 0) out.print(", ");
            params.get(i).printWithType(out);
        }
        out.println(") {");
        blocks.forEach(block -> block.print(out));
        out.println("}");
    }
}
