package com.culinarycore.service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.culinarycore.dao.ClsSupplierDAO;
import com.culinarycore.dao.interfaces.IRepository;
import com.culinarycore.model.ClsSupplier;
import com.culinarycore.model.dto.ClsSupplierTopDTO;

public class ClsSupplierService {
    private IRepository<ClsSupplier> _supplierDAO;

    public ClsSupplierService() {
        this._supplierDAO = new ClsSupplierDAO();
    }

    public List<ClsSupplier> getAll() {
        return _supplierDAO.findAll();
    }
    public boolean addSupplier(ClsSupplier s) {
        return _supplierDAO.save(s);
    }
    public boolean updateSupplier(ClsSupplier s) {
        return _supplierDAO.update(s);
    }
    public boolean deleteSupplier(int id) {
        return _supplierDAO.delete(id);
    }
    public Optional<ClsSupplierTopDTO> getTopSupplierLastMonth() {
      ClsSupplierDAO supplierDAO;
      if(_supplierDAO instanceof  ClsSupplierDAO) {
      supplierDAO=(ClsSupplierDAO) _supplierDAO;
      }
      else {
          supplierDAO=new ClsSupplierDAO();
      }
      return supplierDAO.findTopByQuantityLastMonth();
    }
}
