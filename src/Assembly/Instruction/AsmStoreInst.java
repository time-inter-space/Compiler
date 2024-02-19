package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.Operand.*;

public class AsmStoreInst extends AsmInst {
    public AsmStoreInst(AsmReg rs2, AsmReg rs1, AsmImm imm) {
        this.rs2 = rs2;
        this.rs1 = rs1;
        this.imm = imm;
    }
    @Override public void print(PrintStream out) {
        out.print("sw ");
        rs2.print(out);
        out.print(", ");
        imm.print(out);
        out.print("(");
        rs1.print(out);
        out.println(")");
    }
}
