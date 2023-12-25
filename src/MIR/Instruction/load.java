package MIR.Instruction;

import java.io.PrintStream;

import MIR.*;
import MIR.Entity.*;
import Util.Type;

public class load extends statement {
    IRType type;
    entity ptr;
    entity var;
    public load(Type type, entity ptr, entity var) {
        this.type = new IRType(type);
        this.ptr = ptr;
        this.var = var;
    }
    @Override public void print(PrintStream out) {
        var.print(out);
        out.print(" = load ");
        type.print(out);
        out.print(", ptr ");
        ptr.print(out);
    }
}
