package Assembly.Instruction;

import java.io.PrintStream;

public class AsmRetInst extends AsmInst {
    @Override public void print(PrintStream out) {
        out.println("ret");
    }
}
