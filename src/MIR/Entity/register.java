package MIR.Entity;

import Util.Type;

import java.io.PrintStream;

import MIR.*;

public class register extends entity {
    int num;
    public register(Type type, int regCnt) {
        this.type = new IRType(type);
        num = regCnt;
    }
    @Override public void print(PrintStream out) {
        out.print("%" + String.valueOf(num));
    }
}
