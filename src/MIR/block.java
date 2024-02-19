package MIR;

import Util.error.internalError;
import Util.position;
import Util.Type;
import MIR.Entity.*;
import MIR.Instruction.*;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class block {
    public String label = null;
    public LinkedList<statement> stmts = new LinkedList<>();
    public terminalStmt tailStmt = null;
    public block() {}
    public void push_back(statement stmt) {
        stmts.add(stmt);
        if (stmt instanceof terminalStmt) {
            if (tailStmt != null)
                throw new internalError("multiple tails of a block", new position(0, 0));
            tailStmt = (terminalStmt)stmt;
        }
    }
    public void load_store(Type type, entity lhs, entity rhs, int regCnt) {
        register reg = new register(type, regCnt);
        push_back(new load(type, rhs, reg));
        push_back(new store(type, lhs, reg));
    }
    public ArrayList<block> successors() {
        ArrayList<block> ret = new ArrayList<>();
        if (tailStmt instanceof br) {
            ret.add(((br)tailStmt).trueBlock);
            ret.add(((br)tailStmt).falseBlock);
        }
        else if (tailStmt instanceof jump) {
            ret.add(((jump)tailStmt).destination);
        }
        return ret;
    }
    public void print(PrintStream out) {
        out.println(label + ":");
        for (statement stmt : stmts) {
            out.print("\t");
            stmt.print(out);
            out.println("");
        }
    }
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}