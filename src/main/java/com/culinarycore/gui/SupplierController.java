package com.culinarycore.gui;

import com.culinarycore.model.ClsSupplier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class SupplierController extends BaseController {

    @FXML private TableView<ClsSupplier> tableView;

    @FXML private TextField txtName;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    @Override
    public void initialize() {
        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(supplierService.getAll());
    }

    @Override
    public void clearForm() {

        txtName.clear();
    }

    @FXML
    public void onAdd_Click() {
        showAlert("Added successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onUpdate_Click() {
        showAlert("Updated successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onDelete_Click() {
        showAlert("Deleted successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onRowSelect() {
        ClsSupplier selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {

            txtName.setText(selected.getName());
        }
    }
}
