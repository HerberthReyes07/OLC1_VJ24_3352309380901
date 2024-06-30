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

/**
 *
 * @author herberthreyes
 */
public class Continue extends Instruction {

    public Continue(int line, int column) {
        super(new Type(DataType.VOID), line, column);
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String cpNode = "n" + tree.getCont();
        String cNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = previous + " -> " + cpNode + ";\n";

        result += cpNode + "[label=\"SEN_CONTINUE\"];\n";
        result += cNode + "[label=\"continue\"];\n";
        result += pcNode + "[label=\";\"];\n";

        result += cpNode + " -> " + cNode + ";\n";
        result += cpNode + " -> " + pcNode + ";\n";
        return result;
    }
}
