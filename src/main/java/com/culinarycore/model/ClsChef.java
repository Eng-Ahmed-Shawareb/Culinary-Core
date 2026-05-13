package com.culinarycore.model;

public class ClsChef {
    private int _ID;
    private String _firstName;
    private String _lastName;
    private String _bio;
    private String _expertise;

    public int getID() {
        return _ID;
    }

    public void setID(int _ID) {
        this._ID = _ID;
    }

    public String getExpertise() {
        return _expertise;
    }

    public void setExpertise(String _expertise) {
        this._expertise = _expertise;
    }

    public String getBio() {
        return _bio;
    }

    public void setBio(String _bio) {
        this._bio = _bio;
    }

    public String getLastName() {
        return _lastName;
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
    public ClsChef(String _bio, String _firstName, String _lastName, String _expertise) {
        this._bio = _bio;
        this._lastName = _lastName;
        this._firstName = _firstName;
        this._expertise = _expertise;
    }

    @Override
    public String toString() {
        return _firstName + " " + _lastName;
    }
}