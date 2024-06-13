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

/**
 *
 * @author herberthreyes
 */
public class VariableAssignment extends Instruction {

    private String id;
    private Instruction value;

    public VariableAssignment(String id, Instruction value, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.value = value;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(id);
        if (variable == null) {
            return new Error("SEMANTICO", "Asignación Inválida: No puede asignarle un valor a una variable inexistente",
                    this.line, this.column);
        }

        if (!variable.isMutable()) {
            return new Error("SEMANTICO", "Asignación Inválida: No puede cambiar el valor de una variable declarada como CONST",
                    this.line, this.column);
        }

        // interpretar el nuevo valor a asignar
        var newValue = this.value.interpret(tree, table);
        if (newValue instanceof Error) {
            return newValue;
        }

        //validar tipos
        if (variable.getType().getDataType() != this.value.type.getDataType()) {
            return new Error("SEMANTICO", "Asignación Inválida: No puede asignarle a una variable declarada con el tipo " + variable.getType().getDataType() + " un valor del tipo " + this.value.type.getDataType(),
                    this.line, this.column);
        }
        //this.tipo.setTipo(variable.getTipo().getTipo());
        variable.setValue(newValue);
        return null;
    }

}
