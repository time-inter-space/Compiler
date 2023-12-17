package AST;

import Util.*;

import java.util.ArrayList;

public class FnExprNode extends ExprNode {
    public String fnName;
    public ArrayList<ExprNode> params = new ArrayList<>();

    public FnExprNode(position pos, String fnName) {
        super(pos);
        this.fnName = fnName;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
