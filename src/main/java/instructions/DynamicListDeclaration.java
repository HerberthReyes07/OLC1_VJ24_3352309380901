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

    @Override
    public String generateAST(Tree tree, String previous) {
        //DECLARACION_LISTAS_DINAMICAS ::= list < TIPO > id = new list ( ) ;

        String dlpNode = "n" + tree.getCont();
        String listNode = "n" + tree.getCont();
        String lNode = "n" + tree.getCont();
        String typNode = "n" + tree.getCont();
        String tyNode = "n" + tree.getCont();
        String gNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String igNode = "n" + tree.getCont();
        String newNode = "n" + tree.getCont();
        String list2Node = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = dlpNode + "[label=\"DECLARACION-LISTA_DINAMICA\"];\n";
        result += previous + " -> " + dlpNode + ";\n";

        result += listNode + "[label=\"list\"];\n";
        result += lNode + "[label=\"<\"];\n";
        result += typNode + "[label=\"TIPO\"];\n";
        result += tyNode + "[label=\" " + getDataType() + "\"];\n";
        result += gNode + "[label=\">\"];\n";
        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += igNode + "[label=\"=\"];\n";
        result += newNode + "[label=\"new\"];\n";
        result += list2Node + "[label=\"list\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        result += rpNode + "[label=\")\"];\n";
        result += pcNode + "[label=\";\"];\n";
        
        
        result += dlpNode + " -> " + listNode + ";\n";
        result += dlpNode + " -> " + lNode + ";\n";
        result += dlpNode + " -> " + typNode + ";\n";
        result += typNode + " -> " + tyNode + ";\n";
        result += dlpNode + " -> " + gNode + ";\n";
        result += dlpNode + " -> " + idNode + ";\n";
        result += dlpNode + " -> " + igNode + ";\n";
        result += dlpNode + " -> " + newNode + ";\n";
        result += dlpNode + " -> " + list2Node + ";\n";
        result += dlpNode + " -> " + lpNode + ";\n";
        result += dlpNode + " -> " + rpNode + ";\n";
        result += dlpNode + " -> " + pcNode + ";\n";
        
        return result;
    }
    
    private String getDataType() {

        switch (this.type.getDataType()) {
            case ENTERO:
                return "int";
            case DECIMAL:
                return "double";
            case BOOLEANO:
                return "bool";
            case CARACTER:
                return "char";
            case CADENA:
                return "String";
            default:
                return "";
        }
    }

}
