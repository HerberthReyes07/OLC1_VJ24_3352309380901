/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.io.FileWriter;
import java.io.PrintWriter;
import symbol.Tree;

/**
 *
 * @author herberthreyes
 */
public class GenerateGraph {

    public GenerateGraph(Tree ast, String fileName) {

        String cadena = "digraph ast{\n";
        cadena += "nINICIO[label=\"INICIO\"];\n";
        cadena += "nINSTRUCCIONES[label=\"INSTRUCCIONES\"];\n";
        cadena += "nINICIO -> nINSTRUCCIONES;\n";

        for (var i : ast.getInstructions()) {
            if (i == null) {
                continue;
            }
            String nodoAux = "n" + ast.getCont();
            cadena += nodoAux + "[label=\"INSTRUCCION\"];\n";
            cadena += "nINSTRUCCIONES -> " + nodoAux + ";\n";
            cadena += i.generateAST(ast, nodoAux);
        }

        cadena += "}";
        System.out.println(cadena);
        graph("ast_" + fileName +".jpg", cadena);
    }

    private void graph(String path, String cadena) {
        FileWriter fichero = null;
        PrintWriter escritor;
        try {
            fichero = new FileWriter("aux_grafico.dot");
            escritor = new PrintWriter(fichero);
            escritor.print(cadena);
        } catch (Exception e) {
            System.err.println("Error al escribir el archivo aux_grafico.dot");
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                System.err.println("Error al cerrar el archivo aux_grafico.dot");
            }
        }
        try {
            Runtime rt = Runtime.getRuntime();
            rt.exec("dot -Tjpg -o " + path + " aux_grafico.dot");
            //Se espera medio segundo para dar tiempo a que la imagen se genere.
            Thread.sleep(500);
        } catch (Exception ex) {
            System.err.println("Error al generar la imagen para el archivo aux_grafico.dot");
        }

    }

}
