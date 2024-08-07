/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.LinkedList;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import expressions.Return;

/**
 *
 * @author herberthreyes
 */
public class While extends Instruction {

    private Instruction condition;
    private LinkedList<Instruction> instructions;

    public While(Instruction condition, LinkedList<Instruction> instructions, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.instructions = instructions;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        var cond = this.condition.interpret(tree, table);

        if (cond instanceof Error) {
            return cond;
        }

        if (this.condition.type.getDataType() != DataType.BOOLEANO) {
            return new Error("SEMANTICO", "Condición en Sentencia While Inválida", this.line, this.column);
        }

        return executeWhile(tree, table);
    }

    private Object executeWhile(Tree tree, SymbolTable table) {

        while ((boolean) this.condition.interpret(tree, table)) {
            var newTable = new SymbolTable(table);
            newTable.setName(table.getName() + "-WHILE");
            for (var a : this.instructions) {
                if (a == null) {
                    continue;
                }
                if (a instanceof Return) {
                    a.interpret(tree, newTable);
                    return a;
                }
                if (a instanceof Break) {
                    return null;
                }
                if (a instanceof Continue) {
                    break;
                }
                var res1 = a.interpret(tree, newTable);
                if (res1 instanceof Error) {
                    return res1; //TERMINA LA SECUENCIA DEL WHILE
                }
                if (res1 instanceof Return) {
                    return res1;
                }
                if (res1 instanceof Break) {
                    return null;
                }
                if (res1 instanceof Continue) {
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        //SENTENCIA_WHILE ::= WHILE ( EXPRESION ) { INSTRUCCIONES }
        
        String wpNode = "n" + tree.getCont();
        String whileNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String lbNode = "n" + tree.getCont();
        String inNode = "n" + tree.getCont();
        String rbNode = "n" + tree.getCont();
        
        String result = wpNode + "[label=\"SEN_WHILE\"];\n";
        result += previous + " -> " + wpNode + ";\n";

        result += whileNode + "[label=\"while\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += lbNode + "[label=\"{\"];\n";
        result += inNode + "[label=\"INSTRUCCIONES_WHILE\"];\n";
        result += rbNode + "[label=\"}\"];\n";
        
        result += wpNode + " -> " + whileNode + ";\n";
        result += wpNode + " -> " + lpNode + ";\n";
        result += wpNode + " -> " + expNode + ";\n";
        result += wpNode + " -> " + rpNode + ";\n";
        result += wpNode + " -> " + lbNode + ";\n";
        result += wpNode + " -> " + inNode + ";\n";
        result += wpNode + " -> " + rbNode + ";\n";
        
        result += this.condition.generateAST(tree, expNode);
        
        for (var i : this.instructions) {
            if (i == null) {
                continue;
            }
            String nodoAux = "n" + tree.getCont();
            result += inNode + " -> " + nodoAux + ";\n";
            result += nodoAux + "[label=\"INSTRUCCION\"];\n";
            result += i.generateAST(tree, nodoAux);
        }
        return result;
    }

}
