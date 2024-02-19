package MIR.Instruction;

import java.io.PrintStream;

import MIR.IRVisitor;

public abstract class statement {
    public abstract void print(PrintStream out);
    public abstract void accept(IRVisitor visitor);
}
