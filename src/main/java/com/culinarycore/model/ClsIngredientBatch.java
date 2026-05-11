package com.culinarycore.model;

import java.time.LocalDate;

public class ClsIngredientBatch {
    private int _ID;
    private int _supplierID;
    private String _name;
    private int _unit;
    private LocalDate _expirationDate;
    private LocalDate _deliveryDate;
    private EnIngredientBatch _state;

    public ClsIngredientBatch(int _ID, int _supplierID, int _unit, String _name, LocalDate _expirationDate, LocalDate _deliveryDate, EnIngredientBatch _state) {
        this._ID = _ID;
        this._supplierID = _supplierID;
        this._unit = _unit;
        this._name = _name;
        this._expirationDate = _expirationDate;
        this._deliveryDate = _deliveryDate;
        this._state = _state;
    }

    public EnIngredientBatch getState() {
        return _state;
    }

    public void setState(EnIngredientBatch _state) {
        this._state = _state;
    }

    public LocalDate getDeliveryDate() {
        return _deliveryDate;
    }

    public void setDeliveryDate(LocalDate _deliveryDate) {
        this._deliveryDate = _deliveryDate;
    }

    public LocalDate getExpirationDate() {
        return _expirationDate;
    }

    public void setExpirationDate(LocalDate _expirationDate) {
        this._expirationDate = _expirationDate;
    }

    public int getUnit() {
        return _unit;
    }

    public void setUnit(int _unit) {
        this._unit = _unit;
    }

    public String getName() {
        return _name;
    }

    public void setName(String _name) {
        this._name = _name;
    }

    public int getSupplierID() {
        return _supplierID;
    }

    public void setSupplierID(int _supplierID) {
        this._supplierID = _supplierID;
    }

    public int getID() {
        return _ID;
    }

    public void setID(int _ID) {
        this._ID = _ID;
    }
}
