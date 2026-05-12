package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import com.culinarycore.model.ClsRegister;

public class RegisterService {
    public List<ClsRegister> getAll() { return Collections.emptyList(); }
    public boolean enroll(ClsRegister r) { return true; }
    public boolean updateState(int sID, int wID, String s) { return true; }
    public boolean cancel(int sID, int wID) { return true; }
    public List<Object[]> getTopExpertiseByEnrollments() { return Collections.emptyList(); }
}
