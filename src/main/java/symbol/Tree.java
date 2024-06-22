/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package symbol;

import abstracto.Instruction;
import java.util.LinkedList;

/**
 *
 * @author herberthreyes
 */
public class Tree {
    
    private LinkedList<Instruction> instructions;
    private String console;
    private SymbolTable globalTable;
    private LinkedList<Error> errors;
    private LinkedList<SymbolTable> tables;
    private LinkedList<Struct> structs;

    public Tree(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
        this.console = "";
        this.globalTable = new SymbolTable();
        this.errors = new LinkedList<>();
        this.tables = new LinkedList<>();
        this.structs = new LinkedList<>();
    }
    
    public void Print(String value) {
        //this.console += "> " + value + "\n";
        this.console += value + "\n";
    }

    public LinkedList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public SymbolTable getGlobalTable() {
        return globalTable;
    }

    public void setGlobalTable(SymbolTable globalTable) {
        this.globalTable = globalTable;
    }

    public LinkedList<Error> getErrors() {
        return errors;
    }

    public void setErrors(LinkedList<Error> errors) {
        this.errors = errors;
    }

    public LinkedList<SymbolTable> getTables() {
        return tables;
    }

    public void setTables(LinkedList<SymbolTable> tables) {
        this.tables = tables;
    }

    public LinkedList<Struct> getStructs() {
        return structs;
    }

    public void setStructs(LinkedList<Struct> structs) {
        this.structs = structs;
    }

    public void addStruct(Struct struct){
        this.structs.add(struct);
    }
    
    public Struct getStruct (String id){
        for (var a : this.structs) {
            if (a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }
}
