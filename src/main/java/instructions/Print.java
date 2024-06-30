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

/**
 *
 * @author herberthreyes
 */
public class Print extends Instruction {

    private Instruction expression;

    public Print(Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        var result = this.expression.interpret(tree, table);
        if (result instanceof Error) {
            return result;
        }
        tree.Print(result.toString());
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        // PRINTT -> PRINT ( EXP ) ;
        String ppNode = "n" + tree.getCont();
        String pNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = ppNode + "[label=\"PRINTT\"];\n";
        result += previous + " -> " + ppNode + ";\n";

        result += pNode + "[label=\"println\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += pcNode + "[label=\";\"];\n";

        result += ppNode + " -> " + pNode + ";\n";
        result += ppNode + " -> " + lpNode + ";\n";
        result += ppNode + " -> " + expNode + ";\n";
        result += ppNode + " -> " + rpNode + ";\n";
        result += ppNode + " -> " + pcNode + ";\n";

        result += this.expression.generateAST(tree, expNode);

        return result;
    }

}
