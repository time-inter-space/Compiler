package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.*;

public class AsmLoadInst extends AsmInst {
    public AsmLoadInst(AsmReg rd, AsmReg rs1, AsmImm imm) {
        this.rd = rd;
        this.rs1 = rs1;
        this.imm = imm;
    }
    @Override public void print(PrintStream out) {
        out.print("lw ");
        rd.print(out);
        out.print(", ");
        imm.print(out);
        out.print("(");
        rs1.print(out);
        out.println(")");
    }
}
