package Frontend;

import AST.*;
import parser.*;
import parser.MxParser.FuncDefContext;
import Util.*;
import Util.error.semanticError;
import Util.error.syntaxError;
import java.util.List;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {
    private globalScope gScope;
    public ASTBuilder(globalScope gScope) {
        this.gScope = gScope;
    }
    
    @Override public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        RootNode rt = new RootNode(new position(ctx));
        ctx.classDef().forEach(cd -> rt.classDefs.add((ClassDefNode)visit(cd)));
        ctx.varDef().forEach(vd -> rt.varDefs.add((VarDefStmtNode)visit(vd)));
        for (FuncDefContext fd : ctx.funcDef()) {
            FnDefNode fnDef = (FnDefNode)visit(fd);
            if (fnDef.fnName.equals("main")) {
                if (rt.mainFn != null)
                    throw new semanticError("multiple definition of main function", fnDef.pos);
                if (!fnDef.type.equals("int"))
                    throw new semanticError("main function type not match", fnDef.pos);
                if (fnDef.params.size() > 0)
                    throw new semanticError("main function has no parameter", fnDef.pos);
                rt.mainFn = fnDef;
            }
            else rt.fnDefs.add(fnDef);
        }
        if (rt.mainFn == null)
            throw new semanticError("no main function", rt.pos);
        return rt;
    }

    /*@Override public ASTNode visitMainFn(MxParser.MainFnContext ctx) {
        FnDefNode fn = new FnDefNode(new position(ctx), "int", "main");
        ctx.suite().statement().forEach(stmt -> fn.stmts.add((StmtNode)visit(stmt)));
        return fn;
    }*/

    @Override public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        ClassDefNode classDef = new ClassDefNode(new position(ctx), ctx.Identifier().getText());
        ctx.varDef().forEach(vd -> classDef.varDefs.add((VarDefStmtNode)visit(vd)));
        for (MxParser.FuncInClassDefContext func : ctx.funcInClassDef()) {
            if (func.funcDef() != null) classDef.fns.add((FnDefNode)visit(func.funcDef()));
            else {
                if (!func.Identifier().getText().equals(classDef.className))
                    throw new semanticError("mismatched constructor name", new position(func));
                FnDefNode fn = new FnDefNode(new position(func), "", classDef.className);
                func.suite().statement().forEach(stmt -> fn.stmts.add((StmtNode)visit(stmt)));
                classDef.constructor = fn;
            }
        }
        return classDef;
    }

    @Override public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) {
        FnDefNode fn = new FnDefNode(new position(ctx), ctx.type().getText(), ctx.Identifier(0).getText());
        int i = 1;
        for (MxParser.VarTypeContext vt : ctx.varType()) {
            fn.params.add(new ParamNode(new position(vt), vt.getText(), ctx.Identifier(i).getText()));
            i++;
        }
        ctx.suite().statement().forEach(stmt -> fn.stmts.add((StmtNode)visit(stmt)));
        return fn;
    }

    @Override public ASTNode visitFuncExpr(MxParser.FuncExprContext ctx) {
        FnExprNode expr = new FnExprNode(new position(ctx), ctx.Identifier().getText());
        ctx.expression().forEach(e -> expr.params.add((ExprNode)visit(e)));
        return expr;
    }

    @Override public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        VarDefStmtNode stmt = new VarDefStmtNode(new position(ctx), ctx.varType().getText());
        for (MxParser.InitExprContext ie : ctx.initExpr()) {
            stmt.vars.add(ie.Identifier().getText());
            if (ie.expression() == null) stmt.vals.add(null);
            else stmt.vals.add((ExprNode)visit(ie.expression()));
        }
        return stmt;
    }

    @Override public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        BlockStmtNode block = new BlockStmtNode(new position(ctx));
        ctx.statement().forEach(stmt -> block.stmts.add((StmtNode)visit(stmt)));
        return block;
    }

    @Override public ASTNode visitBlock(MxParser.BlockContext ctx) {
        return visitSuite(ctx.suite());
    }

    @Override public ASTNode visitVarDefStmt(MxParser.VarDefStmtContext ctx) {
        return visitVarDef(ctx.varDef());
    }

    @Override public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        StmtNode thenStmt = (StmtNode)visit(ctx.trueStmt), elseStmt = null;
        ExprNode condition = (ExprNode)visit(ctx.expression());
        if (ctx.falseStmt != null) elseStmt = (StmtNode)visit(ctx.falseStmt);
        return new IfStmtNode(new position(ctx), condition, thenStmt, elseStmt);
    }

    @Override public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        StmtNode stmt = (StmtNode)visit(ctx.statement());
        ExprNode condition = (ExprNode)visit(ctx.expression());
        return new WhileStmtNode(new position(ctx), condition, stmt);
    }

    @Override public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        StmtNode init = null, stmt = (StmtNode)visit(ctx.statement());
        ExprNode cond = null, step = null;
        if (ctx.forInitialization().varDef() != null)
            init = (StmtNode)visit(ctx.forInitialization().varDef());
        else if (ctx.forInitialization().expression() != null)
            init = new ExprStmtNode(new position(ctx.forInitialization().expression()),
                (ExprNode)visit(ctx.forInitialization().expression()));
        if (ctx.forCondition().expression() != null)
            cond = (ExprNode)visit(ctx.forCondition().expression());
        if (ctx.expression() != null) step = (ExprNode)visit(ctx.expression());
        return new ForStmtNode(new position(ctx), init, cond, step, stmt);
    }

    @Override public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        ExprNode expr = null;
        if (ctx.expression() != null) expr = (ExprNode)visit(ctx.expression());
        return new ReturnStmtNode(new position(ctx), expr);
    }

    @Override public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
        return new BreakStmtNode(new position(ctx));
    }

    @Override public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
        return new ContinueStmtNode(new position(ctx));
    }
    
    @Override public ASTNode visitPureExprStmt(MxParser.PureExprStmtContext ctx) {
        return new ExprStmtNode(new position(ctx), (ExprNode)visit(ctx.expression()));
    }

    @Override public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return null;
    }

    @Override public ASTNode visitDotExpr(MxParser.DotExprContext ctx) {
        DotExprNode expr = new DotExprNode(new position(ctx), (ExprNode)visit(ctx.expression()));
        if (ctx.classMember().Identifier() != null)
            expr.var = ctx.classMember().Identifier().getText();
        else expr.fn = (FnExprNode)visit(ctx.classMember().funcExpr());
        return expr;
    }

    @Override public ASTNode visitBracketExpr(MxParser.BracketExprContext ctx) {
        BracketExprNode bra = new BracketExprNode(new position(ctx));
        bra.obj = (ExprNode)visit(ctx.expression(0));
        bra.id = (ExprNode)visit(ctx.expression(1));
        return bra;
    }

    @Override public ASTNode visitNewClassExpr(MxParser.NewClassExprContext ctx) {
        return new NewClassExprNode(new position(ctx), ctx.varType().getText());
    }

    @Override public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        AtomExprNode expr = new AtomExprNode(new position(ctx));
        if (ctx.primary().Identifier() != null)
            expr.iden = ctx.primary().Identifier().getText();
        else if (ctx.primary().literal().DecimalInteger() != null)
            expr.num = ctx.primary().literal().DecimalInteger().getText();
        else if (ctx.primary().literal().StringLiteral() != null)
            expr.str = ctx.primary().literal().StringLiteral().getText();
        else if (ctx.primary().literal().True() != null) expr.isTrue = true;
        else if (ctx.primary().literal().False() != null) expr.isFalse = true;
        else if (ctx.primary().literal().Null() != null) expr.isNull = true;
        else expr.isThis = true;
        return expr;
    }

    @Override public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        BinaryExprNode expr = new BinaryExprNode(
            new position(ctx),
            (ExprNode)visit(ctx.expression(0)),
            (ExprNode)visit(ctx.expression(1))
        );
        if (ctx.Multiple() != null) expr.op = BinaryExprNode.Operator.MUL;
        else if (ctx.Division() != null) expr.op = BinaryExprNode.Operator.DIV;
        else if (ctx.Modulo() != null) expr.op = BinaryExprNode.Operator.MOD;
        else if (ctx.Plus() != null) expr.op = BinaryExprNode.Operator.ADD;
        else if (ctx.Minus() != null) expr.op = BinaryExprNode.Operator.SUB;
        else if (ctx.LeftShift() != null) expr.op = BinaryExprNode.Operator.LSH;
        else if (ctx.RightShift() != null) expr.op = BinaryExprNode.Operator.RSH;
        else if (ctx.Less() != null) expr.op = BinaryExprNode.Operator.L;
        else if (ctx.LessEqual() != null) expr.op = BinaryExprNode.Operator.LEQ;
        else if (ctx.Greater() != null) expr.op = BinaryExprNode.Operator.G;
        else if (ctx.GreaterEqual() != null) expr.op = BinaryExprNode.Operator.GEQ;
        else if (ctx.Equal() != null) expr.op = BinaryExprNode.Operator.EQ;
        else if (ctx.NotEqual() != null) expr.op = BinaryExprNode.Operator.NEQ;
        else if (ctx.And() != null) expr.op = BinaryExprNode.Operator.AND;
        else if (ctx.Caret() != null) expr.op = BinaryExprNode.Operator.XOR;
        else if (ctx.Or() != null) expr.op = BinaryExprNode.Operator.OR;
        else if (ctx.AndAnd() != null) expr.op = BinaryExprNode.Operator.ANDAND;
        else expr.op = BinaryExprNode.Operator.OROR;
        return expr;
    }

    @Override public ASTNode visitPreExpr(MxParser.PreExprContext ctx) {
        PreExprNode expr = new PreExprNode(new position(ctx), (ExprNode)visit(ctx.expression()));
        if (ctx.PlusPlus() != null) expr.op = PreExprNode.Operator.PP;
        else if (ctx.MinusMinus() != null) expr.op = PreExprNode.Operator.MM;
        else if (ctx.Not() != null) expr.op = PreExprNode.Operator.NOT;
        else if (ctx.Tilde() != null) expr.op = PreExprNode.Operator.REV;
        else expr.op = PreExprNode.Operator.NEG;
        return expr;
    }

    @Override public ASTNode visitPostExpr(MxParser.PostExprContext ctx) {
        PostExprNode expr = new PostExprNode(new position(ctx), (ExprNode)visit(ctx.expression()));
        if (ctx.PlusPlus() != null) expr.op = PostExprNode.Operator.PP;
        else expr.op = PostExprNode.Operator.MM;
        return expr;
    }

    @Override public ASTNode visitParenExpr(MxParser.ParenExprContext ctx) {
        return new ParenExprNode(new position(ctx), (ExprNode)visit(ctx.expression()));
    }

    @Override public ASTNode visitFunctionExpr(MxParser.FunctionExprContext ctx) {
        return visitFuncExpr(ctx.funcExpr());
    }

    /*@Override public ASTNode visitCommaExpr(MxParser.CommaExprContext ctx) {
        return new CommaExprNode(
            new position(ctx),
            (ExprNode)visit(ctx.expression(0)),
            (ExprNode)visit(ctx.expression(1))
        );
    }*/

    @Override public ASTNode visitTernaryExpr(MxParser.TernaryExprContext ctx) {
        return new TernaryExprNode(
            new position(ctx),
            (ExprNode)visit(ctx.expression(0)),
            (ExprNode)visit(ctx.expression(1)),
            (ExprNode)visit(ctx.expression(2))
        );
    }

    @Override public ASTNode visitAssignExpr(MxParser.AssignExprContext ctx) {
        return new AssignExprNode(
            new position(ctx),
            (ExprNode)visit(ctx.expression(0)),
            (ExprNode)visit(ctx.expression(1))
        );
    }

    @Override public ASTNode visitNewArrayExpr(MxParser.NewArrayExprContext ctx) {
        NewArrayExprNode arr = new NewArrayExprNode(new position(ctx), ctx.varType().getText());
        ctx.expression().forEach(expr -> arr.sizes.add((ExprNode)visit(expr)));
        for (int i = ctx.LeftBracket().size() - ctx.expression().size(); i > 0; --i)
            arr.sizes.add(null);
        return arr;
    }
}