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
public class Length extends Instruction {

    private Instruction expression;

    public Length(Instruction expression, int line, int column) {
        super(new Type(DataType.ENTERO), line, column);
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        Object expressionToConvert = this.expression.interpret(tree, table);
        if (expressionToConvert instanceof Error) {
            return expressionToConvert;
        }

        if (this.expression.type.getDataType() != DataType.CADENA && !(expressionToConvert instanceof LinkedList)) {
            return new Error("SEMANTICO", "Uso de función Length Inválido: No puede obtener el tamaño de un tipo de dato: " + this.expression.type.getDataType(), this.line, this.column);
        }

        if (expressionToConvert instanceof LinkedList) {
            LinkedList<Object> val = (LinkedList<Object>) expressionToConvert;
            return val.size();
        }

        return expressionToConvert.toString().length();
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String lgtpNode = "n" + tree.getCont();
        String lengthNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        
        String result = lgtpNode + "[label=\"FUNC_LENGTH\"];\n";
        result += previous + " -> " + lgtpNode + ";\n";
        
        result += lengthNode + "[label=\"length\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        
        result += lgtpNode + " -> " + lengthNode + ";\n";
        result += lgtpNode + " -> " + lpNode + ";\n";
        result += lgtpNode + " -> " + expNode + ";\n";
        result += lgtpNode + " -> " + rpNode + ";\n";
        
        result += this.expression.generateAST(tree, expNode);
        
        return result;

    }

}
