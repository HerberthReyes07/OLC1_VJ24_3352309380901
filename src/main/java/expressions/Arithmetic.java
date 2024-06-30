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
                return new Error("SEMANTICO", "Operador Aritmetico Inválido", this.line, this.column);
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
                        return new Error("SEMANTICO", "Suma Inválida: No puede sumar los tipos ENTERO y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Suma Inválida: No puede sumar los tipos DECIMAL y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Suma Inválida: No puede sumar los tipos CARACTER y " + type2, this.line, this.column);
                }
            case BOOLEANO:
                switch (type2) {
                    case CADENA:
                        this.type = new Type(DataType.CADENA);
                        return op1.toString() + op2.toString();
                    default:
                        return new Error("SEMANTICO", "Suma Inválida: No puede sumar los tipos BOOLEANO y " + type2, this.line, this.column);
                }
            case CADENA:
                this.type = new Type(DataType.CADENA);
                return op1.toString() + op2.toString();
            default:
                return new Error("SEMANTICO", "Suma Inválida: No puede sumar los tipos " + type1 + " y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Resta Inválida: No puede restar los tipos ENTERO y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Resta Inválida: No puede restar los tipos DECIMAL y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Resta Inválida: No puede restar los tipos CARACTER y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Resta Inválida: No puede restar los tipos " + type1 + " y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Multiplicación Inválida: No puede multiplicar los tipos ENTERO y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Multiplicación Inválida: No puede multiplicar los tipos DECIMAL y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Multiplicación Inválida: No puede multiplicar los tipos CARACTER y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Multiplicación Inválida: No puede multiplicar los tipos " + type1 + " y " + type2, this.line, this.column);
        }

    }

    private Object division(Object op1, Object op2) {

        DataType type1 = this.operand1.type.getDataType();
        DataType type2 = this.operand2.type.getDataType();

        if (type2 == DataType.ENTERO) {
            if ((int) op2 == 0) {
                return new Error("SEMANTICO", "División Inválida: No puede dividir entre 0", this.line, this.column);
            }
        } else if (type2 == DataType.DECIMAL) {
            if ((double) op2 == 0.0) {
                return new Error("SEMANTICO", "División Inválida: No puede dividir entre 0", this.line, this.column);
            }
        }

        switch (type1) {
            case ENTERO:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        int a = (int) op1;
                        int b = (int) op2;
                        return (double) a / b;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 / (double) op2;
                    case CARACTER:
                        this.type = new Type(DataType.DECIMAL);
                        int a2 = (int) op1;
                        int b2 = op2.toString().codePointAt(0);
                        return (double) a2 / b2;
                    default:
                        return new Error("SEMANTICO", "División Inválida: No puede dividir los tipos ENTERO y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "División Inválida: No puede dividir los tipos DECIMAL y " + type2, this.line, this.column);
                }
            case CARACTER:
                switch (type2) {
                    case ENTERO:
                        this.type = new Type(DataType.DECIMAL);
                        int a = op1.toString().codePointAt(0);
                        int b = (int) op2;
                        return (double) a / b;
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return op1.toString().codePointAt(0) / (double) op2;
                    default:
                        return new Error("SEMANTICO", "División Inválida: No puede dividir los tipos CARACTER y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "División Inválida: No puede dividir los tipos " + type1 + " y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Potenciación Inválida: No puede potenciar el tipo ENTERO a el tipo " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Potenciación Inválida: No puede potenciar el tipo DECIMAL a el tipo " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Potenciación Inválida: No puede potenciar el tipo " + type1 + " a el tipo " + type2, this.line, this.column);
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
                        int a = (int) op1;
                        int b = (int) op2;
                        return (double) (a % b);
                    case DECIMAL:
                        this.type = new Type(DataType.DECIMAL);
                        return (int) op1 % (double) op2;
                    default:
                        return new Error("SEMANTICO", "Módulo Inválido: No puede obtener el módulo entre los tipos ENTERO y " + type2, this.line, this.column);
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
                        return new Error("SEMANTICO", "Módulo Inválido: No puede obtener el módulo entre los tipos DECIMAL y " + type2, this.line, this.column);
                }
            default:
                return new Error("SEMANTICO", "Módulo Inválido: No puede obtener el módulo entre los tipos " + type1 + " y " + type2, this.line, this.column);
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
                return new Error("SEMANTICO", "Negación Unaria Inválida: No puede obtener la Negación Unaria del tipo " + opU, this.line, this.column);
        }
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        if (this.operator == Operators.NEGACION) {
            String opNode = "n" + tree.getCont();
            String exp1Node = "n" + tree.getCont();
            String result = previous + " -> " + opNode + ";\n";
            result += previous + " ->" + exp1Node + ";\n";
            
            result += opNode + "[label=\"-\"];\n";
            result += exp1Node + "[label=\"EXP\"];\n";
            result += this.uniqueOperand.generateAST(tree, exp1Node);
            return result;
        }

        String exp1Node = "n" + tree.getCont();
        String opNode = "n" + tree.getCont();
        String exp2Node = "n" + tree.getCont();

        String result = previous + " -> " + exp1Node + ";\n";
        result += previous + " ->" + opNode + ";\n";
        result += previous + " ->" + exp2Node + ";\n";

        result += exp1Node + "[label=\"EXPRESION\"];\n";
        result += opNode + "[label=\" " + getOperator() + " \"];\n";
        result += exp2Node + "[label=\"EXPRESION\"];\n";
        result += this.operand1.generateAST(tree, exp1Node);
        result += this.operand2.generateAST(tree, exp2Node);
        return result;
    }

    private String getOperator() {

        switch (this.operator) {
            case SUMA:
                return "+";
            case RESTA:
                return "-";
            case MULTIPLICACION:
                return "*";
            case DIVISION:
                return "/";
            case MODULO:
                return "%";
            case POTENCIA:
                return "**";
            default:
                return "";
        }
    }
}
