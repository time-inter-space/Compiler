package Util;

import AST.*;
import Util.error.*;

import java.util.HashMap;

public class Type {
    public String name = "";
    public int dim = 0;
    public boolean hasConstructor = false;
    public HashMap<String, String> memberVars = new HashMap<>();
    public HashMap<String, FnDefNode> memberFns = new HashMap<>();

    public Type(String name) {
        boolean isName = true;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (ch == '[') {
                this.dim++;
                isName = false;
            }
            else if (isName) this.name += ch;
        }
    }
    public Type(Type other) {
        name = other.name;
        dim = other.dim;
        hasConstructor = other.hasConstructor;
        memberVars = other.memberVars;
        memberFns = other.memberFns;
    }
    public void addVar(String name, String type, position pos) {
        if (memberVars.containsKey(name))
            throw new semanticError("multiple definition of " + name, pos);
        memberVars.put(name, type);
    }
    public void addFn(String name, FnDefNode fn, position pos) {
        if (memberFns.containsKey(name))
            throw new semanticError("multiple definition of " + name, pos);
        memberFns.put(name, fn);
    }
    public boolean equals(Type other) {
        if (!name.equals(other.name)) return false;
        return dim == other.dim;
    }
    public boolean primitive() {
        return dim <= 0 && (name.equals("int") || name.equals("bool"));
    }
    public boolean isVoid() {
        return name.equals("") || name.equals("void");
    }
}