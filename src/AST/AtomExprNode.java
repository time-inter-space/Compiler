package AST;

import Util.*;

public class AtomExprNode extends ExprNode {
    public String iden = null, num = null, str = null;
    public boolean isTrue = false, isFalse = false, isNull = false, isThis = false;

    public AtomExprNode(position pos) {
        super(pos);
    }

    @Override public boolean isAssignable() {
        return this.iden != null;
    }
    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
