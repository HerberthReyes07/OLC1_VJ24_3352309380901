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
import exceptions.Error;
import java.util.LinkedList;

/**
 *
 * @author herberthreyes
 */
public class Append extends Instruction {

    private String id;
    private Instruction expression;

    public Append(String id, Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.expression = expression;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var variable = table.getVariable(id);
        if (variable == null) {
            return new Error("SEMANTICO", "Append Inválido: No puede agregar un valor a una lista inexistente",
                    this.line, this.column);
        }

        var newValue = this.expression.interpret(tree, table);
        if (newValue instanceof Error) {
            return newValue;
        }

        if (variable.getType().getDataType() != this.expression.type.getDataType()) {
            return new Error("SEMANTICO", "Append Inválido: No puede agregar a una lista declarada con el tipo " + variable.getType().getDataType() + " un valor del tipo " + this.expression.type.getDataType(),
                    this.line, this.column);
        }

        LinkedList<Object> val = (LinkedList<Object>) variable.getValue();

        val.add(newValue);
        variable.setValue(val);
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        //AGREGAR_VALOR_LISTA ::= ID:a . append ( EXPRESION:b );
        String apNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String pNode = "n" + tree.getCont();
        String appendNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = apNode + "[label=\"LIST-APPEND\"];\n";
        result += previous + " -> " + apNode + ";\n";

        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += pNode + "[label=\".\"];\n";
        result += appendNode + "[label=\"append\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += expNode + "[label=\"EXPRESION\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += pcNode + "[label=\";\"];\n";

        result += apNode + " -> " + idNode + ";\n";
        result += apNode + " -> " + pNode + ";\n";
        result += apNode + " -> " + appendNode + ";\n";
        result += apNode + " -> " + lpNode + ";\n";
        result += apNode + " -> " + expNode + ";\n";
        result += apNode + " -> " + rpNode + ";\n";
        result += apNode + " -> " + pcNode + ";\n";
        
        result += this.expression.generateAST(tree, expNode);
        return result;
    }

}
