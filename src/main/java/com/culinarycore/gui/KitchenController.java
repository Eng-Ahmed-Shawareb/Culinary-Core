package com.culinarycore.gui;

import com.culinarycore.model.ClsKitchen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

public class KitchenController extends BaseController {

    @FXML
    private TableView<ClsKitchen> tableView;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtType;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnClear;

    private int currentSelectedId = -1;

    @Override
    public void initialize() {
        TableColumn<ClsKitchen, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<ClsKitchen, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ClsKitchen, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));

        tableView.getColumns().setAll(colID, colName, colType);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        // Bind column widths to table width
        int numCols = 3;
        colID.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colName.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colType.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));

        loadData();
    }

    @Override
    public void loadData() {
        tableView.getItems().clear();
        tableView.getItems().addAll(kitchenService.getAll());
    }

    @Override
    public void clearForm() {
        currentSelectedId = -1;
        txtName.clear();
        txtType.clear();
    }

    @FXML
    public void onAdd_Click() {
        if (txtName.getText().trim().isEmpty() || txtType.getText().trim().isEmpty()) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        ClsKitchen newKitchen = new ClsKitchen(txtName.getText().trim(), txtType.getText().trim());
        boolean success = kitchenService.addKitchen(newKitchen);

        if (success) {
            showAlert("Kitchen added successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to add kitchen.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a kitchen to update.", true);
            return;
        }
        if (txtName.getText().trim().isEmpty() || txtType.getText().trim().isEmpty()) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        ClsKitchen updatedKitchen = new ClsKitchen(txtName.getText().trim(), txtType.getText().trim());
        updatedKitchen.setID(currentSelectedId);
        boolean success = kitchenService.updateKitchen(updatedKitchen);

        if (success) {
            showAlert("Kitchen updated successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to update kitchen.", true);
        }
    }

    @FXML
    public void onDelete_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a kitchen to delete.", true);
            return;
        }

        boolean success = kitchenService.deleteKitchen(currentSelectedId);

        if (success) {
            showAlert("Kitchen deleted successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to delete kitchen. It may be in use.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsKitchen selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedId = selected.getID();
            txtName.setText(selected.getName());
            txtType.setText(selected.getType());
        }
    }
}
