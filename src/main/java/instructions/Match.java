/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.LinkedList;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;

/**
 *
 * @author herberthreyes
 */
public class Match extends Instruction {

    private Instruction condition;
    private LinkedList<CaseMatch> cases;
    private LinkedList<Instruction> defaultMatch;

    //MATCH - CASES - DEFAULT
    public Match(Instruction condition, LinkedList<CaseMatch> cases, LinkedList<Instruction> defaultMatch, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.cases = cases;
        this.defaultMatch = defaultMatch;
    }

    //MATCH - CASES
    public Match(Instruction condition, LinkedList<CaseMatch> cases, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.cases = cases;
    }

    //MATCH - DEFAULT
    public Match(Instruction condition, LinkedList<Instruction> defaultMatch, boolean onlyDefault, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.defaultMatch = defaultMatch;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var cond = this.condition.interpret(tree, table);
        if (cond instanceof Error) {
            return cond;
        }
        return executeMatch(cond, tree, table);
    }

    private Object executeMatch(Object condition, Tree tree, SymbolTable table) {

        DataType type1 = this.condition.type.getDataType();
        var newTable = new SymbolTable(table);

        if (this.cases != null) {
            for (var a : this.cases) {

                if (a == null) {
                    continue;
                }

                var aux = a.getExpression().interpret(tree, table);
                if (aux instanceof Error) {
                    return aux; //TERMINA LA SECUENCIA DEL MATCH
                }

                if (a.getExpression().type.getDataType() == type1 && aux.toString().equals(condition.toString())) {
                    var res1 = a.interpret(tree, newTable);
                    if (res1 instanceof Error) {
                        return res1; //TERMINA LA SECUENCIA DEL MATCH
                    }
                    if (res1 instanceof Break) {
                        return res1;
                    }
                    return null;
                }
            }
        }

        if (this.defaultMatch != null) {
            for (var a : this.defaultMatch) {
                if (a == null) {
                    continue;
                }
                if (a instanceof Break) {
                    return a;
                }
                var res1 = a.interpret(tree, newTable);
                if (res1 instanceof Error) {
                    return res1; //TERMINA LA SECUENCIA DEL MATCH
                }
                if (res1 instanceof Break) {
                    return res1;
                }
            }
        }
        return null;
    }

}
