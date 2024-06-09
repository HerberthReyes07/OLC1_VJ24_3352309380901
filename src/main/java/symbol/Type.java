/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package symbol;

/**
 *
 * @author herberthreyes
 */
public class Type {

    private DataType dataType;

    public Type(DataType type) {
        this.dataType = type;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setType(DataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "Type{" + "dataType=" + dataType + '}';
    }
    
}
