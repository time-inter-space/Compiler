package Frontend;

import javax.lang.model.util.ElementScanner14;

import AST.*;
import Util.Scope;
import Util.Type;
import Util.error.semanticError;
import Util.globalScope;

public class SemanticChecker implements ASTVisitor {
    private Scope currentScope;
    private globalScope gScope;
    private Type currentClass = null;
    private FnDefNode currentFn = null;
    private int loopNum = 0;

    public SemanticChecker(globalScope gScope) {
        currentScope = this.gScope = gScope;
    }

    @Override public void visit(RootNode it) {
        boolean visMain = false;
        for (int i = 0, j = 0, k = 0; i < it.varDefs.size()
          || j < it.classDefs.size() || k < it.fnDefs.size();)
        {
            if (!visMain
              && (i >= it.varDefs.size() || it.mainFn.pos.before(it.varDefs.get(i).pos))
              && (j >= it.classDefs.size() || it.mainFn.pos.before(it.classDefs.get(j).pos))
              && (k >= it.fnDefs.size() || it.mainFn.pos.before(it.fnDefs.get(k).pos))) {
                it.mainFn.accept(this);
                visMain = true;
            }
            if (i < it.varDefs.size()
              && (j >= it.classDefs.size() || it.varDefs.get(i).pos.before(it.classDefs.get(j).pos))
              && (k >= it.fnDefs.size() || it.varDefs.get(i).pos.before(it.fnDefs.get(k).pos))) {
                it.varDefs.get(i).accept(this);
                i++;
            }
            else if (j < it.classDefs.size()
              && (k >= it.fnDefs.size() || it.classDefs.get(j).pos.before(it.fnDefs.get(k).pos))) {
                it.classDefs.get(j).accept(this);
                j++;
            }
            else {
                it.fnDefs.get(k).accept(this);
                k++;
            }
        }
        if (!visMain) it.mainFn.accept(this);
    }

    @Override public void visit(FnDefNode it) {
        if (currentClass != null && it.fnName.equals(currentClass.name) && !it.type.equals(""))
            throw new semanticError("constructor type error", it.pos);
        if (!it.type.equals("")) gScope.getTypeFromName(it.type, it.pos);
        currentScope = new Scope(currentScope, gScope);
        currentFn = it;
        it.params.forEach(param -> param.accept(this));
        for (StmtNode stmt : it.stmts)
            if (stmt != null) stmt.accept(this);
        currentScope = currentScope.parentScope();
        currentFn = null;
    }

    @Override public void visit(ClassDefNode it) {
        currentClass = new Type(gScope.getTypeFromName(it.className, it.pos));
        currentScope = new Scope(currentScope, gScope);
        it.varDefs.forEach(vd -> vd.accept(this));
        if (it.constructor != null) it.constructor.accept(this);
        it.fns.forEach(fn -> fn.accept(this));
        currentClass = null;
        currentScope = currentScope.parentScope();
    }

    @Override public void visit(ParamNode it) {
        currentScope.defineVariable(it.varName,
          new Type(gScope.getTypeFromName(it.type, it.pos)), it.pos);
    }

    @Override public void visit(FnExprNode it) {
        FnDefNode fn = null;
        if (currentClass != null) fn = currentClass.memberFns.get(it.fnName);
        if (fn == null) fn = gScope.getFnFromName(it.fnName, it.pos);
        else it.inClass = true;
        it.type = new Type(gScope.getTypeFromName(fn.type, fn.pos));
        if (fn.params.size() != it.params.size())
            throw new semanticError("number of parameters not match", it.pos);
        for (int i = 0; i < it.params.size(); i++) {
            ExprNode expr = it.params.get(i);
            expr.accept(this);
            Type type = gScope.getTypeFromName(fn.params.get(i).type, fn.pos);
            if ((type.primitive() || !expr.type.equals(new Type("null")))
              && !expr.type.equals(type))
                throw new semanticError("parameter type not match", expr.pos);
        }
    }

    @Override public void visit(VarDefStmtNode it) {
        Type tmpType = new Type(it.type);
        Type type = new Type(gScope.getTypeFromName(tmpType.name, it.pos));
        type.dim = tmpType.dim;
        for (int i = 0; i < it.vars.size(); i++) {
            String var = it.vars.get(i);
            ExprNode init = it.vals.get(i);
            if (currentClass != null && currentFn == null) {
                if (init != null)
                    throw new semanticError("Mx does not support default init of members",
                                init.pos);
            }
            if (init != null) {
                init.accept(this);
                if ((type.primitive() || !init.type.equals(new Type("null")))
                  && !type.equals(init.type))
                    throw new semanticError("type not match", init.pos);
            }
            currentScope.defineVariable(var, type, it.pos);
        }
    }

    @Override public void visit(BlockStmtNode it) {
        currentScope = new Scope(currentScope, gScope);
        for (StmtNode stmt : it.stmts) 
            if (stmt != null) stmt.accept(this);
        currentScope = currentScope.parentScope();
    }

    @Override public void visit(IfStmtNode it) {
        it.condition.accept(this);
        if (!it.condition.type.equals(new Type("bool")))
            throw new semanticError("condition type not match", it.condition.pos);
        if (it.thenStmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.thenStmt.accept(this);
            currentScope = currentScope.parentScope();
        }
        if (it.elseStmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.elseStmt.accept(this);
            currentScope = currentScope.parentScope();
        }
    }

    @Override public void visit(WhileStmtNode it) {
        it.condition.accept(this);
        if (!it.condition.type.equals(new Type("bool")))
            throw new semanticError("condition type not match", it.condition.pos);
        loopNum++;
        if (it.stmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.stmt.accept(this);
            currentScope = currentScope.parentScope();
        }
        loopNum--;
    }

    @Override public void visit(ForStmtNode it) {
        if (it.init != null) it.init.accept(this);
        if (it.cond != null) {
            it.cond.accept(this);
            if (!it.cond.type.equals(new Type("bool")))
                throw new semanticError("condition type not match", it.cond.pos);
        }
        if (it.step != null) it.step.accept(this);
        loopNum++;
        if (it.stmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.stmt.accept(this);
            currentScope = currentScope.parentScope();
        }
        loopNum--;
    }

    @Override public void visit(ReturnStmtNode it) {
        if (currentFn == null)
            throw new semanticError("return statement not in a function", it.pos);
        if (it.expr == null && !currentFn.type.equals("void") && !currentFn.type.equals(""))
            throw new semanticError("return type not match", it.pos);
        if (it.expr != null) {
            it.expr.accept(this);
            Type type = gScope.getTypeFromName(currentFn.type, currentFn.pos);
            if ((type.primitive() || !it.expr.type.equals(new Type("null")))
              && !it.expr.type.equals(type))
                throw new semanticError("return type not match", it.pos);
        }
    }

    @Override public void visit(BreakStmtNode it) {
        if (loopNum < 1)
            throw new semanticError("break statement not in a loop", it.pos);
    }

    @Override public void visit(ContinueStmtNode it) {
        if (loopNum < 1)
            throw new semanticError("continue statement not in a loop", it.pos);
    }

    @Override public void visit(ExprStmtNode it) {
        it.expr.accept(this);
    }

    @Override public void visit(DotExprNode it) {
        it.obj.accept(this);
        Type type = new Type(gScope.getTypeFromName(it.obj.type.name, it.obj.pos));
        if (it.var != null) {
            if (!type.memberVars.containsKey(it.var))
                throw new semanticError("member variable not match", it.pos);
            it.type = new Type(type.memberVars.get(it.var));
        }
        else if (it.fn.fnName.equals("size") && it.obj.type.dim > 0) {
            if (it.fn.params.size() > 0)
                throw new semanticError("number of parameters not match", it.fn.pos);
            it.type = it.fn.type = new Type(gScope.getTypeFromName("int", it.fn.pos));
        }
        else {
            if (it.obj.type.dim > 0 || !type.memberFns.containsKey(it.fn.fnName))
                throw new semanticError("member function not match", it.fn.pos);
            FnDefNode fn = type.memberFns.get(it.fn.fnName);
            if (it.fn.params.size() != fn.params.size())
                throw new semanticError("number of parameters not match", it.fn.pos);
            for (int i = 0; i < it.fn.params.size(); i++) {
                ExprNode expr = it.fn.params.get(i);
                expr.accept(this);
                if (!expr.type.equals(new Type(fn.params.get(i).type)))
                    throw new semanticError("parameter type not match", expr.pos);
            }
            it.type = it.fn.type = new Type(gScope.getTypeFromName(fn.type, fn.pos));
        }
    }

    @Override public void visit(BracketExprNode it) {
        it.obj.accept(this);
        if (it.obj.isNew())
            throw new semanticError("bracket expression after new expression", it.pos);
        if (it.obj.type.dim < 1)
            throw new semanticError("too many dimensions", it.pos);
        it.type = new Type(it.obj.type);
        it.type.dim--;
        it.id.accept(this);
        if (!it.id.type.equals(new Type("int")))
            throw new semanticError("index type not match", it.id.pos);
    }

    @Override public void visit(NewClassExprNode it) {
        it.type = new Type(gScope.getTypeFromName(it.typeName, it.pos));
    }

    @Override public void visit(AtomExprNode it) {
        if (it.iden != null) {
            it.type = new Type(currentScope.getType(it.iden, true, it.pos));
        }
        else if (it.num != null) {
            it.type = new Type("int");
        }
        else if (it.str != null) {
            it.type = new Type(gScope.getTypeFromName("string", it.pos));
        }
        else if (it.isFalse || it.isTrue) {
            it.type = new Type("bool");
        }
        else if (it.isThis) {
            if (currentClass == null)
                throw new semanticError("this not in a class", it.pos);
            it.type = new Type(currentClass);
        }
        else if (it.isNull) {
            it.type = new Type("null");
        }
    }

    @Override public void visit(BinaryExprNode it) {
        it.expr1.accept(this);
        it.expr2.accept(this);
        if (it.op == BinaryExprNode.Operator.MUL
         || it.op == BinaryExprNode.Operator.DIV
         || it.op == BinaryExprNode.Operator.MOD
         || it.op == BinaryExprNode.Operator.SUB
         || it.op == BinaryExprNode.Operator.LSH
         || it.op == BinaryExprNode.Operator.RSH
         || it.op == BinaryExprNode.Operator.AND
         || it.op == BinaryExprNode.Operator.XOR
         || it.op == BinaryExprNode.Operator.OR) {
            if (!it.expr1.type.equals(new Type("int")) || !it.expr2.type.equals(it.expr1.type))
                throw new semanticError("expression type not match", it.pos);
            it.type = new Type(it.expr1.type);
        }
        else if (it.op == BinaryExprNode.Operator.ADD
              || it.op == BinaryExprNode.Operator.L
              || it.op == BinaryExprNode.Operator.LEQ
              || it.op == BinaryExprNode.Operator.G
              || it.op == BinaryExprNode.Operator.GEQ) {
            if (!it.expr1.type.equals(it.expr2.type))
                throw new semanticError("expression type not match", it.pos);
            if (!it.expr1.type.equals(new Type("int"))
              && !it.expr1.type.equals(gScope.getTypeFromName("string", it.pos)))
                throw new semanticError("expression type not match", it.pos);
            if (it.op == BinaryExprNode.Operator.ADD) it.type = it.expr1.type;
            else it.type = new Type("bool");
        }
        else if (it.op == BinaryExprNode.Operator.EQ
              || it.op == BinaryExprNode.Operator.NEQ) {
            if (!it.expr1.type.equals(it.expr2.type)
              && !(it.expr1.type.equals(new Type("null")) && !it.expr2.type.primitive())
              && !(it.expr2.type.equals(new Type("null")) && !it.expr1.type.primitive()))
                throw new semanticError("expression type not match", it.pos);
            it.type = new Type("bool");
        }
        else {
            if (!it.expr1.type.equals(new Type("bool")) || !it.expr2.type.equals(it.expr1.type))
                throw new semanticError("expression type not match", it.pos);
            it.type = new Type("bool");
        }
    }

    @Override public void visit(PreExprNode it) {
        it.expr.accept(this);
        if (it.op == PreExprNode.Operator.NOT) {
            if (!it.expr.type.equals(new Type("bool")))
                throw new semanticError("expression type not match", it.pos);
            it.type = new Type(it.expr.type);
        }
        else {
            if ((it.op == PreExprNode.Operator.MM || it.op == PreExprNode.Operator.PP)
              && !it.expr.isAssignable())
                throw new semanticError("not a lvalue", it.expr.pos);
            if (!it.expr.type.equals(new Type("int")))
                throw new semanticError("expression type not match", it.pos);
            it.type = new Type(it.expr.type);
        }
    }

    @Override public void visit(PostExprNode it) {
        it.expr.accept(this);
        if (!it.expr.isAssignable())
            throw new semanticError("not a lvalue", it.expr.pos);
        if (!it.expr.type.equals(new Type("int")))
            throw new semanticError("expression type not match", it.pos);
        it.type = new Type(it.expr.type);
    }

    @Override public void visit(ParenExprNode it) {
        it.expr.accept(this);
        it.type = new Type(it.expr.type);
    }

    @Override public void visit(TernaryExprNode it) {
        it.expr1.accept(this);
        it.expr2.accept(this);
        it.expr3.accept(this);
        if (!it.expr1.type.equals(new Type("bool")))
            throw new semanticError("condition type not match", it.expr1.pos);
        if (!it.expr2.type.equals(it.expr3.type)
          && !(it.expr2.type.equals(new Type("null")) && !it.expr3.type.primitive())
          && !(it.expr3.type.equals(new Type("null")) && !it.expr2.type.primitive()))
            throw new semanticError("expression type not match", it.expr2.pos);
        if (it.expr2.type.equals(new Type("null"))) it.type = new Type(it.expr3.type);
        else it.type = new Type(it.expr2.type);
    }

    @Override public void visit(AssignExprNode it) {
        it.expr1.accept(this);
        it.expr2.accept(this);
        if (!it.expr1.isAssignable())
            throw new semanticError("not a lvalue", it.expr1.pos);
        if ((it.expr1.type.primitive() || !it.expr2.type.equals(new Type("null")))
          && !it.expr1.type.equals(it.expr2.type))
            throw new semanticError("expression type not match", it.expr2.pos);
        it.type = new Type(it.expr1.type);
    }
    
    @Override public void visit(NewArrayExprNode it) {
        for (ExprNode expr : it.sizes) {
            if (expr == null) continue;
            expr.accept(this);
            if (!expr.type.equals(new Type("int")))
                throw new semanticError("expression type not match", expr.pos);
        }
        it.type = new Type(gScope.getTypeFromName(it.typeName, it.pos));
        it.type.dim = it.sizes.size();
    }
}