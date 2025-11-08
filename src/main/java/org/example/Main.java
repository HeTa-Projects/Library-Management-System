package org.example;

public class Main {
    public static void main(String[] args) {
        BookInfo book = new BookInfo("Tutunamayanlar", "Oğuz Atay", "In Library", 123456789, 1972, 724);
        BookList bl = new BookList();

        bl.addBook(book);
        bl.printList();
    }
}