package AST;

import Util.*;

public class DotExprNode extends ExprNode {
    public ExprNode obj;
    public String var = null;
    public FnExprNode fn = null;

    public DotExprNode(position pos, ExprNode obj) {
        super(pos);
        this.obj = obj;
    }

    @Override public boolean isAssignable() {
        return this.var != null;
    }
    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
