module com.culinarycore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.culinarycore to javafx.fxml;
    exports com.culinarycore;
}