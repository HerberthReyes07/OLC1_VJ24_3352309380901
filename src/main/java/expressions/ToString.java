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
public class ToString extends Instruction {

    private Instruction expression;

    public ToString(Instruction expression, int line, int column) {
        super(new Type(DataType.CADENA), line, column);
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var expressionToConvert = expression.interpret(tree, table);
        if (expressionToConvert instanceof Error) {
            return expressionToConvert;
        }

        /*if (expression.type.getDataType() == DataType.CADENA) {
            return new Error("SEMANTICO", "Uso de función ToString Inválido. No puede convertir un tipo de dato CADENA a CADENA", this.line, this.column);
        }*/

        if (expression instanceof VariableAccess) {
            var va  = (VariableAccess) expression;
            if (expression.type.getDataType() == DataType.STRUCT) {
                var variable = table.getVariable(va.getId());

                return variable.getStruct().getId() + " " + expressionToConvert.toString();
            }
        }

        return expressionToConvert.toString();
    }

}
