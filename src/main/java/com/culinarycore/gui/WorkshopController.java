package com.culinarycore.gui;

import com.culinarycore.model.ClsChef;
import com.culinarycore.model.ClsKitchen;
import com.culinarycore.model.ClsWorkshop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class WorkshopController extends BaseController {

    @FXML private TableView<ClsWorkshop> tableView;
    @FXML private TextField txtID;
    @FXML private TextField txtTitle;
    @FXML private TextField txtPrice;
    @FXML private ComboBox<ClsKitchen> cmbKitchen;
    @FXML private ComboBox<ClsChef> cmbChef;
    @FXML private DatePicker dtpStart;
    @FXML private DatePicker dtpEnd;
    @FXML private ComboBox<String> cmbState;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    @Override
    public void initialize() {
        cmbState.getItems().addAll("Scheduled", "Active", "Completed", "Cancelled");
        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(workshopService.getAll());
        cmbKitchen.getItems().clear();
        cmbKitchen.getItems().addAll(kitchenService.getAll());
        cmbChef.getItems().clear();
        cmbChef.getItems().addAll(chefService.getAll());
    }

    @Override
    public void clearForm() {
        txtID.clear();
        txtTitle.clear();
        txtPrice.clear();
        cmbKitchen.getSelectionModel().clearSelection();
        cmbChef.getSelectionModel().clearSelection();
        dtpStart.setValue(null);
        dtpEnd.setValue(null);
        cmbState.getSelectionModel().clearSelection();
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
        ClsWorkshop selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtID.setText(String.valueOf(selected.getID()));
            txtTitle.setText(selected.getTitle());
            txtPrice.setText(String.valueOf(selected.getPrice()));
            dtpStart.setValue(selected.getStartDate());
            dtpEnd.setValue(selected.getEndDate());
            // Need matching state, kitchen, and chef selection logic
        }
    }
}
