package MIR.Entity;

import java.io.PrintStream;

import Util.Type;

public class localVar extends variable {
    public localVar(Type type, String name) {
        super(type, name);
    }
    @Override public void print(PrintStream out) {
        out.print("%" + name);
    }
}
