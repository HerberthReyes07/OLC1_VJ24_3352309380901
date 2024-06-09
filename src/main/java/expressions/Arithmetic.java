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
public class Arithmetic extends Instruction {

    private Instruction operand1;
    private Instruction operand2;
    private Operators operator;
    private Instruction uniqueOperand;

    public Arithmetic(Instruction operand1, Operators operator, int line, int column) {
        super(new Type(DataType.ENTERO), line, column);
        this.uniqueOperand = operand1;
        this.operator = operator;
    }

    public Arithmetic(Instruction operand1, Instruction operand2, Operators operator, int line, int column) {
        super(new Type(DataType.ENTERO), line, column);
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        Object opLeft = null;
        Object opRight = null;
        Object unique = null;

        if (this.uniqueOperand != null) {
            unique = this.uniqueOperand.interpret(tree, table);
            if (unique instanceof Error) {
                return unique;
            }
        } else {
            opLeft = this.operand1.interpret(tree, table);
            if (opLeft instanceof Error) {
                return opLeft;
            }
            opRight = this.operand2.interpret(tree, table);
            if (opRight instanceof Error) {
                return opRight;
            }
        }

        switch (this.operator) {
            case SUMA:
                return addition(opLeft, opRight);
            case RESTA:
                return substraction(opLeft, opRight);
            case MULTIPLICACION:
                return multiplication(opLeft, opRight);
            case DIVISION:
                return division(opLeft, opRight);
            case MODULO:
                return modulus(opLeft, opRight);
            case POTENCIA:
                return pow(opLeft, opRight);
            case NEGACION:
                return negation(unique);
            default:
                return new Error("Semantico", "Operador Aritmetico Invalido", this.line, this.column);
        }
    }

    private Object addition(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.ENTERO);
                        return (int) op1 + (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 + (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.ENTERO);
                        return (int) op1 + op2.toString().codePointAt(0);
                    case CADENA:
                        this.type = new Type(DataType.CADENA);
                        return op1.toString() + op2.toString();
                    default:
                        return new Error("Semantico", "Suma Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 + (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 + (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 + op2.toString().codePointAt(0);
                    case CADENA:
                        this.type = new Type(DataType.CADENA);
                        return op1.toString() + op2.toString();
                    default:
                        return new Error("Semantico", "Suma Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.ENTERO);
                        return op1.toString().codePointAt(0) + (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return op1.toString().codePointAt(0) + (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.CADENA);
                        return op1.toString() + op2.toString();
                    case CADENA:
                        this.type = new Type(DataType.CADENA);
                        return op1.toString() + op2.toString();
                    default:
                        return new Error("Semantico", "Suma Invalida", this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.CADENA);
                        return op1.toString() + op2.toString();
                    default:
                        return new Error("Semantico", "Suma Invalida", this.line, this.column);
                }
            case CADENA:
                this.type = new Type(DataType.CADENA);
                return op1.toString() + op2.toString();
            default:
                return new Error("Semantico", "Suma Invalida", this.line, this.column);
        }

    }

    private Object substraction(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.ENTERO);
                        return (int) op1 - (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 - (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.ENTERO);
                        return (int) op1 - op2.toString().codePointAt(0);
                    default:
                        return new Error("Semantico", "Resta Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 - (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 - (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 - op2.toString().codePointAt(0);
                    default:
                        return new Error("Semantico", "Resta Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.ENTERO);
                        return op1.toString().codePointAt(0) - (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return op1.toString().codePointAt(0) - (double) op2;
                    default:
                        return new Error("Semantico", "Resta Invalida", this.line, this.column);
                }
            default:
                return new Error("Semantico", "Resta Invalida", this.line, this.column);
        }

    }

    private Object multiplication(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.ENTERO);
                        return (int) op1 * (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 * (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.ENTERO);
                        return (int) op1 * op2.toString().codePointAt(0);
                    default:
                        return new Error("Semantico", "Multiplicacion Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 * (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 * (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 * op2.toString().codePointAt(0);
                    default:
                        return new Error("Semantico", "Multiplicacion Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.ENTERO);
                        return op1.toString().codePointAt(0) * (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return op1.toString().codePointAt(0) * (double) op2;
                    default:
                        return new Error("Semantico", "Multiplicacion Invalida", this.line, this.column);
                }
            default:
                return new Error("Semantico", "Multiplicacion Invalida", this.line, this.column);
        }

    }

    private Object division(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 / (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 / (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 / op2.toString().codePointAt(0);
                    default:
                        return new Error("Semantico", "Division Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 / (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 / (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 / op2.toString().codePointAt(0);
                    default:
                        return new Error("Semantico", "Division Invalida", this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return op1.toString().codePointAt(0) / (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return op1.toString().codePointAt(0) / (double) op2;
                    default:
                        return new Error("Semantico", "Division Invalida", this.line, this.column);
                }
            default:
                return new Error("Semantico", "Division Invalida", this.line, this.column);
        }

    }

    private Object pow(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.ENTERO);
                        return (int) Math.pow((int) op1, (int) op2);
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return Math.pow((int) op1, (double) op2);
                    default:
                        return new Error("Semantico", "Pontenciacion Invalida", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return Math.pow((double) op1, (int) op2);
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return Math.pow((double) op1, (double) op2);
                    default:
                        return new Error("Semantico", "Pontenciacion Invalida", this.line, this.column);
                }
            default:
                return new Error("Semantico", "Pontenciacion Invalida", this.line, this.column);
        }

    }

    private Object modulus(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 % (int) op1;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 % (double) op2;
                    default:
                        return new Error("Semantico", "Modulo Invalido", this.line, this.column);
                }
            case DECIMAL:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 % (int) op2;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (double) op1 % (double) op2;
                    default:
                        return new Error("Semantico", "Modulo Invalido", this.line, this.column);
                }
            default:
                return new Error("Semantico", "Modulo Invalido", this.line, this.column);
        }

    }

    public Object negation(Object op1) {

        DataType opU = this.uniqueOperand.type.getDataType();
        switch (opU) {
            case ENTERO:
                this.type = new Type(DataType.ENTERO);
                return (int) op1 * -1;
            case DECIMAL:
                this.type = new Type(DataType.DECIMAL);
                return (double) op1 * -1;
            default:
                return new Error("Semantico", "Negacion Invalida", this.line, this.column);
        }
    }
}
