package Assembly.Operand;

import java.io.PrintStream;

import MIR.Entity.*;

public class AsmGlobalStr extends AsmReg {
    public String name, strConst;
    public AsmGlobalStr(globalVar var) {
        this.name = var.name;
        this.strConst = var.strConst;
    }
    @Override public void print(PrintStream out) {
        out.println(name + ":");
        out.println("    .asciz \"" + strConst + "\"");
    }
}
