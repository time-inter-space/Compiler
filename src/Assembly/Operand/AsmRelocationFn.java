package Assembly.Operand;

import java.io.PrintStream;

public class AsmRelocationFn extends AsmImm {
    public String type, symbol;
    public AsmRelocationFn(String type, String symbol) {
        super(0);
        this.type = type;
        this.symbol = symbol;
    }
    @Override public void print(PrintStream out) {
        out.print("%" + type +  "(" + symbol + ")");
    }
}
