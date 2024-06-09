/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressions;

import abstracto.Instruction;
import symbol.DataType;
import symbol.Operators;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;

/**
 *
 * @author herberthreyes
 */
public class Relational extends Instruction {

    private Instruction operand1;
    private Instruction operand2;
    private Operators operator;

    public Relational(Instruction operand1, Instruction operand2, Operators operator, int line, int column) {
        super(new Type(DataType.BOOLEANO), line, column);
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        Object opLeft = this.operand1.interpret(tree, table);
        if (opLeft instanceof exceptions.Error) {
            return opLeft;
        }
        Object opRight = this.operand2.interpret(tree, table);
        if (opRight instanceof exceptions.Error) {
            return opRight;
        }

        switch (this.operator) {
            case IGUALACION:
                return equalTo(opLeft, opRight);
            case DIFERENCIACION:
                return notEqualTo(opLeft, opRight);
            case MENOR_QUE:
                return lessThan(opLeft, opRight);
            case MENOR_IGUAL:
                return lessThanEqualTo(opLeft, opRight);
            case MAYOR_QUE:
                return greaterThan(opLeft, opRight);
            case MAYOR_IGUAL:
                return greaterThanEqualTo(opLeft, opRight);
            default:
                return new Error("Semantico", "Operador Relacional Invalido", this.line, this.column);
        }
    }

    private Object equalTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 == (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 == (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 == op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Igualacion Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 == (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 == (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 == op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Igualacion Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) == (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) == (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) == op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Igualacion Invalida", this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().equals(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Igualacion Invalida", this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().equalsIgnoreCase(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Igualacion Invalida", this.line, this.column);
                }
            default:
                return new Error("Semantico", "Igualacion Invalida", this.line, this.column);
        }
    }

    private Object notEqualTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 != (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 != (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 != op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 != (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 != (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 != op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) != (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) != (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) != op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.BOOLEANO);
                        if (!op1.toString().equals(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (!op1.toString().equalsIgnoreCase(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }
            default:
                return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
        }
    }

    private Object lessThan(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 < (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 < (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 < op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: menor que Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 < (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 < (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 < op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: menor que Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) < (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) < (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) < op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: menor que Invalida", this.line, this.column);
                }
            /*case CADENA:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.BOOLEANO);
                        if (!op1.toString().equals(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }*/
            default:
                return new Error("Semantico", "Operacion: menor que Invalida", this.line, this.column);
        }
    }

    private Object lessThanEqualTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 <= (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 <= (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 <= op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: menor igual que Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 <= (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 <= (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 <= op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: menor igual que Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) <= (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) <= (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) <= op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: menor igual que Invalida", this.line, this.column);
                }
            /*case CADENA:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.BOOLEANO);
                        if (!op1.toString().equals(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }*/
            default:
                return new Error("Semantico", "Operacion: menor igual que Invalida", this.line, this.column);
        }
    }

    private Object greaterThan(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 > (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 > (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 > op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: mayor que Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 > (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 > (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 > op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: mayor que Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) > (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) > (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) > op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: mayor que Invalida", this.line, this.column);
                }
            /*case CADENA:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.BOOLEANO);
                        if (!op1.toString().equals(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }*/
            default:
                return new Error("Semantico", "Operacion: mayor que Invalida", this.line, this.column);
        }
    }

    private Object greaterThanEqualTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 >= (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 >= (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((int) op1 >= op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: mayor igual que Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 >= (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 >= (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if ((double) op1 >= op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: mayor igual que Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) >= (int) op2) {
                            return true;
                        }
                        return false;
                    case DECIMAL:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) >= (double) op2) {
                            return true;
                        }
                        return false;
                    case CARACTER:
                        this.type = new Type(DataType.BOOLEANO);
                        if (op1.toString().codePointAt(0) >= op2.toString().codePointAt(0)) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Operacion: mayor igual que Invalida", this.line, this.column);
                }
            /*case CADENA:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.BOOLEANO);
                        if (!op1.toString().equals(op2.toString())) {
                            return true;
                        }
                        return false;
                    default:
                        return new Error("Semantico", "Diferenciacion Invalida", this.line, this.column);
                }*/
            default:
                return new Error("Semantico", "Operacion: mayor igual que Invalida", this.line, this.column);
        }
    }

}
