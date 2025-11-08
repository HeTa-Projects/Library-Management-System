package org.example;

public class BookList {
    BookInfo first;
    BookInfo last;

    BookList() {
        first = null;
        last = null;
    }

    void addBook(BookInfo newBook) {

        if (first == null) {
            first = newBook;
            last = newBook;
            return;
        } else {
            newBook.forward = first;
            first = newBook;
        }

    }

    void printList() {
        BookInfo temp = first;

        while (temp != null) {
            System.out.println("Book Name :" + temp.bookName + "\nAuthor Name :" + temp.authorName + "\nBook Status : " + temp.bookStatus + "\nBarcode Number : " + temp.barcodeNumber + "\nNumber Of Pages : " + temp.numberOfPages + "\nPublication Year : " + temp.publicationYear);
            temp = temp.forward;
        }
    }
}
