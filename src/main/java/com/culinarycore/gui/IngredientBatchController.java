package com.culinarycore.gui;

import com.culinarycore.model.ClsIngredientBatch;
import com.culinarycore.model.ClsSupplier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class IngredientBatchController extends BaseController {

    @FXML private TableView<ClsIngredientBatch> tableView;

    @FXML private TextField txtName;
    @FXML private TextField txtUnits;
    @FXML private ComboBox<ClsSupplier> cmbSupplier;
    @FXML private DatePicker dtpDelivery;
    @FXML private DatePicker dtpExpiry;
    @FXML private ComboBox<String> cmbState;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    @Override
    public void initialize() {
        cmbState.getItems().addAll("Fresh", "Expiring", "Expired", "Used");
        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(batchService.getAll());
        cmbSupplier.getItems().clear();
        cmbSupplier.getItems().addAll(supplierService.getAll());
    }

    @Override
    public void clearForm() {

        txtName.clear();
        txtUnits.clear();
        cmbSupplier.getSelectionModel().clearSelection();
        dtpDelivery.setValue(null);
        dtpExpiry.setValue(null);
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
        ClsIngredientBatch selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {

            txtName.setText(selected.getName());
            txtUnits.setText(String.valueOf(selected.getUnit()));
            dtpDelivery.setValue(selected.getDeliveryDate());
            dtpExpiry.setValue(selected.getExpirationDate());
        }
    }
}
