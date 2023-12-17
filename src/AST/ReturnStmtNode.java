package AST;

import Util.*;

public class ReturnStmtNode extends StmtNode {
    public ExprNode expr;

    public ReturnStmtNode(position pos, ExprNode expr) {
        super(pos);
        this.expr = expr;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}