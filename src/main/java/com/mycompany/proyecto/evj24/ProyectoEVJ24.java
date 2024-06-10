/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyecto.evj24;

import abstracto.Instruction;
import analysis.parser;
import analysis.scanner;
import frontend.MainInterface;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import symbol.SymbolTable;
import symbol.Tree;

/**
 *
 * @author herberthreyes
 */
public class ProyectoEVJ24 {

    public static void main(String[] args) {
        /*MainInterface mi = new MainInterface();
        mi.setVisible(true);*/
        try {
            //println(\"\\\"CADENA CON \\n COMILLLAS\\t \\\'C:micarpeta\\\' \\\" \");
            /*String texto = "println(\'\\n\');\n"
                    + "println(\'\\\\');\n"
                    + "println(\'\\\"\');\n"
                    + "println(\'\\\'\');\n"
                    + "println(\'\\t\');";*/
            String texto = ""
                    + "match 1 {\n"
                    + "    2**2 => { \n"
                    + "        println(\"ES 4\");\n"
                    + "    }\n"
                    + "    4-1 => { \n"
                    + "        println(\"ES 3\");\n"
                    + "    }\n"
                    + "    2*1 => { \n"
                    + "        println(\"ES 2\");\n"
                    + "    }\n"
                    + "    _ => { \n"
                    + "        println(\"NO ES 4, 3 O 2\");\n"
                    + "    }\n"
                    + "}";
            scanner s = new scanner(new BufferedReader(new StringReader(texto)));
            parser p = new parser(s);
            var resultado = p.parse();
            var ast = new Tree((LinkedList<Instruction>) resultado.value);
            var table = new SymbolTable();
            table.setName("GLOBAL");
            ast.setConsole("");
            for (var a : ast.getInstructions()) {
                var res = a.interpret(ast, table);
            }
            System.out.println(ast.getConsole());
        } catch (Exception ex) {
            System.out.println("Algo salio mal");
            System.out.println(ex);
        }
    }
}
