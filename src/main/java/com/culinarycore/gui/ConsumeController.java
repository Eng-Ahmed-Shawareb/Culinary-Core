package com.culinarycore.gui;

import com.culinarycore.model.ClsConsume;
import com.culinarycore.model.ClsIngredientBatch;
import com.culinarycore.model.ClsWorkshop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ConsumeController extends BaseController {

    @FXML private TableView<ClsConsume> tableView;
    @FXML private ComboBox<ClsIngredientBatch> cmbBatch;
    @FXML private ComboBox<ClsWorkshop> cmbWorkshop;
    @FXML private TextField txtQuantity;
    @FXML private DatePicker dtpConsumeDate;
    @FXML private Button btnLog;
    @FXML private Button btnUpdate;
    @FXML private Button btnRemove;
    @FXML private Button btnClear;

    @Override
    public void initialize() {
        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(consumeService.getAll());
        cmbBatch.getItems().clear();
        cmbBatch.getItems().addAll(batchService.getAll());
        cmbWorkshop.getItems().clear();
        cmbWorkshop.getItems().addAll(workshopService.getAll());
    }

    @Override
    public void clearForm() {
        cmbBatch.getSelectionModel().clearSelection();
        cmbWorkshop.getSelectionModel().clearSelection();
        txtQuantity.clear();
        dtpConsumeDate.setValue(null);
    }

    @FXML
    public void onLog_Click() {
        showAlert("Consumption logged successfully!", false);
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
    public void onRemove_Click() {
        showAlert("Removed successfully!", false);
        loadData();
        clearForm();
    }

    @FXML
    public void onRowSelect() {
        ClsConsume selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            txtQuantity.setText(String.valueOf(selected.getQuantity()));
            dtpConsumeDate.setValue(selected.getConsumeDate());
        }
    }
}
