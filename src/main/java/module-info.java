module com.culinarycore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.culinarycore to javafx.fxml;
    exports com.culinarycore;
    
    opens com.culinarycore.gui to javafx.fxml;
    exports com.culinarycore.gui;
    
    opens com.culinarycore.model to javafx.base;
    exports com.culinarycore.model;
}