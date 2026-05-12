package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import com.culinarycore.model.ClsIngredientBatch;

public class IngredientBatchService {
    public List<ClsIngredientBatch> getAll() { return Collections.emptyList(); }
    public boolean addBatch(ClsIngredientBatch b) { return true; }
    public boolean updateBatch(ClsIngredientBatch b) { return true; }
    public boolean deleteBatch(int id) { return true; }
    public List<ClsIngredientBatch> getByKitchenLastMonth(int kID) { return Collections.emptyList(); }
}
