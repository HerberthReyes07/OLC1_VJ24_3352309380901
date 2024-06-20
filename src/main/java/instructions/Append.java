/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import java.util.LinkedList;

/**
 *
 * @author herberthreyes
 */
public class Append extends Instruction {

    private String id;
    private Instruction expression;

    public Append(String id, Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(id);
        if (variable == null) {
            return new Error("SEMANTICO", "Append Inválido: No puede agregar un valor a una lista inexistente",
                    this.line, this.column);
        }

        var newValue = this.expression.interpret(tree, table);
        if (newValue instanceof Error) {
            return newValue;
        }

        if (variable.getType().getDataType() != this.expression.type.getDataType()) {
            return new Error("SEMANTICO", "Append Inválido: No puede agregar a una lista declarada con el tipo " + variable.getType().getDataType() + " un valor del tipo " + this.expression.type.getDataType(),
                    this.line, this.column);
        }

        LinkedList<Object> val = (LinkedList<Object>) variable.getValue();

        val.add(newValue);
        variable.setValue(val);
        return null;
    }

}
