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
            symbol = new Symbol(this.type, this.id, interpretedValue, false,this.line, this.column);
        }

        boolean creation = table.setVariable(symbol);
        if (!creation) {
            return new Error("SEMANTICO", "Declaración Inválida: No puede declarar una variable ya existente", this.line, this.column);
        }

        return null;
    }

}
