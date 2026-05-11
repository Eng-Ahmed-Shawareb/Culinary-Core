module com.culinarycore {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.culinarycore to javafx.fxml;
    exports com.culinarycore;
}