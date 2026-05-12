package com.culinarycore.gui;

import com.culinarycore.model.ClsRegister;
import com.culinarycore.model.ClsStudent;
import com.culinarycore.model.ClsWorkshop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

public class RegisterController extends BaseController {

    @FXML private TableView<ClsRegister> tableView;
    @FXML private ComboBox<ClsStudent> cmbStudent;
    @FXML private ComboBox<ClsWorkshop> cmbWorkshop;
    @FXML private DatePicker dtpRegDate;
    @FXML private ComboBox<String> cmbState;
    @FXML private Button btnEnroll;
    @FXML private Button btnUpdate;
    @FXML private Button btnCancel;
    @FXML private Button btnClear;

    @Override
    public void initialize() {
        cmbState.getItems().addAll("Pending", "Paid", "Cancelled");
        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(registerService.getAll());
        cmbStudent.getItems().clear();
        cmbStudent.getItems().addAll(studentService.getAll());
        cmbWorkshop.getItems().clear();
        cmbWorkshop.getItems().addAll(workshopService.getAll());
    }

    @Override
    public void clearForm() {
        cmbStudent.getSelectionModel().clearSelection();
        cmbWorkshop.getSelectionModel().clearSelection();
        dtpRegDate.setValue(null);
        cmbState.getSelectionModel().clearSelection();
    }

    @FXML
    public void onEnroll_Click() {
        showAlert("Enrolled successfully!", false);
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
    public void onCancel_Click() {
        showAlert("Cancelled successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onRowSelect() {
        ClsRegister selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            dtpRegDate.setValue(selected.getRegisterDate());
            cmbState.setValue(selected.getPaymentStatus().toString()); // Assuming state is an enum or string
        }
    }
}
