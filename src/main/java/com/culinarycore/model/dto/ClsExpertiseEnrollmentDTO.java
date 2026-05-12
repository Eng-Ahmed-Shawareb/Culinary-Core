package com.culinarycore.model.dto;

public class ClsExpertiseEnrollmentDTO {
    private String _expertise;
    private int _enrollmentCount;

    public ClsExpertiseEnrollmentDTO(String _expertise, int _enrollmentCount) {
        this._expertise = _expertise;
        this._enrollmentCount = _enrollmentCount;
    }

    public String getExpertise() { return _expertise; }
    public int getEnrollmentCount() { return _enrollmentCount; }
}