/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import expressions.Native;
import symbol.MutabilityType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import symbol.Symbol;

/**
 *
 * @author herberthreyes
 */
public class Declaration extends Instruction {

    private MutabilityType mutabilityType;
    private String id;
    private Instruction value;

    public Declaration(MutabilityType mutabilityType, String id, Type type, int line, int column) {
        super(type, line, column);
        this.mutabilityType = mutabilityType;
        this.id = id;
    }

    public Declaration(MutabilityType mutabilityType, String id, Instruction value, Type type, int line, int column) {
        super(type, line, column);
        this.mutabilityType = mutabilityType;
        this.id = id;
        this.value = value;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        Object interpretedValue;

        if (this.value == null) {
            switch (this.type.getDataType()) {
                case ENTERO:
                    this.value = new Native(0, this.type, this.line, this.column);
                    break;
                case DECIMAL:
                    this.value = new Native(0.0, this.type, this.line, this.column);
                    break;
                case BOOLEANO:
                    this.value = new Native(true, this.type, this.line, this.column);
                    break;
                case CARACTER:
                    this.value = new Native('\u0000', this.type, this.line, this.column);
                    break;
                case CADENA:
                    this.value = new Native("", this.type, this.line, this.column);
                    break;
            }
            interpretedValue = this.value.interpret(tree, table);
        } else {
            interpretedValue = this.value.interpret(tree, table);

            if (interpretedValue instanceof Error) {
                return interpretedValue;
            }

            if (this.value.type.getDataType() != this.type.getDataType()) {
                return new Error("SEMANTICO", "Declaración Inválida: No puede declarar una variable con el tipo " + this.type.getDataType() + " y asignarle un valor del tipo " + this.value.type.getDataType(), this.line, this.column);
            }
        }

        Symbol symbol;
        if (this.mutabilityType == MutabilityType.VAR) {
            symbol = new Symbol(this.type, this.id, interpretedValue, true, this.line, this.column);
        } else {
            symbol = new Symbol(this.type, this.id, interpretedValue, false, this.line, this.column);
        }

        boolean creation = table.setVariable(symbol);
        if (!creation) {
            return new Error("SEMANTICO", "Declaración Inválida: No puede declarar una variable ya existente", this.line, this.column);
        }

        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        //DECLARACION ::= MUTABILIDAD id : TIPO
        //              | MUTABILIDAD id : TIPO = EXPRESION:d

        String dpNode = "n" + tree.getCont();
        String mtpNode = "n" + tree.getCont();
        String mtNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String tpNode = "n" + tree.getCont();
        String typNode = "n" + tree.getCont();
        String tyNode = "n" + tree.getCont();
        String igNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = dpNode + "[label=\"DECLARACION\"];\n";
        result += previous + " -> " + dpNode + ";\n";

        result += mtpNode + "[label=\"MUTABILIDAD\"];\n";
        result += mtNode + "[label=\" " + getMutabilityType() + "\"];\n";
        result += idNode + "[label=\" " + this.id + "\"];\n";
        result += tpNode + "[label=\":\"];\n";

        result += typNode + "[label=\"TIPO\"];\n";
        result += tyNode + "[label=\" " + getDataType() + "\"];\n";

        if (this.value != null) {
            result += igNode + "[label=\"=\"];\n";
            result += expNode + "[label=\"EXPRESION\"];\n";
        }

        result += pcNode + "[label=\";\"];\n";

        result += dpNode + " -> " + mtpNode + ";\n";
        result += mtpNode + " -> " + mtNode + ";\n";
        result += dpNode + " -> " + idNode + ";\n";
        result += dpNode + " -> " + tpNode + ";\n";
        result += dpNode + " -> " + typNode + ";\n";
        result += typNode + " -> " + tyNode + ";\n";
        if (this.value != null) {
            result += dpNode + " -> " + igNode + ";\n";
            result += dpNode + " -> " + expNode + ";\n";
            result += this.value.generateAST(tree, expNode);
        }
        result += dpNode + " -> " + pcNode + ";\n";

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
