package org.example;
import java.io.*;

public class FileOperations {

    private static final String FILE_NAME = "books.hot";

    public static void loadAllBooks(BookList list) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 6) continue;

                String bookName = parts[0];
                String authorName = parts[1];
                int numberOfPages = Integer.parseInt(parts[2]);
                String bookStatus = parts[3];
                int barcodeNumber = Integer.parseInt(parts[4]);
                int publicationYear = Integer.parseInt(parts[5]);

                list.addBook(bookName, authorName, numberOfPages, bookStatus, barcodeNumber, publicationYear);
            }

        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Sayıya çevrilemeyen veri hatası: " + e.getMessage());
        }
    }

    public static void saveAllBooks(BookList list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {

            BookInfo temp = list.first;
            while (temp != null) {
                String line =
                        temp.bookName + "|" +
                                temp.authorName + "|" +
                                temp.numberOfPages + "|" +
                                temp.bookStatus + "|" +
                                temp.barcodeNumber + "|" +
                                temp.publicationYear;

                bw.write(line);
                bw.newLine();
                temp = temp.forward;
            }

        } catch (IOException e) {
            System.out.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }
}