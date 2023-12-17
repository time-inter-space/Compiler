package AST;

import Util.*;

public class PreExprNode extends ExprNode {
    public ExprNode expr;
    public enum Operator {
        PP, MM, NOT, REV, NEG;
    }
    public Operator op;

    public PreExprNode(position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override public boolean isAssignable() {
        return op == Operator.PP || op == Operator.MM;
    }
    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
