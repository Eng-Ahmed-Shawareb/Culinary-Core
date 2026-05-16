package com.culinarycore.gui;

import com.culinarycore.model.ClsIngredientBatch;
import com.culinarycore.model.ClsSupplier;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
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

    private int currentSelectedId = -1;

    @Override
    public void initialize() {
        cmbState.getItems().addAll("CONSUMED", "EXIST");
        
        TableColumn<ClsIngredientBatch, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<ClsIngredientBatch, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ClsIngredientBatch, Integer> colUnits = new TableColumn<>("Remaining Units");
        colUnits.setCellValueFactory(new PropertyValueFactory<>("unit"));

        TableColumn<ClsIngredientBatch, java.time.LocalDate> colDelivery = new TableColumn<>("Delivery Date");
        colDelivery.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));

        TableColumn<ClsIngredientBatch, java.time.LocalDate> colExpiry = new TableColumn<>("Expiry Date");
        colExpiry.setCellValueFactory(new PropertyValueFactory<>("expirationDate"));

        TableColumn<ClsIngredientBatch, String> colSupplier = new TableColumn<>("Supplier");
        colSupplier.setCellValueFactory(cellData -> {
            int sId = cellData.getValue().getSupplierID();
            for (com.culinarycore.model.ClsSupplier s : cmbSupplier.getItems()) {
                if (s.getID() == sId) return new javafx.beans.property.SimpleStringProperty(s.getName());
            }
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(sId));
        });

        TableColumn<ClsIngredientBatch, com.culinarycore.model.StatusEnums.EnIngredientBatch> colState = new TableColumn<>("State");
        colState.setCellValueFactory(new PropertyValueFactory<>("state"));

        tableView.getColumns().setAll(colID, colName, colUnits, colSupplier, colDelivery, colExpiry, colState);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        // Bind column widths to table width
        int numCols = 7;
        colID.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colName.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colUnits.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colSupplier.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colDelivery.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colExpiry.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colState.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));

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
        currentSelectedId = -1;
        txtName.clear();
        txtUnits.clear();
        cmbSupplier.getSelectionModel().clearSelection();
        dtpDelivery.setValue(null);
        dtpExpiry.setValue(null);
        cmbState.getSelectionModel().clearSelection();
    }

    @FXML
    public void onAdd_Click() {
        if (txtName.getText().trim().isEmpty() || txtUnits.getText().trim().isEmpty() || 
            cmbSupplier.getValue() == null || dtpDelivery.getValue() == null || 
            dtpExpiry.getValue() == null || cmbState.getValue() == null) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        try {
            int units = Integer.parseInt(txtUnits.getText().trim());
            ClsIngredientBatch newBatch = new ClsIngredientBatch(
                -1,
                cmbSupplier.getValue().getID(),
                units,
                txtName.getText().trim(),
                dtpExpiry.getValue(),
                dtpDelivery.getValue(),
                com.culinarycore.model.StatusEnums.EnIngredientBatch.valueOf(cmbState.getValue().toUpperCase())
            );
            
            boolean success = batchService.addBatch(newBatch);
            if (success) {
                showAlert("Ingredient batch added successfully!", false);
                loadData();
                clearForm();
            } else {
                showAlert("Failed to add ingredient batch.", true);
            }
        } catch (NumberFormatException e) {
            showAlert("Units must be a valid integer number.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a batch to update.", true);
            return;
        }
        if (txtName.getText().trim().isEmpty() || txtUnits.getText().trim().isEmpty() || 
            cmbSupplier.getValue() == null || dtpDelivery.getValue() == null || 
            dtpExpiry.getValue() == null || cmbState.getValue() == null) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        try {
            int units = Integer.parseInt(txtUnits.getText().trim());
            ClsIngredientBatch updatedBatch = new ClsIngredientBatch(
                currentSelectedId,
                cmbSupplier.getValue().getID(),
                units,
                txtName.getText().trim(),
                dtpExpiry.getValue(),
                dtpDelivery.getValue(),
                com.culinarycore.model.StatusEnums.EnIngredientBatch.valueOf(cmbState.getValue().toUpperCase())
            );
            
            boolean success = batchService.updateBatch(updatedBatch);
            if (success) {
                showAlert("Ingredient batch updated successfully!", false);
                loadData();
                clearForm();
            } else {
                showAlert("Failed to update ingredient batch.", true);
            }
        } catch (NumberFormatException e) {
            showAlert("Units must be a valid integer number.", true);
        }
    }

    @FXML
    public void onDelete_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a batch to delete.", true);
            return;
        }
        
        boolean success = batchService.deleteBatch(currentSelectedId);
        
        if (success) {
            showAlert("Ingredient batch deleted successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to delete ingredient batch. It may be in use.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsIngredientBatch selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedId = selected.getID();
            txtName.setText(selected.getName());
            txtUnits.setText(String.valueOf(selected.getUnit()));
            dtpDelivery.setValue(selected.getDeliveryDate());
            dtpExpiry.setValue(selected.getExpirationDate());
            
            for (String state : cmbState.getItems()) {
                if (state.equalsIgnoreCase(selected.getState().name())) {
                    cmbState.setValue(state);
                    break;
                }
            }
            
            for (ClsSupplier s : cmbSupplier.getItems()) {
                if (s.getID() == selected.getSupplierID()) {
                    cmbSupplier.setValue(s);
                    break;
                }
            }
        }
    }
}
