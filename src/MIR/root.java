package MIR;

import MIR.Entity.*;

import java.io.PrintStream;
import java.util.ArrayList;

public class root {
    public ArrayList<classDef> cls = new ArrayList<>();
    public ArrayList<globalVar> vars = new ArrayList<>();
    public ArrayList<function> fns = new ArrayList<>();
    public void print(PrintStream out) {
        out.println("target datalayout = \"e-m:e-p:32:32-p270:32:32-p271:32:32-p272:64:64-f64:32:64-f80:32-n8:16:32-S128\"");
        out.println("target triple = \"riscv32-unknown-unknown-elf\"");
        out.println("");
        out.println("declare dso_local void @print(ptr)");
        out.println("declare dso_local void @println(ptr)");
        out.println("declare dso_local void @printInt(i32)");
        out.println("declare dso_local void @printlnInt(i32)");
        out.println("declare dso_local ptr @getString()");
        out.println("declare dso_local i32 @getInt()");
        out.println("declare dso_local ptr @toString(i32)");
        out.println("declare dso_local ptr @builtin.string.add(ptr, ptr)");
        out.println("declare dso_local zeroext i1 @builtin.string.eq(ptr, ptr)");
        out.println("declare dso_local zeroext i1 @builtin.string.ne(ptr, ptr)");
        out.println("declare dso_local zeroext i1 @builtin.string.lt(ptr, ptr)");
        out.println("declare dso_local zeroext i1 @builtin.string.le(ptr, ptr)");
        out.println("declare dso_local zeroext i1 @builtin.string.gt(ptr, ptr)");
        out.println("declare dso_local zeroext i1 @builtin.string.ge(ptr, ptr)");
        out.println("declare dso_local i32 @builtin.string.length(ptr)");
        out.println("declare dso_local ptr @builtin.string.substring(ptr, i32, i32)");
        out.println("declare dso_local i32 @builtin.string.parseInt(ptr)");
        out.println("declare dso_local i32 @builtin.string.ord(ptr, i32)");
        out.println("declare dso_local i32 @builtin.array.size(ptr)");
        out.println("declare dso_local noalias nonnull ptr @builtin.new.array(i32, i32)");
        out.println("declare dso_local noalias ptr @builtin.new.var(i32)");
        out.println("");
        cls.forEach(c -> c.print(out));
        vars.forEach(var -> var.printDef(out));
        fns.forEach(fn -> fn.print(out));
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
