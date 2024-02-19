package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.*;

public class AsmLuiInst extends AsmInst {
    public AsmLuiInst(AsmReg rd, AsmImm imm) {
        this.rd = rd;
        this.imm = imm;
    }
    @Override public void print(PrintStream out) {
        out.print("lui ");
        rd.print(out);
        out.print(", ");
        imm.print(out);
        out.println("");
    }
}
