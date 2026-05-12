package com.culinarycore.service;
import java.util.List;

import com.culinarycore.dao.ClsWorkshopDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsWorkshop;

public class ClsWorkshopService {
    private IRepository<ClsWorkshop> _workshopDAO;

    public ClsWorkshopService(){
        _workshopDAO = new ClsWorkshopDAO();
    }

    public List<ClsWorkshop> getAll() {
        return _workshopDAO.findAll();
    }

    public boolean addWorkshop(ClsWorkshop workshopInstance) {
        return _workshopDAO.save(workshopInstance);
    }

    public boolean updateWorkshop(ClsWorkshop workshopInstance) {
        return _workshopDAO.update(workshopInstance);
    }

    public boolean deleteWorkshop(int ID) {
        return _workshopDAO.delete(ID);
    }
}
