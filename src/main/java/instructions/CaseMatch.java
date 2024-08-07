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
import expressions.Return;
import java.util.LinkedList;

/**
 *
 * @author herberthreyes
 */
public class CaseMatch extends Instruction {

    private Instruction condition;
    private LinkedList<Instruction> instructions;

    public CaseMatch(Instruction condition, LinkedList<Instruction> instructions, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.instructions = instructions;
        this.condition = condition;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var caseM = this.condition.interpret(tree, table);
        if (caseM instanceof exceptions.Error) {
            return caseM;
        }

        DataType typeE = this.condition.type.getDataType();
        if (typeE != DataType.VOID) {
            return executeCase(caseM, tree, table);
        }

        return new Error("SEMANTICO", "Operación Lógica en Caso Match Inválida", this.line, this.column);

    }

    private Object executeCase(Object caseM, Tree tree, SymbolTable table) {

        var newTable = new SymbolTable(table);
        newTable.setName(table.getName() + "-CASE");
        tree.getTables().add(newTable);
        for (var a : this.instructions) {
            if (a == null) {
                continue;
            }
            if (a instanceof Return) {
                a.interpret(tree, newTable);
                return a;
            }
            if (a instanceof Break || a instanceof Continue) {
                return a;
            }
            var res1 = a.interpret(tree, newTable);
            if (res1 instanceof Error) {
                return res1; //TERMINA LA SECUENCIA DEL IF
            }
            if (res1 instanceof Break || res1 instanceof Continue || res1 instanceof Return) {
                return res1;
            }
        }
        return null;
    }

    public Instruction getExpression() {
        return condition;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        return "";
    }

}
