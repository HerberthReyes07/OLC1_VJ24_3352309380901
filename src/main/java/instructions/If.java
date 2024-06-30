/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import expressions.Return;
import java.util.LinkedList;
import symbol.DataType;

/**
 *
 * @author herberthreyes
 */
public class If extends Instruction {

    private Instruction condition;
    private LinkedList<Instruction> instructionsIf;
    private LinkedList<Instruction> instructionsElse;
    private Instruction elseIf;

    //IF
    public If(Instruction condition, LinkedList<Instruction> instructionsIf, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.instructionsIf = instructionsIf;
    }

    //IF-ELSE
    public If(Instruction condition, LinkedList<Instruction> instructionsIf, LinkedList<Instruction> instructionsElse, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.instructionsIf = instructionsIf;
        this.instructionsElse = instructionsElse;
    }

    //IF-ELSE-IF
    public If(Instruction condition, LinkedList<Instruction> instructionsIf, Instruction elseIf, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.condition = condition;
        this.instructionsIf = instructionsIf;
        this.elseIf = elseIf;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var cond = this.condition.interpret(tree, table);
        if (cond instanceof Error) {
            return cond;
        }

        if (this.condition.type.getDataType() != DataType.BOOLEANO) {
            return new Error("SEMANTICO", "Condición en Sentencia If Inválida", this.line, this.column);
        }
        return executeIf(cond, tree, table);
    }

    private Object executeIf(Object condition, Tree tree, SymbolTable table) {

        if ((boolean) condition) {
            var newTable = new SymbolTable(table);
            newTable.setName(table.getName() + "-IF");
            tree.getTables().add(newTable);
            for (var a : this.instructionsIf) {
                if (a == null) {
                    continue;
                }
                if (a instanceof Return) {
                    a.interpret(tree, newTable);
                    return a;
                }
                if (a instanceof Break || a instanceof Continue) {
                    return a;
                }
                var res1 = a.interpret(tree, newTable);
                if (res1 instanceof Error) {
                    return res1; //TERMINA LA SECUENCIA DEL IF
                }
                if (res1 instanceof Break || res1 instanceof Continue || res1 instanceof Return) {
                    return res1;
                }
            }
            //tree.getTables().add(newTable);
        } else {
            if (this.instructionsElse != null) {
                var newTable2 = new SymbolTable(table);
                newTable2.setName(table.getName() + "-ELSE");
                tree.getTables().add(newTable2);
                for (var a : this.instructionsElse) {
                    if (a == null) {
                        continue;
                    }
                    if (a instanceof Return) {
                        a.interpret(tree, newTable2);
                        return a;
                    }
                    if (a instanceof Break || a instanceof Continue) {
                        return a;
                    }
                    var res2 = a.interpret(tree, newTable2);
                    if (res2 instanceof Error) {
                        return res2;
                    }
                    if (res2 instanceof Break || res2 instanceof Continue || res2 instanceof Return) {
                        return res2;
                    }
                }
                //tree.getTables().add(newTable2);
            } else if (this.elseIf != null) {
                var res3 = elseIf.interpret(tree, table);
                if (res3 instanceof Error) {
                    return res3;
                }
                if (res3 instanceof Break || res3 instanceof Continue || res3 instanceof Return) {
                    return res3;
                }
            }
        }
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {

        //SEN_IF -> IF ( EXPRESION ) { INSTRUCCIONES }
        String ipNode = "n" + tree.getCont();
        String ifNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String lbIfNode = "n" + tree.getCont();
        String inIfNode = "n" + tree.getCont();
        String rbIfNode = "n" + tree.getCont();

        String result = ipNode + "[label=\"SEN_IF\"];\n";
        result += previous + " -> " + ipNode + ";\n";

        result += ifNode + "[label=\"if\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += lbIfNode + "[label=\"{\"];\n";
        result += inIfNode + "[label=\"INSTRUCCIONES_IF\"];\n";
        result += rbIfNode + "[label=\"}\"];\n";

        result += ipNode + " -> " + ifNode + ";\n";
        result += ipNode + " -> " + lpNode + ";\n";
        result += ipNode + " -> " + expNode + ";\n";
        result += ipNode + " -> " + rpNode + ";\n";
        result += ipNode + " -> " + lbIfNode + ";\n";
        result += ipNode + " -> " + inIfNode + ";\n";
        result += ipNode + " -> " + rbIfNode + ";\n";

        result += this.condition.generateAST(tree, expNode);

        for (var i : this.instructionsIf) {
            if (i == null) {
                continue;
            }
            String nodoAux = "n" + tree.getCont();
            result += inIfNode + " -> " + nodoAux + ";\n";
            result += nodoAux + "[label=\"INSTRUCCION\"];\n";
            result += i.generateAST(tree, nodoAux);
        }

        //SEN_IF -> IF ( EXPRESION ) { INSTRUCCIONES } ELSE { INSTRUCCIONES }
        if (this.instructionsElse != null) {
            
            String elseNode = "n" + tree.getCont();
            String lbElseNode = "n" + tree.getCont();
            String inElseNode = "n" + tree.getCont();
            String rbElseNode = "n" + tree.getCont();
            
            result += elseNode + "[label=\"else\"];\n";
            result += lbElseNode + "[label=\"{\"];\n";
            result += inElseNode + "[label=\"INSTRUCCIONES_ELSE\"];\n";
            result += rbElseNode + "[label=\"}\"];\n";
            
            result += ipNode + " -> " + elseNode + ";\n";
            result += ipNode + " -> " + lbElseNode + ";\n";
            result += ipNode + " -> " + inElseNode + ";\n";
            result += ipNode + " -> " + rbElseNode + ";\n";

            for (var i : this.instructionsElse) {
                if (i == null) {
                    continue;
                }
                String nodoAux = "n" + tree.getCont();
                result += inElseNode + " -> " + nodoAux + ";\n";
                result += nodoAux + "[label=\"INSTRUCCION\"];\n";
                result += i.generateAST(tree, nodoAux);
            }
        }
        
        //SEN_IF -> IF ( EXPRESION ) { INSTRUCCIONES } ELSE IF ( EXPRESION ) { INSTRUCCIONES }
        if (this.elseIf != null) {
            String elseNode = "n" + tree.getCont();
            result += elseNode + "[label=\"else\"];\n";
            result += ipNode + " -> " + elseNode + ";\n";
            result += this.elseIf.generateAST(tree, elseNode);
        }
        
        
        return result;
    }

}
