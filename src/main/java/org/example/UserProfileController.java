package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;

public class UserProfileController {

    @FXML
    private ImageView imgAvatar;

    @FXML
    private Label txtUsername;

    @FXML
    private Label txtEmail;

    @FXML
    private Label lblTitle;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setUserData(String username, String email) {
        if (username != null && !username.isEmpty()) {
            txtUsername.setText(username);
        }
        if (email != null && !email.isEmpty()) {
            txtEmail.setText(email);
        }
        lblTitle.setText("Profil");
    }

    public void showBooks(String username, String booksText) {
        lblTitle.setText("Kitaplarım");

        if (username != null && !username.isEmpty()) {
            txtUsername.setText(username);
        }

        if (booksText == null || booksText.isEmpty()) {
            txtEmail.setText("Ödünç alınan kitap yok");
        } else {
            txtEmail.setText(booksText);
        }
    }

    public void showBooks(String username, List<LoanRecord> loans) {
        String booksText;

        if (loans == null || loans.isEmpty()) {
            booksText = "Ödünç alınan kitap yok";
        } else {
            StringBuilder sb = new StringBuilder();
            for (LoanRecord loan : loans) {
                if (sb.length() > 0) sb.append("\n");
                sb.append("Kitap: ")
                        .append(loan.getBookTitle())
                        .append(" | Barkod: ")
                        .append(loan.getBarcode());
            }
            booksText = sb.toString();
        }
        showBooks(username, booksText);
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
        if (mainController != null) {
            mainController.showHome();
        }
    }
}