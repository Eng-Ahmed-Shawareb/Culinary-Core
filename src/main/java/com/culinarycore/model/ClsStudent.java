package com.culinarycore.model;


public class ClsStudent {
   private int _ID;
   private String _firstName;
   private String _lastName;
   private char _gender;
   private String _phone;

    public int getID() {
        return _ID;
    }

    public void setID(int _ID) {
        this._ID = _ID;
    }

    public String getPhone() {
        return _phone;
    }

    public void setPhone(String _phone) {
        this._phone = _phone;
    }

    public char getGender() {
        return _gender;
    }

    public void setGender(char _gender) {
        this._gender = _gender;
    }

    public String getLastName() {
        return _lastName;
    }

    public ClsStudent(String _phone, char _gender, String _lastName, String _firstName) {
        this._phone = _phone;
        this._gender = _gender;
        this._lastName = _lastName;
        this._firstName = _firstName;
    }

    public void setLastName(String _lastName) {
        this._lastName = _lastName;
    }

    public String getFirstName() {
        return _firstName;
    }

    public void setFirstName(String _firstName) {
        this._firstName = _firstName;
    }
}
