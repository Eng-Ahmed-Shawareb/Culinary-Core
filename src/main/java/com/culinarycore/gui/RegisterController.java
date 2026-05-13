package com.culinarycore.gui;

import com.culinarycore.model.ClsRegister;
import com.culinarycore.model.ClsStudent;
import com.culinarycore.model.ClsWorkshop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private int currentSelectedStudentId = -1;
    private int currentSelectedWorkshopId = -1;

    @Override
    public void initialize() {
        cmbState.getItems().addAll("PENDING", "CONFIRMED");
        
        TableColumn<ClsRegister, String> colStudent = new TableColumn<>("Student");
        colStudent.setCellValueFactory(cellData -> {
            int sId = cellData.getValue().getStudentID();
            for (ClsStudent s : cmbStudent.getItems()) {
                if (s.getID() == sId) return new javafx.beans.property.SimpleStringProperty(s.getFirstName() + " " + s.getLastName());
            }
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(sId));
        });

        TableColumn<ClsRegister, String> colWorkshop = new TableColumn<>("Workshop");
        colWorkshop.setCellValueFactory(cellData -> {
            int wId = cellData.getValue().getWorkshopID();
            for (ClsWorkshop w : cmbWorkshop.getItems()) {
                if (w.getID() == wId) return new javafx.beans.property.SimpleStringProperty(w.getTitle());
            }
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(wId));
        });

        TableColumn<ClsRegister, java.time.LocalDate> colDate = new TableColumn<>("Register Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("registerDate"));

        TableColumn<ClsRegister, com.culinarycore.model.StatusEnums.EnPaymentStatus> colState = new TableColumn<>("Payment Status");
        colState.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        tableView.getColumns().setAll(colStudent, colWorkshop, colDate, colState);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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
        currentSelectedStudentId = -1;
        currentSelectedWorkshopId = -1;
        cmbStudent.getSelectionModel().clearSelection();
        cmbWorkshop.getSelectionModel().clearSelection();
        dtpRegDate.setValue(null);
        cmbState.getSelectionModel().clearSelection();
    }

    @FXML
    public void onEnroll_Click() {
        if (cmbStudent.getValue() == null || cmbWorkshop.getValue() == null || 
            dtpRegDate.getValue() == null || cmbState.getValue() == null) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        ClsRegister newReg = new ClsRegister(
            cmbStudent.getValue().getID(),
            cmbWorkshop.getValue().getID(),
            dtpRegDate.getValue(),
            com.culinarycore.model.StatusEnums.EnPaymentStatus.valueOf(cmbState.getValue())
        );
        
        boolean success = registerService.enroll(newReg);
        if (success) {
            showAlert("Enrolled successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to enroll.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedStudentId == -1 || currentSelectedWorkshopId == -1) {
            showAlert("Please select a registration to update.", true);
            return;
        }
        if (cmbState.getValue() == null) {
            showAlert("Please select a payment status.", true);
            return;
        }

        boolean success = registerService.updateState(
            currentSelectedStudentId,
            currentSelectedWorkshopId,
            com.culinarycore.model.StatusEnums.EnPaymentStatus.valueOf(cmbState.getValue())
        );
        
        if (success) {
            showAlert("Status updated successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to update status.", true);
        }
    }

    @FXML
    public void onCancel_Click() {
        if (currentSelectedStudentId == -1 || currentSelectedWorkshopId == -1) {
            showAlert("Please select a registration to cancel.", true);
            return;
        }
        
        boolean success = registerService.cancel(currentSelectedStudentId, currentSelectedWorkshopId);
        
        if (success) {
            showAlert("Cancelled successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to cancel registration.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsRegister selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedStudentId = selected.getStudentID();
            currentSelectedWorkshopId = selected.getWorkshopID();
            
            dtpRegDate.setValue(selected.getRegisterDate());
            
            for (String state : cmbState.getItems()) {
                if (state.equalsIgnoreCase(selected.getPaymentStatus().name())) {
                    cmbState.setValue(state);
                    break;
                }
            }
            
            for (ClsStudent s : cmbStudent.getItems()) {
                if (s.getID() == selected.getStudentID()) {
                    cmbStudent.setValue(s);
                    break;
                }
            }
            
            for (ClsWorkshop w : cmbWorkshop.getItems()) {
                if (w.getID() == selected.getWorkshopID()) {
                    cmbWorkshop.setValue(w);
                    break;
                }
            }
        }
    }
}
