package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class LoanOperations {

    private static final String LOANS_FILE = "loans.hot";

    public void addLoan(String tc, long barcode, String bookTitle) throws IOException {
        if (tc == null || tc.isBlank()) {
            throw new IllegalArgumentException("TC boş olamaz.");
        }
        if (bookTitle == null || bookTitle.isBlank()) {
            throw new IllegalArgumentException("Kitap adı boş olamaz.");
        }
        tc = tc.trim();
        bookTitle = bookTitle.trim().replace("|", "/");

        List<LoanRecord> existing = getLoansByTc(tc);
        for (LoanRecord lr : existing) {
            if (lr.getBarcode() == barcode) {
                throw new IllegalStateException("Bu kullanıcı bu kitabı zaten ödünç almış.");
            }
        }

        Path path = Paths.get(LOANS_FILE);
        String line = tc + "|" + barcode + "|" + bookTitle + System.lineSeparator();

        Files.write(path,
                line.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public List<LoanRecord> getLoansByTc(String tc) throws IOException {
        List<LoanRecord> result = new ArrayList<>();
        if (tc == null || tc.isBlank()) {
            return result;
        }
        tc = tc.trim();

        Path path = Paths.get(LOANS_FILE);
        if (!Files.exists(path)) {
            return result;
        }

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split("\\|", 3);
                if (parts.length < 3) continue;

                String lineTc = parts[0].trim();
                if (!lineTc.equals(tc)) continue;

                long barcode;
                try {
                    barcode = Long.parseLong(parts[1].trim());
                } catch (NumberFormatException e) {
                    continue;
                }
                String title = parts[2].trim();
                result.add(new LoanRecord(lineTc, barcode, title));
            }
        }

        System.out.println("getLoansByTc(" + tc + ") -> " + result.size() + " kayıt");
        for (LoanRecord lr : result) {
            System.out.println("  " + lr.getTc() + " | " + lr.getBarcode() + " | " + lr.getBookTitle());
        }
        return result;
    }

    public void returnLoan(String tc, long barcode) throws IOException {
        Path path = Paths.get(LOANS_FILE);
        if (!Files.exists(path)) {
            return;
        }

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        List<String> newLines = new ArrayList<>();

        boolean removed = false;

        for (String line : lines) {
            if (line == null || line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\|", 3);
            if (parts.length < 3) {
                newLines.add(line);
                continue;
            }
            long lineBarcode;
            try {
                lineBarcode = Long.parseLong(parts[1].trim());
            } catch (NumberFormatException e) {
                newLines.add(line);
                continue;
            }

            if (!removed && lineBarcode == barcode) {
                removed = true;
                System.out.println("returnLoan: kayıt silindi -> " + line);
                continue;
            }
            newLines.add(line);
        }

        Files.write(path,
                newLines,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);

        System.out.println("returnLoan: barkod=" + barcode +
                " -> silinen=" + removed);
    }
}