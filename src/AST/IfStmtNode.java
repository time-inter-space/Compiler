package AST;

import Util.*;

public class IfStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode thenStmt, elseStmt;

    public IfStmtNode(position pos, ExprNode condition, StmtNode thenStmt, StmtNode elseStmt) {
        super(pos);
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}