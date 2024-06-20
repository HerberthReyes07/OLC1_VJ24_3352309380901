/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressions;

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
public class VectorAccess extends Instruction {

    private String id;
    private Instruction expression;
    private Instruction expression2;

    public VectorAccess(String id, Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.expression = expression;
    }

    public VectorAccess(String id, Instruction expression, Instruction expression2, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.expression = expression;
        this.expression2 = expression2;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var value = table.getVariable(this.id);
        if (value == null) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder al valor de una variable inexistente",
                    this.line, this.column);
        }

        var access = this.expression.interpret(tree, table);

        if (this.expression.type.getDataType() != DataType.ENTERO) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }

        if (this.expression2 == null) {
            LinkedList<Object> val = (LinkedList<Object>) value.getValue();

            int pos = (int) access;
            if (pos < 0 || pos > (val.size() - 1)) {
                return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                        this.line, this.column);
            }

            this.type.setType(value.getType().getDataType());
            return val.get(pos);
        }

        var access2 = this.expression2.interpret(tree, table);

        if (this.expression2.type.getDataType() != DataType.ENTERO) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }

        LinkedList<LinkedList<Object>> val = (LinkedList<LinkedList<Object>>) value.getValue();

        int pos = (int) access;
        if (pos < 0 || pos > (val.size() - 1)) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }

        int pos2 = (int) access2;
        if (pos2 < 0 || pos2 > (val.get(pos).size() - 1)) {
            return new Error("SEMANTICO", "Acceso Inválido: No puede acceder a una posición del vector inexistente",
                    this.line, this.column);
        }
        
        this.type.setType(value.getType().getDataType());
        return val.get(pos).get(pos2);
    }

}
