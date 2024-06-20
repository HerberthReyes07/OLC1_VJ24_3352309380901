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
public class VectorAssignment extends Instruction {

    private String id;
    private Instruction access;
    private Instruction access2;
    private Instruction value;

    public VectorAssignment(String id, Instruction access, Instruction value, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.access = access;
        this.value = value;
    }

    public VectorAssignment(String id, Instruction access, Instruction access2, Instruction value, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.access = access;
        this.access2 = access2;
        this.value = value;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(id);
        if (variable == null) {
            return new Error("SEMANTICO", "Asignación Inválida: No puede asignarle un valor a una variable inexistente",
                    this.line, this.column);
        }

        var acc = this.access.interpret(tree, table);

        if (this.access.type.getDataType() != DataType.ENTERO) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }
        
        if (!variable.isMutable()) {
            return new Error("SEMANTICO", "Asignación Inválida: No puede cambiar el valor de una variable declarada como CONST",
                    this.line, this.column);
        }

        var newValue = this.value.interpret(tree, table);
        if (newValue instanceof Error) {
            return newValue;
        }

        if (variable.getType().getDataType() != this.value.type.getDataType()) {
            return new Error("SEMANTICO", "Asignación Inválida: No puede asignarle a una variable declarada con el tipo " + variable.getType().getDataType() + " un valor del tipo " + this.value.type.getDataType(),
                    this.line, this.column);
        }

        if (this.access2 == null) {
            LinkedList<Object> val = (LinkedList<Object>) variable.getValue();

            int pos = (int) acc;
            if (pos < 0 || pos > (val.size() - 1)) {
                return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                        this.line, this.column);
            }
            val.set(pos, newValue);
            variable.setValue(val);
            return null;
        }
        
        /**/
        var acc2 = this.access2.interpret(tree, table);

        if (this.access2.type.getDataType() != DataType.ENTERO) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }

        LinkedList<LinkedList<Object>> val = (LinkedList<LinkedList<Object>>) variable.getValue();

        int pos = (int) acc;
        if (pos < 0 || pos > (val.size() - 1)) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }

        int pos2 = (int) acc2;
        if (pos2 < 0 || pos2 > (val.get(pos).size() - 1)) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }
        
        val.get(pos).set(pos2, newValue);
        variable.setValue(val);
        return null;
    }

}
