package Assembly.Operand;

import java.io.PrintStream;

public class AsmMemReg extends AsmReg {
    public static int cnt = 0;
    public int size, id = -1, paramId = -1;
    public AsmMemReg(int size) {
        this.size = size;
        id = cnt++;
    }
    public AsmMemReg(int size, int paramId) {
        this.size = size;
        this.paramId = paramId;
    }
    @Override public void print(PrintStream out) {}
}
