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
public class Print extends Instruction {
    
    private Instruction expression;

    public Print(Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        var resultado = this.expression.interpret(tree, table);
        if (resultado instanceof Error) {
            return resultado;
        }
        tree.Print(resultado.toString());
        return null;
    }
    
    
}
