/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import symbol.Symbol;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import java.util.LinkedList;

/**
 *
 * @author herberthreyes
 */
public class DynamicListDeclaration extends Instruction {
    
    private String id;

    public DynamicListDeclaration(String id, Type type, int line, int column) {
        super(type, line, column);
        this.id = id;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        
        LinkedList<Object> val = new LinkedList<>();
        
        Symbol symbol = new Symbol(this.type, this.id, val, true, this.line, this.column);
        
        boolean creation = table.setVariable(symbol);
        if (!creation) {
            return new Error("SEMANTICO", "Declaración Inválida: No puede declarar una variable ya existente", this.line, this.column);
        }

        return null;
    }
    
    
    
}
