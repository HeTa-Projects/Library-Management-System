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
    private PasswordField txtPasswordConfirm;

    @FXML
    private Label lblError;

    @FXML
    private void onRegisterClick() {

        UserOperations newuser = new UserOperations();

        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        String confirm = txtPasswordConfirm.getText();

        if (user.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            lblError.setText("Tüm alanları doldurun.");
            return;
        }
        if (!pass.equals(confirm)) {
            lblError.setText("Şifreler eşleşmiyor!");
            return;
        }
        newuser.addUser(user,pass);

        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}
