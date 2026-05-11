package com.culinarycore.model;

import java.time.LocalDate;

public class ClsConsume {
    private int _batchID;
    private int _workshopID;
    private int _quantity;
    private LocalDate _consumeDate;

    public int getBatchID() {
        return _batchID;
    }

    public void setBatchID(int _batchID) {
        this._batchID = _batchID;
    }

    public int getWorkshopID() {
        return _workshopID;
    }

    public void setWorkshopID(int _workshopID) {
        this._workshopID = _workshopID;
    }

    public int getQuantity() {
        return _quantity;
    }

    public void setQuantity(int _quantity) {
        this._quantity = _quantity;
    }

    public LocalDate getConsumeDate() {
        return _consumeDate;
    }

    public void setConsumeDate(LocalDate _consumeDate) {
        this._consumeDate = _consumeDate;
    }

    public ClsConsume(int _batchID, int _workshopID, int _quantity, LocalDate _consumeDate) {
        this._batchID = _batchID;
        this._workshopID = _workshopID;
        this._quantity = _quantity;
        this._consumeDate = _consumeDate;
    }
}
