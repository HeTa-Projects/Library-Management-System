package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblError;

    @FXML
    private void onRegisterClick() {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        String email = txtEmail.getText();

        if (user.isEmpty() || pass.isEmpty() || email.isEmpty()) {
            lblError.setText("Tüm alanları doldurmalısınız.");
            return;
        }
        UserOperations ops = new UserOperations();
        ops.addUser(user, pass, email);
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}