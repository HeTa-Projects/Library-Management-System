package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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
    private BorderPane rootPane;

    @FXML
    private MenuButton accountMenu;

    private String loggedInUsername;
    private String loggedInEmail;

    private BookList bookList = new BookList();

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

        String selectedFilter = cmbFilter.getValue();
        BookInfo found = null;

        if ("Kitap Adı".equals(selectedFilter)) {
            found = bookList.searchByName(query);

        } else if ("Yazar Adı".equals(selectedFilter)) {
            found = bookList.searchByAuthor(query);

        } else {
            try {
                long barcode = Long.parseLong(query.trim());
                found = bookList.searchByBarcode(barcode);
            } catch (NumberFormatException e) {
                found = null;
            }
        }

        tblResults.getItems().clear();

        if (found == null) {
            resultsBox.setVisible(false);
            resultsBox.setManaged(false);
            return;
        }

        resultsBox.setVisible(true);
        resultsBox.setManaged(true);
        tblResults.getItems().add(found);
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

    private void openProfilePage() {
        System.out.println("Kullanıcı profil / kitaplarım sayfası açılacak.");
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

    public void onAdminLoginSuccess(String username) {
        System.out.println("Admin olarak giriş yapan: " + username);

        accountMenu.setText(username);
        accountMenu.getItems().clear();

        MenuItem profileItem = new MenuItem("Profil");
        profileItem.setOnAction(e -> openAdminProfile(username));

        MenuItem actionsItem = new MenuItem("İşlemler");
        actionsItem.setOnAction(e -> openAdminInterface());

        MenuItem logoutItem = new MenuItem("Çıkış Yap");
        logoutItem.setOnAction(e -> logout());

        accountMenu.getItems().addAll(
                profileItem,
                actionsItem,
                new SeparatorMenuItem(),
                logoutItem
        );
    }

    private void openAdminProfile(String username) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminProfile.fxml"));
            Parent profileRoot = loader.load();

            AdminProfileController controller = loader.getController();
            controller.setAdminName(username);

            rootPane.setCenter(profileRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAdminInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminInterface.fxml"));
            Parent adminRoot = loader.load();

            AdminController adminController = loader.getController();

            rootPane.setCenter(adminRoot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
