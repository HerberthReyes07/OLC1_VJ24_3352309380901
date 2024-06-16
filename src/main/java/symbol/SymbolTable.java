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

    public boolean setVariable(Symbol symbol) {
        Symbol search
                = (Symbol) this.currentTable.get(symbol.getId().
                        toLowerCase());
        if (search == null) {
            this.currentTable.put(symbol.getId().toLowerCase(),
                    symbol);
            return true;
        }
        return false;
    }

    public Symbol getVariable(String id) {
        for (SymbolTable i = this; i != null; i = i.getPreviousTable()) {
            Symbol search = (Symbol) i.currentTable.
                    get(id.toLowerCase());
            if (search != null) {
                return search;
            }
        }
        return null;
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

    @Override
    public String toString() {
        if (previousTable == null) {
            return "SymbolTable{" + "previousTable=" + previousTable + ", currentTable=" + currentTable + ", name=" + name + '}';
        }
        return "SymbolTable{" + "previousTable=" + previousTable.getName() + ", currentTable=" + currentTable + ", name=" + name + '}';
    }

}
