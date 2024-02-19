import AST.RootNode;
import Assembly.AsmRoot;
import Backend.*;
import Frontend.ASTBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import MIR.root;
import MIR.function;
import parser.MxLexer;
import parser.MxParser;
import Util.MxErrorListener;
import Util.error.error;
import Util.globalScope;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {
    public static void main(String[] args) throws Exception{
        try {
            RootNode ASTRoot;
            globalScope gScope = new globalScope(null, null);
            gScope.gScope = gScope;

            MxLexer lexer = new MxLexer(CharStreams.fromStream(System.in));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder = new ASTBuilder(gScope);
            ASTRoot = (RootNode)astBuilder.visit(parseTreeRoot);
            new SymbolCollector(gScope).visit(ASTRoot);
            new SemanticChecker(gScope).visit(ASTRoot);

            root irProgram = new root();
            function init = new function(gScope.getTypeFromName("void", null), 
                           null, "_init");
            irProgram.fns.add(init);
            new IRBuilder(irProgram, init, gScope).visit(ASTRoot);
            // irProgram.print(System.out);

            AsmRoot asmProgram = new AsmRoot();
            new InstSelector(gScope, asmProgram).visit(irProgram);
            new RegAlloc(asmProgram).work();
            asmProgram.print(System.out);
        } catch (error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}