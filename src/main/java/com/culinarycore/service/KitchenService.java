package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import com.culinarycore.model.ClsKitchen;

public class KitchenService {
    public List<ClsKitchen> getAll() { return Collections.emptyList(); }
    public boolean addKitchen(ClsKitchen k) { return true; }
    public boolean updateKitchen(ClsKitchen k) { return true; }
    public boolean deleteKitchen(int id) { return true; }
    public List<ClsKitchen> getUnusedLastMonth() { return Collections.emptyList(); }
}
