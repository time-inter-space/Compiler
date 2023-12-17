package AST;

import Util.*;

public class NewClassExprNode extends ExprNode {
    public String typeName;

    public NewClassExprNode(position pos, String typeName) {
        super(pos);
        this.typeName = typeName;
    }

    @Override public boolean isNew() {
        return true;
    }
    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
