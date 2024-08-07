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
import java.util.HashMap;
import java.util.LinkedList;
import symbol.MutabilityType;
import symbol.Struct;
import symbol.Symbol;

/**
 *
 * @author herberthreyes
 */
public class StructValue extends Instruction {

    private String field;
    private Instruction value;
    private LinkedList<StructValue> values;
    private Symbol auxSym;
    private Object auxValue;
    private Struct auxStruct;

    public StructValue(String field, Instruction value, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.field = field;
        this.value = value;
    }

    public StructValue(String field, LinkedList<StructValue> values, int line, int column) {
        super(new Type(DataType.VOID), line, column);
        this.field = field;
        this.values = values;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        if (this.value != null) {
            var val = this.value.interpret(tree, table);
            if (val instanceof Error) {
                return val;
            }
            //ver si funciona o mejor quitarlo: 
            if (this.value instanceof VariableAccess && this.value.type.getDataType() == DataType.STRUCT) {
                VariableAccess va  = (VariableAccess) this.value;
                auxSym = table.getVariable(va.getId());
            }
            this.type.setType(this.value.type.getDataType());
        } else {
            HashMap<String, Object> aux = new HashMap<>();
            Struct structType = auxStruct.getValuesStruct().get(field.toLowerCase());
            var si = new StructInstantiation(MutabilityType.VAR, field, structType.getId(), values, line, column);
            si.setFlag(true);
            var res = si.interpret(tree, table);
            if (res instanceof Error) {
                return res;
            }
            for (var a : values) {
                var val = a.interpret(tree, table);
                if (val instanceof Error) {
                    return val;
                }
                //ver si funciona o mejor quitarlo: 
                if (a.getValue() instanceof VariableAccess && a.getValue().type.getDataType() == DataType.STRUCT) {
                    VariableAccess va  = (VariableAccess) a.getValue();
                    auxSym = table.getVariable(va.getId());
                }
                aux.put(a.getField(), a.getValue().interpret(tree, table));
            }
            this.type.setType(DataType.STRUCT);
            this.auxValue = aux;
        }

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

    public Object getAuxValue() {
        return auxValue;
    }

    public void setAuxStruct(Struct auxStruct) {
        this.auxStruct = auxStruct;
    }

    @Override
    public String generateAST(Tree tree, String previous) {

        /*
            VALOR_STRUCT ::= ID:a : EXPRESION:b                     <-
                           | ID:a : { LISTA_VALORES_STRUCT:b }      <-

         */
        String svpNode = "n" + tree.getCont();
        String fnNode = "n" + tree.getCont();
        String fNode = "n" + tree.getCont();
        String tpNode = "n" + tree.getCont();

        String expNode = "n" + tree.getCont();

        String lbNode = "n" + tree.getCont();
        String vNode = "n" + tree.getCont();
        String rbNode = "n" + tree.getCont();;

        String result = svpNode + "[label=\"VALOR_STRUCT\"];\n";
        result += previous + " -> " + svpNode + ";\n";

        result += fnNode + "[label=\"CAMPO_STRUCT\"];\n";
        result += fNode + "[label=\" " + this.field + "\"];\n";
        result += tpNode + "[label=\":\"];\n";

        if (value != null) {
            result += expNode + "[label=\"EXPRESION\"];\n";
        } else {
            result += lbNode + "[label=\"{\"];\n";
            result += vNode + "[label=\"VALORES_STRUCT\"];\n";
            result += rbNode + "[label=\"}\"];\n";
        }

        result += svpNode + " -> " + fnNode + ";\n";
        result += fnNode + " -> " + fNode + ";\n";
        result += svpNode + " -> " + tpNode + ";\n";

        if (value != null) {
            result += svpNode + " -> " + expNode + ";\n";
            result += this.value.generateAST(tree, expNode);
        } else {
            result += svpNode + " -> " + lbNode + ";\n";
            result += svpNode + " -> " + vNode + ";\n";
            result += svpNode + " -> " + rbNode + ";\n";
            int cont = 0;
            for (var i : this.values) {
                if (i == null) {
                    continue;
                }

                result += i.generateAST(tree, vNode);
                cont++;
                if (cont + 1 <= this.values.size()) {
                    String auxCmNode = "n" + tree.getCont();
                    result += vNode + " -> " + auxCmNode + ";\n";
                    result += auxCmNode + "[label=\",\"];\n";
                }
            }
        }

        return result;
    }

}
