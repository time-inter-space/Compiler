package AST;

import Util.*;

public class TernaryExprNode extends ExprNode {
    public ExprNode expr1, expr2, expr3;

    public TernaryExprNode(position pos, ExprNode expr1, ExprNode expr2, ExprNode expr3) {
        super(pos);
        this.expr1 = expr1;
        this.expr2 = expr2;
        this.expr3 = expr3;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
