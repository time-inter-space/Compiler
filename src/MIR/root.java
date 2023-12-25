package MIR;

import MIR.Entity.*;

import java.io.PrintStream;
import java.util.ArrayList;

public class root {
    public ArrayList<classDef> cls = new ArrayList<>();
    public ArrayList<globalVar> vars = new ArrayList<>();
    public ArrayList<function> fns = new ArrayList<>();
    public void print(PrintStream out) {
        cls.forEach(c -> c.print(out));
        vars.forEach(var -> var.printDef(out));
        fns.forEach(fn -> fn.print(out));
    }
}
