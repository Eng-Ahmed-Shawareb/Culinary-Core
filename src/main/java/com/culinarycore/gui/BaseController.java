package com.culinarycore.gui;

import com.culinarycore.service.*;
import javafx.scene.control.Alert;

public abstract class BaseController {
    protected KitchenService kitchenService;
    protected ChefService chefService;
    protected ClsWorkshopService workshopService;
    protected ClsStudentService studentService;
    protected ClsRegisterService registerService;
    protected ClsSupplierService supplierService;
    protected ClsIngredientBatchService batchService;
    protected ClsConsumeService consumeService;

    public BaseController() {
        // Services injected at startup or instantiated here
        // For now, instantiate dummy instances to allow the app to run
        this.kitchenService = new KitchenService();
        this.chefService = new ChefService();
        this.workshopService = new ClsWorkshopService();
        this.studentService = new ClsStudentService();
        this.registerService = new ClsRegisterService();
        this.supplierService = new ClsSupplierService();
        this.batchService = new ClsIngredientBatchService();
        this.consumeService = new ClsConsumeService();
    }

    public abstract void initialize();
    public abstract void loadData();
    public abstract void clearForm();

    public void showAlert(String msg, boolean isError) {
        Alert alert = new Alert(isError ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(isError ? "Error" : "Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
