package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.culinarycore.dao.ClsConsumeDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsConsume;

public class ClsConsumeService {
    private IRepository<ClsConsume> _consumeDAO;

    public ClsConsumeService(){
        _consumeDAO = new ClsConsumeDAO();
    }
    public List<ClsConsume> getAll() {
        return _consumeDAO.findAll();
    }
    public boolean logConsumption(ClsConsume c) {
        com.culinarycore.dao.ClsIngredientBatchDAO batchDAO = new com.culinarycore.dao.ClsIngredientBatchDAO();
        Optional<com.culinarycore.model.ClsIngredientBatch> batchOpt = batchDAO.findByID(c.getBatchID());
        if (batchOpt.isPresent()) {
            com.culinarycore.model.ClsIngredientBatch batch = batchOpt.get();
            if (c.getQuantity() > batch.getUnit()) {
                return false; // Prevent overdraft
            }
            batch.setUnit(batch.getUnit() - c.getQuantity());
            if (batch.getUnit() == 0) {
                batch.setState(com.culinarycore.model.StatusEnums.EnIngredientBatch.CONSUMED);
            }
            if (batchDAO.update(batch)) {
                return _consumeDAO.save(c);
            }
        }
        return false;
    }
    public boolean updateQuantity(int bID, int wID, int qty) {
        ClsConsumeDAO consumeDAO;
        if(_consumeDAO instanceof ClsConsumeDAO){
            consumeDAO = (ClsConsumeDAO) _consumeDAO;
        }else{
            consumeDAO = new ClsConsumeDAO();
        }
        
        com.culinarycore.dao.ClsIngredientBatchDAO batchDAO = new com.culinarycore.dao.ClsIngredientBatchDAO();
        Optional<ClsConsume> consumeOptional = consumeDAO.findById(bID , wID);
        Optional<com.culinarycore.model.ClsIngredientBatch> batchOpt = batchDAO.findByID(bID);
        
        if(consumeOptional.isPresent() && batchOpt.isPresent()){
            ClsConsume consumeInstance = consumeOptional.get();
            com.culinarycore.model.ClsIngredientBatch batch = batchOpt.get();
            
            int diff = qty - consumeInstance.getQuantity();
            if (diff > batch.getUnit()) {
                return false;
            }
            
            batch.setUnit(batch.getUnit() - diff);
            if (batch.getUnit() == 0) {
                batch.setState(com.culinarycore.model.StatusEnums.EnIngredientBatch.CONSUMED);
            } else if (batch.getUnit() > 0 && batch.getState() == com.culinarycore.model.StatusEnums.EnIngredientBatch.CONSUMED) {
                batch.setState(com.culinarycore.model.StatusEnums.EnIngredientBatch.EXIST);
            }
            
            if (batchDAO.update(batch)) {
                consumeInstance.setQuantity(qty);
                return consumeDAO.update(consumeInstance);
            }
        }
        return false;
    }
    public boolean removeLog(int bID, int wID) { 
        ClsConsumeDAO consumeDAO;
        if(_consumeDAO instanceof ClsConsumeDAO){
            consumeDAO = (ClsConsumeDAO) _consumeDAO;
        }else{
            consumeDAO = new ClsConsumeDAO();
        }

        com.culinarycore.dao.ClsIngredientBatchDAO batchDAO = new com.culinarycore.dao.ClsIngredientBatchDAO();
        Optional<ClsConsume> consumeOptional = consumeDAO.findById(bID, wID);
        Optional<com.culinarycore.model.ClsIngredientBatch> batchOpt = batchDAO.findByID(bID);
        
        if (consumeOptional.isPresent() && batchOpt.isPresent()) {
            ClsConsume consumeInstance = consumeOptional.get();
            com.culinarycore.model.ClsIngredientBatch batch = batchOpt.get();
            
            batch.setUnit(batch.getUnit() + consumeInstance.getQuantity());
            if (batch.getState() == com.culinarycore.model.StatusEnums.EnIngredientBatch.CONSUMED) {
                batch.setState(com.culinarycore.model.StatusEnums.EnIngredientBatch.EXIST);
            }
            
            if (batchDAO.update(batch)) {
                return consumeDAO.delete(bID, wID);
            }
        }
        return false;
    }
}
