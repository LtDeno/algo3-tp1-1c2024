module edu.fiuba {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jsobject;
    requires com.google.gson;

    opens edu.fiuba to javafx.fxml;
    exports edu.fiuba;
}
