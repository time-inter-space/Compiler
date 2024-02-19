package Assembly.Instruction;

import java.io.PrintStream;

import Assembly.AsmBlock;

public class AsmJumpInst extends AsmInst {
    public AsmBlock block;
    public AsmJumpInst(AsmBlock block) {
        this.block = block;
    }
    @Override public void print(PrintStream out) {
        out.println("j " + block.name);
    }
}
