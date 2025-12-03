package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ComboBox<String> cmbFilter;

    @FXML
    private TextField txtQuery;

    @FXML
    private MenuButton accountMenu;

    private String loggedInUsername;

    @FXML
    private void initialize() {
        cmbFilter.getItems().addAll(
                "Kitap Adı",
                "Yazar Adı",
                "Barkod No"
        );
        cmbFilter.getSelectionModel().selectFirst();
    }

    @FXML
    private void onSearchClick() {
        FileOperations newsearch = new FileOperations();

        String selectedFilter = cmbFilter.getValue();
        String query = txtQuery.getText();

        newsearch.readOnlySearched(query);
    }

    @FXML
    private void onRegisterClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Register.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Kayıt Ol");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoginClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setMainController(this);

            Stage stage = new Stage();
            stage.setTitle("Giriş Yap");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onLoginSuccess(String username) {
        this.loggedInUsername = username;

        accountMenu.getItems().clear();
        accountMenu.setText(username);

        MenuItem profileItem = new MenuItem("Profil");
        profileItem.setOnAction(e -> openProfilePage());

        MenuItem myBooksItem = new MenuItem("Kitaplarım");
        myBooksItem.setOnAction(e -> openProfilePage());

        accountMenu.getItems().addAll(profileItem, myBooksItem);
    }

    private void openProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
            Parent root = loader.load();

            UserProfileController controller = loader.getController();
            controller.setUserData(loggedInUsername, "");

            Stage stage = new Stage();
            stage.setTitle("Profilim");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }

