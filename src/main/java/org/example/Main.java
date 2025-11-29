package org.example;

public class Main {
    public static void main(String[] args) {
        BookList bl = new BookList();

        bl.addBook("Tutunamayanlar", "Oğuz Atay", 724, "In Library", 123456789, 1972);
        bl.printList();
        System.out.println("--------------------------------");
        bl.addBook("Hayvan Çiftliği", "George Orwell", 386, "In Library", 13579, 1980);
        bl.printList();
        System.out.println("--------------------------------");
        bl.addBook("Suç Ve Ceza", "Fyodor Mihailoviç Dostoyevski", 688, "Not Available", 233444, 1866 );
        bl.printList();


        System.out.println("--------------------------------");
        //bl.removeBook(123456789);
        //bl.printList();
        bl.searchBook();


        FileOperations.saveAllBooks(bl);
        //bl.printList();
    }
}