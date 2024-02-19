package Assembly.Operand;

import java.io.PrintStream;

public class AsmBaseReg extends AsmReg {
    public String name;
    public AsmBaseReg(String name) {
        this.name = name;
    }
    @Override public void print(PrintStream out) {
        out.print(name);
    }
}
