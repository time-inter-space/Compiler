package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.*;

public abstract class AsmInst {
    public AsmReg rd, rs1, rs2;
    public AsmImm imm;
    public abstract void print(PrintStream out);
}
