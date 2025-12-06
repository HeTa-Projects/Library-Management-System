package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdminController {

    @FXML
    private TextField txtTitle;

    @FXML
    private TextField txtAuthor;

    @FXML
    private TextField txtPages;

    @FXML
    private TextField txtBarcode;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtYear;

    @FXML
    private Label lblMessage;

    @FXML
    private TextField txtDeleteTitle;

    @FXML
    private TextField txtDeleteBarcode;

    @FXML
    private Label lblDeleteMessage;

    @FXML
    private TextField txtLoanTc;

    @FXML
    private TextField txtLoanBookTitle;

    @FXML
    private TextField txtLoanBarcode;

    @FXML
    private Label lblLoanMessage;

    @FXML
    private TextField txtReturnTc;

    @FXML
    private TextField txtReturnBarcode;

    @FXML
    private Label lblReturnMessage;

    @FXML
    private void onSaveBookClick() {
        lblMessage.setText("");

        String bookName          = txtTitle.getText();
        String authorName        = txtAuthor.getText();
        String numberOfPagesStr  = txtPages.getText();
        String barcodeNumberStr  = txtBarcode.getText();
        String bookStatus        = txtStatus.getText();
        String publicationYearStr = txtYear.getText();

        if (bookName == null || bookName.isBlank() ||
                authorName == null || authorName.isBlank() ||
                numberOfPagesStr == null || numberOfPagesStr.isBlank() ||
                barcodeNumberStr == null || barcodeNumberStr.isBlank() ||
                bookStatus == null || bookStatus.isBlank() ||
                publicationYearStr == null || publicationYearStr.isBlank()) {

            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Tüm alanları doldurmalısınız.");
            return;
        }

        try {
            int numberOfPages   = Integer.parseInt(numberOfPagesStr.trim());
            long barcodeNumber  = Long.parseLong(barcodeNumberStr.trim());
            int publicationYear = Integer.parseInt(publicationYearStr.trim());

            BookList list = new BookList();
            list.addBook(bookName.trim(),
                    authorName.trim(),
                    numberOfPages,
                    bookStatus.trim(),
                    barcodeNumber,
                    publicationYear);

            lblMessage.setStyle("-fx-text-fill: #00b894;");
            lblMessage.setText("Kitap başarıyla eklendi.");
            clearForm();

        } catch (NumberFormatException e) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Sayfa, barkod ve yıl sayısal olmalı.");
            e.printStackTrace();
        } catch (Exception e) {
            lblMessage.setStyle("-fx-text-fill: red;");
            lblMessage.setText("Kitap eklenirken hata oluştu.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onClearFormClick() {
        clearForm();
        lblMessage.setText("");
    }

    private void clearForm() {
        txtTitle.clear();
        txtAuthor.clear();
        txtPages.clear();
        txtBarcode.clear();
        txtStatus.clear();
        txtYear.clear();
    }

    @FXML
    private void onDeleteBookClick() {
        lblDeleteMessage.setText("");

        String title   = txtDeleteTitle.getText();
        String barcodeStr = txtDeleteBarcode.getText();

        if (title == null || title.isBlank() ||
                barcodeStr == null || barcodeStr.isBlank()) {

            lblDeleteMessage.setStyle("-fx-text-fill: red;");
            lblDeleteMessage.setText("Kitap adı ve barkod zorunludur.");
            return;
        }

        try {
            long barcode = Long.parseLong(barcodeStr.trim());

            BookList list = new BookList();
            BookInfo book = list.searchByBarcode(barcode);

            if (book == null) {
                lblDeleteMessage.setStyle("-fx-text-fill: red;");
                lblDeleteMessage.setText("Bu barkoda ait kitap bulunamadı.");
                return;
            }

            if (!book.getTitle().equalsIgnoreCase(title.trim())) {
                lblDeleteMessage.setStyle("-fx-text-fill: red;");
                lblDeleteMessage.setText("Kitap adı ile barkod uyuşmuyor.");
                return;
            }

            list.removeBook(barcode);

            lblDeleteMessage.setStyle("-fx-text-fill: #00b894;");
            lblDeleteMessage.setText("Kitap silindi.");

            txtDeleteTitle.clear();
            txtDeleteBarcode.clear();

        } catch (NumberFormatException e) {
            lblDeleteMessage.setStyle("-fx-text-fill: red;");
            lblDeleteMessage.setText("Barkod sayısal olmalıdır.");
        } catch (Exception e) {
            lblDeleteMessage.setStyle("-fx-text-fill: red;");
            lblDeleteMessage.setText("Kitap silinirken hata oluştu.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoanBookClick() {
        lblLoanMessage.setText("");

        String tc        = txtLoanTc.getText();
        String bookTitle = txtLoanBookTitle.getText();
        String barcodeStr = txtLoanBarcode.getText();

        if (tc == null || tc.isBlank() ||
                bookTitle == null || bookTitle.isBlank() ||
                barcodeStr == null || barcodeStr.isBlank()) {

            lblLoanMessage.setStyle("-fx-text-fill: red;");
            lblLoanMessage.setText("TC, kitap adı ve barkod zorunludur.");
            return;
        }

        try {
            long barcode = Long.parseLong(barcodeStr.trim());

            LoanOperations ops = new LoanOperations();
            ops.addLoan(tc.trim(), barcode, bookTitle.trim());

            BookList list = new BookList();
            list.updateBookStatus(barcode, "Kullanıcıda");

            lblLoanMessage.setStyle("-fx-text-fill: #00b894;");
            lblLoanMessage.setText("Ödünç verme işlemi kaydedildi.");

            txtLoanTc.clear();
            txtLoanBookTitle.clear();
            txtLoanBarcode.clear();

        } catch (NumberFormatException e) {
            lblLoanMessage.setStyle("-fx-text-fill: red;");
            lblLoanMessage.setText("Barkod sayısal olmalıdır.");
        } catch (IllegalStateException | IllegalArgumentException e) {
            lblLoanMessage.setStyle("-fx-text-fill: red;");
            lblLoanMessage.setText(e.getMessage());
        } catch (Exception e) {
            lblLoanMessage.setStyle("-fx-text-fill: red;");
            lblLoanMessage.setText("Ödünç verirken hata oluştu.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onReturnLoanClick() {
        lblReturnMessage.setText("");

        String tc        = txtReturnTc.getText();
        String barcodeStr = txtReturnBarcode.getText();

        if (barcodeStr == null || barcodeStr.isBlank()) {
            lblReturnMessage.setStyle("-fx-text-fill: red;");
            lblReturnMessage.setText("Barkod zorunludur.");
            return;
        }

        try {
            long barcode = Long.parseLong(barcodeStr.trim());

            LoanOperations ops = new LoanOperations();
            ops.returnLoan(tc != null ? tc.trim() : null, barcode);

            BookList list = new BookList();
            list.returnBook(barcode);

            lblReturnMessage.setStyle("-fx-text-fill: #00b894;");
            lblReturnMessage.setText("İade işlemi kaydedildi.");

            txtReturnTc.clear();
            txtReturnBarcode.clear();

        } catch (NumberFormatException e) {
            lblReturnMessage.setStyle("-fx-text-fill: red;");
            lblReturnMessage.setText("Barkod sayısal olmalıdır.");
        } catch (Exception e) {
            lblReturnMessage.setStyle("-fx-text-fill: red;");
            lblReturnMessage.setText("İade alınırken hata oluştu.");
            e.printStackTrace();
        }
    }
}