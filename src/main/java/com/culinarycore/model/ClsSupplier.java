package com.culinarycore.model;

public class ClsSupplier {
    private int _ID;
    private String _name;

    public ClsSupplier(int _ID, String _name) {
        this._ID = _ID;
        this._name = _name;
    }

    public ClsSupplier(String _name) {
        this._name = _name;
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
}
