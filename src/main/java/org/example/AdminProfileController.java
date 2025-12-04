package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AdminProfileController {

    @FXML
    private Label lblAdminName;

    public void setAdminName(String name) {
        lblAdminName.setText(name);
    }
}
