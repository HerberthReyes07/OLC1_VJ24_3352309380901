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
import java.util.LinkedList;

/**
 *
 * @author herberthreyes
 */
public class CaseMatch extends Instruction {

    private Instruction expression;
    private LinkedList<Instruction> instructions;

    public CaseMatch(Instruction expression, LinkedList<Instruction> instructions, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.instructions = instructions;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var caseM = this.expression.interpret(tree, table);
        if (caseM instanceof exceptions.Error) {
            return caseM;
        }

        DataType typeE = this.expression.type.getDataType();
        if (typeE == DataType.ENTERO || typeE == DataType.DECIMAL || typeE == DataType.BOOLEANO || typeE == DataType.CARACTER || typeE == DataType.CADENA) {
            return executeCase(caseM, tree, table);
        }

        return new Error("Semantico", "Operacion Logica en Caso Match Invalida", this.line, this.column);

    }

    private Object executeCase(Object caseM, Tree tree, SymbolTable table) {

        Object res = null;
        for (var a : this.instructions) {
            res = a.interpret(tree, table);
        }
        return res;
    }

    public Instruction getExpression() {
        return expression;
    }

}
