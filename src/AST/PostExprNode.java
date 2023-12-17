package AST;

import Util.*;

public class PostExprNode extends ExprNode {
    public ExprNode expr;
    public enum Operator {
        PP, MM;
    }
    public Operator op;

    public PostExprNode(position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}