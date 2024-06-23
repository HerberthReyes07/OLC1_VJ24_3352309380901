/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.HashMap;
import java.util.LinkedList;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;

/**
 *
 * @author herberthreyes
 */
public class Method extends Instruction {
    
    public String id;
    public LinkedList<HashMap> parameters;
    public LinkedList<Instruction> instructions;

    public Method(String id, LinkedList<HashMap> parameters, LinkedList<Instruction> instructions, Type type, int line, int column) {
        super(type, line, column);
        this.id = id;
        this.parameters = parameters;
        this.instructions = instructions;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        for (var a  : this.instructions) {
            a.interpret(tree, table);
            //resultado instancia de errores
            //return;
        }
        return null;
    }
    
}
