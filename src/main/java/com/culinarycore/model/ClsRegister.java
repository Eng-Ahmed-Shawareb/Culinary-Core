package com.culinarycore.model;

import java.time.LocalDate;

public class ClsRegister{
    private int _studentID;
    private int _workshopID;
    private LocalDate _registerDate;
    private EnPaymentStatus _paymentStatus;

    public ClsRegister(int _studentID, int _workshopID, LocalDate _registerDate, EnPaymentStatus _paymentStatus) {
        this._studentID = _studentID;
        this._workshopID = _workshopID;
        this._registerDate = _registerDate;
        this._paymentStatus = _paymentStatus;
    }


    public int getStudentID() {
        return _studentID;
    }

    public void setStudentID(int _studentID) {
        this._studentID = _studentID;
    }

    public int getWorkshopID() {
        return _workshopID;
    }

    public void setWorkshopID(int _workshopID) {
        this._workshopID = _workshopID;
    }

    public LocalDate getRegisterDate() {
        return _registerDate;
    }

    public void setRegisterDate(LocalDate _registerDate) {
        this._registerDate = _registerDate;
    }

    public EnPaymentStatus getPaymentStatus() {
        return _paymentStatus;
    }

    public void setPaymentStatus(EnPaymentStatus _paymentStatus) {
        this._paymentStatus = _paymentStatus;
    }
}