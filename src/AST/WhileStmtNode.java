package AST;

import Util.*;

public class WhileStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode stmt;

    public WhileStmtNode(position pos, ExprNode condition, StmtNode stmt) {
        super(pos);
        this.condition = condition;
        this.stmt = stmt;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}