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

    @Override
    public String generateAST(Tree tree, String previous) {
        /*
            ID:a [ EXPRESION:b ]  
            ID:a [ EXPRESION:b ] [ EXPRESION:c ]  
            
         */

        String apNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String ciNode = "n" + tree.getCont();
        String acc1Node = "n" + tree.getCont();
        String cdNode = "n" + tree.getCont();
        String ci2Node = "n" + tree.getCont();
        String acc2Node = "n" + tree.getCont();
        String cd2Node = "n" + tree.getCont();

        String result = apNode + "[label=\"ACCESO-VECTOR\"];\n";
        result += previous + " -> " + apNode + ";\n";

        result += idNode + "[label=\"" + this.id + "\"];\n";
        result += ciNode + "[label=\"[\"];\n";
        result += acc1Node + "[label=\"EXPRESION\"];\n";
        result += cdNode + "[label=\"]\"];\n";

        if (expression2 != null) {
            result += ci2Node + "[label=\"[\"];\n";
            result += acc2Node + "[label=\"EXPRESION\"];\n";
            result += cd2Node + "[label=\"]\"];\n";
        }

        result += apNode + " -> " + idNode + ";\n";
        result += apNode + " -> " + ciNode + ";\n";
        result += apNode + " -> " + acc1Node + ";\n";
        result += apNode + " -> " + cdNode + ";\n";

        if (expression2 != null) {
            result += apNode + " -> " + ci2Node + ";\n";
            result += apNode + " -> " + acc2Node + ";\n";
            result += apNode + " -> " + cd2Node + ";\n";
        }

        result += this.expression.generateAST(tree, acc1Node);
        if (expression2 != null) {
            result += this.expression2.generateAST(tree, acc2Node);
        }

        return result;

    }

}
