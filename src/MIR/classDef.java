package MIR;

import java.util.HashMap;
import java.io.PrintStream;
import java.util.ArrayList;
import Util.Type;

public class classDef {
    public String className;
    public HashMap<String, Integer> varNum = new HashMap<>();
    public ArrayList<IRType> vars = new ArrayList<>();
    public classDef(String className) {
        this.className = className;
    }
    public void addVar(Type type, String name) {
        varNum.put(name, vars.size());
        vars.add(new IRType(type));
    }
    public void print(PrintStream out) {
        out.print("%class." + className + " = type <{ ");
        for (int i = 0; i < vars.size(); i++) {
            if (i > 0) out.print(", ");
            vars.get(i).print(out);
        }
        out.println(" }>");
    }
}
