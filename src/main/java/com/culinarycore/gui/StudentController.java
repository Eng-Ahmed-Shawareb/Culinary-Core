package com.culinarycore.gui;

import com.culinarycore.model.ClsStudent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class StudentController extends BaseController {

    @FXML private TableView<ClsStudent> tableView;
    @FXML private TextField txtID;
    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private ComboBox<String> cmbGender;
    @FXML private TextField txtPhone;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    @Override
    public void initialize() {
        cmbGender.getItems().addAll("M", "F", "O");
        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(studentService.getAll());
    }

    @Override
    public void clearForm() {
        txtID.clear();
        txtFirstName.clear();
        txtLastName.clear();
        cmbGender.getSelectionModel().clearSelection();
        txtPhone.clear();
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
        ClsStudent selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtID.setText(String.valueOf(selected.getID()));
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtPhone.setText(selected.getPhone());
            cmbGender.setValue(String.valueOf(selected.getGender()));
        }
    }
}
