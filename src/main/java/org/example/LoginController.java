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

    @FXML
    private void onLoginClick() {
        UserOperations olduser = new UserOperations();

        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            lblError.setText("Kullanıcı adı ve şifre boş olamaz.");
            return;
        }
        if(olduser.readUser(user,pass)==true){
            System.out.printf("Giriş başarılı");
        }else{
            System.out.println("Kullanıcı bulunamadı. Kayıt olunuz");
        }

        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancelClick() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}
