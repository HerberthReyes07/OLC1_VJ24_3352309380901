/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.HashMap;
import java.util.LinkedList;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import expressions.Return;
import symbol.DataType;

/**
 *
 * @author herberthreyes
 */
public class Method extends Instruction {

    public String id;
    public LinkedList<HashMap> parameters;
    public LinkedList<Instruction> instructions;

    public Method(String id, LinkedList<HashMap> parameters, LinkedList<Instruction> instructions, Type type, int line, int column) {
        super(type, line, column);
        this.id = id;
        this.parameters = parameters;
        this.instructions = instructions;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        boolean flagReturn = false;
        Object aux = null;
        for (var a : this.instructions) {

            if (a == null) {
                continue;
            }
            if (a instanceof Return) {
                flagReturn = true;
                aux = a;
                break;
            }

            var res = a.interpret(tree, table);
            if (res instanceof Error) {
                return res;
            }
            if (res instanceof Return) {
                aux = res;
                break;
            }
        }
        if (aux instanceof Return) {
            var rtn = (Return) aux;
            if (flagReturn) {
                rtn.interpret(tree, table);
            }
            var res = rtn.getValueToReturn();
            if (res instanceof Error) {
                return res;
            }
            if (this.type.getDataType() != rtn.type.getDataType()) {
                return new Error("SEMANTICO", "Retorno en método: " + this.id + " Inválido. "
                        + "No puede retornar un tipo de dato: " + rtn.type.getDataType() + " en un Método definido como: " + this.type.getDataType(), this.line, this.column);
            }
            this.type.setType(rtn.type.getDataType());
            return res;
        }
        if (this.type.getDataType() != DataType.VOID) {
            return new Error("SEMANTICO", "Retorno en método: " + this.id + " Inválido. "
                    + "Debe retornar un valor en una función", this.line, this.column);
        }
        return null;
    }

}
