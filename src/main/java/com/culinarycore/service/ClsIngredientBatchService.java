package com.culinarycore.service;
import java.util.Collections;
import java.util.List;

import com.culinarycore.dao.ClsIngredientBatchDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsIngredientBatch;
import com.culinarycore.model.dto.ClsKitchenBatchReportDTO;

public class ClsIngredientBatchService {
    private IRepository<ClsIngredientBatch> _iIngredientBatchDAO;

    public ClsIngredientBatchService() {
        this._iIngredientBatchDAO = new ClsIngredientBatchDAO();
    }

    public List<ClsIngredientBatch> getAll() {
        return _iIngredientBatchDAO.findAll(); }

    public boolean addBatch(ClsIngredientBatch b) {
        return _iIngredientBatchDAO.save(b);
    }

    public boolean updateBatch(ClsIngredientBatch b) {
        return _iIngredientBatchDAO.update(b);
    }

    public boolean deleteBatch(int id) {
        return _iIngredientBatchDAO.delete(id);
    }

    public List<ClsKitchenBatchReportDTO> getDashboardReport() {
        if (_iIngredientBatchDAO instanceof ClsIngredientBatchDAO) {
            return ((ClsIngredientBatchDAO) _iIngredientBatchDAO).getKitchenConsumptionReportDTO();
        }
        return Collections.emptyList();
    }
}
