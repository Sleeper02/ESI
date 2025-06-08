module com.mycompany.facilita_uni {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.facilita_uni to javafx.fxml;
    exports com.mycompany.facilita_uni;
}
