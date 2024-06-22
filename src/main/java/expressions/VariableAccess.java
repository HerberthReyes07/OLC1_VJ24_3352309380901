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

/**
 *
 * @author herberthreyes
 */
public class VariableAccess extends Instruction {

    private String id;

    public VariableAccess(String id, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        var value = table.getVariable(this.id);
        if (value == null) {
            return new Error("SEMANTICA", "Acceso Inválido: No puede acceder al valor de una variable inexistente",
                    this.line, this.column);
        }
        this.type.setType(value.getType().getDataType());
        return value.getValue();
    }

    public String getId() {
        return id;
    }

}
