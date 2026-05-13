package com.culinarycore.model;

import com.culinarycore.model.StatusEnums.EnWorkshopStatus;

import java.time.LocalDate;

public class ClsWorkshop {
    private int _ID;
    private int _kitchenID;
    private int _chefID;
    private String _title;
    private String _technique;
    private double _price;
    private LocalDate _startDate;
    private LocalDate _endDate;
    private EnWorkshopStatus _status;

    public ClsWorkshop(int _ID, int _kitchenID,
                       int _chefID, String _title,
                       LocalDate _startDate,
                       LocalDate _endDate, double _price,
                       EnWorkshopStatus _status, String _technique) {
        this._ID = _ID;
        this._kitchenID = _kitchenID;
        this._chefID = _chefID;
        this._title = _title;
        this._startDate = _startDate;
        this._price = _price;
        this._endDate = _endDate;
        this._status = _status;
        this._technique = _technique;
    }


    public int getID() {
        return _ID;
    }

    public void setID(int _ID) {
        this._ID = _ID;
    }

    public int getKitchenID() {
        return _kitchenID;
    }

    public void setKitchenID(int _kitchenID) {
        this._kitchenID = _kitchenID;
    }

    public int getChefID() {
        return _chefID;
    }

    public void setChefID(int _chefID) {
        this._chefID = _chefID;
    }

    public String getTitle() {
        return _title;
    }

    public void setTitle(String _title) {
        this._title = _title;
    }

    public double getPrice() {
        return _price;
    }

    public void setPrice(double _price) {
        this._price = _price;
    }

    public LocalDate getStartDate() {
        return _startDate;
    }

    public void setStartDate(LocalDate _startDate) {
        this._startDate = _startDate;
    }

    public LocalDate getEndDate() {
        return _endDate;
    }

    public void setEndDate(LocalDate _endDate) {
        this._endDate = _endDate;
    }

    public EnWorkshopStatus getStatus() {
        return _status;
    }

    public void setStatus(EnWorkshopStatus _status) {
        this._status = _status;
    }

    public void setTechnique(String _technique){
        this._technique = _technique;
    }

    public String getTechnique(){
        return _technique;
    }

    @Override
    public String toString() {
        return _title;
    }
}
