package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.*;

public class AsmImmInst extends AsmInst {
    String op;
    public AsmImmInst(String op, AsmReg rd, AsmReg rs1, AsmImm imm) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.imm = imm;
    }
    @Override public void print(PrintStream out) {
        out.print(op + " ");
        rd.print(out);
        out.print(", ");
        rs1.print(out);
        out.print(", ");
        imm.print(out);
        out.println("");
    }
}
