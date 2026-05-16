package com.culinarycore.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class DashboardController extends BaseController {

    @FXML
    private ComboBox<String> cmbInquiry;
    @FXML
    private Button btnRun;
    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblSubtitle;

    @Override
    public void initialize() {
        cmbInquiry.getItems().addAll(
                "1. Top expertise by enrollments",
                "2. Kitchens unused last month",
                "3. Top supplier by qty last month",
                "4. Inactive chefs last month",
                "5. Batches per kitchen last month",
                "6. Student contact & workshop count");

    }

    @Override
    public void loadData() {
    }

    @Override
    public void clearForm() {
        tableView.getColumns().clear();
        tableView.getItems().clear();
    }

    @FXML
    public void onRun_Click() {
        String selected = cmbInquiry.getValue();
        if (selected == null)
            return;

        clearForm();
        lblTitle.setText(selected);

        if (selected.startsWith("1"))
            runInquiry1_TopExpertise();
        else if (selected.startsWith("2"))
            runInquiry2_UnusedKitchens();
        else if (selected.startsWith("3"))
            runInquiry3_TopSupplier();
        else if (selected.startsWith("4"))
            runInquiry4_InactiveChefs();
        else if (selected.startsWith("5"))
            runInquiry5_BatchesByKitchen();
        else if (selected.startsWith("6"))
            runInquiry6_StudentWorkshops();
    }

    public void runInquiry1_TopExpertise() {
        buildTableColumns(List.of("Expertise", "Enrollments"));
        
        java.util.Optional<com.culinarycore.model.dto.ClsExpertiseEnrollmentDTO> result = registerService.getTopExpertiseByEnrollments();
        tableView.getItems().clear();
        
        if (result.isPresent()) {
            com.culinarycore.model.dto.ClsExpertiseEnrollmentDTO dto = result.get();
            ObservableList<String> row = FXCollections.observableArrayList(
                dto.getExpertise(),
                String.valueOf(dto.getEnrollmentCount())
            );
            tableView.getItems().add(row);
        }
    }

    public void runInquiry2_UnusedKitchens() {
        buildTableColumns(List.of("Kitchen ID", "Name", "Type"));
        tableView.getItems().clear();
        
        java.util.List<com.culinarycore.model.ClsKitchen> unused = kitchenService.getUnusedLastMonth();
        for (com.culinarycore.model.ClsKitchen k : unused) {
            ObservableList<String> row = FXCollections.observableArrayList(
                String.valueOf(k.getID()),
                k.getName(),
                k.getType()
            );
            tableView.getItems().add(row);
        }
    }

    public void runInquiry3_TopSupplier() {
        buildTableColumns(List.of("Supplier ID", "Name", "Total Quantity"));
        tableView.getItems().clear();
        
        java.util.Optional<com.culinarycore.model.dto.ClsSupplierTopDTO> opt = supplierService.getTopSupplierLastMonth();
        if (opt.isPresent()) {
            com.culinarycore.model.dto.ClsSupplierTopDTO dto = opt.get();
            ObservableList<String> row = FXCollections.observableArrayList(
                String.valueOf(dto.getId()),
                dto.getName(),
                String.valueOf(dto.getTotalQuantitySupplied())
            );
            tableView.getItems().add(row);
        }
    }

    public void runInquiry4_InactiveChefs() {
        buildTableColumns(List.of("Chef ID", "First Name", "Last Name", "Expertise"));
        tableView.getItems().clear();
        
        java.util.List<com.culinarycore.model.ClsChef> inactive = chefService.getInactiveLastMonth();
        for (com.culinarycore.model.ClsChef c : inactive) {
            ObservableList<String> row = FXCollections.observableArrayList(
                String.valueOf(c.getID()),
                c.getFirstName(),
                c.getLastName(),
                c.getExpertise()
            );
            tableView.getItems().add(row);
        }
    }

    public void runInquiry5_BatchesByKitchen() {
        buildTableColumns(List.of("Kitchen Name", "Batch Name", "Quantity"));
        tableView.getItems().clear();
        
        java.util.List<com.culinarycore.model.dto.ClsKitchenBatchReportDTO> report = batchService.getDashboardReport();
        for (com.culinarycore.model.dto.ClsKitchenBatchReportDTO dto : report) {
            ObservableList<String> row = FXCollections.observableArrayList(
                dto.getKitchenName(),
                dto.getBatchName(),
                String.valueOf(dto.getConsumedQuantity())
            );
            tableView.getItems().add(row);
        }
    }

    public void runInquiry6_StudentWorkshops() {
        buildTableColumns(List.of("First Name", "Last Name", "Phone", "Workshop Count"));
        tableView.getItems().clear();
        
        java.util.List<com.culinarycore.model.dto.ClsStudentWorkshopCountDTO> students = studentService.getStudentsWithWorkShopCount();
        for (com.culinarycore.model.dto.ClsStudentWorkshopCountDTO dto : students) {
            ObservableList<String> row = FXCollections.observableArrayList(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getPhone(),
                String.valueOf(dto.getWorkshopCount())
            );
            tableView.getItems().add(row);
        }
    }

    private void buildTableColumns(List<String> headers) {
        tableView.getColumns().clear();
        int numColumns = headers.size();
        for (int i = 0; i < numColumns; i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(headers.get(i));
            column.setCellValueFactory(param -> {
                if (param.getValue() != null && param.getValue().size() > colIndex) {
                    return new SimpleStringProperty(param.getValue().get(colIndex));
                }
                return new SimpleStringProperty("");
            });
            column.setPrefWidth(100);
            column.prefWidthProperty().bind(tableView.widthProperty().divide(numColumns));
            tableView.getColumns().add(column);
        }
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    }
}
