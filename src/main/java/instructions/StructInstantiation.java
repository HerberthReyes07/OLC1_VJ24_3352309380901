/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.LinkedList;
import symbol.DataType;
import symbol.MutabilityType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import java.util.HashMap;
import symbol.Symbol;

/**
 *
 * @author herberthreyes
 */
public class StructInstantiation extends Instruction {

    private MutabilityType mutabilityType;
    private String id;
    private String structName;
    private LinkedList<StructValue> values;
    private boolean flag = false;

    public StructInstantiation(MutabilityType mutabilityType, String id, String structName, LinkedList<StructValue> values, int line, int column) {
        super(new Type(DataType.STRUCT), line, column);
        this.mutabilityType = mutabilityType;
        this.id = id;
        this.structName = structName;
        this.values = values;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var struct = tree.getStruct(this.structName);

        if (struct == null) {
            return new Error("SEMANTICO", "Instanciación de Struct Inválida: No puede instanciar un Struct que no existente", this.line, this.column);
        }

        if (struct.getValues().size() != this.values.size()) {
            return new Error("SEMANTICO", "Instanciación de Struct Inválida: Faltan valores en la instancia del Struct", this.line, this.column);
        }

        HashMap<String, Object> val = new HashMap<>();
        for (var a : this.values) {
            if (a == null) {
                continue;
            }
            a.setAuxStruct(struct);
            var res1 = a.interpret(tree, table);
            if (res1 instanceof Error) {
                return res1; //RETORNA EL ERROR
            }

            var aux = struct.getValues().get(a.getField().toLowerCase());
            if (aux == null) {
                return new Error("SEMANTICO", "Instanciación de Struct Inválida: El campo: " + a.getField().toLowerCase() + " inexistente en Struct: " + this.structName, this.line, this.column);
            }

            DataType aux2 = struct.getValuesTypes().get(a.getField().toLowerCase());
            if (a.type.getDataType() != aux2) {
                return new Error("SEMANTICO", "Instanciación de Struct Inválida: El campo: " + a.getField().toLowerCase() + " es del tipo " + aux2 + ", no puede asignarle un valor del tipo " + a.type.getDataType(), this.line, this.column);
            }

            if (a.type.getDataType() == DataType.STRUCT) {
                var sym = a.getAuxSym();
                if (sym != null) {
                    var structType = struct.getValuesStruct().get(a.getField().toLowerCase());
                    if (!sym.getStruct().getId().equals(structType.getId())) {
                        return new Error("SEMANTICO", "Instanciación de Struct Inválida: El campo: " + a.getField().toLowerCase() + " es del tipo Struct: " + structType.getId() + ", no puede asignarle un valor del tipo Struct: " + sym.getStruct().getId(), this.line, this.column);
                    }
                }
            }

            if (a.getValue() != null) {
                val.put(a.getField().toLowerCase(), a.getValue().interpret(tree, table));
            } else {
                val.put(a.getField().toLowerCase(), a.getAuxValue());
            }
        }

        if (!flag) {
            Symbol symbol;
            if (this.mutabilityType == MutabilityType.VAR) {
                symbol = new Symbol(this.type, this.id, val, struct, true, this.line, this.column);
            } else {
                symbol = new Symbol(this.type, this.id, val, struct, false, this.line, this.column);
            }

            boolean creation = table.setVariable(symbol);
            if (!creation) {
                return new Error("SEMANTICO", "Instanciación de Struct Inválida: No puede declarar una variable ya existente", this.line, this.column);
            }
        }

        return null;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
