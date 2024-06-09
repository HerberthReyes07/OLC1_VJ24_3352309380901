/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package symbol;

import java.util.HashMap;

/**
 *
 * @author herberthreyes
 */
public class SymbolTable {
    
    private SymbolTable previousTable;
    private HashMap<String, Object> currentTable;
    private String name;

    public SymbolTable() {
        this.currentTable = new HashMap<>();
        this.name = "";
    }

    public SymbolTable(SymbolTable previousTable) {
        this.previousTable = previousTable;
        this.currentTable = new HashMap<>();
        this.name = "";
    }

    public SymbolTable getPreviousTable() {
        return previousTable;
    }

    public void setPreviousTable(SymbolTable previousTable) {
        this.previousTable = previousTable;
    }

    public HashMap<String, Object> getCurrentTable() {
        return currentTable;
    }

    public void setCurrentTable(HashMap<String, Object> currentTable) {
        this.currentTable = currentTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
