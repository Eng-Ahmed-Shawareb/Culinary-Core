package com.culinarycore.model;

public class ClsKitchen {
    private int _ID;
    private String _name;
    private String _type;

    public String getType() {
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
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public ClsKitchen(String _name, String _type) {
        this._name = _name;
        this._type = _type;
    }
}
