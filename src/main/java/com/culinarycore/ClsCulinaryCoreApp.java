package com.culinarycore;

import javafx.application.Application;
import javafx.stage.Stage;

public class ClsCulinaryCoreApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Culinary Studio & Gourmet Ingredient Network");
        com.culinarycore.gui.ClsSceneManager sceneManager = com.culinarycore.gui.ClsSceneManager.getInstance();
        sceneManager.setPrimaryStage(primaryStage);
        sceneManager.switchTo("/com/culinarycore/gui/Main.fxml");
    }
}
