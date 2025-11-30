package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;


public class MainController {

    @FXML
    private ComboBox<String> cmbFilter;

    @FXML
    private TextField txtQuery;

    @FXML
    private MenuButton accountMenu;

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
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
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

            Stage stage = new Stage();
            stage.setTitle("Giriş Yap");
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
