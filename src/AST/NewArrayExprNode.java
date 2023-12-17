package AST;

import Util.*;

import java.util.ArrayList;

public class NewArrayExprNode extends ExprNode {
    public String typeName;
    public ArrayList<ExprNode> sizes = new ArrayList<>();

    public NewArrayExprNode(position pos, String type) {
        super(pos);
        this.typeName = type;
    }

    @Override public boolean isNew() {
        return true;
    }
    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
