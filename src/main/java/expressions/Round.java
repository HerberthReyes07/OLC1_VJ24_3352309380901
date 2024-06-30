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
public class Round extends Instruction {

    private Instruction expression;

    public Round(Instruction expression, int line, int column) {
        super(new Type(DataType.ENTERO), line, column);
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        Object expressionToRound = this.expression.interpret(tree, table);
        if (expressionToRound instanceof Error) {
            return expressionToRound;
        }

        if (this.expression.type.getDataType() != DataType.DECIMAL && this.expression.type.getDataType() != DataType.ENTERO) {
            return new Error("SEMANTICO", "Uso de función Round Inválido. No puede redondear un tipo de dato: " + this.expression.type.getDataType(), this.line, this.column);
        }

        if (this.expression.type.getDataType() == DataType.ENTERO) {
            return (int) expressionToRound;
        }

        return Math.round((double) expressionToRound);
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        //ROUND ::= round ( EXPRESION )
        
        String rndpNode = "n" + tree.getCont();
        String roundNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        
        String result = rndpNode + "[label=\"FUNC_ROUND\"];\n";
        result += previous + " -> " + rndpNode + ";\n";
        
        result += roundNode + "[label=\"round\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        
        result += rndpNode + " -> " + roundNode + ";\n";
        result += rndpNode + " -> " + lpNode + ";\n";
        result += rndpNode + " -> " + expNode + ";\n";
        result += rndpNode + " -> " + rpNode + ";\n";
        
        result += this.expression.generateAST(tree, expNode);
        
        return result;
    }

}
