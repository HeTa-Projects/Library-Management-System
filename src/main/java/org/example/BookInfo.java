package org.example;

import java.awt.print.Book;

public class BookInfo {

    BookInfo forward;

    String bookName;
    String authorName;
    String bookStatus;
    int barcodeNumber;
    int publicationYear;
    int numberOfPages;

    BookInfo(String bookName, String authorName, String bookStatus, int barcodeNumber, int publicationYear,int numberOfPages) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookStatus = bookStatus;
        this.barcodeNumber = barcodeNumber;
        this.numberOfPages = numberOfPages;
        this.publicationYear = publicationYear;
        forward = null;
    }

}
