/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herberthreyes
 */
public class FileController {

    public String readFile(File file) throws FileNotFoundException {
        BufferedReader brp;
        brp = new BufferedReader(new FileReader(file));
        String input = "";
        try {
            String aux = brp.readLine();
            while (aux != null) {
                input = input + aux;
                aux = brp.readLine();
                if (aux != null) {
                    input = input + "\n";
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return input;
    }

    public void saveFile(String name, String text) {
        write(create(name), text, false);
    }
    
    private File create(String name){
        File file = new File(name);
        try {
            if(file.createNewFile() || file.exists()){
                return file;
            } else {
                System.out.println("Error al crear archivo");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    private void write(File file, String data, boolean append){
        try {
            FileWriter fileWriter = new FileWriter(file, append);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(data);
            printWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado.");
        } catch (IOException e) {
            System.out.println("Error al escribir en el archivo.");
        }
    }

}
