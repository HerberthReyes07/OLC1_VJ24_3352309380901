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

}
