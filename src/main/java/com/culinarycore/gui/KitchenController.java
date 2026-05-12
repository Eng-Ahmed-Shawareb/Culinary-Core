package com.culinarycore.gui;

import com.culinarycore.model.ClsKitchen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class KitchenController extends BaseController {

    @FXML private TableView<ClsKitchen> tableView;
    @FXML private TextField txtID;
    @FXML private TextField txtName;
    @FXML private TextField txtType;
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
        tableView.getItems().addAll(kitchenService.getAll());
    }

    @Override
    public void clearForm() {
        txtID.clear();
        txtName.clear();
        txtType.clear();
    }

    @FXML
    public void onAdd_Click() {
        // Implement add logic using kitchenService
        showAlert("Added successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onUpdate_Click() {
        // Implement update logic using kitchenService
        showAlert("Updated successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onDelete_Click() {
        // Implement delete logic using kitchenService
        showAlert("Deleted successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onRowSelect() {
        ClsKitchen selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtID.setText(String.valueOf(selected.getID()));
            txtName.setText(selected.getName());
            txtType.setText(selected.get_type());
        }
    }
}
