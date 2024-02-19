package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.*;

public class AsmCalcInst extends AsmInst {
    public String op;
    public AsmCalcInst(String op, AsmReg rd, AsmReg rs1, AsmReg rs2) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
        this.rs2 = rs2;
    }
    @Override public void print(PrintStream out) {
        out.print(op + " ");
        rd.print(out);
        out.print(", ");
        rs1.print(out);
        out.print(", ");
        rs2.print(out);
        out.println("");
    }
}
