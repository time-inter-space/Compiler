package AST;

import Util.*;

public abstract class ExprNode extends ASTNode {
    public Type type;

    public ExprNode(position pos) {
        super(pos);
    }

    public boolean isAssignable() {
        return false;
    }

    public boolean isNew() {
        return false;
    }
}