package AST;

import Util.*;

import java.util.ArrayList;

public class BlockStmtNode extends StmtNode {
    public ArrayList<StmtNode> stmts = new ArrayList<>();

    public BlockStmtNode(position pos) {
        super(pos);
    }

    @Override public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}