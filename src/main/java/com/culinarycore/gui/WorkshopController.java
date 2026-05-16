package com.culinarycore.gui;

import com.culinarycore.model.ClsChef;
import com.culinarycore.model.ClsKitchen;
import com.culinarycore.model.ClsWorkshop;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

public class WorkshopController extends BaseController {

    @FXML private TableView<ClsWorkshop> tableView;

    @FXML private TextField txtTitle;
    @FXML private TextField txtPrice;
    @FXML private TextField txtTechnique;
    @FXML private ComboBox<ClsKitchen> cmbKitchen;
    @FXML private ComboBox<ClsChef> cmbChef;
    @FXML private DatePicker dtpStart;
    @FXML private DatePicker dtpEnd;
    @FXML private ComboBox<String> cmbState;
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;

    private int currentSelectedId = -1;

    @Override
    public void initialize() {
        cmbState.getItems().addAll("ACTIVE", "COMPLETED", "SCHEDULED");

        TableColumn<ClsWorkshop, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<ClsWorkshop, String> colTitle = new TableColumn<>("Title");
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<ClsWorkshop, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<ClsWorkshop, java.time.LocalDate> colStart = new TableColumn<>("Start Date");
        colStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

        TableColumn<ClsWorkshop, java.time.LocalDate> colEnd = new TableColumn<>("End Date");
        colEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        
        TableColumn<ClsWorkshop, String> colKitchen = new TableColumn<>("Kitchen");
        colKitchen.setCellValueFactory(cellData -> {
            int kId = cellData.getValue().getKitchenID();
            for (ClsKitchen k : cmbKitchen.getItems()) {
                if (k.getID() == kId) return new javafx.beans.property.SimpleStringProperty(k.getName());
            }
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(kId));
        });
        
        TableColumn<ClsWorkshop, String> colChef = new TableColumn<>("Chef");
        colChef.setCellValueFactory(cellData -> {
            int cId = cellData.getValue().getChefID();
            for (ClsChef c : cmbChef.getItems()) {
                if (c.getID() == cId) return new javafx.beans.property.SimpleStringProperty(c.getFirstName() + " " + c.getLastName());
            }
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(cId));
        });

        TableColumn<ClsWorkshop, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<ClsWorkshop, String> colTechnique = new TableColumn<>("Technique");
        colTechnique.setCellValueFactory(new PropertyValueFactory<>("technique"));

        tableView.getColumns().setAll(colID, colTitle, colKitchen, colChef, colStart, colEnd, colPrice, colStatus, colTechnique);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        
        // Bind column widths to table width
        int numCols = 9;
        colID.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colTitle.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colKitchen.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colChef.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colStart.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colEnd.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colPrice.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colStatus.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));
        colTechnique.prefWidthProperty().bind(tableView.widthProperty().divide(numCols));

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
        currentSelectedId = -1;
        txtTitle.clear();
        txtPrice.clear();
        txtTechnique.clear();
        cmbKitchen.getSelectionModel().clearSelection();
        cmbChef.getSelectionModel().clearSelection();
        dtpStart.setValue(null);
        dtpEnd.setValue(null);
        cmbState.getSelectionModel().clearSelection();
    }

    @FXML
    public void onAdd_Click() {
        if (txtTitle.getText().trim().isEmpty() || txtPrice.getText().trim().isEmpty() || 
            txtTechnique.getText().trim().isEmpty() ||
            cmbKitchen.getValue() == null || cmbChef.getValue() == null || 
            dtpStart.getValue() == null || dtpEnd.getValue() == null || 
            cmbState.getValue() == null) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            ClsWorkshop newWorkshop = new ClsWorkshop(
                -1,
                cmbKitchen.getValue().getID(),
                cmbChef.getValue().getID(),
                txtTitle.getText().trim(),
                dtpStart.getValue(),
                dtpEnd.getValue(),
                price,
                com.culinarycore.model.StatusEnums.EnWorkshopStatus.valueOf(cmbState.getValue()),
                txtTechnique.getText().trim()
            );
            
            boolean success = workshopService.addWorkshop(newWorkshop);
            if (success) {
                showAlert("Workshop added successfully!", false);
                loadData();
                clearForm();
            } else {
                showAlert("Failed to add workshop.", true);
            }
        } catch (NumberFormatException e) {
            showAlert("Price must be a valid number.", true);
        }
    }

    @FXML
    public void onUpdate_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a workshop to update.", true);
            return;
        }
        if (txtTitle.getText().trim().isEmpty() || txtPrice.getText().trim().isEmpty() || 
            txtTechnique.getText().trim().isEmpty() ||
            cmbKitchen.getValue() == null || cmbChef.getValue() == null || 
            dtpStart.getValue() == null || dtpEnd.getValue() == null || 
            cmbState.getValue() == null) {
            showAlert("Please fill in all fields.", true);
            return;
        }

        try {
            double price = Double.parseDouble(txtPrice.getText().trim());
            ClsWorkshop updatedWorkshop = new ClsWorkshop(
                currentSelectedId,
                cmbKitchen.getValue().getID(),
                cmbChef.getValue().getID(),
                txtTitle.getText().trim(),
                dtpStart.getValue(),
                dtpEnd.getValue(),
                price,
                com.culinarycore.model.StatusEnums.EnWorkshopStatus.valueOf(cmbState.getValue()),
                txtTechnique.getText().trim()
            );
            
            boolean success = workshopService.updateWorkshop(updatedWorkshop);
            if (success) {
                showAlert("Workshop updated successfully!", false);
                loadData();
                clearForm();
            } else {
                showAlert("Failed to update workshop.", true);
            }
        } catch (NumberFormatException e) {
            showAlert("Price must be a valid number.", true);
        }
    }

    @FXML
    public void onDelete_Click() {
        if (currentSelectedId == -1) {
            showAlert("Please select a workshop to delete.", true);
            return;
        }
        
        boolean success = workshopService.deleteWorkshop(currentSelectedId);
        
        if (success) {
            showAlert("Workshop deleted successfully!", false);
            loadData();
            clearForm();
        } else {
            showAlert("Failed to delete workshop.", true);
        }
    }

    @FXML
    public void onRowSelect() {
        ClsWorkshop selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            currentSelectedId = selected.getID();
            txtTitle.setText(selected.getTitle());
            txtPrice.setText(String.valueOf(selected.getPrice()));
            txtTechnique.setText(selected.getTechnique());
            dtpStart.setValue(selected.getStartDate());
            dtpEnd.setValue(selected.getEndDate());
            cmbState.setValue(selected.getStatus().name());
            
            for (ClsKitchen k : cmbKitchen.getItems()) {
                if (k.getID() == selected.getKitchenID()) {
                    cmbKitchen.setValue(k);
                    break;
                }
            }
            for (ClsChef c : cmbChef.getItems()) {
                if (c.getID() == selected.getChefID()) {
                    cmbChef.setValue(c);
                    break;
                }
            }
        }
    }
}
