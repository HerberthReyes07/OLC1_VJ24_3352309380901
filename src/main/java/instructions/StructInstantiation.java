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

    @Override
    public String generateAST(Tree tree, String previous) {
        
        String sipNode = "n" + tree.getCont();
        String mtpNode = "n" + tree.getCont();
        String mtNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String tpNode = "n" + tree.getCont();
        String snNode = "n" + tree.getCont();
        String igNode = "n" + tree.getCont();
        String lbNode = "n" + tree.getCont();
        String vNode = "n" + tree.getCont();
        String rbNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();
        
        
        String result = sipNode + "[label=\"INSTANCIACION-STRUCT\"];\n";
        result += previous + " -> " + sipNode + ";\n";

        result += mtpNode + "[label=\"MUTABILIDAD\"];\n";
        result += mtNode + "[label=\" " + getMutabilityType() + "\"];\n";
        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += tpNode + "[label=\":\"];\n";
        result += snNode + "[label=\" " + this.structName + "\"];\n";
        result += igNode + "[label=\"=\"];\n";
        result += lbNode + "[label=\"{\"];\n";
        result += vNode + "[label=\"VALORES_STRUCT\"];\n";
        result += rbNode + "[label=\"}\"];\n";
        result += pcNode + "[label=\";\"];\n";
        
        result += sipNode + " -> " + mtpNode + ";\n";
        result += mtpNode + " -> " + mtNode + ";\n";
        result += sipNode + " -> " + idNode + ";\n";
        result += sipNode + " -> " + tpNode + ";\n";
        result += sipNode + " -> " + snNode + ";\n";
        result += sipNode + " -> " + igNode + ";\n";
        result += sipNode + " -> " + lbNode + ";\n";
        result += sipNode + " -> " + vNode + ";\n";
        result += sipNode + " -> " + rbNode + ";\n";
        result += sipNode + " -> " + pcNode + ";\n";
        
        int cont = 0;
        for (var i : this.values) {
            if (i == null) {
                continue;
            }

            result += i.generateAST(tree, vNode);
            cont++;
            if (cont + 1 <= this.values.size()) {
                String auxCmNode = "n" + tree.getCont();
                result += vNode + " -> " + auxCmNode + ";\n";
                result += auxCmNode + "[label=\",\"];\n";
            }
        }
        
        return result;
    }
    
    private String getMutabilityType() {

        switch (this.mutabilityType) {
            case VAR:
                return "var";
            case CONST:
                return "const";
            default:
                return "";
        }
    }

}
