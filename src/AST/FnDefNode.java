package AST;

import Util.*;

import java.util.ArrayList;

public class FnDefNode extends ASTNode {
    public String type;
    public String fnName;
    public ArrayList<ParamNode> params = new ArrayList<>();
    public ArrayList<StmtNode> stmts = new ArrayList<>();

    public FnDefNode(position pos, String type, String fnName) {
        super(pos);
        this.type = type;
        this.fnName = fnName;
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}