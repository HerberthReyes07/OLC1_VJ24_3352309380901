/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package symbol;

/**
 *
 * @author herberthreyes
 */
public class Symbol {
    
    private Type type;
    private String id;
    private Object value;

    public Symbol(Type type, String id) {
        this.type = type;
        this.id = id;
    }

    public Symbol(Type type, String id, Object value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
}
