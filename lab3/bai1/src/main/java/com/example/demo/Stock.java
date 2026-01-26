package com.example.demo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Stock extends Subject<Stock> {

    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public void setPrice(double newPrice) {
        double oldPrice = this.price;
        this.price = newPrice;

        double change = ((newPrice - oldPrice) / oldPrice) * 100;

        Map<String, Object> data = new HashMap<>();
        data.put("symbol", symbol);
        data.put("oldPrice", oldPrice);
        data.put("newPrice", newPrice);
        data.put("changePercent", change);
        data.put("timestamp", LocalDateTime.now());

        System.out.printf(
                "\nGiá cổ phiếu %s: %.2f → %.2f (%+.2f%%)%n",
                symbol, oldPrice, newPrice, change
        );

        notifyObservers(data);
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}

