package AST;

import Util.*;

public class BracketExprNode extends ExprNode {
    public ExprNode obj, id;

    public BracketExprNode(position pos) {
        super(pos);
    }

    @Override public boolean isAssignable() {
        return true;
    }
    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
