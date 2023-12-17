package AST;

import Util.*;

import java.util.ArrayList;

public class ClassDefNode extends ASTNode {
    public String className;
    public ArrayList<VarDefStmtNode> varDefs = new ArrayList<>();
    public FnDefNode constructor = null;
    public ArrayList<FnDefNode> fns = new ArrayList<>();

    public ClassDefNode(position pos, String className) {
        super(pos);
        this.className = className;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}