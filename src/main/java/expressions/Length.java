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

        //ver lo de los lengths de arreglos de dos dimensiones
        return expressionToConvert.toString().length();
    }

}
