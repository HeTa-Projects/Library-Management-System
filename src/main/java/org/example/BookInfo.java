package org.example;
import java.awt.print.Book;

public class BookInfo {

    BookInfo forward;

    String bookName;
    String authorName;
    int numberOfPages;
    String bookStatus;
    int barcodeNumber;
    int publicationYear;


    BookInfo(String bookName, String authorName, int numberOfPages, String bookStatus, int barcodeNumber, int publicationYear) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.numberOfPages = numberOfPages;
        this.bookStatus = bookStatus;
        this.barcodeNumber = barcodeNumber;
        this.publicationYear = publicationYear;
        forward = null;
    }

}
