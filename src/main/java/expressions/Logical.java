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
public class Logical extends Instruction {

    private Instruction operand1;
    private Instruction operand2;
    private Operators operator;
    private Instruction uniqueOperand;

    public Logical(Instruction operand1, Operators operator, int line, int column) {
        super(new Type(DataType.BOOLEANO), line, column);
        this.uniqueOperand = operand1;
        this.operator = operator;
    }

    public Logical(Instruction operand1, Instruction operand2, Operators operator, int line, int column) {
        super(new Type(DataType.BOOLEANO), line, column);
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
            case OR:
                return or(opLeft, opRight);
            case AND:
                return and(opLeft, opRight);
            case XOR:
                return xor(opLeft, opRight);
            case NOT:
                return not(unique);
            default:
                return new Error("SEMANTICO", "Operador Lógico Inválido", this.line, this.column);
        }
    }

    private Object or(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return (boolean) op1 || (boolean) op2; 
                    default:
                        return new Error("SEMANTICO", "Operación lógica OR (||) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Operación lógica OR (||) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

    private Object and(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return (boolean) op1 && (boolean) op2;
                    default:
                        return new Error("SEMANTICO", "Operación lógica AND (&&) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Operación lógica AND (&&) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

    private Object xor(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();
        switch (type1) {
            case BOOLEANO:
                switch (type2) {
                    case BOOLEANO:
                        return (boolean) op1 ^ (boolean) op2;
                    default:
                        return new Error("SEMANTICO", "Operación lógica XOR (^) Inválida: no puede comparar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Operación lógica XOR (^) Inválida: no puede comparar los tipos " + type1 + " y " + type2, this.line, this.column);
        }
    }

    public Object not(Object op1) {

        DataType typeUOp = this.uniqueOperand.type.getDataType();
        switch (typeUOp) {
            case BOOLEANO:
                return !((boolean) op1);
            default:
                return new Error("SEMANTICO", "Operación lógica NOT (!) Inválida: no puede negar el tipo " + typeUOp, this.line, this.column);
        }
    }

}
