package com.innovationhtb.products;

import java.util.List;

public class ProductModel {
    private String id;
    private String code;
    private String name;
    private String characteristic;
    private int quantity;
    private List<Price> prices;

    // Constructor
    public ProductModel(String id, String code, String name, String characteristic, int quantity, List<Price> prices) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.characteristic = characteristic;
        this.quantity = quantity;
        this.prices = prices;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<Price> getPrices() {
        return prices;
    }

    // Clase interna para manejar precios
    public static class Price {
        private double value;

        public Price(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }
}
