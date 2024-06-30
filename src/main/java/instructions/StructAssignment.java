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
import java.util.LinkedList;
import symbol.Struct;
import symbol.Symbol;

/**
 *
 * @author herberthreyes
 */
public class StructAssignment extends Instruction {

    private LinkedList<String> idList;
    private Instruction expression;

    public StructAssignment(LinkedList<String> idList, Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.idList = idList;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        int cont = 0;
        HashMap<String, Object> val = null;
        HashMap<String, Object> initialVal = null;
        boolean structFlag = false;
        String auxKey = null;
        Symbol variable = null;
        Struct currentStruct = null;

        for (var id : idList) {

            //System.out.println("cont: " + cont + ", id: " + id);
            if (cont == 0) {
                variable = table.getVariable(id.toLowerCase());
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
                val = (HashMap<String, Object>) variable.getValue();
                currentStruct = variable.getStruct();
                initialVal = val;
            } else {
                var aux = val.get(id.toLowerCase());
                if (aux == null) {
                    return new Error("SEMANTICO", "Asignación a Struct Inválida: El campo: " + id.toLowerCase() + " no existe en el Struct: " + currentStruct.getId(), this.line, this.column);
                }

                HashMap<String, DataType> dataType = currentStruct.getValuesTypes();
                var aux2 = dataType.get(id.toLowerCase());

                if (aux2 != DataType.STRUCT) {
                    if (idList.size() != (cont + 1)) {
                        continue;
                    }

                    var newValue = this.expression.interpret(tree, table);
                    if (newValue instanceof Error) {
                        return newValue;
                    }

                    if (this.expression.type.getDataType() != aux2) {
                        return new Error("SEMANTICO", "Asignación a Struct Inválida: No puede asignarle a una variable declarada con el tipo " + aux2 + " un valor del tipo " + this.expression.type.getDataType(),
                                this.line, this.column);
                    }
                    this.type.setType(aux2);
                    val.put(id.toLowerCase(), newValue);
                    if (structFlag) {
                        initialVal.put(auxKey.toLowerCase(), val);
                    }
                    variable.setValue(initialVal);
                    return null;
                } else {
                    HashMap<String, Struct> structType = currentStruct.getValuesStruct();
                    currentStruct = structType.get(id.toLowerCase());
                    val = (HashMap<String, Object>) val.get(id.toLowerCase());
                    structFlag = true;
                    auxKey = id.toLowerCase();
                }
            }
            cont++;
        }
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {

        String sapNode = "n" + tree.getCont();
        String igNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = sapNode + "[label=\"ASIGNACION-STRUCT\"];\n";
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
        
        result += igNode + "[label=\"=\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += pcNode + "[label=\";\"];\n";

        result += sapNode + " -> " + igNode + ";\n";
        result += sapNode + " -> " + expNode + ";\n";
        result += sapNode + " -> " + pcNode + ";\n";

        result += this.expression.generateAST(tree, expNode);
        return result;
    }

}
