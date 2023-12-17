package AST;

import Util.*;

public class ParenExprNode extends ExprNode {
    public ExprNode expr;

    public ParenExprNode(position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override public boolean isAssignable() {
        return expr.isAssignable();
    }
    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
