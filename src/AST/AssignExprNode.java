package AST;

import Util.*;

public class AssignExprNode extends ExprNode {
    public ExprNode expr1, expr2;

    public AssignExprNode(position pos, ExprNode expr1, ExprNode expr2) {
        super(pos);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
