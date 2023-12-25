package AST;

import Util.*;
import MIR.Entity.*;

public abstract class ExprNode extends ASTNode {
    public Type type;
    public entity val = null;
    public entity addr = null;

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