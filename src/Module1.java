import java.io.*;
import java.util.Scanner;


class DailyRecord {
    private String date;        
    private double generated;   
    private double consumed;    

    DailyRecord(String date, double generated, double consumed) {
        this.date = date;
        this.generated = generated;
        this.consumed = consumed;
    }

    String getDate()       { return date; }
    double getGenerated()  { return generated; }
    double getConsumed()   { return consumed; }

    double getSurplus() {
        return generated > consumed ? generated - consumed : 0 ;
    }

  
    double getGridImport() {
        return consumed > generated ? consumed - generated : 0 ;
    }

    double getSelfUsed() {
        return Math.min(generated, consumed);
    }

    String toCsv() {
        return date + "," + generated + "," + consumed;
    }
}
