package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.AsmBlock;
import Assembly.Operand.AsmReg;

public class AsmBrInst extends AsmInst {
    public String op;
    public AsmBlock block;
    public AsmBrInst(String op, AsmReg rs1, AsmReg rs2, AsmBlock block) {
        this.op = op;
        this.rs1 = rs1;
        this.rs2 = rs2;
        this.block = block;
    }
    @Override public void print(PrintStream out) {
        out.print(op + " ");
        rs1.print(out);
        out.print(", ");
        rs2.print(out);
        out.println(", " + block.name);
    }
}
