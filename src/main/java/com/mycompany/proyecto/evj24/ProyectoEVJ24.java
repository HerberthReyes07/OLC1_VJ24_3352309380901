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
            String texto = "println((1+1)<2 || 3<2);//false\n"
                    + "println(1<2 && 3<2);//false\n"
                    + "println(1<2 ^ 3<2);//true\n"
                    + "println(!true);//false";
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
