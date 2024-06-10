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

    private Instruction expression;
    private LinkedList<CaseMatch> cases;
    private LinkedList<Instruction> defaultMatch;

    //MATCH - CASES - DEFAULT
    public Match(Instruction expression, LinkedList<CaseMatch> cases, LinkedList<Instruction> defaultMatch, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
        this.cases = cases;
        this.defaultMatch = defaultMatch;
    }

    //MATCH - CASES
    public Match(Instruction expression, LinkedList<CaseMatch> cases, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
        this.cases = cases;
    }

    //MATCH - DEFAULT
    public Match(Instruction expression, LinkedList<Instruction> defaultMatch, boolean onlyDefault, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
        this.defaultMatch = defaultMatch;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var condition = this.expression.interpret(tree, table);
        if (condition instanceof Error) {
            return condition;
        }
        return executeMatch(condition, tree, table);
    }

    private Object executeMatch(Object condition, Tree tree, SymbolTable table) {

        DataType type1 = this.expression.type.getDataType();

        if (this.cases != null) {
            for (var a : this.cases) {
                var aux = a.getExpression().interpret(tree, table);
                //System.out.println("AT: " + aux1.toString());
                //System.out.println("CT: " + condition.toString());
                if (a.getExpression().type.getDataType() == type1 && aux.toString().equals(condition.toString())) {
                    return a.interpret(tree, table);
                }
            }
        }

        if (this.defaultMatch != null) {
            Object res = null;
            for (var a : this.defaultMatch) {
                res = a.interpret(tree, table);
            }
            return res;
        }

        return null;
    }

}
