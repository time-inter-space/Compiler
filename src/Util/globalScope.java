package Util;

import Util.error.semanticError;
import AST.*;
import MIR.*;

import java.util.HashMap;

public class globalScope extends Scope {
    public HashMap<String, Type> types = new HashMap<>();
    public HashMap<String, FnDefNode> fns = new HashMap<>();
    public HashMap<String, classDef> irTypes = new HashMap<>();
    public globalScope(Scope parentScope, globalScope gScope) {
        super(parentScope, gScope);
        types.put("int", new Type("int"));
        types.put("bool", new Type("bool"));
        types.put("void", new Type("void"));
        
        position pos = new position(-1, -1);
        Type S = new Type("string");
        FnDefNode fn = new FnDefNode(pos, "int", "length");
        S.memberFns.put("length", fn);

        fn = new FnDefNode(pos, "string", "substring");
        ParamNode pa = new ParamNode(pos, "int", "left");
        ParamNode _pa = new ParamNode(pos, "int", "right");
        fn.params.add(pa);
        fn.params.add(_pa);
        S.memberFns.put("substring", fn);

        fn = new FnDefNode(pos, "int", "parseInt");
        S.memberFns.put("parseInt", fn);

        fn = new FnDefNode(pos, "int", "ord");
        pa = new ParamNode(pos, "int", "pos");
        fn.params.add(pa);
        S.memberFns.put("ord", fn);
        types.put("string", S);
        
        fn = new FnDefNode(pos, "void", "print");
        pa = new ParamNode(pos, "string", "str");
        fn.params.add(pa);
        fns.put("print", fn);

        fn = new FnDefNode(pos, "void", "println");
        fn.params.add(pa);
        fns.put("println", fn);

        fn = new FnDefNode(pos, "void", "printInt");
        pa = new ParamNode(pos, "int", "n");
        fn.params.add(pa);
        fns.put("printInt", fn);

        fn = new FnDefNode(pos, "void", "printlnInt");
        fn.params.add(pa);
        fns.put("printlnInt", fn);

        fn = new FnDefNode(pos, "string", "getString");
        fns.put("getString", fn);

        fn = new FnDefNode(pos, "int", "getInt");
        fns.put("getInt", fn);

        fn = new FnDefNode(pos, "string", "toString");
        pa = new ParamNode(pos, "int", "i");
        fn.params.add(pa);
        fns.put("toString", fn);
    }
    public void addType(String name, Type t, position pos) {
        if (name.equals("main") || fns.containsKey(name))
            throw new semanticError("duplicate name for function name and class name", pos);
        if (types.containsKey(name))
            throw new semanticError("multiple definition of " + name, pos);
        types.put(name, t);
    }
    public Type getTypeFromName(String name, position pos) {
        Type type = new Type(name);
        int dim = type.dim;
        if (!types.containsKey(type.name))
            throw new semanticError("no such type: " + name, pos);
        type = types.get(type.name);
        type.dim = dim;
        return type;
    }

    public void addFn(String name, FnDefNode fn, position pos) {
        if (fns.containsKey(name))
            throw new semanticError("multiple definition of " + name, pos);
        fns.put(name, fn);
    }
    public FnDefNode getFnFromName(String name, position pos) {
        if (fns.containsKey(name)) return fns.get(name);
        throw new semanticError("no such function: " + name, pos);
    }

    public classDef getIRClass(String type) {
        return irTypes.get(type);
    }
}