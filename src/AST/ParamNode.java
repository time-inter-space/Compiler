package AST;

import Util.*;

public class ParamNode extends ASTNode {
    public String type, varName;

    public ParamNode(position pos, String type, String varName) {
        super(pos);
        this.type = type;
        this.varName = varName;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
