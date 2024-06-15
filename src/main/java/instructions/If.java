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

    private Instruction condition;
    private LinkedList<Instruction> instructionsIf;
    private LinkedList<Instruction> instructionsElse;
    private Instruction elseIf;

    //IF
    public If(Instruction condition, LinkedList<Instruction> instructionsIf, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.instructionsIf = instructionsIf;
    }

    //IF-ELSE
    public If(Instruction condition, LinkedList<Instruction> instructionsIf, LinkedList<Instruction> instructionsElse, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.instructionsIf = instructionsIf;
        this.instructionsElse = instructionsElse;
    }

    //IF-ELSE-IF
    public If(Instruction condition, LinkedList<Instruction> instructionsIf, Instruction elseIf, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.instructionsIf = instructionsIf;
        this.elseIf = elseIf;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var cond = this.condition.interpret(tree, table);
        if (cond instanceof Error) {
            return cond;
        }

        if (this.condition.type.getDataType() != DataType.BOOLEANO) {
            return new Error("SEMANTICO", "Condición en Sentencia If Inválida", this.line, this.column);
        }
        return executeIf(cond, tree, table);
    }

    private Object executeIf(Object condition, Tree tree, SymbolTable table) {

        var newTable = new SymbolTable(table);

        if ((boolean) condition) {
            for (var a : this.instructionsIf) {
                if (a == null) {
                    continue;
                }
                if (a instanceof Break || a instanceof Continue) {
                    return a;
                }
                var res1 = a.interpret(tree, newTable);
                if (res1 instanceof Error) {
                    return res1; //TERMINA LA SECUENCIA DEL IF
                }
                if (res1 instanceof Break || res1 instanceof Continue) {
                    return res1;
                }
            }
        } else {
            if (this.instructionsElse != null) {
                for (var a : this.instructionsElse) {
                    if (a == null) {
                        continue;
                    }
                    if (a instanceof Break || a instanceof Continue) {
                        return a;
                    }
                    var res2 = a.interpret(tree, newTable);
                    if (res2 instanceof Error) {
                        return res2;
                    }
                    if (res2 instanceof Break || res2 instanceof Continue) {
                        return res2;
                    }
                }
            } else if (this.elseIf != null) {
                var res3 = elseIf.interpret(tree, newTable);
                if (res3 instanceof Error) {
                    return res3;
                }
                if (res3 instanceof Break || res3 instanceof Continue) {
                    return res3;
                }
            }
        }
        return null;
    }

}
