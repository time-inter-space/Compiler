package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.*;

public class AsmLiInst extends AsmInst {
    public AsmLiInst(AsmReg rd, AsmImm imm) {
        this.rd = rd;
        this.imm = imm;
    }
    @Override public void print(PrintStream out) {
        out.print("li ");
        rd.print(out);
        out.print(", ");
        imm.print(out);
        out.println("");
    }
}
