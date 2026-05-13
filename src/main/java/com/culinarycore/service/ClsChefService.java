package com.culinarycore.service;
import java.util.List;
import java.util.Optional;

import com.culinarycore.dao.ClsChefDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsChef;

public class ClsChefService {
    private IRepository<ClsChef>_chefDAO;
    public ClsChefService(){
        _chefDAO=new ClsChefDAO();


    }
    public Optional<ClsChef>getById(int id){
        return _chefDAO.findByID(id);
    }
    public List<ClsChef> getAll() {

        return _chefDAO.findAll(); }
    public boolean addChef(ClsChef c) { return _chefDAO.save(c); }
    public boolean updateChef(ClsChef c) { return _chefDAO.update(c); }
    public boolean deleteChef(int id) { return _chefDAO.delete(id); }
    public List<ClsChef> getInactiveLastMonth() {
        ClsChefDAO chefDAO2;
        if(_chefDAO instanceof ClsChefDAO) {
            chefDAO2 = (ClsChefDAO) _chefDAO;
        }
        else
            chefDAO2=new ClsChefDAO();
        {
            return chefDAO2.getInactiveLastMonth();
        }
    }
}