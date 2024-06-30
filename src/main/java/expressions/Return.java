/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expressions;

import abstracto.Instruction;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;

/**
 *
 * @author herberthreyes
 */
public class Return extends Instruction {

    private Instruction expression;
    private Object valueToReturn;

    public Return(Instruction expression, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.expression = expression;
    }

    public Return(int line, int column) {
        super(new Type(DataType.VOID), line, column);
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        if (this.expression != null) {
            var res = this.expression.interpret(tree, table);
            if (res instanceof Error) {
                return res;
            }

            this.type.setType(this.expression.type.getDataType());
            this.valueToReturn = res;
            return res;
        }
        return null;
    }

    public Object getValueToReturn() {
        return valueToReturn;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        
        /*
            SENTENCIA_RETURN ::= return EXPRESION    
                               | return;                
        */
        String rpNode = "n" + tree.getCont();
        String returnNode = "n" + tree.getCont();
        String expNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = previous + " -> " + rpNode + ";\n";

        result += rpNode + "[label=\"SEN_RETURN\"];\n";
        result += returnNode + "[label=\"return\"];\n";
        if (expression != null) {
            result += expNode + "[label=\"EXPRESION\"];\n";
        }
        result += pcNode + "[label=\";\"];\n";

        result += rpNode + " -> " + returnNode + ";\n";
        if (expression != null) {
            result += rpNode + " -> " + expNode + ";\n";
        }
        result += rpNode + " -> " + pcNode + ";\n";
        
        if (expression != null) {
            result += this.expression.generateAST(tree, expNode);
        }
        
        return result;
    }

}
