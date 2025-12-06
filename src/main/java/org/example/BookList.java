package org.example;

import java.io.*;

public class BookList {
    BookInfo first;
    BookInfo last;

    private BookTree bookTree = new BookTree();

    BookList() {
        first = null;
        last = null;
        loadFromFile();
    }

    private void addFromFile(BookInfo newBook) {
        if (first == null) {
            first = newBook;
            last = newBook;
        } else {
            last.forward = newBook;
            last = newBook;
        }
        bookTree.insert(newBook.barcodeNumber, newBook);
    }

    void addBook(String bookName, String authorName, int numberOfPages,
                 String bookStatus, long barcodeNumber, int publicationYear) {

        BookInfo newBook = new BookInfo(bookName, authorName, numberOfPages,
                bookStatus, barcodeNumber, publicationYear);

        if (first == null) {
            first = newBook;
            last = newBook;
        } else {
            last.forward = newBook;
            last = newBook;
        }
        bookTree.insert(barcodeNumber, newBook);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.hot", true))) {

            bw.write(bookName + "|" +
                    authorName + "|" +
                    numberOfPages + "|" +
                    bookStatus + "|" +
                    barcodeNumber + "|" +
                    publicationYear);
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File f = new File("books.hot");
        if (!f.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split("\\|");
                if (p.length < 6) continue;

                String fileBookName = p[0].trim();
                String authorName = p[1].trim();
                int numberOfPages = Integer.parseInt(p[2].trim());
                String bookStatus = p[3].trim();
                long barcodeNumber = Long.parseLong(p[4].trim());
                int publicationYear = Integer.parseInt(p[5].trim());

                BookInfo book = new BookInfo(fileBookName, authorName, numberOfPages,
                        bookStatus, barcodeNumber, publicationYear);

                addFromFile(book);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    void removeBook(long barcodeNumber) {
        BookInfo temp = first;
        BookInfo before = null;

        while (temp != null) {
            if (temp.barcodeNumber == barcodeNumber) {
                if (temp == first) {
                    first = temp.forward;
                } else if (temp == last) {
                    last = before;
                    if (before != null) {
                        before.forward = null;
                    }
                } else {
                    if (before != null) {
                        before.forward = temp.forward;
                    }
                }
                System.out.println("Book Deleted: " + temp.bookName);
                saveToFile();
                return;
            }
            before = temp;
            temp = temp.forward;
        }
        System.out.println("Barcode number not found.");
    }

    class BookTreeNode {
        long barcodeNumber;
        BookInfo book;
        BookTreeNode left;
        BookTreeNode right;

        BookTreeNode(long barcodeNumber, BookInfo book) {
            this.barcodeNumber = barcodeNumber;
            this.book = book;
        }
    }

    public class BookTree {
        BookTreeNode root;

        void insert(long barcodeNumber, BookInfo book) {
            root = insertRec(root, barcodeNumber, book);
        }

        private BookTreeNode insertRec(BookTreeNode node, long key, BookInfo book) {
            if (node == null) return new BookTreeNode(key, book);

            if (key < node.barcodeNumber)
                node.left = insertRec(node.left, key, book);
            else if (key > node.barcodeNumber)
                node.right = insertRec(node.right, key, book);
            return node;
        }

        BookInfo search(long barcodeNumber) {
            BookTreeNode node = root;
            while (node != null) {
                if (barcodeNumber == node.barcodeNumber) return node.book;
                if (barcodeNumber < node.barcodeNumber) node = node.left;
                else node = node.right;
            }
            return null;
        }

        BookInfo searchByTitle(String title) {
            if (root == null) return null;
            String query = title.trim().toLowerCase();
            return searchByTitleRec(root, query, null);
        }

        private BookInfo searchByTitleRec(BookTreeNode node, String query, BookInfo head) {
            if (node == null) return head;

            if (node.book.bookName != null &&
                    node.book.bookName.toLowerCase().contains(query)) {

                BookInfo newNode = new BookInfo(
                        node.book.bookName,
                        node.book.authorName,
                        node.book.numberOfPages,
                        node.book.bookStatus,
                        node.book.barcodeNumber,
                        node.book.publicationYear);
                newNode.forward = head;
                head = newNode;
            }
            head = searchByTitleRec(node.left, query, head);
            head = searchByTitleRec(node.right, query, head);
            return head;
        }

        BookInfo searchByAuthor(String author) {
            if (root == null) return null;
            String query = author.trim().toLowerCase();
            return searchByAuthorRec(root, query, null);
        }

        private BookInfo searchByAuthorRec(BookTreeNode node, String query, BookInfo head) {
            if (node == null) return head;

            if (node.book.authorName != null &&
                    node.book.authorName.toLowerCase().contains(query)) {

                BookInfo newNode = new BookInfo(
                        node.book.bookName,
                        node.book.authorName,
                        node.book.numberOfPages,
                        node.book.bookStatus,
                        node.book.barcodeNumber,
                        node.book.publicationYear);
                newNode.forward = head;
                head = newNode;
            }
            head = searchByAuthorRec(node.left, query, head);
            head = searchByAuthorRec(node.right, query, head);
            return head;
        }
    }

    void printList() {
        BookInfo temp = first;

        while (temp != null) {
            System.out.println(
                    "Book Name :" + temp.bookName +
                            "\nAuthor Name :" + temp.authorName +
                            "\nNumber Of Pages : " + temp.numberOfPages +
                            "\nBook Status : " + temp.bookStatus +
                            "\nBarcode Number : " + temp.barcodeNumber +
                            "\nPublication Year : " + temp.publicationYear);
            temp = temp.forward;
        }
    }

    public BookInfo searchByName(String bookName) {
        if (bookTree == null) return null;
        return bookTree.searchByTitle(bookName);
    }

    public BookInfo searchByAuthor(String authorName) {
        if (bookTree == null) return null;
        return bookTree.searchByAuthor(authorName);
    }

    public BookInfo searchByBarcode(long barcodeNumberToFind) {
        if (bookTree == null) return null;
        return bookTree.search(barcodeNumberToFind);
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.hot", false))) {
            BookInfo temp = first;
            while (temp != null) {
                bw.write(temp.bookName + "|" +
                        temp.authorName + "|" +
                        temp.numberOfPages + "|" +
                        temp.bookStatus + "|" +
                        temp.barcodeNumber + "|" +
                        temp.publicationYear);
                bw.newLine();
                temp = temp.forward;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBookStatus(long barcodeNumber, String newStatus) {
        if (newStatus == null || newStatus.isBlank()) return;

        BookInfo book = searchByBarcode(barcodeNumber);
        if (book == null) {
            System.out.println("Kitap bulunamadı, durum güncellenemedi.");
            return;
        }
        book.setStatus(newStatus);
        saveToFile();
    }

    public void returnBook(long barcodeNumber) {
        updateBookStatus(barcodeNumber, "Mevcut");
    }
}