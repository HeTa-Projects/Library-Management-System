package org.example;

import java.io.*;

public class BookList {
    BookInfo first;
    BookInfo last;

    BookList() {
        first = null;
        last = null;
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
                            "\nPublication Year : " + temp.publicationYear
            );
            temp = temp.forward;
        }
    }

    public BookInfo searchByName(String bookName) {
        String query = bookName.trim();

        try (BufferedReader br = new BufferedReader(new FileReader("books.hot"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split("\\|");
                if (p.length < 6) continue;

                String fileBookName   = p[0].trim();
                String authorName     = p[1].trim();
                int numberOfPages     = Integer.parseInt(p[2].trim());
                String bookStatus     = p[3].trim();
                long barcodeNumber    = Long.parseLong(p[4].trim());
                int publicationYear   = Integer.parseInt(p[5].trim());

                if (fileBookName.equalsIgnoreCase(query)) {
                    return new BookInfo(fileBookName, authorName, numberOfPages,
                            bookStatus, barcodeNumber, publicationYear);
                }
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BookInfo searchByAuthor(String authorName) {
        String query = authorName.trim();

        try (BufferedReader br = new BufferedReader(new FileReader("books.hot"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split("\\|");
                if (p.length < 6) continue;

                String fileBookName   = p[0].trim();
                String fileAuthorName = p[1].trim();
                int numberOfPages     = Integer.parseInt(p[2].trim());
                String bookStatus     = p[3].trim();
                long barcodeNumber    = Long.parseLong(p[4].trim());
                int publicationYear   = Integer.parseInt(p[5].trim());

                if (fileAuthorName.equalsIgnoreCase(query)) {
                    return new BookInfo(fileBookName, fileAuthorName, numberOfPages,
                            bookStatus, barcodeNumber, publicationYear);
                }
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }

    public BookInfo searchByBarcode(long barcodeNumberToFind) {
        try (BufferedReader br = new BufferedReader(new FileReader("books.hot"))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] p = line.split("\\|");
                if (p.length < 6) continue;

                String fileBookName   = p[0].trim();
                String authorName     = p[1].trim();
                int numberOfPages     = Integer.parseInt(p[2].trim());
                String bookStatus     = p[3].trim();
                long barcodeNumber    = Long.parseLong(p[4].trim());
                int publicationYear   = Integer.parseInt(p[5].trim());

                if (barcodeNumber == barcodeNumberToFind) {
                    return new BookInfo(fileBookName, authorName, numberOfPages,
                            bookStatus, barcodeNumber, publicationYear);
                }
            }

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }
}
