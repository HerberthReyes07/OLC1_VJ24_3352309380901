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
public class For extends Instruction{
    
    private Instruction assignation;
    private Instruction condition;
    private Instruction update;
    private LinkedList<Instruction> instructions;

    public For(Instruction assignation, Instruction condition, Instruction update, LinkedList<Instruction> instructions, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.assignation = assignation;
        this.condition = condition;
        this.update = update;
        this.instructions = instructions;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        
        var newTable = new SymbolTable(table);
        newTable.setName(table.getName() + "-FOR");
        
        var asig = assignation.interpret(tree, newTable);
        if (asig instanceof Error) {
            return asig;
        }
        
        var cond = this.condition.interpret(tree, newTable);
        if (cond instanceof Error) {
            return cond;
        }

        if (this.condition.type.getDataType() != DataType.BOOLEANO) {
            return new Error("SEMANTICO", "Condición en Sentencia If Inválida", this.line, this.column);
        }
        
        return executeFor(tree, newTable);
        //return executeFor(tree, table);
    }
    
    private Object executeFor(Tree tree, SymbolTable newTable){
        
        while ((boolean) this.condition.interpret(tree, newTable)) {
            //nuevo entorno
            var newTable2 = new SymbolTable(newTable);
            newTable2.setName(newTable.getName());
            //ejecutar instrucciones
            for (var a : this.instructions) {
                if (a == null) {
                    continue;
                }
                if (a instanceof Break) {
                    return null;
                }
                if (a instanceof Continue) {
                    break;
                }
                var res1 = a.interpret(tree, newTable2);
                if (res1 instanceof Error) {
                    return res1; //TERMINA LA SECUENCIA DEL IF
                }
                if (res1 instanceof Break) {
                    return null;
                }
                if (res1 instanceof Continue) {
                    break;
                }
            }

            //actualizar la variable
            var act = this.update.interpret(tree, newTable);
            if (act instanceof Error) {
                return act;
            }
        }
        return null;
    }
    
    
    
    
}
