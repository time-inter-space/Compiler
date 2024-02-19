package Assembly;

import java.io.PrintStream;
import java.util.ArrayList;

public class AsmFunction {
    public String name;
    public ArrayList<AsmBlock> blocks = new ArrayList<>();
    public int paramSize = 0, allocaSize = 4, stackSize = 0;
    public AsmFunction(String name) {
        this.name = name;
    }
    public void print(PrintStream out) {
        out.println("    .globl " + name);
        out.println(name + ":");
        for (AsmBlock blk : blocks) blk.print(out);
    }
}
