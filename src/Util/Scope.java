package Util;

import MIR.Entity.*;
import Util.error.semanticError;

import java.util.HashMap;

public class Scope {

    public HashMap<String, Type> members = new HashMap<>();
    public HashMap<String, variable> entities = new HashMap<>();
    private Scope parentScope;
    public globalScope gScope;
    public boolean isClass = false;

    public Scope(Scope parentScope, globalScope gScope) {
        this.parentScope = parentScope;
        this.gScope = gScope;
    }

    public Scope parentScope() {
        return parentScope;
    }

    public void defineVariable(String name, Type t, position pos) {
        if (members.containsKey(name))
            throw new semanticError("Semantic Error: variable redefine", pos);
        members.put(name, t);
    }
    public boolean containsVariable(String name, boolean lookUpon) {
        if (members.containsKey(name)) return true;
        else if (parentScope != null && lookUpon)
            return parentScope.containsVariable(name, true);
        else return false;
    }
    public Type getType(String name, boolean lookUpon, position pos) {
        if (members.containsKey(name)) return members.get(name);
        else if (parentScope != null && lookUpon)
            return parentScope.getType(name, true, pos);
        throw new semanticError("no such variable: " + name, pos);
    }
    public variable getEntity(String name, boolean lookUpon) {
        if (entities.containsKey(name)) {
            if (isClass) return null;
            return entities.get(name);
        }
        else if (parentScope != null && lookUpon)
            return parentScope.getEntity(name, true);
        return null;
    }
}