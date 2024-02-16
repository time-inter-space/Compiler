package MIR.Entity;

import java.io.PrintStream;

import Util.Type;

public class globalVar extends variable {
    String strConst = null;
    int len = 0;
    public globalVar(Type type, String name) {
        super(type, name);
    }
    public globalVar(Type type, String str, int strConstCnt) {
        super(type, "string." + strConstCnt);
        strConst = str.substring(1, str.length() - 1);
        len = strConst.replace("\\n", "0").replace("\\\"", "1").replace("\\\\", "2").length();
    }
    @Override public void print(PrintStream out) {
        out.print("@" + name);
    }
    public void printDef(PrintStream out) {
        if (strConst != null) {
            out.print("@" + name + " = private unnamed_addr constant [");
            out.print(Integer.toString(len + 1));
            out.print(" x i8] c\"");
            out.println(strConst.replace("\\n", "\\0A").replace("\\\"", "\\22") + "\\00\"");
        }
        else {
            out.print("@" + name + " = global ");
            type.print(out);
            if (type.IRName.equals("i32") || type.IRName.equals("i1")) out.println(" 0");
            else out.println(" null");
        }
    }
}
