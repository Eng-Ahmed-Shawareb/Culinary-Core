package com.culinarycore.model;

public class ClsKitchen {
    private int _ID;
    private String name;
    private String _type;

    public String get_type() {
        return _type;
    }

    public void setType(String _type) {
        this._type = _type;
    }

    public int getID() {
        return _ID;
    }

    public void setID(int _ID) {
        this._ID = _ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClsKitchen(String name, String _type) {
        this.name = name;
        this._type = _type;
    }
}
