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
public class Struct {
    
    private String id;
    private HashMap<String, Object> values;
    private HashMap<String, DataType> valuesTypes;
    private HashMap<String, Struct> valuesStruct;
    private int line;
    private int column;

    public Struct(String id, HashMap<String, Object> values, HashMap<String, DataType> valuesTypes, HashMap<String, Struct> valuesStruct, int line, int column) {
        this.id = id;
        this.values = values;
        this.valuesTypes = valuesTypes;
        this.valuesStruct = valuesStruct;
        this.line = line;
        this.column = column;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public HashMap<String, Object> getValues() {
        return values;
    }

    public void setValues(HashMap<String, Object> values) {
        this.values = values;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public HashMap<String, DataType> getValuesTypes() {
        return valuesTypes;
    }

    public void setValuesTypes(HashMap<String, DataType> valuesTypes) {
        this.valuesTypes = valuesTypes;
    }

    public HashMap<String, Struct> getValuesStruct() {
        return valuesStruct;
    }

    public void setValuesStruct(HashMap<String, Struct> valuesStruct) {
        this.valuesStruct = valuesStruct;
    }
    
    @Override
    public String toString() {
        return "Struct{" + "id=" + id + ", values=" + values + ", line=" + line + ", column=" + column + '}';
    }
    
}
