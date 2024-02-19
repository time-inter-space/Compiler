package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.AsmReg;

public class AsmCmpzInst extends AsmInst {
    public String op;
    public AsmCmpzInst(String op, AsmReg rd, AsmReg rs1) {
        this.op = op;
        this.rd = rd;
        this.rs1 = rs1;
    }
    @Override public void print(PrintStream out) {
        out.print(op + " ");
        rd.print(out);
        out.print(", ");
        rs1.print(out);
        out.println("");
    }
}
