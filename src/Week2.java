
/** Holds all records and does the calculations and file handling. */
class EnergyMonitor {
    private DailyRecord[] records = new DailyRecord[10];
    private int count = 0;
    private double tariff = 7.0;       // Rs saved per kWh of solar used at home
    private double exportRate = 3.0;   // Rs earned per kWh of surplus sent to grid

    int getCount()             { return count; }
    double getTariff()         { return tariff; }
    double getExportRate()     { return exportRate; }
    void setTariff(double t)   { tariff = t; }
    void setExportRate(double r) { exportRate = r; }

    /** Adds a record; grows the array when it is full. Returns false if the date already exists. */
    boolean addRecord(DailyRecord r) {
        for (int i = 0; i < count; i++) {
            if (records[i].getDate().equals(r.getDate())) {
                return false;
            }
        }
        if (count == records.length) {
            DailyRecord[] bigger = new DailyRecord[records.length * 2];
            for (int i = 0; i < count; i++) {
                bigger[i] = records[i];
            }
            records = bigger;
        }
        records[count++] = r;
        return true;
    }

    void printRecords() {
        if (count == 0) {
            System.out.println("No records yet. Choose option 1 to add one.");
            return;
        }
        System.out.println();
        System.out.printf("%-12s %12s %12s %12s %12s%n",
                "Date", "Generated", "Consumed", "Surplus", "Grid import");
        System.out.println("-------------------------------------------------------------------");
        for (int i = 0; i < count; i++) {
            DailyRecord r = records[i];
            System.out.printf("%-12s %9.2f kWh %9.2f kWh %9.2f kWh %9.2f kWh%n",
                    r.getDate(), r.getGenerated(), r.getConsumed(),
                    r.getSurplus(), r.getGridImport());
        }
    }
