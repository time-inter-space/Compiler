package AST;

import Util.*;

public class ExprStmtNode extends StmtNode {
    public ExprNode expr;

    public ExprStmtNode(position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}