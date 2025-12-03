package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void onLoginClick() {
        UserOperations olduser = new UserOperations();

        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Kullanıcı adı ve şifre boş olamaz.");
            return;
        }

        if (olduser.readUser(user, pass)) {
            System.out.println("Giriş başarılı");

            if (mainController != null) {
                mainController.onLoginSuccess(user);
            }

            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.close();
        } else {
            lblError.setText("Kullanıcı bulunamadı. Kayıt olunuz.");
        }
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}
