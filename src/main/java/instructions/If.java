/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import java.util.LinkedList;
import symbol.DataType;

/**
 *
 * @author herberthreyes
 */
public class If extends Instruction {

    private Instruction expression;
    private LinkedList<Instruction> instructions1;
    private LinkedList<Instruction> instructions2;
    private Instruction instruction3;

    //IF
    public If(Instruction expression, LinkedList<Instruction> instructions1, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
        this.instructions1 = instructions1;
    }

    //IF-ELSE
    public If(Instruction expression, LinkedList<Instruction> instructions1, LinkedList<Instruction> instructions2, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
        this.instructions1 = instructions1;
        this.instructions2 = instructions2;
    }

    //IF-ELSE-IF
    public If(Instruction expression, LinkedList<Instruction> instructions1, Instruction instruction3, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
        this.instructions1 = instructions1;
        this.instruction3 = instruction3;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var condition = this.expression.interpret(tree, table);
        if (condition instanceof Error) {
            return condition;
        }

        switch (this.expression.type.getDataType()) {
            case BOOLEANO:
                return executeIf(condition, tree, table);
            /*if (instructions2.isEmpty()) {
                    return executeIf(condition, tree, table);
                } else {
                }*/
            default:
                return new Error("Semantico", "Operacion Logica en Sentencia If Invalida", this.line, this.column);
        }
    }

    private Object executeIf(Object condition, Tree tree, SymbolTable table) {

        DataType type1 = this.expression.type.getDataType();

        switch (type1) {
            case BOOLEANO:
                if (condition.toString().equalsIgnoreCase("true")) {
                    Object res1 = null;
                    for (var a : this.instructions1) {
                        res1 = a.interpret(tree, table);
                    }
                    return res1;
                } else {
                    
                    if (this.instructions2 != null) {
                        Object res2 = null;
                        for (var a : instructions2) {
                            res2 = a.interpret(tree, table);
                        }
                        return res2;
                    } else if (this.instruction3 != null){
                        return instruction3.interpret(tree, table);
                    }
                    return null;
                }
            default:
                return new Error("Semantico", "Condicion Invalida", this.line, this.column);
        }

    }

}
