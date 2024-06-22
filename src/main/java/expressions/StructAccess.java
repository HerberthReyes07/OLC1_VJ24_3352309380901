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
import java.util.HashMap;
import symbol.Struct;

/**
 *
 * @author herberthreyes
 */
public class StructAccess extends Instruction {

    private String id;
    private String field;

    public StructAccess(String id, String field, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.field = field;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(this.id);
        if (variable == null) {
            return new Error("SEMANTICO", "Acceso a Struct Inválido: No puede acceder al valor de una variable inexistente",
                    this.line, this.column);
        }
        
        if (variable.getType().getDataType() != DataType.STRUCT) {
            return new Error("SEMANTICO", "Acceso a Struct Inválido: No puede acceder al valor de una variable que no es un Struct",
                    this.line, this.column);
        }

        HashMap<String, Object> val = (HashMap<String, Object>) variable.getValue();

        var aux = val.get(this.field.toLowerCase());
        if (aux == null) {
            return new Error("SEMANTICO", "Acceso a Struct Inválido: El campo: " + this.field.toLowerCase() + " no existe en la instancia: " + this.id, this.line, this.column);
        }

        Struct struct = variable.getStruct();
        HashMap<String, DataType> dataType = struct.getValuesTypes();
        var aux2 = dataType.get(this.field.toLowerCase());
        this.type.setType(aux2);
        return aux;
    }

}
