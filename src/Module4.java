public class SolarMonitor {
    private static final String DATA_FILE = "solar_records.csv";
    private static final Scanner sc = new Scanner(System.in);

    static double readNumber(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                double v = Double.parseDouble(s);
                if (v < 0) {
                    System.out.println("Please enter a value of 0 or more.");
                } else {
                    return v;
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a number. Try again.");
            }
        }
    }

    static String readDate() {
        while (true) {
            System.out.print("Date (YYYY-MM-DD): ");
            String s = sc.nextLine().trim();
            if (s.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return s;
            }
            System.out.println("Use the format YYYY-MM-DD, for example 2026-09-28.");
        }
    }

    static void printMenu() {
        System.out.println();
        System.out.println("---- Rooftop Solar Energy Monitor ----");
        System.out.println("1. Add daily record");
        System.out.println("2. View all records");
        System.out.println("3. Show summary report");
        System.out.println("4. Change tariff / export rate");
        System.out.println("5. Save and exit");
        System.out.print("Choose an option (1-5): ");
    }
