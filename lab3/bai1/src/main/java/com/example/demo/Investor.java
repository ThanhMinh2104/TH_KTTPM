package com.example.demo;

import java.util.Map;

public class Investor implements Observer<Stock> {

    private String name;

    public Investor(String name) {
        this.name = name;
    }

    @Override
    public void update(Stock stock, Object dataObj) {
        Map<String, Object> data = (Map<String, Object>) dataObj;

        String symbol = (String) data.get("symbol");
        double change = (double) data.get("changePercent");
        double newPrice = (double) data.get("newPrice");

        if (change > 5) {
            System.out.println("   " + name + ": " + symbol + " tăng mạnh! Cân nhắc BÁN");
        } else if (change < -5) {
            System.out.println("   " + name + ": " + symbol + " giảm mạnh! Cân nhắc MUA");
        } else {
            System.out.printf(
                    "   %s: %s = %.2f (%+.2f%%)%n",
                    name, symbol, newPrice, change
            );
        }
    }

    @Override
    public String toString() {
        return "Investor(" + name + ")";
    }
}
