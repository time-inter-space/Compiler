package Assembly.Operand;

import java.io.PrintStream;

import MIR.Entity.*;

public class AsmGlobalVar extends AsmReg {
    public String name;
    public int word = 0;
    public AsmGlobalVar(globalVar var) {
        name = var.name;
    }
    @Override public void print(PrintStream out) {
        out.println("    .globl " + name);
        out.println(name + ":");
        out.println("    .word " + word);
    }
}
