package MIR.Entity;

import java.io.PrintStream;

import MIR.*;
import Util.Type;

public class nullConst extends entity {
    public nullConst() {
        type = new IRType(new Type("null"));
    }
    @Override public void print(PrintStream out) {
        out.print("null");
    }
}
