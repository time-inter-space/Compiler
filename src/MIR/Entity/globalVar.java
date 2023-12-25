package MIR.Entity;

import java.io.PrintStream;

import Util.Type;

public class globalVar extends variable {
    String strConst = null;
    public globalVar(Type type, String name) {
        super(type, name);
    }
    public globalVar(Type type, String str, int strConstCnt) {
        super(type, "string." + strConstCnt);
        strConst = str;
    }
    @Override public void print(PrintStream out) {
        out.print("@" + name);
    }
    public void printDef(PrintStream out) {
        out.print("@" + name + " = global ");
        type.print(out);
        if (type.IRName.equals("i32") || type.IRName.equals("i1")) out.println(" 0");
        else out.println(" null");
    }
}
