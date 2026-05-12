package com.culinarycore.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.beans.property.SimpleStringProperty;
import java.util.List;

public class DashboardController extends BaseController {

    @FXML private ComboBox<String> cmbInquiry;
    @FXML private Button btnRun;
    @FXML private TableView<ObservableList<String>> tableView;
    @FXML private Label lblTitle;
    @FXML private Label lblSubtitle;
    @FXML private StackPane chartPane;

    private BarChart<String, Number> barChart;
    private PieChart pieChart;

    @Override
    public void initialize() {
        cmbInquiry.getItems().addAll(
            "1. Top expertise by enrollments",
            "2. Kitchens unused last month",
            "3. Top supplier by qty last month",
            "4. Inactive chefs last month",
            "5. Batches per kitchen last month",
            "6. Student contact & workshop count"
        );
        
        // Setup empty charts
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setAnimated(false);
        
        pieChart = new PieChart();
        pieChart.setAnimated(false);
    }

    @Override
    public void loadData() {}

    @Override
    public void clearForm() {
        tableView.getColumns().clear();
        tableView.getItems().clear();
        chartPane.getChildren().clear();
    }

    @FXML
    public void onRun_Click() {
        String selected = cmbInquiry.getValue();
        if (selected == null) return;
        
        clearForm();
        lblTitle.setText(selected);
        
        if (selected.startsWith("1")) runInquiry1_TopExpertise();
        else if (selected.startsWith("2")) runInquiry2_UnusedKitchens();
        else if (selected.startsWith("3")) runInquiry3_TopSupplier();
        else if (selected.startsWith("4")) runInquiry4_InactiveChefs();
        else if (selected.startsWith("5")) runInquiry5_BatchesByKitchen();
        else if (selected.startsWith("6")) runInquiry6_StudentWorkshops();
    }

    public void runInquiry1_TopExpertise() {
        buildTableColumns(List.of("Expertise", "Enrollments"));
        // Stub data as service does not return real data yet
        ObservableList<String> row = FXCollections.observableArrayList("Baking", "150");
        tableView.getItems().add(row);
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Enrollments");
        series.getData().add(new XYChart.Data<>("Baking", 150));
        barChart.getData().clear();
        barChart.getData().add(series);
        showChart("BarChart");
    }

    public void runInquiry2_UnusedKitchens() {
        buildTableColumns(List.of("Kitchen ID", "Name", "Type"));
        showChart("None");
    }

    public void runInquiry3_TopSupplier() {
        buildTableColumns(List.of("Supplier ID", "Name", "Total Quantity"));
        
        pieChart.getData().clear();
        pieChart.getData().add(new PieChart.Data("Fresh Farms", 500));
        showChart("PieChart");
    }

    public void runInquiry4_InactiveChefs() {
        buildTableColumns(List.of("Chef ID", "First Name", "Last Name", "Expertise"));
        showChart("None");
    }

    public void runInquiry5_BatchesByKitchen() {
        buildTableColumns(List.of("Kitchen Name", "Batch Name", "Quantity"));
        showChart("None");
    }

    public void runInquiry6_StudentWorkshops() {
        buildTableColumns(List.of("First Name", "Last Name", "Phone", "Workshop Count"));
        showChart("None");
    }

    private void buildTableColumns(List<String> headers) {
        tableView.getColumns().clear();
        for (int i = 0; i < headers.size(); i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(headers.get(i));
            column.setCellValueFactory(param -> {
                if (param.getValue() != null && param.getValue().size() > colIndex) {
                    return new SimpleStringProperty(param.getValue().get(colIndex));
                }
                return new SimpleStringProperty("");
            });
            tableView.getColumns().add(column);
        }
    }

    private void showChart(String type) {
        chartPane.getChildren().clear();
        if ("BarChart".equals(type)) {
            chartPane.getChildren().add(barChart);
        } else if ("PieChart".equals(type)) {
            chartPane.getChildren().add(pieChart);
        }
    }
}
