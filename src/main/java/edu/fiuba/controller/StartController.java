package edu.fiuba.controller;

import edu.fiuba.App;
import edu.fiuba.configpackage.Configurator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class StartController {
    @FXML
    public TextField widthTextField;
    @FXML
    public TextField heightTextField;
    @FXML
    private Label gridSizeInvalid;

    private Configurator config;

    public void load(Configurator config) {
        this.config = config;
        this.widthTextField.setPromptText(String.valueOf(this.config.getnCol()));
        this.heightTextField.setPromptText(String.valueOf(this.config.getnRow()));
    }

    public void switchToGame() throws IOException {
        try {
            this.config.setnCol(Integer.parseInt(this.widthTextField.getText()));
            this.config.setnRow(Integer.parseInt(this.heightTextField.getText()));
        } catch (NumberFormatException ignoredException) {
        } finally {
            Exception gridSizeValidity = this.config.checkGridSizeValidity();
            if (gridSizeValidity == null) {
                App.changeSceneToGame();
            } else {
                this.gridSizeInvalid.setText(gridSizeValidity.getMessage());
                this.gridSizeInvalid.setVisible(true);
                this.gridSizeInvalid.setWrapText(true);
            }
        }
    }
}
