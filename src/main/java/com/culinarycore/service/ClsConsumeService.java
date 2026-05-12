package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import com.culinarycore.model.ClsConsume;

public class ClsConsumeService {
    public List<ClsConsume> getAll() { return Collections.emptyList(); }
    public boolean logConsumption(ClsConsume c) { return true; }
    public boolean updateQuantity(int bID, int wID, int qty) { return true; }
    public boolean removeLog(int bID, int wID) { return true; }
}
