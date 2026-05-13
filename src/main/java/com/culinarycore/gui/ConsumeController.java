package com.culinarycore.gui;

import com.culinarycore.model.ClsConsume;
import com.culinarycore.model.ClsIngredientBatch;
import com.culinarycore.model.ClsWorkshop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private int currentSelectedBatchId = -1;
    private int currentSelectedWorkshopId = -1;

    @Override
    public void initialize() {
        TableColumn<ClsConsume, Integer> colBatch = new TableColumn<>("Batch ID");
        colBatch.setCellValueFactory(new PropertyValueFactory<>("batchID"));

        TableColumn<ClsConsume, Integer> colWorkshop = new TableColumn<>("Workshop ID");
        colWorkshop.setCellValueFactory(new PropertyValueFactory<>("workshopID"));

        TableColumn<ClsConsume, Integer> colQuantity = new TableColumn<>("Quantity");
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<ClsConsume, java.time.LocalDate> colDate = new TableColumn<>("Consume Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("consumeDate"));

        tableView.getColumns().setAll(colBatch, colWorkshop, colQuantity, colDate);

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
        currentSelectedBatchId = -1;
        currentSelectedWorkshopId = -1;
        cmbBatch.getSelectionModel().clearSelection();
        cmbWorkshop.getSelectionModel().clearSelection();
        txtQuantity.clear();
        dtpConsumeDate.setValue(null);
    }

    @FXML
    public void onLog_Click() {
        if (cmbBatch.getValue() == null || cmbWorkshop.getValue() == null || 
            txtQuantity.getText().trim().isEmpty() || dtpConsumeDate.getValue() == null) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        try {
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            ClsConsume newConsume = new ClsConsume(
                cmbBatch.getValue().getID(),
                cmbWorkshop.getValue().getID(),
                qty,
                dtpConsumeDate.getValue()
            );
            
            boolean success = consumeService.logConsumption(newConsume);
            if (success) {
                showAlert("Consumption logged successfully!", false);
                loadData();
                clearForm();
            } else {
                showAlert("Failed to log consumption.", true);
            }
        } catch (NumberFormatException e) {
            showAlert("Quantity must be a valid integer.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedBatchId == -1 || currentSelectedWorkshopId == -1) {
            showAlert("Please select a log to update.", true);
            return;
        }
        if (txtQuantity.getText().trim().isEmpty()) {
            showAlert("Please fill in the quantity.", true);
            return;
        }

        try {
            int qty = Integer.parseInt(txtQuantity.getText().trim());
            boolean success = consumeService.updateQuantity(
                currentSelectedBatchId,
                currentSelectedWorkshopId,
                qty
            );
            
            if (success) {
                showAlert("Quantity updated successfully!", false);
                loadData();
                clearForm();
            } else {
                showAlert("Failed to update quantity.", true);
            }
        } catch (NumberFormatException e) {
            showAlert("Quantity must be a valid integer.", true);
        }
    }

    @FXML
    public void onRemove_Click() {
        if (currentSelectedBatchId == -1 || currentSelectedWorkshopId == -1) {
            showAlert("Please select a log to remove.", true);
            return;
        }
        
        boolean success = consumeService.removeLog(currentSelectedBatchId, currentSelectedWorkshopId);
        
        if (success) {
            showAlert("Removed successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to remove log.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsConsume selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedBatchId = selected.getBatchID();
            currentSelectedWorkshopId = selected.getWorkshopID();
            
            txtQuantity.setText(String.valueOf(selected.getQuantity()));
            dtpConsumeDate.setValue(selected.getConsumeDate());
            
            for (ClsIngredientBatch b : cmbBatch.getItems()) {
                if (b.getID() == selected.getBatchID()) {
                    cmbBatch.setValue(b);
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
