package Frontend;

import AST.*;
import Util.Type;
import Util.globalScope;
import Util.error.semanticError;

import java.util.HashMap;

public class SymbolCollector implements ASTVisitor {
    private globalScope gScope;
    public SymbolCollector(globalScope gScope) {
        this.gScope = gScope;
    }

    @Override public void visit(RootNode it) {
        it.fnDefs.forEach(fd -> fd.accept(this));
        it.classDefs.forEach(cd -> cd.accept(this));
    }
    @Override public void visit(FnDefNode it) {
        gScope.addFn(it.fnName, it, it.pos);
    }
    @Override public void visit(ClassDefNode it) {
        Type cls = new Type(it.className);
        cls.memberVars = new HashMap<>();
        for (VarDefStmtNode vd : it.varDefs) {
            vd.vars.forEach(var -> cls.addVar(var, vd.type, vd.pos));
        }
        if (it.constructor != null) cls.hasConstructor = true;
        it.fns.forEach(fd -> cls.addFn(fd.fnName, fd, fd.pos));
        gScope.addType(it.className, cls, it.pos);
    }
    
    @Override public void visit(VarDefStmtNode it) {}
    @Override public void visit(ParamNode it) {}
    @Override public void visit(FnExprNode it) {}
    @Override public void visit(BlockStmtNode it) {}
    @Override public void visit(IfStmtNode it) {}
    @Override public void visit(WhileStmtNode it) {}
    @Override public void visit(ForStmtNode it) {}
    @Override public void visit(ReturnStmtNode it) {}
    @Override public void visit(BreakStmtNode it) {}
    @Override public void visit(ContinueStmtNode it) {}
    @Override public void visit(ExprStmtNode it) {}
    @Override public void visit(DotExprNode it) {}
    @Override public void visit(BracketExprNode it) {}
    @Override public void visit(NewClassExprNode it) {}
    @Override public void visit(AtomExprNode it) {}
    @Override public void visit(BinaryExprNode it) {}
    @Override public void visit(PreExprNode it) {}
    @Override public void visit(PostExprNode it) {}
    @Override public void visit(ParenExprNode it) {}
    @Override public void visit(CommaExprNode it) {}
    @Override public void visit(TernaryExprNode it) {}
    @Override public void visit(AssignExprNode it) {}
    @Override public void visit(NewArrayExprNode it) {}
}