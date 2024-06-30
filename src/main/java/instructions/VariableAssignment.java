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
    private String incrementDecrement;

    public VariableAssignment(String id, Instruction value, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.value = value;
    }

    public VariableAssignment(String id, String incrementDecrement, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.incrementDecrement = incrementDecrement;
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

        Object newValue;

        if (this.incrementDecrement == null) {
            // interpretar el nuevo valor a asignar
            newValue = this.value.interpret(tree, table);
            if (newValue instanceof Error) {
                return newValue;
            }

            //validar tipos
            if (variable.getType().getDataType() != this.value.type.getDataType()) {
                return new Error("SEMANTICO", "Asignación Inválida: No puede asignarle a una variable declarada con el tipo " + variable.getType().getDataType() + " un valor del tipo " + this.value.type.getDataType(),
                        this.line, this.column);
            }
        } else {

            if (variable.getType().getDataType() != DataType.ENTERO && variable.getType().getDataType() != DataType.DECIMAL) {
                return new Error("SEMANTICO", this.incrementDecrement + " Inválido: No puede realizar el " + this.incrementDecrement + " de una variable declarada con el tipo " + variable.getType().getDataType(),
                        this.line, this.column);
            }

            if (this.incrementDecrement.equals("Incremento")) {
                if (variable.getType().getDataType() == DataType.ENTERO) {
                    newValue = (int) variable.getValue() + 1;
                } else {
                    newValue = (double) variable.getValue() + 1.0;
                }
            } else {
                if (variable.getType().getDataType() == DataType.ENTERO) {
                    newValue = (int) variable.getValue() - 1;
                } else {
                    newValue = (double) variable.getValue() - 1.0;
                }
            }
        }

        //this.tipo.setTipo(variable.getTipo().getTipo());
        variable.setValue(newValue);
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {

        String result = "";

        if (this.incrementDecrement == null) {

            //ASIGNACION ::= id = EXPRESION
            String apNode = "n" + tree.getCont();
            String idNode = "n" + tree.getCont();
            String igNode = "n" + tree.getCont();
            String expNode = "n" + tree.getCont();
            String pcNode = "n" + tree.getCont();

            result = apNode + "[label=\"ASIGNACION-VARIABLE\"];\n";
            result += previous + " -> " + apNode + ";\n";

            result += idNode + "[label=\" " + this.id + "\"];\n";
            result += igNode + "[label=\"=\"];\n";
            result += expNode + "[label=\"EXPRESION\"];\n";
            result += pcNode + "[label=\";\"];\n";

            result += apNode + " -> " + idNode + ";\n";
            result += apNode + " -> " + igNode + ";\n";
            result += apNode + " -> " + expNode + ";\n";
            result += apNode + " -> " + pcNode + ";\n";

            result += this.value.generateAST(tree, expNode);
        } else {

            //INCREMENTO_DECREMENTO ::= ID:a MAS MAS
            //                        | ID:a MENOS MENOS
            String apNode = "n" + tree.getCont();
            String idNode = "n" + tree.getCont();
            String symNode = "n" + tree.getCont();

            String aux = "";
            if (this.incrementDecrement.equalsIgnoreCase("Incremento")) {
                result = apNode + "[label=\"INCREMENTO-VARIABLE\"];\n";
                aux = "++";
            } else {
                result = apNode + "[label=\"DECREMENTO-VARIABLE\"];\n";
                aux = "--";
            }

            result += previous + " -> " + apNode + ";\n";

            result += idNode + "[label=\" " + this.id + "\"];\n";
            result += symNode + "[label=\" " + aux + "\"];\n";

            result += apNode + " -> " + idNode + ";\n";
            result += apNode + " -> " + symNode + ";\n";
        }

        return result;
    }

}
