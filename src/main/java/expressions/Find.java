/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressions;

import abstracto.Instruction;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import java.util.LinkedList;

/**
 *
 * @author herberthreyes
 */
public class Find extends Instruction {

    private String id;
    private Instruction expression;

    public Find(String id, Instruction expression, int line, int column) {
        super(new Type(DataType.BOOLEANO), line, column);
        this.id = id;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(id);
        if (variable == null) {
            return new Error("SEMANTICO", "Uso de función Find Inválido: No puede buscar un valor en una lista inexistente",
                    this.line, this.column);
        }

        var searchValue = this.expression.interpret(tree, table);
        if (searchValue instanceof Error) {
            return searchValue;
        }

        if (variable.getType().getDataType() != this.expression.type.getDataType()) {
            return new Error("SEMANTICO", "Uso de función Find Inválido: No puede buscar en una lista declarada con el tipo " + variable.getType().getDataType() + " un valor del tipo " + this.expression.type.getDataType(),
                    this.line, this.column);
        }

        if (!(variable.getValue() instanceof LinkedList)) {
            return new Error("SEMANTICO", "Uso de función Find Inválido: No puede buscar un valor en un tipo de dato: " + this.expression.type.getDataType(), this.line, this.column);
        }

        LinkedList<Object> val = (LinkedList<Object>) variable.getValue();

        if (!val.isEmpty()) {
            if (val.get(0) instanceof LinkedList) {
                for (var a : val) {
                    LinkedList<Object> aux = (LinkedList<Object>) a;
                    for (var b : aux) {
                        if (b.equals(searchValue)) {
                            return true;
                        }
                    }
                }
            } else {
                for (var a : val) {
                    if (a.equals(searchValue)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String fpNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String pNode = "n" + tree.getCont();
        String findNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();

        String result = fpNode + "[label=\"FUNC-FIND\"];\n";
        result += previous + " -> " + fpNode + ";\n";

        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += pNode + "[label=\".\"];\n";
        result += findNode + "[label=\"find\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";

        result += fpNode + " -> " + idNode + ";\n";
        result += fpNode + " -> " + pNode + ";\n";
        result += fpNode + " -> " + findNode + ";\n";
        result += fpNode + " -> " + lpNode + ";\n";
        result += fpNode + " -> " + expNode + ";\n";
        result += fpNode + " -> " + rpNode + ";\n";
        
        result += this.expression.generateAST(tree, expNode);
        return result;
    }

}
