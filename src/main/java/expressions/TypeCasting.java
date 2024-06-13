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

}
