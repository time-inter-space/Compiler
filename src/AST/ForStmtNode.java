package AST;

import Util.position;

public class ForStmtNode extends StmtNode {
    public StmtNode init, stmt;
    public ExprNode cond, step;

    public ForStmtNode(position pos, StmtNode init, ExprNode cond, ExprNode step, StmtNode stmt) {
        super(pos);
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.stmt = stmt;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}