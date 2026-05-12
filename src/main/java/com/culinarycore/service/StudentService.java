package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import com.culinarycore.model.ClsStudent;

public class StudentService {
    public List<ClsStudent> getAll() { return Collections.emptyList(); }
    public boolean addStudent(ClsStudent s) { return true; }
    public boolean updateStudent(ClsStudent s) { return true; }
    public boolean deleteStudent(int id) { return true; }
    public List<Object[]> getStudentsWithWorkshopCount() { return Collections.emptyList(); }
}
