package MIR.Entity;

import Util.Type;

import java.io.PrintStream;

import MIR.*;

public class variable extends entity {
    public String name;
    public variable(Type type, String name) {
        this.type = new IRType(type);
        this.name = name;
    }
    @Override public void print(PrintStream out) {
        out.print("%" + name);
    }
    public void printWithType(PrintStream out) {
        type.print(out);
        out.print(" %" + name);
    }
}
