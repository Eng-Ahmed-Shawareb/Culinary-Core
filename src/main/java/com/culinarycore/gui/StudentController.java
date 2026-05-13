package com.culinarycore.gui;

import com.culinarycore.model.ClsStudent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

public class StudentController extends BaseController {

    @FXML private TableView<ClsStudent> tableView;

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private ComboBox<String> cmbGender;
    @FXML private TextField txtPhone;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    private int currentSelectedId = -1;

    @Override
    public void initialize() {
        cmbGender.getItems().addAll("M", "F");
        
        TableColumn<ClsStudent, String> colFirstName = new TableColumn<>("First Name");
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        TableColumn<ClsStudent, String> colLastName = new TableColumn<>("Last Name");
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        
        TableColumn<ClsStudent, Character> colGender = new TableColumn<>("Gender");
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        
        TableColumn<ClsStudent, String> colPhone = new TableColumn<>("Phone");
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        tableView.getColumns().setAll(colFirstName, colLastName, colGender, colPhone);

        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(studentService.getAll());
    }

    @Override
    public void clearForm() {
        currentSelectedId = -1;
        txtFirstName.clear();
        txtLastName.clear();
        cmbGender.getSelectionModel().clearSelection();
        txtPhone.clear();
    }

    @FXML
    public void onAdd_Click() {
        if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty() || cmbGender.getValue() == null) {
            showAlert("Please fill in required fields (First Name, Last Name, Gender).", true);
            return;
        }
        
        ClsStudent newStudent = new ClsStudent(
            txtPhone.getText().trim(),
            cmbGender.getValue().charAt(0),
            txtLastName.getText().trim(),
            txtFirstName.getText().trim()
        );
        boolean success = studentService.addStudent(newStudent);
        
        if (success) {
            showAlert("Student added successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to add student.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a student to update.", true);
            return;
        }
        if (txtFirstName.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty() || cmbGender.getValue() == null) {
            showAlert("Please fill in required fields (First Name, Last Name, Gender).", true);
            return;
        }

        ClsStudent updatedStudent = new ClsStudent(
            txtPhone.getText().trim(),
            cmbGender.getValue().charAt(0),
            txtLastName.getText().trim(),
            txtFirstName.getText().trim()
        );
        updatedStudent.setID(currentSelectedId);
        boolean success = studentService.updateStudent(updatedStudent);
        
        if (success) {
            showAlert("Student updated successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to update student.", true);
        }
    }

    @FXML
    public void onDelete_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a student to delete.", true);
            return;
        }
        
        boolean success = studentService.deleteStudent(currentSelectedId);
        
        if (success) {
            showAlert("Student deleted successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to delete student. They may be enrolled in workshops.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsStudent selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedId = selected.getID();
            txtFirstName.setText(selected.getFirstName());
            txtLastName.setText(selected.getLastName());
            txtPhone.setText(selected.getPhone());
            cmbGender.setValue(String.valueOf(selected.getGender()));
        }
    }
}
