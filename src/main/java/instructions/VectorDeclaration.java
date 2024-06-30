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

    @Override
    public String generateAST(Tree tree, String previous) {
        /*
        DECLARACION_VECTORES_1D ::= MUTABILIDAD ID : TIPO [ ] = [ LISTA_VALORES_V1D ] ;
        DECLARACION_VECTORES_2D ::= MUTABILIDAD ID : TIPO [ ] [ ] = [ LISTA_VALORES_V2D ] ;
         */

        String dpNode = "n" + tree.getCont();
        String mtpNode = "n" + tree.getCont();
        String mtNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String tpNode = "n" + tree.getCont();
        String typNode = "n" + tree.getCont();
        String tyNode = "n" + tree.getCont();

        String ciNode = "n" + tree.getCont();
        String cdNode = "n" + tree.getCont();
        String ci2dNode = "n" + tree.getCont();
        String cd2dNode = "n" + tree.getCont();

        String igNode = "n" + tree.getCont();

        String ci2Node = "n" + tree.getCont();
        String vlNode = "n" + tree.getCont();
        String cd2Node = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = dpNode + "[label=\"DECLARACION-VECTOR\"];\n";
        result += previous + " -> " + dpNode + ";\n";

        result += mtpNode + "[label=\"MUTABILIDAD\"];\n";
        result += mtNode + "[label=\" " + getMutabilityType() + "\"];\n";
        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += tpNode + "[label=\":\"];\n";
        result += typNode + "[label=\"TIPO\"];\n";
        result += tyNode + "[label=\" " + getDataType() + "\"];\n";

        result += ciNode + "[label=\"[\"];\n";
        result += cdNode + "[label=\"]\"];\n";

        if (this.values == null) {
            result += ci2dNode + "[label=\"[\"];\n";
            result += cd2dNode + "[label=\"]\"];\n";
        }

        result += igNode + "[label=\"=\"];\n";

        result += ci2Node + "[label=\"[\"];\n";
        result += vlNode + "[label=\"LISTA_VALORES\"];\n";
        result += cd2Node + "[label=\"]\"];\n";
        result += pcNode + "[label=\";\"];\n";

        result += dpNode + " -> " + mtpNode + ";\n";
        result += mtpNode + " -> " + mtNode + ";\n";
        result += dpNode + " -> " + idNode + ";\n";
        result += dpNode + " -> " + tpNode + ";\n";
        result += dpNode + " -> " + typNode + ";\n";
        result += typNode + " -> " + tyNode + ";\n";

        result += dpNode + " -> " + ciNode + ";\n";
        result += dpNode + " -> " + cdNode + ";\n";

        if (this.values == null) {
            result += dpNode + " -> " + ci2dNode + ";\n";
            result += dpNode + " -> " + cd2dNode + ";\n";
        }

        result += dpNode + " -> " + igNode + ";\n";

        result += dpNode + " -> " + ci2Node + ";\n";
        result += dpNode + " -> " + vlNode + ";\n";
        result += dpNode + " -> " + cd2Node + ";\n";
        result += dpNode + " -> " + pcNode + ";\n";

        int cont = 0;

        if (this.values != null) {
            for (var i : values) {
                if (i == null) {
                    continue;
                }
                String nodoAux = "n" + tree.getCont();
                result += vlNode + " -> " + nodoAux + ";\n";
                result += nodoAux + "[label=\"VALOR\"];\n";
                result += i.generateAST(tree, nodoAux);

                cont++;
                if (cont + 1 <= this.values.size()) {
                    String auxCmNode = "n" + tree.getCont();
                    result += vlNode + " -> " + auxCmNode + ";\n";
                    result += auxCmNode + "[label=\",\"];\n";
                }
            }
        } else {
            int cont2 = 0;
            for (var i : values2) {
                if (i == null) {
                    continue;
                }
                //int cont2 = 0;
                String ciAuxNode = "n" + tree.getCont();
                result += ciAuxNode + "[label=\"[\"];\n";
                result += vlNode + " -> " + ciAuxNode + ";\n";

                for (var j : i) {
                    String nodoAux = "n" + tree.getCont();
                    result += vlNode + " -> " + nodoAux + ";\n";
                    result += nodoAux + "[label=\"VALOR\"];\n";
                    result += j.generateAST(tree, nodoAux);

                    cont++;
                    if (cont + 1 <= i.size()) {
                        String auxCmNode = "n" + tree.getCont();
                        result += vlNode + " -> " + auxCmNode + ";\n";
                        result += auxCmNode + "[label=\",\"];\n";
                    }
                }
                cont = 0;

                String cdAuxNode = "n" + tree.getCont();
                result += cdAuxNode + "[label=\"]\"];\n";
                result += vlNode + " -> " + cdAuxNode + ";\n";

                cont2++;
                if (cont2 + 1 <= values2.size()) {
                    String auxCm2Node = "n" + tree.getCont();
                    result += vlNode + " -> " + auxCm2Node + ";\n";
                    result += auxCm2Node + "[label=\",\"];\n";
                }
            }
        }

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
