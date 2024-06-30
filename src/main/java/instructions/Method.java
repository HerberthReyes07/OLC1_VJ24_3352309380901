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

    @Override
    public String generateAST(Tree tree, String previous) {

        //METODO: TIPO nombre ( PARAMS ) { instrucciones }
        String mpNode = "n" + tree.getCont();
        String tyNode = "n" + tree.getCont();
        String auxMTyNode = "n" + tree.getCont();
        String nmNode = "n" + tree.getCont();
        String lpNode = "n" + tree.getCont();
        String prmNode = "n" + tree.getCont();
        String rpNode = "n" + tree.getCont();
        String lbNode = "n" + tree.getCont();
        String inNode = "n" + tree.getCont();
        String rbNode = "n" + tree.getCont();

        String result = mpNode + "[label=\"METODO\"];\n";
        result += previous + " -> " + mpNode + ";\n";

        result += tyNode + "[label=\"TIPO\"];\n";
        result += auxMTyNode + "[label=\" " + getDataType(this.type.getDataType()) + "\"];\n";
        result += nmNode + "[label=\" " + this.id + "\"];\n";
        result += lpNode + "[label=\"(\"];\n";
        if (!parameters.isEmpty()) {
            result += prmNode + "[label=\"PARAMS\"];\n";
        }
        result += rpNode + "[label=\")\"];\n";
        result += lbNode + "[label=\"{\"];\n";
        result += inNode + "[label=\"INSTRUCCIONES_METODO\"];\n";
        result += rbNode + "[label=\"}\"];\n";

        result += mpNode + " -> " + tyNode + ";\n";
        result += tyNode + " -> " + auxMTyNode + ";\n";
        result += mpNode + " -> " + nmNode + ";\n";
        result += mpNode + " -> " + lpNode + ";\n";
        if (!parameters.isEmpty()) {
            result += mpNode + " -> " + prmNode + ";\n";
        }
        result += mpNode + " -> " + rpNode + ";\n";
        result += mpNode + " -> " + lbNode + ";\n";
        result += mpNode + " -> " + inNode + ";\n";
        result += mpNode + " -> " + rbNode + ";\n";

        int cont = 0;
        for (var i : this.parameters) {
            if (i == null) {
                continue;
            }

            var ty = (Type) i.get("tipo");
            var prm = (String) i.get("id");

            String auxTyNode = "n" + tree.getCont();
            String auxTy2Node = "n" + tree.getCont();
            String auxIdNode = "n" + tree.getCont();

            result += prmNode + " -> " + auxTyNode + ";\n";
            result += auxTyNode + " -> " + auxTy2Node + ";\n";
            result += prmNode + " -> " + auxIdNode + ";\n";

            //result += auxTyNode + "[label=\" " + getDataType(ty.getDataType()) + "\"];\n";
            result += auxTyNode + "[label=\"TIPO\"];\n";
            result += auxTy2Node + "[label=\" " + getDataType(ty.getDataType()) + "\"];\n";
            result += auxIdNode + "[label=\" " + prm + "\"];\n";

            cont++;
            if (cont + 1 <= this.parameters.size()) {
                String auxCmNode = "n" + tree.getCont();
                result += prmNode + " -> " + auxCmNode + ";\n";
                result += auxCmNode + "[label=\",\"];\n";
            }
        }

        for (var i : this.instructions) {
            if (i == null) {
                continue;
            }
            String nodoAux = "n" + tree.getCont();
            result += inNode + " -> " + nodoAux + ";\n";
            result += nodoAux + "[label=\"INSTRUCCION\"];\n";
            result += i.generateAST(tree, nodoAux);
        }
        return result;
    }

    private String getDataType(DataType dt) {

        switch (dt) {
            case ENTERO:
                return "int";
            case DECIMAL:
                return "double";
            case BOOLEANO:
                return "bool";
            case CARACTER:
                return "char";
            case CADENA:
                return "String";
            case VOID:
                return "void";
            default:
                return "";
        }
    }

}
