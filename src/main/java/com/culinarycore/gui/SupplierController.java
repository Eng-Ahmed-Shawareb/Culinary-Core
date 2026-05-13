package com.culinarycore.gui;

import com.culinarycore.model.ClsSupplier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

public class SupplierController extends BaseController {

    @FXML
    private TableView<ClsSupplier> tableView;

    @FXML
    private TextField txtName;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;

    private int currentSelectedId = -1;

    @Override
    public void initialize() {
        TableColumn<ClsSupplier, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<ClsSupplier, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableView.getColumns().setAll(colID, colName);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(supplierService.getAll());
    }

    @Override
    public void clearForm() {
        currentSelectedId = -1;
        txtName.clear();
    }

    @FXML
    public void onAdd_Click() {
        if (txtName.getText().trim().isEmpty()) {
            showAlert("Please fill in the supplier name.", true);
            return;
        }

        ClsSupplier newSupplier = new ClsSupplier(txtName.getText().trim());
        boolean success = supplierService.addSupplier(newSupplier);

        if (success) {
            showAlert("Supplier added successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to add supplier.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a supplier to update.", true);
            return;
        }
        if (txtName.getText().trim().isEmpty()) {
            showAlert("Please fill in the supplier name.", true);
            return;
        }

        ClsSupplier updatedSupplier = new ClsSupplier(currentSelectedId, txtName.getText().trim());
        boolean success = supplierService.updateSupplier(updatedSupplier);

        if (success) {
            showAlert("Supplier updated successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to update supplier.", true);
        }
    }

    @FXML
    public void onDelete_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a supplier to delete.", true);
            return;
        }

        boolean success = supplierService.deleteSupplier(currentSelectedId);

        if (success) {
            showAlert("Supplier deleted successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to delete supplier. They may have ingredient batches.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsSupplier selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedId = selected.getID();
            txtName.setText(selected.getName());
        }
    }
}
