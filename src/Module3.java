void printSummary() {
        if (count == 0) {
            System.out.println("No records yet. Choose option 1 to add one.");
            return;
        }
        double totalGen = 0, totalCons = 0, totalSurplus = 0, totalSelf = 0;
        int best = 0, worst = 0;

        for (int i = 0; i < count; i++) {
            DailyRecord r = records[i];
            totalGen + = r.getGenerated();
            totalCons + = r.getConsumed();
            totalSurplus + = r.getSurplus();
            totalSelf + = r.getSelfUsed();
            if (r.getGenerated() > records[best].getGenerated())  best = i;
            if (r.getGenerated() < records[worst].getGenerated()) worst = i;
        }

        double savings = totalSelf * tariff + totalSurplus * exportRate;
        double solarShare = totalCons > 0 ? totalSelf / totalCons * 100 : 0;

        System.out.println();
        System.out.println("========== Solar Energy Report ==========");
        System.out.println("Days recorded            : " + count);
        System.out.printf("Total generated          : %.2f kWh%n", totalGen);
        System.out.printf("Total consumed           : %.2f kWh%n", totalCons);
        System.out.printf("Total surplus            : %.2f kWh%n", totalSurplus);
        System.out.printf("Average generation / day : %.2f kWh%n", totalGen / count);
        System.out.printf("Solar share of home use  : %.1f %%%n", solarShare);
        System.out.printf("Best day                 : %s (%.2f kWh)%n",
                records[best].getDate(), records[best].getGenerated());
        System.out.printf("Lowest day               : %s (%.2f kWh)%n",
                records[worst].getDate(), records[worst].getGenerated());
        System.out.printf("Estimated savings        : Rs %.2f%n", savings);

        if (solarShare >= 100) {
            System.out.println("Status: Your solar system covers all of your home's use.");
        } else if (solarShare >= 50) {
            System.out.println("Status: Solar covers most of your use. Shifting heavy loads to daytime will help.");
        } else {
            System.out.println("Status: Solar covers under half of your use. Consider daytime appliance use.");
        }
        System.out.println("=========================================");
    }

    / Saves tariff settings and all records to a text file. */
    void save(String fileName) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.println("#settings," + tariff + "," + exportRate);
            for (int i = 0; i < count; i++) {
                out.println(records[i].toCsv());
            }
        }
    }

    / Loads records from a file if it exists. Returns number of records loaded. */
    int load(String fileName) throws IOException {
        File f = new File(fileName);
        if (!f.exists()) {
            return 0;
        }
        int loaded = 0;
        try (BufferedReader in = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] p = line.trim().split(",");
                try {
                    if (p[0].equals("#settings") && p.length == 3) {
                        tariff = Double.parseDouble(p[1]);
                        exportRate = Double.parseDouble(p[2]);
                    } else if (p.length == 3) {
                        if (addRecord(new DailyRecord(p[0],
                                Double.parseDouble(p[1]), Double.parseDouble(p[2])))) {
                            loaded++;
                        }
                    }
                } catch (NumberFormatException e) {
                    // skip a damaged line and keep reading
                }
            }
        }
        return loaded;
    }
}
