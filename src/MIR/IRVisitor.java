package MIR;

import MIR.Instruction.*;

public interface IRVisitor {
    public void visit(root it);
    public void visit(function it);
    public void visit(block it);
    public void visit(alloca it);
    public void visit(binary it);
    public void visit(br it);
    public void visit(call it);
    public void visit(getelementptr it);
    public void visit(icmp it);
    public void visit(jump it);
    public void visit(load it);
    public void visit(ret it);
    public void visit(select it);
    public void visit(store it);
}
