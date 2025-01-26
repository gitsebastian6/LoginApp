package com.innovationhtb.products;

import java.util.List;

public class ProductModel {
    private String id;
    private String name;
    private String characteristic;
    private List<ProductModel.Price> prices;

    public ProductModel(String id, String name, String characteristic, List<ProductModel.Price> price) {
        this.id = id;
        this.name = name;
        this.characteristic = characteristic;
        this.prices = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ProductModel.Price> getPrice() {
        return prices;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }
    static class Price {
        private String value;
        private String description;
        public Price(String value, String description) {
            this.value = value;
            this.description = description;
        }
    }

}
