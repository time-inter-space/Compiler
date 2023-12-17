package AST;

import Util.*;

import java.util.ArrayList;

public class RootNode extends ASTNode {
    public FnDefNode mainFn = null;
    public ArrayList<ClassDefNode> classDefs = new ArrayList<>();
    public ArrayList<FnDefNode> fnDefs = new ArrayList<>();
    public ArrayList<VarDefStmtNode> varDefs = new ArrayList<>();

    public RootNode(position pos) {
        super(pos);
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}