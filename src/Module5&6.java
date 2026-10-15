public static void main(String[] args) {
        EnergyMonitor monitor = new EnergyMonitor();

        try {
            int n = monitor.load(DATA_FILE);
            if (n > 0) System.out.println("Loaded " + n + " saved record(s).");
        } catch (IOException e) {
            System.out.println("Could not read saved data: " + e.getMessage());
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    String date = readDate();
                    double gen = readNumber("Energy generated (kWh): ");
                    double cons = readNumber("Energy consumed (kWh): ");
                    if (monitor.addRecord(new DailyRecord(date, gen, cons))) {
                        System.out.println("Record added.");
                    } else {
                        System.out.println("A record for " + date + " already exists.");
                    }
                    break;
                case "2":
                    monitor.printRecords();
                    break;
                case "3":
                    monitor.printSummary();
                    break;
                case "4":
                    monitor.setTariff(readNumber("Rs saved per kWh of solar used at home (now "
                            + monitor.getTariff() + "): "));
                    monitor.setExportRate(readNumber("Rs earned per kWh sent to grid (now "
                            + monitor.getExportRate() + "): "));
                    System.out.println("Rates updated.");
                    break;
                case "5":
                    try {
                        monitor.save(DATA_FILE);
                        System.out.println("Saved " + monitor.getCount() + " record(s) to " + DATA_FILE + ". Goodbye!");
                    } catch (IOException e) {
                        System.out.println("Could not save data: " + e.getMessage());
                    }
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a number from 1 to 5.");
            }
        }
    }
}
