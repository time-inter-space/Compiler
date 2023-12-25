package MIR.Entity;

import java.io.PrintStream;

public class intConst extends entity {
    public String val;
    public intConst(String val) {
        this.val = val;
    }
    @Override public void print(PrintStream out) {
        out.print(val);
    }
}
