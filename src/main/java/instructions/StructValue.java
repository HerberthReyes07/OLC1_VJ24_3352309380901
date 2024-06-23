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
    private Symbol auxSym;

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
        //ver si funciona o mejor quitarlo: 
        if (this.value instanceof VariableAccess && this.value.type.getDataType() == DataType.STRUCT) {
            VariableAccess va = (VariableAccess) this.value;
            auxSym = table.getVariable(va.getId());
        }
        this.type.setType(this.value.type.getDataType());
        return null;
    }

    public String getField() {
        return field;
    }

    public Instruction getValue() {
        return value;
    }

    public Symbol getAuxSym() {
        return auxSym;
    }

}
