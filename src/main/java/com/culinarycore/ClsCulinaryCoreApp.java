package com.culinarycore;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ClsCulinaryCoreApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Culinary School System");
        primaryStage.getIcons()
                .add(new Image(ClsCulinaryCoreApp.class.getResourceAsStream("/com/culinarycore/gui/cutlery.png")));
        com.culinarycore.gui.ClsSceneManager sceneManager = com.culinarycore.gui.ClsSceneManager.getInstance();
        sceneManager.setPrimaryStage(primaryStage);
        sceneManager.switchTo("/com/culinarycore/gui/Main.fxml");
    }
}
