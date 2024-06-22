/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import expressions.VariableAccess;
import symbol.Symbol;

/**
 *
 * @author herberthreyes
 */
public class StructValue extends Instruction {
    
    private String field;
    private Instruction value;

    public StructValue(String field, Instruction value, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.field = field;
        this.value = value;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {
        
        var val = this.value.interpret(tree, table);
        if (val instanceof Error) {
            return val;
        }
        /*if (this.value instanceof VariableAccess) {
            System.out.println("ES ACCESO A VARIABLE");
            VariableAccess va = (VariableAccess) this.value;
            System.out.println(va.getId());
        }*/
        this.type.setType(this.value.type.getDataType());
        return null;
    }

    public String getField() {
        return field;
    }

    public Instruction getValue() {
        return value;
    }
    
}
