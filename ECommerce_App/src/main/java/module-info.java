module com.example.ecommerce_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ecommerce_app to javafx.fxml;
    exports com.example.ecommerce_app;
}