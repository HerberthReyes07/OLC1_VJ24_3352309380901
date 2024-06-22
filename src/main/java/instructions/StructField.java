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
    
}
