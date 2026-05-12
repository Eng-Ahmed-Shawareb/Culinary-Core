package com.culinarycore.model.dto;

public class ClsStudentWorkshopCountDTO {
    private String _firstName;
    private String _lastName;
    private String _phone;
    private int _workshopCount;

    public ClsStudentWorkshopCountDTO(String _firstName, String _lastName, String _phone, int _workshopCount) {
        this._firstName = _firstName;
        this._lastName = _lastName;
        this._phone = _phone;
        this._workshopCount = _workshopCount;
    }

    public String getFirstName() {
        return _firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public String getPhone() {
        return _phone;
    }

    public int getWorkshopCount() {
        return _workshopCount;
    }
}