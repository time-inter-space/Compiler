package Assembly.Operand;

import java.io.PrintStream;

public class AsmImmReg extends AsmReg {
    public int num;
    public AsmImmReg(int num) {
        this.num = num;
    }
    @Override public void print(PrintStream out) {
        out.print(Integer.toString(num));
    }
}
