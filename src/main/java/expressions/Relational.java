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
                return new Error("SEMANTICO", "Operador Relacional Inválido", this.line, this.column);
        }
    }

    private Object equalTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        return ((int) op1 == (int) op2);
                    case DECIMAL:
                        return (int) op1 == (double) op2;
                    case CARACTER:
                        return (int) op1 == op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Igualación (==) Inválida: no puede comparar los tipos ENTERO y " + type2, this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        return (double) op1 == (int) op2;
                    case DECIMAL:
                        return (double) op1 == (double) op2;
                    case CARACTER:
                        return (double) op1 == op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Igualación (==) Inválida: no puede comparar los tipos DECIMAL y " + type2, this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        return op1.toString().codePointAt(0) == (int) op2;
                    case DECIMAL:
                        return op1.toString().codePointAt(0) == (double) op2;
                    case CARACTER:
                        return op1.toString().codePointAt(0) == op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Igualación (==) Inválida: no puede comparar los tipos CARACTER y " + type2, this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        return op1.toString().equals(op2.toString());
                    default:
                        return new Error("SEMANTICO", "Igualación (==) Inválida: no puede comparar los tipos CADENA y " + type2, this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return (boolean) op1 == (boolean) op2;
                    default:
                        return new Error("SEMANTICO", "Igualación (==) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Igualación (==) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

    private Object notEqualTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        System.out.println("OP1: " + op1);
        System.out.println("OP2: " + op2);
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        return (int) op1 != (int) op2;
                    case DECIMAL:
                        return (int) op1 != (double) op2;
                    case CARACTER:
                        return (int) op1 != op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Diferenciación (!=) Inválida: no puede comparar los tipos ENTERO y " + type2, this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        return (double) op1 != (int) op2;
                    case DECIMAL:
                        return (double) op1 != (double) op2;
                    case CARACTER:
                        return (double) op1 != op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Diferenciación (!=) Inválida: no puede comparar los tipos DECIMAL y " + type2, this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        return op1.toString().codePointAt(0) != (int) op2;
                    case DECIMAL:
                        return op1.toString().codePointAt(0) != (double) op2;
                    case CARACTER:
                        return op1.toString().codePointAt(0) != op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Diferenciación (!=) Inválida: no puede comparar los tipos CARACTER y " + type2, this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        return !op1.toString().equals(op2.toString());
                    default:
                        return new Error("SEMANTICO", "Diferenciación (!=) Inválida: no puede comparar los tipos CADENA y " + type2, this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return (boolean) op1 != (boolean) op2;
                    default:
                        return new Error("SEMANTICO", "Diferenciación (!=) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Diferenciación (!=) Inválida: no puede comparar los tipos" + type1 + " y " + type2, this.line, this.column);
        }
    }

    private Object lessThan(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        return (int) op1 < (int) op2;
                    case DECIMAL:
                        return (int) op1 < (double) op2;
                    case CARACTER:
                        return (int) op1 < op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor que (<) Inválida: no puede comparar los tipos ENTERO y " + type2, this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        return (double) op1 < (int) op2;
                    case DECIMAL:
                        return (double) op1 < (double) op2;
                    case CARACTER:
                        return (double) op1 < op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor que (<) Inválida: no puede comparar los tipos DECIMAL y " + type2, this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        return op1.toString().codePointAt(0) < (int) op2;
                    case DECIMAL:
                        return op1.toString().codePointAt(0) < (double) op2;
                    case CARACTER:
                        return op1.toString().codePointAt(0) < op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor que (<) Inválida: no puede comparar los tipos CARACTER y " + type2, this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return ((boolean) op1 == false) && ((boolean) op2 == true);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor que (<) Inválida: no puede comparar los tipos BOOLEAN y " + type2, this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        return op1.toString().length() < op2.toString().length();
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor que (<) Inválida: no puede comparar los tipos CADENA y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Operación Relacional menor que (<) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

    private Object lessThanEqualTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        return (int) op1 <= (int) op2;
                    case DECIMAL:
                        return (int) op1 <= (double) op2;
                    case CARACTER:
                        return (int) op1 <= op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor igual que (<=) Inválida: no puede comparar los tipos ENTERO y " + type2, this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        return (double) op1 <= (int) op2;
                    case DECIMAL:
                        return (double) op1 <= (double) op2;
                    case CARACTER:
                        return (double) op1 <= op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor igual que (<=) Inválida: no puede comparar los tipos DECIMAL y " + type2, this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        return op1.toString().codePointAt(0) <= (int) op2;
                    case DECIMAL:
                        return op1.toString().codePointAt(0) <= (double) op2;
                    case CARACTER:
                        return op1.toString().codePointAt(0) <= op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor igual que (<=) Inválida: no puede comparar los tipos CARACTER y " + type2, this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return ((boolean) op1 == false && (boolean) op2 == true) || (boolean) op1 == (boolean) op2;
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor igual que (<=) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        return op1.toString().length() <= op2.toString().length();
                    default:
                        return new Error("SEMANTICO", "Operación Relacional menor igual que (<=) Inválida: no puede comparar los tipos CADENA y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Operación Relacional menor igual que (<=) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

    private Object greaterThan(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        return (int) op1 > (int) op2;
                    case DECIMAL:
                        return (int) op1 > (double) op2;
                    case CARACTER:
                        return (int) op1 > op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor que (>) Inválida: no puede comparar los tipos ENTERO y " + type2, this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        return (double) op1 > (int) op2;
                    case DECIMAL:
                        return (double) op1 > (double) op2;
                    case CARACTER:
                        return (double) op1 > op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor que (>) Inválida: no puede comparar los tipos DECIMAL y " + type2, this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        return op1.toString().codePointAt(0) > (int) op2;
                    case DECIMAL:
                        return op1.toString().codePointAt(0) > (double) op2;
                    case CARACTER:
                        return op1.toString().codePointAt(0) > op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor que (>) Inválida: no puede comparar los tipos CARACTER y " + type2, this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return (boolean) op1 == true && (boolean) op2 == false; 
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor que (>) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        return op1.toString().length() > op2.toString().length();
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor que (>) Inválida: no puede comparar los tipos CADENA y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Operación Relacional mayor que (>) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

    private Object greaterThanEqualTo(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        return (int) op1 >= (int) op2;
                    case DECIMAL:
                        return (int) op1 >= (double) op2;
                    case CARACTER:
                        return (int) op1 >= op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor igual que (>=) Inválida: no puede comparar los tipos ENTERO y " + type2, this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        return (double) op1 >= (int) op2;
                    case DECIMAL:
                        return (double) op1 >= (double) op2;
                    case CARACTER:
                        return (double) op1 >= op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor igual que (>=) Inválida: no puede comparar los tipos DECIMAL y " + type2, this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        return op1.toString().codePointAt(0) >= (int) op2;
                    case DECIMAL:
                        return op1.toString().codePointAt(0) >= (double) op2;
                    case CARACTER:
                        return op1.toString().codePointAt(0) >= op2.toString().codePointAt(0);
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor igual que (>=) Inválida: no puede comparar los tipos CARACTER y " + type2, this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return ((boolean) op1 == true && (boolean) op2 == false) || (boolean) op1 == (boolean) op2;
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor igual que (>=) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            case CADENA:
                switch (type2) {
                    case CADENA:
                        return op1.toString().length() >= op2.toString().length();
                    default:
                        return new Error("SEMANTICO", "Operación Relacional mayor igual que (>=) Inválida: no puede comparar los tipos CADENA y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Operación Relacional mayor igual que (>=) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

}
