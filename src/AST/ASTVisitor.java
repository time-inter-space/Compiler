package AST;

public interface ASTVisitor {
    void visit(RootNode it);
    void visit(FnDefNode it);
    void visit(ClassDefNode it);
    void visit(ParamNode it);
    void visit(FnExprNode it);
    void visit(VarDefStmtNode it);
    void visit(BlockStmtNode it);
    void visit(IfStmtNode it);
    void visit(WhileStmtNode it);
    void visit(ForStmtNode it);
    void visit(ReturnStmtNode it);
    void visit(BreakStmtNode it);
    void visit(ContinueStmtNode it);
    void visit(ExprStmtNode it);
    void visit(DotExprNode it);
    void visit(BracketExprNode it);
    void visit(NewClassExprNode it);
    void visit(AtomExprNode it);
    void visit(BinaryExprNode it);
    void visit(PreExprNode it);
    void visit(PostExprNode it);
    void visit(ParenExprNode it);
    void visit(TernaryExprNode it);
    void visit(AssignExprNode it);
    void visit(NewArrayExprNode it);
}