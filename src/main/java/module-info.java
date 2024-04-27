module edu.fiuba {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens edu.fiuba to javafx.fxml;
    opens edu.fiuba.model to javafx.fxml;
    opens edu.fiuba.view to javafx.fxml;
    exports edu.fiuba;
    exports edu.fiuba.model;
    exports edu.fiuba.view;
    exports edu.fiuba.configpackage;
    exports edu.fiuba.controller;
    opens edu.fiuba.controller to javafx.fxml;
}
