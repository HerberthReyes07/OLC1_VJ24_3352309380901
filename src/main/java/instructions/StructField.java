/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import symbol.Struct;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import symbol.DataType;

/**
 *
 * @author herberthreyes
 */
public class StructField extends Instruction {

    private String name;
    private String structName;

    public StructField(String name, Type type, int line, int column) {
        super(type, line, column);
        this.name = name;
    }

    public StructField(String name, String structName, int line, int column) {
        super(new Type(DataType.STRUCT), line, column);
        this.name = name;
        this.structName = structName;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        if (structName != null) {
            Struct struct = tree.getStruct(this.structName.toLowerCase());
            if (struct == null) {
                return new Error("SEMANTICO", "Declaración de Struct Inválida: No puede declarar un Struct con un tipo que no existente", this.line, this.column);
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public String getStructName() {
        return structName;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        /*
            CAMPO_STRUCT ::= ID:a : TIPO:b
                           | ID:a : ID:b
         */
        String sfpNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String tpNode = "n" + tree.getCont();

        String typNode = "n" + tree.getCont();
        String tyNode = "n" + tree.getCont();

        String snNode = "n" + tree.getCont();

        String pcNode = "n" + tree.getCont();

        String result = sfpNode + "[label=\"CAMPO_STRUCT\"];\n";
        result += previous + " -> " + sfpNode + ";\n";

        result += idNode + "[label=\" " + this.name + "\"];\n";
        result += tpNode + "[label=\":\"];\n";

        if (structName == null) {
            result += typNode + "[label=\"TIPO\"];\n";
            result += tyNode + "[label=\" " + getDataType() + "\"];\n";
        } else {
            result += snNode + "[label=\" " + this.structName + "\"];\n";
            result += typNode + "[label=\"TIPO\"];\n";
            result += tyNode + "[label=\" " + getDataType() + "\"];\n";
        }

        result += pcNode + "[label=\";\"];\n";

        result += sfpNode + " -> " + idNode + ";\n";
        result += sfpNode + " -> " + tpNode + ";\n";

        if (structName == null) {
            result += sfpNode + " -> " + typNode + ";\n";
            result += typNode + " -> " + tyNode + ";\n";
        } else {
            result += sfpNode + " -> " + snNode + ";\n";
            result += snNode + " -> " + typNode + ";\n";
            result += typNode + " -> " + tyNode + ";\n";
        }

        result += sfpNode + " -> " + pcNode + ";\n";

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
            case STRUCT:
                return "Struct";
            default:
                return "";
        }
    }

}
