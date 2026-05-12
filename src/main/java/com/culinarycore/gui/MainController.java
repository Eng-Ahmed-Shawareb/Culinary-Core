package com.culinarycore.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import java.io.IOException;

public class MainController extends BaseController {

    @FXML private BorderPane borderPane;
    @FXML private Button btnDashboard;
    @FXML private Button btnKitchen;
    @FXML private Button btnChef;
    @FXML private Button btnWorkshop;
    @FXML private Button btnStudent;
    @FXML private Button btnRegister;
    @FXML private Button btnSupplier;
    @FXML private Button btnBatch;
    @FXML private Button btnConsume;
    @FXML private Button btnTheme;
    private boolean isDarkMode = false;

    @Override
    public void initialize() {
        // Load the dashboard by default on startup
        onDashboard_Click();
    }

    @Override
    public void loadData() {}

    @Override
    public void clearForm() {}

    @FXML
    public void onDashboard_Click() { loadCenter("/com/culinarycore/gui/Dashboard.fxml"); }

    @FXML
    public void onKitchen_Click() { loadCenter("/com/culinarycore/gui/Kitchen.fxml"); }

    @FXML
    public void onChef_Click() { loadCenter("/com/culinarycore/gui/Chef.fxml"); }

    @FXML
    public void onWorkshop_Click() { loadCenter("/com/culinarycore/gui/Workshop.fxml"); }

    @FXML
    public void onStudent_Click() { loadCenter("/com/culinarycore/gui/Student.fxml"); }

    @FXML
    public void onRegister_Click() { loadCenter("/com/culinarycore/gui/Register.fxml"); }

    @FXML
    public void onSupplier_Click() { loadCenter("/com/culinarycore/gui/Supplier.fxml"); }

    @FXML
    public void onBatch_Click() { loadCenter("/com/culinarycore/gui/IngredientBatch.fxml"); }

    @FXML
    public void onConsume_Click() { loadCenter("/com/culinarycore/gui/Consume.fxml"); }

    @FXML
    public void onThemeToggle_Click() {
        isDarkMode = !isDarkMode;
        btnTheme.setText(isDarkMode ? "Toggle Light Mode" : "Toggle Dark Mode");
        String darkCss = getClass().getResource("/com/culinarycore/gui/dark-style.css").toExternalForm();
        if (isDarkMode) {
            borderPane.getScene().getStylesheets().add(darkCss);
        } else {
            borderPane.getScene().getStylesheets().remove(darkCss);
        }
    }

    public void loadCenter(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            borderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Could not load view: " + fxml);
        }
    }
}
