/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package instructions;

import abstracto.Instruction;
import java.util.LinkedList;
import symbol.DataType;
import symbol.SymbolTable;
import symbol.Tree;
import symbol.Type;
import exceptions.Error;
import java.util.HashMap;
import symbol.Struct;

/**
 *
 * @author herberthreyes
 */
public class StructDeclaration extends Instruction {

    private LinkedList<StructField> list;
    private String id;

    public StructDeclaration(LinkedList<StructField> list, String id, int line, int column) {
        super(new Type(DataType.STRUCT), line, column);
        this.list = list;
        this.id = id;
    }

    @Override
    public Object interpret(Tree tree, SymbolTable table) {

        var aux = tree.getStruct(this.id);

        if (aux != null) {
            return new Error("SEMANTICO", "Struct Inválido: No puede declarar un Struct ya existente", this.line, this.column);
        }

        HashMap<String, Object> map = new HashMap();
        HashMap<String, DataType> map2 = new HashMap();
        HashMap<String, Struct> map3 = new HashMap();

        for (var a : this.list) {

            var res = a.interpret(tree, table);
            if (res instanceof Error) {
                return res;
            }

            var search = map.get(a.getName().toLowerCase());

            if (search == null) {
                map.put(a.getName().toLowerCase(), "");
                map2.put(a.getName().toLowerCase(), a.type.getDataType());
                if (a.type.getDataType() == DataType.STRUCT) {
                    Struct struct = tree.getStruct(a.getStructName().toLowerCase());
                    map3.put(a.getName().toLowerCase(), struct);
                }
            } else {
                return new Error("SEMANTICO", "Struct Inválido: No puede tener dos campos con el mismo nombre", this.line, this.column);
            }
        }

        tree.addStruct(new Struct(this.id, map, map2, map3, this.line, this.column));
        return null;
    }

    @Override
    public String generateAST(Tree tree, String previous) {
        //DECLARACION_STRUCT ::= struct { LISTA_STRUCT:a } ID:b ;

        String stpNode = "n" + tree.getCont();
        String structNode = "n" + tree.getCont();
        String lbNode = "n" + tree.getCont();
        String fNode = "n" + tree.getCont();
        String rbNode = "n" + tree.getCont();
        String idNode = "n" + tree.getCont();
        String pcNode = "n" + tree.getCont();

        String result = stpNode + "[label=\"DECLARACION-STRUCT\"];\n";
        result += previous + " -> " + stpNode + ";\n";

        result += structNode + "[label=\"struct\"];\n";
        result += lbNode + "[label=\"{\"];\n";
        result += fNode + "[label=\"CAMPOS_STRUCT\"];\n";
        result += rbNode + "[label=\"}\"];\n";
        result += idNode + "[label=\"" + this.id + "\"];\n";
        result += pcNode + "[label=\";\"];\n";

        result += stpNode + " -> " + structNode + ";\n";
        result += stpNode + " -> " + lbNode + ";\n";
        result += stpNode + " -> " + fNode + ";\n";
        result += stpNode + " -> " + rbNode + ";\n";
        result += stpNode + " -> " + idNode + ";\n";
        result += stpNode + " -> " + pcNode + ";\n";

        for (var i : this.list) {
            if (i == null) {
                continue;
            }

            result += i.generateAST(tree, fNode);
        }

        return result;
    }

}
