package MIR.Entity;

import java.io.PrintStream;

import Assembly.Operand.*;
import MIR.*;

public abstract class entity {
    public IRType type;
    public AsmReg asmReg = null;
    public void print(PrintStream out) {}
}
