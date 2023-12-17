package Util;

//import MIR.register;
import Util.error.semanticError;

import java.util.HashMap;

public class Scope {

    public HashMap<String, Type> members;
    //public HashMap<String, register> entities = new HashMap<>();
    private Scope parentScope;
    public globalScope gScope;


    public Scope(Scope parentScope, globalScope gScope) {
        members = new HashMap<>();
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
    /*public register getEntity(String name, boolean lookUpon) {
        if (entities.containsKey(name)) return entities.get(name);
        else if (parentScope != null && lookUpon)
            return parentScope.getEntity(name, true);
        return null;
    }*/
}