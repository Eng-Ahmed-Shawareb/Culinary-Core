package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.culinarycore.dao.ClsRegisterDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsRegister;
import com.culinarycore.model.StatusEnums.EnPaymentStatus;
import com.culinarycore.model.dto.ClsExpertiseEnrollmentDTO;
import com.culinarycore.model.dto.ClsStudentWorkshopCountDTO;

public class ClsRegisterService {
    private IRepository<ClsRegister> _registerDAO;

    public ClsRegisterService(){
        _registerDAO = new ClsRegisterDAO();
    }

    public List<ClsRegister> getAll() {
        return _registerDAO.findAll();
    }

    public boolean enroll(ClsRegister registerInstance) {
        return _registerDAO.save(registerInstance);
    }

    public boolean updateState(int studentID, int workshopID, EnPaymentStatus state) {
        ClsRegisterDAO registerDAO;

        if (_registerDAO instanceof ClsRegisterDAO) {
            registerDAO = (ClsRegisterDAO) _registerDAO;
        } else {
            registerDAO = new ClsRegisterDAO();
        }
        Optional<ClsRegister> registerOptional = registerDAO.findByID(studentID , workshopID);
        ClsRegister registerInstance;
        if(registerOptional.isPresent()){
            registerInstance = registerOptional.get();
            registerInstance.setPaymentStatus(state);
            return registerDAO.update(registerInstance);
        }
        return false;
    }

    public boolean cancel(int studentID, int workshopID) {
        ClsRegisterDAO registerDAO;

        if (_registerDAO instanceof ClsRegisterDAO) {
            registerDAO = (ClsRegisterDAO) _registerDAO;
        } else {
            registerDAO = new ClsRegisterDAO();
        }
        return registerDAO.delete(studentID , workshopID);
    }

    public Optional<ClsExpertiseEnrollmentDTO> getTopExpertiseByEnrollments() {
        ClsRegisterDAO registerDAO;

        if (_registerDAO instanceof ClsRegisterDAO) {
            registerDAO = (ClsRegisterDAO) _registerDAO;
        } else {
            registerDAO = new ClsRegisterDAO();
        }

        return registerDAO.getTopExpertiseByEnrollments();
    }
}
