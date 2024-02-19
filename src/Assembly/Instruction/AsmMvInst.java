package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.AsmReg;

public class AsmMvInst extends AsmInst {
    public AsmMvInst(AsmReg rd, AsmReg rs1) {
        this.rd = rd;
        this.rs1 = rs1;
    }
    @Override public void print(PrintStream out) {
        out.print("mv ");
        rd.print(out);
        out.print(", ");
        rs1.print(out);
        out.println("");
    }
}
