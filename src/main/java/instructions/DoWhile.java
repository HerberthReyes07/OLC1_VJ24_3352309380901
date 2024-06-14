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
public class DoWhile extends Instruction {

    private LinkedList<Instruction> instructions;
    private Instruction condition;

    public DoWhile(LinkedList<Instruction> instructions, Instruction condition, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.instructions = instructions;
        this.condition = condition;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        
        var cond = this.condition.interpret(tree, table);
        
        if (cond instanceof Error) {
            return cond;
        }

        if (this.condition.type.getDataType() != DataType.BOOLEANO) {
            return new Error("SEMANTICO", "Condición en Sentencia While Inválida", this.line, this.column);
        }
        
        return executeDoWhile(tree, table);
    }

    private Object executeDoWhile(Tree tree, SymbolTable table) {

        do {
            var newTable = new SymbolTable(table);
            newTable.setName("SENTENCIA DO-WHILE");

            for (var a : this.instructions) {
                if (a == null) {
                    continue;
                }
                var res1 = a.interpret(tree, newTable);
                if (res1 instanceof Error) {
                    return res1; //TERMINA LA SECUENCIA DEL DO-WHILE
                }
            }
        } while (Boolean.parseBoolean(this.condition.interpret(tree, table).toString()));

        return null;
    }

}
