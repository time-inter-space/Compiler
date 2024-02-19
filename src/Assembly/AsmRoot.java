package Assembly;

import java.io.PrintStream;
import java.util.ArrayList;

import Assembly.Operand.*;

public class AsmRoot {
    public ArrayList<AsmFunction> fns = new ArrayList<>();
    public ArrayList<AsmGlobalVar> vars = new ArrayList<>();
    public ArrayList<AsmGlobalStr> strs = new ArrayList<>();
    public void print(PrintStream out) {
        out.println("    .section text");
        for (AsmFunction fn : fns) {
            fn.print(out);
            out.println("");
        }
        if (vars.size() > 0) out.println("\n    .section data");
        for (AsmGlobalVar var : vars) var.print(out);
        if (strs.size() > 0) out.println("\n    .section rodata");
        for (AsmGlobalStr str : strs) str.print(out);
    }
}
