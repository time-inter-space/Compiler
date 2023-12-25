package MIR;

import java.io.PrintStream;

import Util.Type;

public class IRType extends Type {
    public String IRName;
    public IRType(Type type) {
        super(type);
        if (type.dim > 0 || type.name.equals("string")
          || type.name.equals("null")) IRName = "ptr";
        else if (type.name.equals("int")) IRName = "i32";
        else if (type.name.equals("bool")) IRName = "i1";
        else if (type.name.equals("") || type.name.equals("void")) IRName = "void";
        else IRName = "ptr";
    }
    public boolean isArray() {
        return dim > 0;
    }
    public boolean isString() {
        return name.equals("string");
    }
    public void print(PrintStream out) {
        out.print(IRName);
    }
}
