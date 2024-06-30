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
public class For extends Instruction {

    private Instruction assignation;
    private Instruction condition;
    private Instruction update;
    private LinkedList<Instruction> instructions;

    public For(Instruction assignation, Instruction condition, Instruction update, LinkedList<Instruction> instructions, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.assignation = assignation;
        this.condition = condition;
        this.update = update;
        this.instructions = instructions;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var newTable = new SymbolTable(table);
        newTable.setName(table.getName() + "-FOR");

        var asig = assignation.interpret(tree, newTable);
        if (asig instanceof Error) {
            return asig;
        }

        var cond = this.condition.interpret(tree, newTable);
        if (cond instanceof Error) {
            return cond;
        }

        if (this.condition.type.getDataType() != DataType.BOOLEANO) {
            return new Error("SEMANTICO", "Condición en Sentencia If Inválida", this.line, this.column);
        }

        return executeFor(tree, newTable);
        //return executeFor(tree, table);
    }

    private Object executeFor(Tree tree, SymbolTable newTable) {

        while ((boolean) this.condition.interpret(tree, newTable)) {
            //nuevo entorno
            var newTable2 = new SymbolTable(newTable);
            newTable2.setName(newTable.getName());
            //ejecutar instrucciones
            for (var a : this.instructions) {
                if (a == null) {
                    continue;
                }
                if (a instanceof Return) {
                    a.interpret(tree, newTable2);
                    return a;
                }
                if (a instanceof Break) {
                    return null;
                }
                if (a instanceof Continue) {
                    break;
                }
                var res1 = a.interpret(tree, newTable2);
                if (res1 instanceof Error) {
                    return res1; //TERMINA LA SECUENCIA DEL IF
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

            //actualizar la variable
            var act = this.update.interpret(tree, newTable);
            if (act instanceof Error) {
                return act;
            }
        }
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {

        //SENTENCIA_FOR ::= for ( ASIGNACION ; EXPRESION ; ACTUALIZA_FOR ) { INSTRUCCIONES:d }
        String fpNode = "n" + tree.getCont();
        String forNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();

        String assigNode = "n" + tree.getCont();
        String pcaNode = "n" + tree.getCont();
        String condNode = "n" + tree.getCont();
        String pccNode = "n" + tree.getCont();
        String updtNode = "n" + tree.getCont();

        String rpNode = "n" + tree.getCont();
        String lbNode = "n" + tree.getCont();
        String inNode = "n" + tree.getCont();
        String rbNode = "n" + tree.getCont();

        String result = fpNode + "[label=\"SEN_FOR\"];\n";
        result += previous + " -> " + fpNode + ";\n";

        result += forNode + "[label=\"for\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += assigNode + "[label=\"ASIGNACION\"];\n";
        result += pcaNode + "[label=\";\"];\n";
        result += condNode + "[label=\"CONDICION\"];\n";
        result += pccNode + "[label=\";\"];\n";
        result += updtNode + "[label=\"ACTUALIZACION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += lbNode + "[label=\"{\"];\n";
        result += inNode + "[label=\"INSTRUCCIONES_FOR\"];\n";
        result += rbNode + "[label=\"}\"];\n";

        result += fpNode + " -> " + forNode + ";\n";
        result += fpNode + " -> " + lpNode + ";\n";
        result += fpNode + " -> " + assigNode + ";\n";
        result += fpNode + " -> " + pcaNode + ";\n";
        result += fpNode + " -> " + condNode + ";\n";
        result += fpNode + " -> " + pccNode + ";\n";
        result += fpNode + " -> " + updtNode + ";\n";
        result += fpNode + " -> " + rpNode + ";\n";
        result += fpNode + " -> " + lbNode + ";\n";
        result += fpNode + " -> " + inNode + ";\n";
        result += fpNode + " -> " + rbNode + ";\n";

        result += removePC(this.assignation.generateAST(tree, assigNode));
        //result += this.assignation.generateAST(tree, assigNode);
        result += this.condition.generateAST(tree, condNode);
        //result += this.update.generateAST(tree, updtNode);
        result += removePC(this.update.generateAST(tree, updtNode));
        
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

    private String removePC(String input) {
        
        if (!input.contains("[label=\";\"];")) {
            return input;
        }
        
        String aux = input;
        String[] aux2 = aux.split("\n");
        String aux3 = null;
        String aux4 = "";
        for (var a : aux2) {
            if (aux3 == null) {
                if (a.contains("[label=\";\"];")) {
                    String[] b = a.split("\\[");
                    aux3 = b[0];
                } else {
                    aux4 += a + "\n";
                }
            } else {
                if (!a.contains(aux3)) {
                    aux4 += a + "\n";
                }
            }
        }
        return aux4;
    }
}
