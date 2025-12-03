package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UserProfileController {

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label txtUsername;

    @FXML
    private Label txtEmail;

    public void setUserData(String username, String email) {
        if (username != null && !username.isEmpty()) {
            txtUsername.setText(username);
        }
        if (email != null && !email.isEmpty()) {
            txtEmail.setText(email);
        }
    }

    @FXML
    private void initialize() {
        if (txtUsername.getText() == null || txtUsername.getText().isEmpty()) {
            txtUsername.setText("Kullanıcı adı");
        }
        if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
            txtEmail.setText("email@example.com");
        }
    }

    @FXML
    private void onBackClick() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}
