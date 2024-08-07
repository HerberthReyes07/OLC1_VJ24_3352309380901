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
public class DoWhile extends Instruction {

    private LinkedList<Instruction> instructions;
    private Instruction condition;

    public DoWhile(LinkedList<Instruction> instructions, Instruction condition, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.instructions = instructions;
        this.condition = condition;
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

        return executeDoWhile(tree, table);
    }

    private Object executeDoWhile(Tree tree, SymbolTable table) {

        do {
            var newTable = new SymbolTable(table);
            newTable.setName(table.getName() + "-DO_WHILE");

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
                    return res1; //TERMINA LA SECUENCIA DEL DO-WHILE
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
        } while ((boolean) this.condition.interpret(tree, table));

        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        //SENTENCIA_DO_WHILE ::= do { INSTRUCCIONES } while ( EXPRESION )
        String dwpNode = "n" + tree.getCont();
        String doNode = "n" + tree.getCont();
        String lbNode = "n" + tree.getCont();
        String inNode = "n" + tree.getCont();
        String rbNode = "n" + tree.getCont();
        String whileNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();
        
        String result = dwpNode + "[label=\"SEN_DO-WHILE\"];\n";
        result += previous + " -> " + dwpNode + ";\n";

        result += doNode + "[label=\"do\"];\n";
        result += lbNode + "[label=\"{\"];\n";
        result += inNode + "[label=\"INSTRUCCIONES_DO-WHILE\"];\n";
        result += rbNode + "[label=\"}\"];\n";
        result += whileNode + "[label=\"while\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += pcNode + "[label=\";\"];\n";
        
        result += dwpNode + " -> " + doNode + ";\n";
        result += dwpNode + " -> " + lbNode + ";\n";
        result += dwpNode + " -> " + inNode + ";\n";
        result += dwpNode + " -> " + rbNode + ";\n";
        result += dwpNode + " -> " + whileNode + ";\n";
        result += dwpNode + " -> " + lpNode + ";\n";
        result += dwpNode + " -> " + expNode + ";\n";
        result += dwpNode + " -> " + rpNode + ";\n";
        result += dwpNode + " -> " + pcNode + ";\n";
        
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
