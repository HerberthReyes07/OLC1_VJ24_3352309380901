/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abstracto;

import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;

/**
 *
 * @author herberthreyes
 */
public abstract class Instruction {

    public Type type;
    public int line;
    public int column;

    public Instruction(Type type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public abstract Object interpret(Tree tree, SymbolTable table);
    
    public abstract String generateAST(Tree tree, String previous);

    @Override
    public String toString() {
        return "Instruction{" + "type=" + type + ", line=" + line + ", column=" + column + '}';
    }
    
}
