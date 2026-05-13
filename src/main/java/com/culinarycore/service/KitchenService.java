package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.culinarycore.dao.ClsKitchenDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsChef;
import com.culinarycore.model.ClsKitchen;

public class KitchenService {
    IRepository<ClsKitchen>_kitchenDAO;
    public KitchenService(){
        _kitchenDAO=new ClsKitchenDAO();
    }
    public Optional<ClsKitchen> getById(int id){
        return _kitchenDAO.findByID(id);
    }
    public List<ClsKitchen> getAll() { return _kitchenDAO.findAll(); }
    public boolean addKitchen(ClsKitchen k) { return _kitchenDAO.save(k); }
    public boolean updateKitchen(ClsKitchen k) { return _kitchenDAO.update(k); }
    public boolean deleteKitchen(int id) { return _kitchenDAO.delete(id); }
    public List<ClsKitchen> getUnusedLastMonth() {
        ClsKitchenDAO kitchenDAO2;
        if(_kitchenDAO instanceof ClsKitchenDAO) {
            kitchenDAO2 = (ClsKitchenDAO) _kitchenDAO;
        }
        else {
            kitchenDAO2 = new ClsKitchenDAO();
        }
        return kitchenDAO2.getUnusedLastMonth();
    }
}