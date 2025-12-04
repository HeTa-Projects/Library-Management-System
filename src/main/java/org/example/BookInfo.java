package org.example;

public class BookInfo {

    BookInfo forward;

    String bookName;
    String authorName;
    int numberOfPages;
    String bookStatus;
    int barcodeNumber;
    int publicationYear;

    BookInfo(String bookName, String authorName, int numberOfPages,
             String bookStatus, int barcodeNumber, int publicationYear) {

        this.bookName = bookName;
        this.authorName = authorName;
        this.numberOfPages = numberOfPages;
        this.bookStatus = bookStatus;
        this.barcodeNumber = barcodeNumber;
        this.publicationYear = publicationYear;

        this.forward = null;
    }

    public String getTitle() {
        return bookName;
    }

    public String getAuthor() {
        return authorName;
    }

    public int getPages() {
        return numberOfPages;
    }

    public String getStatus() {
        return bookStatus;
    }

    public int getBarcode() {
        return barcodeNumber;
    }

    public int getYear() {
        return publicationYear;
    }
}