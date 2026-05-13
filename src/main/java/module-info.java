module com.culinarycore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.naming;

    requires javafx.base;

    opens com.culinarycore to javafx.fxml;
    exports com.culinarycore;
    
    opens com.culinarycore.gui to javafx.fxml;
    exports com.culinarycore.gui;
    
    opens com.culinarycore.model to javafx.base;
    exports com.culinarycore.model;
    exports com.culinarycore.model.StatusEnums;
    opens com.culinarycore.model.StatusEnums to javafx.base;

    opens com.culinarycore.model.dto to javafx.base;
    exports com.culinarycore.model.dto;
}