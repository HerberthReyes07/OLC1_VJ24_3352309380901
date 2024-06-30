/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressions;

import abstracto.Instruction;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import symbol.DataType;

/**
 *
 * @author herberthreyes
 */
public class TypeCasting extends Instruction {

    private Type typeCasting;
    private Instruction expression;

    public TypeCasting(Type typeCasting, Instruction expression, int line, int column) {
        super(typeCasting, line, column);
        this.typeCasting = typeCasting;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        Object expressionToCast = this.expression.interpret(tree, table);
        if (expressionToCast instanceof Error) {
            return expressionToCast;
        }

        switch (this.typeCasting.getDataType()) {
            case ENTERO:
                return castToInteger(expressionToCast);
            case DECIMAL:
                return castToDouble(expressionToCast);
            case CARACTER:
                return castToChar(expressionToCast);
            default:
                return new Error("SEMANTICO", "Casteo Inválido", this.line, this.column);
        }
    }

    private Object castToInteger(Object expressionToCast) {

        DataType dataType = this.expression.type.getDataType();

        switch (dataType) {
            case DECIMAL:
                double aux = (double) expressionToCast;
                int aux2 = (int) aux;
                return aux2;
            case CARACTER:
                return expressionToCast.toString().codePointAt(0);
            default:
                return new Error("SEMANTICO", "Casteo Inválido: de tipo " + dataType + " a tipo ENTERO", this.line, this.column);
        }
    }

    private Object castToDouble(Object expressionToCast) {

        DataType dataType = this.expression.type.getDataType();

        switch (dataType) {
            case ENTERO:
                double aux1 = (int) expressionToCast;
                return aux1;
            case CARACTER:
                double aux2 = expressionToCast.toString().codePointAt(0);
                return aux2;
            default:
                return new Error("SEMANTICO", "Casteo Inválido: de tipo " + dataType + " a tipo DECIMAL", this.line, this.column);
        }
    }

    private Object castToChar(Object expressionToCast) {

        DataType dataType = this.expression.type.getDataType();

        switch (dataType) {
            case ENTERO:
                int aux = (int) expressionToCast;
                return (char) aux;
            default:
                return new Error("SEMANTICO", "Casteo Inválido: de tipo " + dataType + " a tipo CARACTER", this.line, this.column);
        }
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        //CASTEO ::= ( TIPO ) EXPRESION

        String cpNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String tyNode = "n" + tree.getCont();
        String auxTyNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();

        String result = previous + " -> " + cpNode + ";\n";
        result += cpNode + " -> " + lpNode + ";\n";
        result += cpNode + " ->" + tyNode + ";\n";
        result += tyNode + " -> " + auxTyNode + ";\n";
        result += cpNode + " ->" + rpNode + ";\n";
        result += cpNode + " ->" + expNode + ";\n";

        result += cpNode + "[label=\"CASTEO\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += tyNode + "[label=\"TIPO\"];\n";
        result += auxTyNode + "[label=\" " + getDataType() + "\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";

        result += this.expression.generateAST(tree, expNode);
        return result;
    }

    private String getDataType() {

        switch (this.type.getDataType()) {
            case ENTERO:
                return "int";
            case DECIMAL:
                return "double";
            case BOOLEANO:
                return "bool";
            case CARACTER:
                return "char";
            case CADENA:
                return "String";
            default:
                return "";
        }
    }
}
