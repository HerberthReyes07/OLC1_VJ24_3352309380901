/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressions;

import abstracto.Instruction;
import java.util.LinkedList;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import instructions.Declaration;
import instructions.Method;
import symbol.MutabilityType;

/**
 *
 * @author herberthreyes
 */
public class MethodCall extends Instruction {

    private String id;
    private LinkedList<Instruction> parameters;

    public MethodCall(String id, LinkedList<Instruction> parameters, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.id = id;
        this.parameters = parameters;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var search = tree.getFunction(id);
        if (search == null) {
            return new Error("SEMANTICO", "Llamada a método: " + this.id + " Inválida. No puede llamar a un método inexistente", this.line, this.column);
        }

        if (search instanceof Method) {
            var method = (Method) search;

            var newTable = new SymbolTable(tree.getGlobalTable());
            newTable.setName(newTable.getPreviousTable().getName() + "-LLAMADA METODO: " + method.id);
            tree.getTables().add(newTable);

            if (parameters.size() != method.parameters.size()) {
                return new Error("SEMANTICO", "Llamada a método: " + this.id + " Inválida. Faltan parámetros en la llamada", this.line, this.column);
            }

            for (int i = 0; i < parameters.size(); i++) {
                var idMethod = (String) method.parameters.get(i).get("id");
                var value = this.parameters.get(i);
                var type2 = (Type) method.parameters.get(i).get("tipo");

                var parameterDeclaration = new Declaration(MutabilityType.CONST, idMethod, type2, this.line, this.column);

                var resultDeclaration = parameterDeclaration.interpret(tree, newTable);
                if (resultDeclaration instanceof Error) {
                    return resultDeclaration;
                }

                var interpretedValue = value.interpret(tree, table);
                if (interpretedValue instanceof Error) {
                    return resultDeclaration;
                }

                var variable = newTable.getVariable(idMethod);
                if (variable == null) {
                    return new Error("SEMANTICO", "Llamada a método: " + this.id + " Inválida. Error en la declaración parámetros", this.line, this.column);
                }

                if (variable.getType().getDataType() != value.type.getDataType()) {
                    return new Error("SEMANTICO", "Llamada a método: " + this.id + " Inválida. El parámetro" + variable.getId() + " es del tipo: " + variable.getType().getDataType()
                            + ", no puede asignarle un valor del tipo: " + value.type.getDataType(), this.line, this.column);
                }

                variable.setValue(interpretedValue);
            }
            var resultFunction = method.interpret(tree, newTable);
            if (resultFunction instanceof Error) {
                return resultFunction;
            }
            this.type.setType(method.type.getDataType());
            return resultFunction;
        }

        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String mcpNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String prmNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        
        String result = mcpNode + "[label=\"LLAMADA_METODO\"];\n";
        result += previous + " -> " + mcpNode + ";\n";
        
        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        if (!parameters.isEmpty()) {
            result += prmNode + "[label=\"PARAMS\"];\n";
        }
        result += rpNode + "[label=\")\"];\n";
        
        result += mcpNode + " -> " + idNode + ";\n";
        result += mcpNode + " -> " + lpNode + ";\n";
        if (!parameters.isEmpty()) {
            result += mcpNode + " -> " + prmNode + ";\n";
        }
        result += mcpNode + " -> " + rpNode + ";\n";
        
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
