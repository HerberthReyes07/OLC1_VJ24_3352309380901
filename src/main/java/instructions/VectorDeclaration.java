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
import symbol.MutabilityType;
import symbol.Symbol;

/**
 *
 * @author herberthreyes
 */
public class VectorDeclaration extends Instruction {

    private MutabilityType mutabilityType;
    private String id;
    private LinkedList<Instruction> values;
    private LinkedList<LinkedList<Instruction>> values2;

    public VectorDeclaration(MutabilityType mutabilityType, String id, Type type, LinkedList<Instruction> values, int line, int column) {
        super(type, line, column);
        this.mutabilityType = mutabilityType;
        this.id = id;
        this.values = values;
    }

    public VectorDeclaration(MutabilityType mutabilityType, String id, Type type, LinkedList<LinkedList<Instruction>> values2, boolean is2D, int line, int column) {
        super(type, line, column);
        this.mutabilityType = mutabilityType;
        this.id = id;
        this.values2 = values2;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        Object val = null;

        if (this.values != null) {

            LinkedList<Object> val1 = new LinkedList<>();

            for (var a : this.values) {
                if (a == null) {
                    continue;
                }
                var res1 = a.interpret(tree, table);
                if (res1 instanceof Error) {
                    return res1; //RETORNA EL ERROR
                }
                if (a.type.getDataType() != this.type.getDataType()) {
                    return new Error("SEMANTICO", "Declaración de Vector Inválida: No puede declarar un vector con el tipo " + this.type.getDataType() + " y tener un valor del tipo " + a.type.getDataType(), this.line, this.column);
                }
                val1.add(res1);
            }
            val = val1;
        } else {
            LinkedList<LinkedList<Object>> val2 = new LinkedList<>();
            
            for (int i = 0; i < this.values2.size(); i++) {
                LinkedList<Object> aux = new LinkedList<>();
                for (var a : this.values2.get(i)) {
                    if (a == null) {
                        continue;
                    }
                    var res1 = a.interpret(tree, table);
                    if (res1 instanceof Error) {
                        return res1; //RETORNA EL ERROR
                    }
                    if (a.type.getDataType() != this.type.getDataType()) {
                        return new Error("SEMANTICO", "Declaración de Vector Inválida: No puede declarar un vector con el tipo " + this.type.getDataType() + " y tener un valor del tipo " + a.type.getDataType(), this.line, this.column);
                    }
                    aux.add(res1);
                }
                val2.add(aux);
            }
            val = val2;
        }

        Symbol symbol;
        if (this.mutabilityType == MutabilityType.VAR) {
            symbol = new Symbol(this.type, this.id, val, true, this.line, this.column);
        } else {
            symbol = new Symbol(this.type, this.id, val, false, this.line, this.column);
        }

        boolean creation = table.setVariable(symbol);
        if (!creation) {
            return new Error("SEMANTICO", "Declaración Inválida: No puede declarar una variable ya existente", this.line, this.column);
        }

        return null;
    }

}
