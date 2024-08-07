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
public class Break extends Instruction {

    public Break(int line, int column) {
        super(new Type(DataType.VOID), line, column);
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String bpNode = "n" + tree.getCont();
        String bNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = previous + " -> " + bpNode + ";\n";

        result += bpNode + "[label=\"SEN_BREAK\"];\n";
        result += bNode + "[label=\"break\"];\n";
        result += pcNode + "[label=\";\"];\n";

        result += bpNode + " -> " + bNode + ";\n";
        result += bpNode + " -> " + pcNode + ";\n";
        return result;
    }

}
