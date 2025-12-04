package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblError;

    @FXML
    private ToggleButton btnUser;

    @FXML
    private ToggleButton btnAdmin;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnCancel;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        selectUserRole();
    }

    @FXML
    private void onUserRoleClick() {
        selectUserRole();
    }

    @FXML
    private void onAdminRoleClick() {
        selectAdminRole();
    }

    private void selectUserRole() {
        btnUser.setSelected(true);
        btnAdmin.setSelected(false);
        updateRoleStyles();
    }

    private void selectAdminRole() {
        btnUser.setSelected(false);
        btnAdmin.setSelected(true);
        updateRoleStyles();
    }

    private void updateRoleStyles() {
        if (btnAdmin.isSelected()) {
            btnAdmin.setStyle(
                    "-fx-background-radius: 20;" +
                            "-fx-padding: 6 20;" +
                            "-fx-background-color: linear-gradient(to right, #7f5af0, #9d4edd);" +
                            "-fx-text-fill: white;"
            );
            btnUser.setStyle(
                    "-fx-background-radius: 20;" +
                            "-fx-padding: 6 20;" +
                            "-fx-background-color: white;" +
                            "-fx-border-color: #cccccc;" +
                            "-fx-border-radius: 20;" +
                            "-fx-text-fill: #444;"
            );
        } else {
            btnUser.setStyle(
                    "-fx-background-radius: 20;" +
                            "-fx-padding: 6 20;" +
                            "-fx-background-color: linear-gradient(to right, #7f5af0, #9d4edd);" +
                            "-fx-text-fill: white;"
            );
            btnAdmin.setStyle(
                    "-fx-background-radius: 20;" +
                            "-fx-padding: 6 20;" +
                            "-fx-background-color: white;" +
                            "-fx-border-color: #cccccc;" +
                            "-fx-border-radius: 20;" +
                            "-fx-text-fill: #444;"
            );
        }
    }

    @FXML
    private void onLoginClick() {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        lblError.setText("");

        if (user == null || user.isEmpty() || pass == null || pass.isEmpty()) {
            lblError.setText("Kullanıcı adı ve şifre boş olamaz.");
            return;
        }

        boolean isAdmin = btnAdmin.isSelected();

        UserOperations ops = new UserOperations();

        if (isAdmin) {
            if (ops.readAdmin(user, pass)) {
                System.out.println("Admin girişi başarılı!");

                if (mainController != null) {
                    mainController.onAdminLoginSuccess(user);
                }

                closeWindow();
            } else {
                lblError.setText("Admin bulunamadı veya şifre yanlış.");
            }
            return;
        }

        if (ops.readUser(user, pass)) {
            System.out.println("Kullanıcı girişi başarılı!");
            String email = ops.getUserEmail(user);

            if (mainController != null) {
                mainController.onLoginSuccess(user, email);
            }

            closeWindow();
        } else {
            lblError.setText("Kullanıcı bulunamadı. Kayıt olunuz.");
        }
    }


    @FXML
    private void onCancelClick() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) txtUsername.getScene().getWindow();
        stage.close();
    }
}
