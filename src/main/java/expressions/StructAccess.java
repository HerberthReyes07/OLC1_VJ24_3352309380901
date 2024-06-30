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

    @Override
    public String generateAST(Tree tree, String previous) {

        //ID:a . ID:b
        String sapNode = "n" + tree.getCont();

        String result = sapNode + "[label=\"ACCESO-STRUCT\"];\n";
        result += previous + " -> " + sapNode + ";\n";

        int cont = 0;
        for (var i : this.idList) {
            if (i == null) {
                continue;
            }
            if (cont == 0) {
                String nodoAux = "n" + tree.getCont();
                result += sapNode + " -> " + nodoAux + ";\n";
                result += nodoAux + "[label=\"" + idList.get(cont) + "\"];\n";
            } else {
                String nodoAux = "n" + tree.getCont();
                String nodoAux2 = "n" + tree.getCont();

                result += sapNode + " -> " + nodoAux + ";\n";
                result += nodoAux + " -> " + nodoAux2 + ";\n";

                result += nodoAux + "[label=\"CAMPO_STRUCT\"];\n";
                result += nodoAux2 + "[label=\"" + idList.get(cont) + "\"];\n";
            }

            cont++;
            if (cont + 1 <= this.idList.size()) {
                String auxCmNode = "n" + tree.getCont();
                result += sapNode + " -> " + auxCmNode + ";\n";
                result += auxCmNode + "[label=\".\"];\n";
            }

        }
        return result;

    }

}
