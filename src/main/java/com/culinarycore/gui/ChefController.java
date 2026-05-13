package com.culinarycore.gui;

import com.culinarycore.model.ClsChef;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private int currentSelectedId = -1;

    @Override
    public void initialize() {
        TableColumn<ClsChef, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        TableColumn<ClsChef, String> colLastName = new TableColumn<>("Last Name");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        TableColumn<ClsChef, String> colExpertise = new TableColumn<>("Expertise");
        colExpertise.setCellValueFactory(new PropertyValueFactory<>("expertise"));
        
        TableColumn<ClsChef, String> colBio = new TableColumn<>("Bio");
        colBio.setCellValueFactory(new PropertyValueFactory<>("bio"));
        
        tableView.getColumns().setAll(colFirstName, colLastName, colExpertise, colBio);

        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(chefService.getAll());
    }

    @Override
    public void clearForm() {
        currentSelectedId = -1;
        txtFirstName.clear();
        txtLastName.clear();
        txtBio.clear();
        txtExpertise.clear();
    }

    @FXML
    public void onAdd_Click() {
        if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty()) {
            showAlert("Please fill in required fields (First and Last name).", true);
            return;
        }
        
        ClsChef newChef = new ClsChef(
            txtBio.getText().trim(),
            txtFirstName.getText().trim(),
            txtLastName.getText().trim(),
            txtExpertise.getText().trim()
        );
        boolean success = chefService.addChef(newChef);
        
        if (success) {
            showAlert("Chef added successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to add chef.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a chef to update.", true);
            return;
        }
        if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty()) {
            showAlert("Please fill in required fields (First and Last name).", true);
            return;
        }

        ClsChef updatedChef = new ClsChef(
            txtBio.getText().trim(),
            txtFirstName.getText().trim(),
            txtLastName.getText().trim(),
            txtExpertise.getText().trim()
        );
        updatedChef.setID(currentSelectedId);
        boolean success = chefService.updateChef(updatedChef);
        
        if (success) {
            showAlert("Chef updated successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to update chef.", true);
        }
    }

    @FXML
    public void onDelete_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a chef to delete.", true);
            return;
        }
        
        boolean success = chefService.deleteChef(currentSelectedId);
        
        if (success) {
            showAlert("Chef deleted successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to delete chef.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsChef selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedId = selected.getID();
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtBio.setText(selected.getBio());
            txtExpertise.setText(selected.getExpertise());
        }
    }
}
