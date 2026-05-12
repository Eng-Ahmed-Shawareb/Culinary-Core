package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import com.culinarycore.model.ClsChef;

public class ChefService {
    public List<ClsChef> getAll() { return Collections.emptyList(); }
    public boolean addChef(ClsChef c) { return true; }
    public boolean updateChef(ClsChef c) { return true; }
    public boolean deleteChef(int id) { return true; }
    public List<ClsChef> getInactiveLastMonth() { return Collections.emptyList(); }
}
