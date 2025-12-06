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
    private TextField txtEmail;

    @FXML
    private TextField txtTc;

    @FXML
    private Label lblError;

    @FXML
    private void onRegisterClick() {

        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        String passConfirm = txtPasswordConfirm.getText();
        String email = txtEmail.getText();
        String tc = txtTc.getText();

        if (user.isBlank() || pass.isBlank() || passConfirm.isBlank()
                || email.isBlank() || tc.isBlank()) {
            lblError.setText("Tüm alanları doldurmalısınız.");
            return;
        }

        if (!pass.equals(passConfirm)) {
            lblError.setText("Şifreler uyuşmuyor.");
            return;
        }

        if (!tc.matches("\\d{11}")) {
            lblError.setText("TC 11 haneli ve sadece rakam olmalıdır.");
            return;
        }

        UserOperations ops = new UserOperations();

        if (ops.isTcUsed(tc)) {
            lblError.setText("Bu TC ile kayıtlı kullanıcı zaten var.");
            return;
        }

        ops.addUser(user, pass, email, tc);
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}