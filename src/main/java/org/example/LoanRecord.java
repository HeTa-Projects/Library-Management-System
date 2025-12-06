package org.example;

public class LoanRecord {

    private final String tc;
    private final long barcode;
    private final String bookTitle;

    public LoanRecord(String tc, long barcode, String bookTitle) {
        this.tc = tc;
        this.barcode = barcode;
        this.bookTitle = bookTitle;
    }

    public String getTc() {
        return tc;
    }

    public long getBarcode() {
        return barcode;
    }

    public String getBookTitle() {
        return bookTitle;
    }
}