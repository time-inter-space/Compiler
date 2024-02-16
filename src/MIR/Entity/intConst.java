package MIR.Entity;

import java.io.PrintStream;

import MIR.IRType;
import Util.Type;

public class intConst extends entity {
    public String val;
    public intConst(String val) {
        type = new IRType(new Type("int"));
        this.val = val;
    }
    @Override public void print(PrintStream out) {
        out.print(val);
    }
}
