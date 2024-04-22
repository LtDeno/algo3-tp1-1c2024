module edu.fiuba {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens edu.fiuba to javafx.fxml;
    exports edu.fiuba;
}
