/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.HashMap;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import symbol.Struct;

/**
 *
 * @author herberthreyes
 */
public class StructAssignment extends Instruction {

    private String id;
    private String field;
    private Instruction expression;

    public StructAssignment(String id, String field, Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.field = field;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(this.id);
        if (variable == null) {
            return new Error("SEMANTICO", "Asignación a Struct Inválida: No puede acceder al valor de una variable inexistente",
                    this.line, this.column);
        }

        if (variable.getType().getDataType() != DataType.STRUCT) {
            return new Error("SEMANTICO", "Asignación a Struct Inválida: No puede acceder al valor de una variable que no es un Struct",
                    this.line, this.column);
        }

        if (!variable.isMutable()) {
            return new Error("SEMANTICO", "Asignación a Struct Inválida: No puede cambiar el valor de una variable declarada como CONST",
                    this.line, this.column);
        }

        Struct struct = variable.getStruct();
        HashMap<String, DataType> dataType = struct.getValuesTypes();
        var aux = dataType.get(this.field.toLowerCase());

        if (aux == null) {
            return new Error("SEMANTICO", "Asignación a Struct Inválida: El campo: " + this.field.toLowerCase() + " no existe en el Struct: " + struct.getId(), this.line, this.column);
        }

        var newValue = this.expression.interpret(tree, table);
        if (newValue instanceof Error) {
            return newValue;
        }

        if (this.expression.type.getDataType() != aux) {
            return new Error("SEMANTICO", "Asignación a Struct Inválida: No puede asignarle a una variable declarada con el tipo " + aux + " un valor del tipo " + this.expression.type.getDataType(),
                    this.line, this.column);
        }

        this.type.setType(aux);

        HashMap<String, Object> val = (HashMap<String, Object>) variable.getValue();

        val.put(this.field.toLowerCase(), newValue);
        variable.setValue(val);
        return null;
    }

}
