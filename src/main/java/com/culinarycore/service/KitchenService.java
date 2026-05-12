package com.culinarycore.service;
import com.culinarycore.dao.ClsKitchenDAO;
import com.culinarycore.dao.interfaces.IRepository;
import java.util.Collections;
import java.util.List;
import com.culinarycore.model.ClsKitchen;

public class KitchenService {
    private final IRepository<ClsKitchen> kitchenDAO;

    public KitchenService() {
        this.kitchenDAO = new ClsKitchenDAO();
    }

    public List<ClsKitchen> getAll() { return kitchenDAO.findAll(); }
    public boolean addKitchen(ClsKitchen k) { return kitchenDAO.save(k); }
    public boolean updateKitchen(ClsKitchen k) { return kitchenDAO.update(k); }
    public boolean deleteKitchen(int id) { return kitchenDAO.delete(id); }
    public List<ClsKitchen> getUnusedLastMonth() { return Collections.emptyList(); }
}
