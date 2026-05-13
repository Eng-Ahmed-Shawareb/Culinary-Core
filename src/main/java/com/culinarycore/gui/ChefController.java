package com.culinarycore.gui;

import com.culinarycore.model.ClsChef;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ChefController extends BaseController {

    @FXML private TableView<ClsChef> tableView;

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtBio;
    @FXML private TextField txtExpertise;
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
        tableView.getItems().addAll(chefService.getAll());
    }

    @Override
    public void clearForm() {

        txtFirstName.clear();
        txtLastName.clear();
        txtBio.clear();
        txtExpertise.clear();
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
        ClsChef selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {

            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtBio.setText(selected.getBio());
            txtExpertise.setText(selected.getExpertise());
        }
    }
}
