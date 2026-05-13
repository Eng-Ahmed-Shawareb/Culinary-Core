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
        return _consumeDAO.save(c);
    }
    public boolean updateQuantity(int bID, int wID, int qty) {
        ClsConsumeDAO consumeDAO;
        if(_consumeDAO instanceof ClsConsumeDAO){
            consumeDAO = (ClsConsumeDAO) _consumeDAO;
        }else{
            consumeDAO = new ClsConsumeDAO();
        }
        ClsConsume consumeInstance;
        Optional<ClsConsume> consumeOptional = consumeDAO.findById(bID , wID);
        if(consumeOptional.isPresent()){
            consumeInstance = consumeOptional.get();
            consumeInstance.setQuantity(qty);
            return consumeDAO.update(consumeInstance);
        }
        return false;
    }
    public boolean removeLog(int bID, int wID) { ClsConsumeDAO consumeDAO;
        if(_consumeDAO instanceof ClsConsumeDAO){
            consumeDAO = (ClsConsumeDAO) _consumeDAO;
        }else{
            consumeDAO = new ClsConsumeDAO();
        }

        return consumeDAO.delete(bID , wID);
    }
}
