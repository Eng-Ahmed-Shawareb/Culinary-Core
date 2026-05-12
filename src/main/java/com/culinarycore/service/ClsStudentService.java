package com.culinarycore.service;
import java.util.Collections;
import java.util.List;

import com.culinarycore.dao.ClsStudentDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsStudent;
import com.culinarycore.model.dto.ClsStudentWorkshopCountDTO;

public class ClsStudentService {
    private IRepository<ClsStudent> _studentDAO;

    public ClsStudentService(){
        _studentDAO = new ClsStudentDAO();
    }

    public List<ClsStudent> getAll() {
        return _studentDAO.findAll();
    }

    public boolean addStudent(ClsStudent studentInstance) {
        return _studentDAO.save(studentInstance);
    }

    public boolean updateStudent(ClsStudent studentInstance) {
        return _studentDAO.update(studentInstance);
    }

    public boolean deleteStudent(int ID) {
        return _studentDAO.delete(ID);
    }

    public List<ClsStudentWorkshopCountDTO> getStudentsWithWorkShopCount() {
        ClsStudentDAO studentDAO;

        if (_studentDAO instanceof ClsStudentDAO) {
            studentDAO = (ClsStudentDAO) _studentDAO;
        } else {
            studentDAO = new ClsStudentDAO();
        }

        return studentDAO.getStudentWorkshopCounts();
    }
}
