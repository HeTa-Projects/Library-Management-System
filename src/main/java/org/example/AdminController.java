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
    private void onSaveBookClick() {
        lblMessage.setText("");

        // İSİMLERİ BİREBİR AYNI YAPTIK:
        String bookName        = txtTitle.getText();
        String authorName      = txtAuthor.getText();
        String numberOfPagesStr = txtPages.getText();
        String barcodeNumberStr = txtBarcode.getText();
        String bookStatus      = txtStatus.getText();
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
            int numberOfPages    = Integer.parseInt(numberOfPagesStr.trim());
            long barcodeNumber   = Long.parseLong(barcodeNumberStr.trim());
            int publicationYear  = Integer.parseInt(publicationYearStr.trim());

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
}
