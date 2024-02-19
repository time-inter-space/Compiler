package Assembly;

import java.io.PrintStream;
import java.util.LinkedList;

import Assembly.Instruction.*;

public class AsmBlock {
    public String name;
    public LinkedList<AsmInst> insts = new LinkedList<>();
    public AsmBlock(String name) {
        this.name = name;
    }
    public void push_back(AsmInst inst) {
        insts.addLast(inst);
    }
    public void push_front(AsmInst inst) {
        insts.addFirst(inst);
    }
    public void print(PrintStream out) {
        out.println(name + ":");
        for (AsmInst inst : insts) {
            out.print("    ");
            inst.print(out);
        }
    }
}
