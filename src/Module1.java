import java.io.*;
import java.util.Scanner;


class DailyRecord {
    private String date;        // e.g. 2026-09-28
    private double generated;   // kWh produced by the panels
    private double consumed;    // kWh used by the home

    DailyRecord(String date, double generated, double consumed) {
        this.date = date;
        this.generated = generated;
        this.consumed = consumed;
    }

    String getDate()       { return date; }
    double getGenerated()  { return generated; }
    double getConsumed()   { return consumed; }

    /** Solar energy left over after the home's needs (0 if none). */
    double getSurplus() {
        return generated > consumed ? generated - consumed : 0 ;
    }

    /** Energy the home had to draw from the grid (0 if none). */
    double getGridImport() {
        return consumed > generated ? consumed - generated : 0 ;
    }

    /** Portion of solar energy used directly by the home. */
    double getSelfUsed() {
        return Math.min(generated, consumed);
    }

    String toCsv() {
        return date + "," + generated + "," + consumed;
    }
}
