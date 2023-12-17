package AST;

import Util.*;

import java.util.ArrayList;

public class VarDefStmtNode extends StmtNode {
    public String type;
    public ArrayList<String> vars = new ArrayList<>();
    public ArrayList<ExprNode> vals = new ArrayList<>();

    public VarDefStmtNode(position pos, String type) {
        super(pos);
        this.type = type;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}