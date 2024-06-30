/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.LinkedList;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import symbol.DataType;
import symbol.MutabilityType;

/**
 *
 * @author herberthreyes
 */
public class StartWith extends Instruction {

    private String id;
    private LinkedList<Instruction> parameters;

    public StartWith(String id, LinkedList<Instruction> parameters, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.parameters = parameters;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var search = tree.getFunction(id);
        if (search == null) {
            return new Error("SEMANTICO", "Uso de función start_with Inválido. No puede llamar a una función inexistente", this.line, this.column);
        }

        if (search instanceof Method) {
            var method = (Method) search;

            var newTable = new SymbolTable(tree.getGlobalTable());
            newTable.setName(newTable.getPreviousTable().getName() + "-START_WITH-" + method.id);
            tree.getTables().add(newTable);

            if (parameters.size() != method.parameters.size()) {
                return new Error("SEMANTICO", "Uso de función start_with Inválido. Faltan parametros en la llamada de la función: " + id, this.line, this.column);
            }

            for (int i = 0; i < parameters.size(); i++) {
                var idMethod = (String) method.parameters.get(i).get("id");
                var value = this.parameters.get(i);
                var type2 = (Type) method.parameters.get(i).get("tipo");

                var parameterDeclaration = new Declaration(MutabilityType.CONST, idMethod, value, type2, this.line, this.column);

                var resultDeclaration = parameterDeclaration.interpret(tree, newTable);

                if (resultDeclaration instanceof Error) {
                    return resultDeclaration;
                }
            }
            var resultFunction = method.interpret(tree, newTable);
            if (resultFunction instanceof Error) {
                return resultFunction;
            }
        }

        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String swpNode = "n" + tree.getCont();
        String startWithNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String prmNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();
        
        String result = swpNode + "[label=\"FUNC_START_WITH\"];\n";
        result += previous + " -> " + swpNode + ";\n";
        
        result += startWithNode + "[label=\"start_with\"];\n";
        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        if (!parameters.isEmpty()) {
            result += prmNode + "[label=\"PARAMS\"];\n";
        }
        result += rpNode + "[label=\")\"];\n";
        result += pcNode + "[label=\";\"];\n";
        
        result += swpNode + " -> " + startWithNode + ";\n";
        result += swpNode + " -> " + idNode + ";\n";
        result += swpNode + " -> " + lpNode + ";\n";
        if (!parameters.isEmpty()) {
            result += swpNode + " -> " + prmNode + ";\n";
        }
        result += swpNode + " -> " + rpNode + ";\n";
        result += swpNode + " -> " + pcNode + ";\n";
        
        int cont = 0;
        for (var i : this.parameters) {
            if (i == null) {
                continue;
            }
            String nodoAux = "n" + tree.getCont();
            result += prmNode + " -> " + nodoAux + ";\n";
            result += nodoAux + "[label=\"EXPRESION\"];\n";
            result += i.generateAST(tree, nodoAux);
            
            cont++;
            if (cont + 1 <= this.parameters.size()) {
                String auxCmNode = "n" + tree.getCont();
                result += prmNode + " -> " + auxCmNode + ";\n";
                result += auxCmNode + "[label=\",\"];\n";
            }
        }
        
        return result;
    }

}
