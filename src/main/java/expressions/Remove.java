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
public class Remove extends Instruction {

    private String id;
    private Instruction expression;

    public Remove(String id, Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(this.id);
        if (variable == null) {
            return new Error("SEMANTICO", "Remove Inválido: No puede quitar un valor de una lista inexistente",
                    this.line, this.column);
        }

        var access = this.expression.interpret(tree, table);
        if (this.expression.type.getDataType() != DataType.ENTERO) {
            return new Error("SEMANTICO", "Remove Inválido: No puede acceder a una posición inexistente de la lista",
                    this.line, this.column);
        }

        LinkedList<Object> val = (LinkedList<Object>) variable.getValue();

        int pos = (int) access;
        if (pos < 0 || pos > (val.size() - 1)) {
            return new Error("SEMANTICO", "Remove Inválido: No puede acceder a una posición inexistente de la lista",
                    this.line, this.column);
        }

        this.type.setType(variable.getType().getDataType());
        var aux = val.get(pos);
        val.remove(pos);
        variable.setValue(val);
        return aux;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        //QUITAR_VALOR_LISTA ::= ID:a . remove ( EXPRESION:b )
        
        String rmvpNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String pNode = "n" + tree.getCont();
        String removeNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        
        String result = rmvpNode + "[label=\"LIST-REMOVE\"];\n";
        result += previous + " -> " + rmvpNode + ";\n";

        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += pNode + "[label=\".\"];\n";
        result += removeNode + "[label=\"remove\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";

        result += rmvpNode + " -> " + idNode + ";\n";
        result += rmvpNode + " -> " + pNode + ";\n";
        result += rmvpNode + " -> " + removeNode + ";\n";
        result += rmvpNode + " -> " + lpNode + ";\n";
        result += rmvpNode + " -> " + expNode + ";\n";
        result += rmvpNode + " -> " + rpNode + ";\n";
        
        result += this.expression.generateAST(tree, expNode);
        return result;
    }

}
