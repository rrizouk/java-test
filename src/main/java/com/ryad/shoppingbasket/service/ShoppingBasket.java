package com.ryad.shoppingbasket.service;


import com.ryad.shoppingbasket.domain.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {

    private List<Product> products = new ArrayList<>();

    public void addProducts(List<Product> products) {
        this.products = products;
    }

    public BigDecimal getTotalCost() {
        return this.products.stream().map(p -> p.getCost()).reduce((c1,c2) -> c1.add(c2)).get();

    }
}
