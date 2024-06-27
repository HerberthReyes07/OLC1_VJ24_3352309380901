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
import java.util.LinkedList;
import symbol.Struct;
import symbol.Symbol;

/**
 *
 * @author herberthreyes
 */
public class StructAccess extends Instruction {

    private LinkedList<String> idList;

    public StructAccess(LinkedList<String> idList, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.idList = idList;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        int cont = 0;
        HashMap<String, Object> val = null;
        Symbol variable = null;
        Struct currentStruct = null;
        
        for (var id : idList) {

            //System.out.println("cont: " + cont + ", id: " + id);
            if (cont == 0) {
                variable = table.getVariable(id.toLowerCase());
                if (variable == null) {
                    return new Error("SEMANTICO", "Acceso a Struct Inválido: No puede acceder al valor de una variable inexistente",
                            this.line, this.column);
                }

                if (variable.getType().getDataType() != DataType.STRUCT) {
                    return new Error("SEMANTICO", "Acceso a Struct Inválido: No puede acceder al valor de una variable que no es un Struct",
                            this.line, this.column);
                }
                val = (HashMap<String, Object>) variable.getValue();
                currentStruct = variable.getStruct();
            } else {
                var aux = val.get(id.toLowerCase());
                if (aux == null) {
                    return new Error("SEMANTICO", "Acceso a Struct Inválido: El campo: " + id.toLowerCase() + " no existe en la instancia: " + this.idList.get(0), this.line, this.column);
                }
                
                HashMap<String, DataType> dataType = currentStruct.getValuesTypes();
                var aux2 = dataType.get(id.toLowerCase());
                
                if (aux2 != DataType.STRUCT) {
                    if (idList.size() != (cont + 1)) {
                        continue;
                    }
                    this.type.setType(aux2);
                    return aux;
                } else {
                    HashMap<String, Struct> structType = currentStruct.getValuesStruct();
                    currentStruct = structType.get(id.toLowerCase());
                    val = (HashMap<String, Object>) val.get(id.toLowerCase());
                }
            }
            cont++;
        }
        return null;
    }

}
