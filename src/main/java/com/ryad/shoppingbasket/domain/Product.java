package com.ryad.shoppingbasket.domain;

import java.math.BigDecimal;

public class Product {


    private final ProductType type;
    private final Unit unit;
    private final BigDecimal cost;

    public Product(ProductType type, Unit unit, BigDecimal cost) {
        this.type = type;
        this.unit = unit;
        this.cost = cost;
    }


    public Unit getUnit() {
        return unit;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public ProductType getType() {
        return this.type;
    }

}
