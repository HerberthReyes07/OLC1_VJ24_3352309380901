/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressions;

import abstracto.Instruction;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;

/**
 *
 * @author herberthreyes
 */
public class Native extends Instruction {

    public Object value;

    public Native(Object value, Type type, int line, int column) {
        super(type, line, column);
        this.value = value;
    }

    @Override
    public Object interpret(Tree arbol, SymbolTable tabla) {
        return this.value;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String nativeNode = "n" + tree.getCont();
        String valueNode = "n" + tree.getCont();

        String result = previous + " -> " + nativeNode + ";\n";

        result += nativeNode + "[label=\"NATIVO\"];\n";
        result += valueNode + "[label=\""
                + this.value.toString() + "\"];\n";

        result += nativeNode + " -> " + valueNode + ";\n";
        return result;
    }

}
