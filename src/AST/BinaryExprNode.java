package AST;

import Util.*;

public class BinaryExprNode extends ExprNode {
    public ExprNode expr1, expr2;
    public enum Operator {
        MUL, DIV, MOD,
        ADD, SUB,
        LSH, RSH,
        L, LEQ, G, GEQ,
        EQ, NEQ,
        AND,
        XOR,
        OR,
        ANDAND,
        OROR;
    }
    public Operator op;

    public BinaryExprNode(position pos, ExprNode expr1, ExprNode expr2) {
        super(pos);
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
