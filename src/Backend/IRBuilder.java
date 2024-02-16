package Backend;

import AST.*;
import MIR.*;
import MIR.Entity.*;
import MIR.Instruction.*;
import Util.Scope;
import Util.globalScope;
import Util.Type;
import java.util.Stack;
import java.util.HashMap;

public class IRBuilder implements ASTVisitor {
    private block currentBlock;
    private root rt;
    private function init;
    private Scope currentScope;
    private globalScope gScope;
    private classDef currentClass = null;
    private Type currentClassType = null;
    private function currentFn = null;
    private ExprNode currentObj = null;
    private Stack<block> afterLoopBlock = new Stack<>();
    private Stack<block> nextLoopBlock = new Stack<>();
    private int regCnt = 0, strConstCnt = 0;
    private HashMap<String, Integer> varCnt = new HashMap<>();

    private int getVarCnt(String name) {
        if (!varCnt.containsKey(name)) {
            varCnt.put(name, 0);
            return 0;
        }
        int num = varCnt.get(name) + 1;
        varCnt.replace(name, num);
        return num;
    }

    public IRBuilder(root rt, function init, globalScope gScope) {
        this.rt = rt;
        this.init = init;
        currentScope = gScope;
        this.gScope = gScope;
    }

    @Override public void visit(RootNode it) {
        it.classDefs.forEach(cd -> gScope.irTypes.put(cd.className, new classDef(cd.className)));
        it.classDefs.forEach(cd -> cd.accept(this));
        currentFn = init;
        currentBlock = new block();
        it.varDefs.forEach(vd -> vd.accept(this));
        currentBlock.push_back(new ret(init.type, new voidConst()));
        init.blocks.add(currentBlock);
        currentFn = null;
        it.fnDefs.forEach(fd -> fd.accept(this));
        it.mainFn.accept(this);
    }

    @Override public void visit(FnDefNode it) {
        currentScope = new Scope(currentScope, gScope);
        currentFn = new function(gScope.getTypeFromName(it.type, null),
                                 currentClass, it.fnName);
        currentBlock = new block();
        if (currentClass != null) {
            localVar var = new localVar(currentClassType, "this." + getVarCnt("this"));
            currentScope.entities.put("this", var);
            currentFn.params.add(var);
            AtomExprNode thisExpr = new AtomExprNode(null);
            thisExpr.isThis = true;
            thisExpr.type = currentClassType;
            thisExpr.val = var;
            currentObj = thisExpr;
        }
        it.params.forEach(param -> param.accept(this));
        FnExprNode initFn = new FnExprNode(null, "_init");
        initFn.type = new Type("void");
        if (it.fnName.equals("main")) currentBlock.stmts.push(new call(null, null, initFn));
        for (StmtNode stmt : it.stmts)
            if (stmt != null) stmt.accept(this);
        IRType type = currentFn.type;
        if (type.IRName.equals("i32") || type.IRName.equals("i1"))
            currentBlock.push_back(new ret(type, new intConst("0")));
        else if (type.IRName.equals("void"))
            currentBlock.push_back(new ret(type, new voidConst()));
        else currentBlock.push_back(new ret(type, new nullConst()));
        currentFn.blocks.add(currentBlock);
        rt.fns.add(currentFn);
        currentFn = null;
        currentObj = null;
        currentScope = currentScope.parentScope();
    }

    @Override public void visit(ClassDefNode it) {
        currentScope = new Scope(currentScope, gScope);
        currentScope.isClass = true;
        currentClass = gScope.getIRClass(it.className);
        currentClassType = gScope.getTypeFromName(it.className, null);
        it.varDefs.forEach(vd -> vd.accept(this));
        if (it.constructor != null) {
            it.constructor.type = "void";
            it.constructor.accept(this);
        }
        it.fns.forEach(f -> f.accept(this));
        rt.cls.add(currentClass);
        currentClass = null;
        currentClassType = null;
        currentScope = currentScope.parentScope();
    }

    @Override public void visit(ParamNode it) {
        String name = it.varName;
        Type type = gScope.getTypeFromName(it.type, null);
        localVar var = new localVar(type, name + '.' + getVarCnt(name));
        currentFn.params.add(var);
        localVar tmpVar = new localVar(type, name + '.' + getVarCnt(name));
        currentBlock.push_back(new alloca(tmpVar));
        currentBlock.push_back(new store(type, tmpVar, var));
        currentScope.entities.put(name, tmpVar);
    }

    @Override public void visit(FnExprNode it) {
        it.params.forEach(expr -> expr.accept(this));
        register reg;
        if (it.type.isVoid()) reg = null;
        else reg = new register(it.type, regCnt++);
        it.val = reg;
        ExprNode dotObj = null;
        if (it.inClass) dotObj = currentObj;
        currentBlock.push_back(new call(reg, dotObj, it));
    }

    @Override public void visit(VarDefStmtNode it) {
        Type type = new Type(it.type);
        for (int i = 0; i < it.vars.size(); i++) {
            String name = it.vars.get(i);
            ExprNode expr = it.vals.get(i);
            if (currentFn != init && currentFn != null) {
                localVar var = new localVar(type, name + '.' + getVarCnt(name));
                currentScope.entities.put(name, var);
                currentBlock.push_back(new alloca(var));
                if (expr != null) {
                    expr.accept(this);
                    currentBlock.push_back(new store(type, var, expr.val));
                }
            }
            else if (currentClass != null) currentClass.addVar(type, name);
            else {
                globalVar var = new globalVar(type, name + '.' + getVarCnt(name));
                gScope.entities.put(name, var);
                if (expr != null) {
                    expr.accept(this);
                    currentBlock.push_back(new store(type, var, expr.val));
                }
                rt.vars.add(var);
            }
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
        block trueBlock = new block(), falseBlock = new block();
        block destination = new block();
        currentBlock.push_back(new br(it.condition.val, trueBlock, falseBlock));
        currentFn.blocks.add(currentBlock);

        currentBlock = trueBlock;
        if (it.thenStmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.thenStmt.accept(this);
            currentScope = currentScope.parentScope();
        }
        currentBlock.push_back(new jump(destination));
        currentFn.blocks.add(currentBlock);

        currentBlock = falseBlock;
        if (it.elseStmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.elseStmt.accept(this);
            currentScope = currentScope.parentScope();
        }
        currentBlock.push_back(new jump(destination));
        currentFn.blocks.add(currentBlock);

        currentBlock = destination;
    }

    @Override public void visit(WhileStmtNode it) {
        block conditionBlock = new block();
        currentBlock.push_back(new jump(conditionBlock));
        currentFn.blocks.add(currentBlock);
        
        currentBlock = conditionBlock;
        it.condition.accept(this);
        block whileBlock = new block(), destination = new block();
        currentBlock.push_back(new br(it.condition.val, whileBlock, destination));
        currentFn.blocks.add(currentBlock);
        
        currentBlock = whileBlock;
        afterLoopBlock.add(destination);
        nextLoopBlock.add(conditionBlock);
        if (it.stmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.stmt.accept(this);
            currentScope = currentScope.parentScope();
        }
        afterLoopBlock.pop();
        nextLoopBlock.pop();
        currentBlock.push_back(new jump(conditionBlock));
        currentFn.blocks.add(currentBlock);

        currentBlock = destination;
    }

    @Override public void visit(ForStmtNode it) {
        if (it.init != null) it.init.accept(this);
        block conditionBlock = new block();
        currentBlock.push_back(new jump(conditionBlock));
        currentFn.blocks.add(currentBlock);

        currentBlock = conditionBlock;
        block forBlock = new block(), destination = new block();
        if (it.cond != null) {
            it.cond.accept(this);
            currentBlock.push_back(new br(it.cond.val, forBlock, destination));
        }
        else currentBlock.push_back(new jump(forBlock));
        currentFn.blocks.add(currentBlock);

        block stepBlock = new block();
        currentBlock = stepBlock;
        if (it.step != null) it.step.accept(this);
        currentBlock.push_back(new jump(conditionBlock));
        currentFn.blocks.add(currentBlock);

        currentBlock = forBlock;
        afterLoopBlock.add(destination);
        nextLoopBlock.add(stepBlock);
        if (it.stmt != null) {
            currentScope = new Scope(currentScope, gScope);
            it.stmt.accept(this);
            currentScope = currentScope.parentScope();
        }
        afterLoopBlock.pop();
        nextLoopBlock.add(stepBlock);
        currentBlock.push_back(new jump(stepBlock));
        currentFn.blocks.add(currentBlock);

        currentBlock = destination;
    }

    @Override public void visit(ReturnStmtNode it) {
        if (it.expr != null) {
            it.expr.accept(this);
            currentBlock.push_back(new ret(new IRType(it.expr.type), it.expr.val));
        }
        else currentBlock.push_back(new ret(
            new IRType(gScope.getTypeFromName("void", null)), new voidConst()));
        currentFn.blocks.add(currentBlock);
        currentBlock = new block();
    }

    @Override public void visit(BreakStmtNode it) {
        currentBlock.push_back(new jump(afterLoopBlock.peek()));
        currentFn.blocks.add(currentBlock);
        currentBlock = new block();
    }

    @Override public void visit(ContinueStmtNode it) {
        currentBlock.push_back(new jump(nextLoopBlock.peek()));
        currentFn.blocks.add(currentBlock);
        currentBlock = new block();
    }

    @Override public void visit(ExprStmtNode it) {
        it.expr.accept(this);
    }

    @Override public void visit(DotExprNode it) {
        it.obj.accept(this);
        if (it.var != null) {
            register ptr = new register(it.type, regCnt++);
            intConst num = new intConst(String.valueOf(
                gScope.getIRClass(it.obj.type.name).varNum.get(it.var)));
            currentBlock.push_back(new getelementptr(ptr, it.obj.type, it.obj.val, num, true));
            it.addr = ptr;
            register reg = new register(it.type, regCnt++);
            currentBlock.push_back(new load(it.type, ptr, reg));
            it.val = reg;
        }
        else if (it.fn.fnName.equals("size") && it.obj.type.dim > 0) {
            register reg = new register(it.fn.type, regCnt++);
            it.val = it.fn.val = reg;
            currentBlock.push_back(new call(reg, it.obj, it.fn));
        }
        else {
            it.fn.params.forEach(expr -> expr.accept(this));
            register reg;
            if (it.type.isVoid()) reg = null;
            else reg = new register(it.type, regCnt++);
            it.val = it.fn.val = reg;
            currentBlock.push_back(new call(reg, it.obj, it.fn));
        }
    }
    
    @Override public void visit(BracketExprNode it) {
        it.obj.accept(this);
        it.id.accept(this);
        register ptr = new register(it.type, regCnt++);
        currentBlock.push_back(new getelementptr(ptr, it.obj.type, it.obj.val, it.id.val, false));
        it.addr = ptr;
        register reg = new register(it.type, regCnt++);
        currentBlock.push_back(new load(it.type, ptr, reg));
        it.val = reg;
    }

    @Override public void visit(NewClassExprNode it) {
        register reg = new register(it.type, regCnt++);
        it.val = reg;
        FnExprNode expr = new FnExprNode(null, "builtin.new.var");
        expr.type = new Type(it.type);
        String irSize = Integer.toString(gScope.irSizes.get(it.typeName));
        AtomExprNode atomExpr = new AtomExprNode(null);
        atomExpr.num = irSize;
        atomExpr.val = new intConst(irSize);
        expr.params.add(atomExpr);
        currentBlock.push_back(new call(reg, null, expr));
        if (it.type.hasConstructor) {
            FnExprNode _expr = new FnExprNode(null, it.typeName);
            _expr.type = new Type("void");
            currentBlock.push_back(new call(null, it, _expr));
        }
    }

    @Override public void visit(AtomExprNode it) {
        if (it.iden != null) {
            it.addr = currentScope.getEntity(it.iden, true);
            if (it.addr == null) {
                entity obj = currentScope.getEntity("this", true);
                register reg = new register(it.type, regCnt++);
                intConst num = new intConst(String.valueOf(
                    currentClass.varNum.get(it.iden)));
                currentBlock.push_back(new getelementptr(
                    reg, currentClassType, obj, num, true));
                it.addr = reg;
            }
            register reg = new register(it.type, regCnt++);
            it.val = reg;
            currentBlock.push_back(new load(it.type, it.addr, reg));
        }
        else if (it.num != null) it.val = new intConst(it.num);
        else if (it.str != null) {
            globalVar strConst = new globalVar(it.type, it.str, strConstCnt++);
            rt.vars.add(strConst);
            it.val = strConst;
        }
        else if (it.isTrue) it.val = new intConst("1");
        else if (it.isFalse) it.val = new intConst("0");
        else if (it.isNull) it.val = new nullConst();
        else it.val = currentScope.getEntity("this", true);
    }
    
    @Override public void visit(BinaryExprNode it) {
        Type strType = gScope.getTypeFromName("string", null);
        if (it.op == BinaryExprNode.Operator.ANDAND
         || it.op == BinaryExprNode.Operator.OROR) {
            block block2 = new block(), destination = new block();
            it.expr1.accept(this);
            entity cond = it.expr1.val;
            if (it.op == BinaryExprNode.Operator.OROR) {
                cond = new register(it.expr1.type, regCnt++);
                currentBlock.push_back(new binary(it.type, (register)cond,
                    it.expr1.val, new intConst("1"), "xor"));
            }
            currentBlock.push_back(new br(cond, block2, destination));
            currentFn.blocks.add(currentBlock);

            currentBlock = block2;
            it.expr2.accept(this);
            currentBlock.push_back(new jump(destination));
            currentFn.blocks.add(currentBlock);

            currentBlock = destination;
            String op;
            if (it.op == BinaryExprNode.Operator.ANDAND) op = "and";
            else op = "or";
            register reg = new register(it.type, regCnt++);
            it.val = reg;
            currentBlock.push_back(new binary(it.type, reg, it.expr1.val, it.expr2.val, op));
        }
        else if (it.expr1.type.equals(strType)) {
            it.expr1.accept(this);
            it.expr2.accept(this);
            register reg = new register(it.type, regCnt++);
            it.val = reg;
            String fnName;
            if (it.op == BinaryExprNode.Operator.ADD) fnName = "add";
            else if (it.op == BinaryExprNode.Operator.EQ) fnName = "eq";
            else if (it.op == BinaryExprNode.Operator.NEQ) fnName = "ne";
            else if (it.op == BinaryExprNode.Operator.L) fnName = "lt";
            else if (it.op == BinaryExprNode.Operator.LEQ) fnName = "le";
            else if (it.op == BinaryExprNode.Operator.G) fnName = "gt";
            else fnName = "ge";
            FnExprNode expr = new FnExprNode(null, fnName);
            expr.type = it.type;
            expr.params.add(it.expr2);
            currentBlock.push_back(new call(reg, it.expr1, expr));
        }
        else if (it.op == BinaryExprNode.Operator.EQ
              || it.op == BinaryExprNode.Operator.NEQ
              || it.op == BinaryExprNode.Operator.L
              || it.op == BinaryExprNode.Operator.LEQ
              || it.op == BinaryExprNode.Operator.G
              || it.op == BinaryExprNode.Operator.GEQ) {
            it.expr1.accept(this);
            it.expr2.accept(this);
            register reg = new register(it.type, regCnt++);
            it.val = reg;
            String op;
            if (it.op == BinaryExprNode.Operator.EQ) op = "eq";
            else if (it.op == BinaryExprNode.Operator.NEQ) op = "ne";
            else if (it.op == BinaryExprNode.Operator.L) op = "slt";
            else if (it.op == BinaryExprNode.Operator.LEQ) op = "sle";
            else if (it.op == BinaryExprNode.Operator.G) op = "sgt";
            else op = "sge";
            currentBlock.push_back(new icmp(it.expr1.type, reg, it.expr1.val, it.expr2.val, op));
        }
        else {
            it.expr1.accept(this);
            it.expr2.accept(this);
            register reg = new register(it.type, regCnt++);
            it.val = reg;
            String op;
            if (it.op == BinaryExprNode.Operator.ADD) op = "add";
            else if (it.op == BinaryExprNode.Operator.SUB) op = "sub";
            else if (it.op == BinaryExprNode.Operator.MUL) op = "mul";
            else if (it.op == BinaryExprNode.Operator.DIV) op = "sdiv";
            else if (it.op == BinaryExprNode.Operator.MOD) op = "srem";
            else if (it.op == BinaryExprNode.Operator.LSH) op = "shl";
            else if (it.op == BinaryExprNode.Operator.RSH) op = "ashr";
            else if (it.op == BinaryExprNode.Operator.AND) op = "and";
            else if (it.op == BinaryExprNode.Operator.OR) op = "or";
            else op = "xor";
            currentBlock.push_back(new binary(it.type, reg, it.expr1.val, it.expr2.val, op));
        }
    }

    @Override public void visit(PreExprNode it) {
        it.expr.accept(this);
        if (it.op == PreExprNode.Operator.PP
         || it.op == PreExprNode.Operator.MM) {
            it.addr = it.expr.addr;
            register reg = new register(it.type, regCnt++);
            it.val = reg;
            String op;
            if (it.op == PreExprNode.Operator.PP) op = "add";
            else op = "sub";
            currentBlock.push_back(new binary(
                it.type, reg, it.expr.val, new intConst("1"), op));
            currentBlock.push_back(new store(it.type, it.addr, reg));
        }
        else {
            register reg = new register(it.type, regCnt++);
            it.val = reg;
            if (it.op == PreExprNode.Operator.NOT)
                currentBlock.push_back(new binary(
                    it.type, reg, it.expr.val, new intConst("1"), "xor"));
            else if (it.op == PreExprNode.Operator.REV)
                currentBlock.push_back(new binary(
                    it.type, reg, it.expr.val, new intConst("-1"), "xor"));
            else currentBlock.push_back(new binary(
                it.type, reg, new intConst("0"), it.expr.val, "sub"));
        }
    }

    @Override public void visit(PostExprNode it) {
        it.expr.accept(this);
        it.val = it.expr.val;
        register reg = new register(it.type, regCnt++);
        String op;
        if (it.op == PostExprNode.Operator.PP) op = "add";
        else op = "sub";
        currentBlock.push_back(new binary(
            it.type, reg, it.expr.val, new intConst("1"), op));
        currentBlock.push_back(new store(it.type, it.expr.addr, reg));
    }

    @Override public void visit(ParenExprNode it) {
        it.expr.accept(this);
        it.val = it.expr.val;
        it.addr = it.expr.addr;
    }

    @Override public void visit(TernaryExprNode it) {
        it.expr1.accept(this);
        block block2 = new block(), block3 = new block();
        block destination = new block();
        currentBlock.push_back(new br(it.expr1.val, block2, block3));
        currentFn.blocks.add(currentBlock);

        currentBlock = block2;
        it.expr2.accept(this);
        currentBlock.push_back(new jump(destination));
        currentFn.blocks.add(currentBlock);

        currentBlock = block3;
        it.expr3.accept(this);
        currentBlock.push_back(new jump(destination));
        currentFn.blocks.add(currentBlock);

        currentBlock = destination;
        register reg;
        if (it.type.isVoid()) reg = null;
        else {
            reg = new register(it.type, regCnt++);
            currentBlock.push_back(new select(
                it.type, reg, it.expr1.val, it.expr2.val, it.expr3.val));
        }
        it.val = reg;
    }

    @Override public void visit(AssignExprNode it) {
        it.expr1.accept(this);
        it.expr2.accept(this);
        currentBlock.push_back(new store(it.type, it.expr1.addr, it.expr2.val));
    }

    void newExprInit(NewArrayExprNode it, int id, block destination, register ptr) {
        Type type = new Type(it.type);
        type.dim = it.sizes.size() - id - 1;
        ExprNode expr = it.sizes.get(id);
        localVar var = new localVar(expr.type, "i." + getVarCnt("i"));
        currentBlock.push_back(new alloca(var));
        currentBlock.push_back(new store(expr.type, var, expr.val));
        block condBlock = new block(), loopBlock = new block();
        currentBlock.push_back(new jump(condBlock));
        currentFn.blocks.add(currentBlock);

        currentBlock = condBlock;
        Type boolType = gScope.getTypeFromName("bool", null);
        register cond = new register(boolType, regCnt++);
        register reg = new register(expr.type, regCnt++);
        currentBlock.push_back(new load(expr.type, var, reg));
        currentBlock.push_back(new icmp(
            expr.type, cond, reg, new intConst("0"), "sgt"));
        currentBlock.push_back(new br(cond, loopBlock, destination));
        currentFn.blocks.add(currentBlock);

        currentBlock = loopBlock;
        register reg1 = new register(expr.type, regCnt++);
        register reg2 = new register(expr.type, regCnt++);
        currentBlock.push_back(new load(expr.type, var, reg1));
        currentBlock.push_back(new binary(
            expr.type, reg2, reg1, new intConst("1"), "sub"));
        currentBlock.push_back(new store(expr.type, var, reg2));
        register subPtr = new register(type, regCnt++);
        type.dim++;
        currentBlock.push_back(new getelementptr(subPtr, type, ptr, reg2, false));
        if (id + 1 < it.sizes.size() && it.sizes.get(id + 1) != null) {
            FnExprNode _expr = new FnExprNode(null, "builtin.new.array");
            _expr.params.add(it.sizes.get(id + 1));
            _expr.type = new Type(it.type);
            _expr.type.dim -= id + 1;
            register _reg = new register(_expr.type, regCnt++);
            currentBlock.push_back(new call(_reg, null, _expr));
            currentBlock.push_back(new store(_expr.type, subPtr, _reg));
            block initBlock = new block();
            currentBlock.push_back(new jump(initBlock));
            currentFn.blocks.add(currentBlock);

            currentBlock = initBlock;
            newExprInit(it, id + 1, condBlock, _reg);
        }
        else {
            type.dim--;
            if (type.primitive())
                currentBlock.push_back(new store(type, subPtr, new intConst("0")));
            else currentBlock.push_back(new store(type, subPtr, new nullConst()));
            currentBlock.push_back(new jump(destination));
            currentFn.blocks.add(currentBlock);
        }
    }
    @Override public void visit(NewArrayExprNode it) {
        for (ExprNode expr : it.sizes)
            if (expr != null) expr.accept(this);
        register reg = new register(it.type, regCnt++);
        FnExprNode expr = new FnExprNode(null, "builtin.new.array");
        expr.type = new Type(it.type);
        expr.params.add(it.sizes.get(0));
        currentBlock.push_back(new call(reg, null, expr));
        it.val = reg;
        block destination = new block(), initBlock = new block();
        currentBlock.push_back(new jump(initBlock));
        currentFn.blocks.add(currentBlock);

        currentBlock = initBlock;
        newExprInit(it, 0, destination, reg);
        currentBlock = destination;
    }
}