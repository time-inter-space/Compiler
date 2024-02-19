package Assembly.Operand;

import java.io.PrintStream;

public class AsmImm extends AsmOperand {
    public int num;
    public AsmImm(int num) {
        this.num = num;
    }
    @Override public void print(PrintStream out) {
        out.print(Integer.toString(num));
    }
}
