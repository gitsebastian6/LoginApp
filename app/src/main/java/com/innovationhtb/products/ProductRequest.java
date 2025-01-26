package com.innovationhtb.products;

import java.util.List;

public class ProductRequest {
    private String code;
    private String name;
    private String characteristic;
    private String quantity;
    private String companyId;
    private List<Price> prices;
    private List<String> categories;

    public ProductRequest(String code,
                          String name,
                          String characteristic,
                          String quantity,
                          String companyId,
                          List<ProductRequest.Price> prices,
                          List<String> categories) {
        this.code = code;
        this.name = name;
        this.characteristic = characteristic;
        this.quantity = quantity;
        this.companyId = companyId;
        this.prices = prices;
        this.categories = categories;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    static class Price {
        private String value;
        private String currencyId;
        public Price(String value, String currencyId) {
            this.value = value;
            this.currencyId = currencyId;
        }
    }


}
