module edu.fiuba {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.fiuba to javafx.fxml;
    exports edu.fiuba;
}
