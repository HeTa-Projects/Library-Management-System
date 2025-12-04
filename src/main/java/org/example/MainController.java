package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private ComboBox<String> cmbFilter;

    @FXML
    private TextField txtQuery;

    @FXML
    private TableView<BookInfo> tblResults;

    @FXML
    private TableColumn<BookInfo, String> colTitle;

    @FXML
    private TableColumn<BookInfo, String> colAuthor;

    @FXML
    private TableColumn<BookInfo, String> colStatus;

    @FXML
    private TableColumn<BookInfo, String> colBarcode;

    @FXML
    private TableColumn<BookInfo, String> colYear;

    @FXML
    private TableColumn<BookInfo, String> colPages;

    @FXML
    private VBox resultsBox;

    @FXML
    private MenuButton accountMenu;
    private String loggedInUsername;
    private String loggedInEmail;

    @FXML
    private void initialize() {
        cmbFilter.getItems().addAll("Kitap Adı", "Yazar Adı", "Barkod No");
        cmbFilter.getSelectionModel().selectFirst();

        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colPages.setCellValueFactory(new PropertyValueFactory<>("pages"));

        resultsBox.setVisible(false);
        resultsBox.setManaged(false);
    }

    @FXML
    private void onSearchClick() {

        String query = txtQuery.getText();
        if (query == null || query.isBlank()) {
            return;
        }
        resultsBox.setVisible(true);
        resultsBox.setManaged(true);
        tblResults.getItems().clear();

        String line = FileOperations.readOnlySearched(query);

        if (line == null || line.isBlank()) {
            return;
        }
        line = line.trim();
        String[] p = line.split("\\|");
        if (p.length < 6) {
            return;
        }

        try {
            String title   = p[0].trim();
            String author  = p[1].trim();
            int pages      = Integer.parseInt(p[2].trim());
            String status  = p[3].trim();
            int barcode    = Integer.parseInt(p[4].trim());
            int year       = Integer.parseInt(p[5].trim());

            BookInfo book = new BookInfo(title, author, pages, status, barcode, year);
            tblResults.getItems().add(book);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
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

    public void onLoginSuccess(String username, String email) {
        this.loggedInUsername = username;
        this.loggedInEmail = email;

        accountMenu.setText(username);
        accountMenu.getItems().clear();

        MenuItem profileItem = new MenuItem("Profil");
        profileItem.setOnAction(e -> openProfilePage());

        MenuItem myBooksItem = new MenuItem("Kitaplarım");
        myBooksItem.setOnAction(e -> openProfilePage());

        MenuItem logoutItem = new MenuItem("Çıkış Yap");
        logoutItem.setOnAction(e -> logout());

        accountMenu.getItems().addAll(
                profileItem,
                myBooksItem,
                new SeparatorMenuItem(),
                logoutItem
        );
    }

    private void logout() {
        loggedInUsername = null;
        loggedInEmail = null;

        accountMenu.setText(null);
        accountMenu.getItems().clear();

        MenuItem loginItem = new MenuItem("Giriş Yap");
        loginItem.setOnAction(e -> onLoginClick());

        MenuItem registerItem = new MenuItem("Kayıt Ol");
        registerItem.setOnAction(e -> onRegisterClick());

        accountMenu.getItems().addAll(loginItem, registerItem);
    }

    private void openProfilePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserProfile.fxml"));
            Parent root = loader.load();

            UserProfileController controller = loader.getController();
            controller.setUserData(loggedInUsername, loggedInEmail);

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