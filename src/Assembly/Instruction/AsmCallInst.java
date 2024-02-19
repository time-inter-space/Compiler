package Assembly.Instruction;

import java.io.PrintStream;

public class AsmCallInst extends AsmInst {
    public String symbol;
    public AsmCallInst(String symbol) {
        this.symbol = symbol;
    }
    @Override public void print(PrintStream out) {
        out.println("call " + symbol);
    }
}
