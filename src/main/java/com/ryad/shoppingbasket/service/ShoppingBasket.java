package com.ryad.shoppingbasket.service;


import com.ryad.shoppingbasket.domain.Product;
import com.ryad.shoppingbasket.domain.ProductType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ryad.shoppingbasket.domain.ProductType.*;
import static java.util.stream.Collectors.groupingBy;

public class ShoppingBasket {

    private final List<Product> products;
    private final LocalDate purchaseDate;

    public ShoppingBasket(List<Product> products, LocalDate purchaseDate) {
        this.products = products;
        this.purchaseDate = purchaseDate;
    }


    public BigDecimal getTotalShoppingBasketCost() {
        BigDecimal totalCost = getShoppingBasketCostWithDiscounts(products,purchaseDate);
        return totalCost;

    }

    private BigDecimal getTotalCostWithoutDiscount() {
        return this.products.stream().map(p -> p.getCost()).reduce((c1,c2) -> c1.add(c2)).get();
    }


    private BigDecimal getShoppingBasketCostWithDiscounts(List<Product> products, LocalDate purchaseDate) {
        Map<ProductType,BigDecimal> discounts = new HashMap<>();
        if(purchaseDate.isAfter(LocalDate.now().minusDays(2))
                && purchaseDate.isBefore(LocalDate.now().plusDays(6)) ){
              Map<ProductType, List<Product>> productsByTypeMap = getProductsByTypeMap(products);
               int soupsSize = 0;
              if(productsByTypeMap.containsKey(SOUP)){
                   soupsSize = productsByTypeMap.get(SOUP).size() ;
              }
              if(soupsSize > 1){
                  BigDecimal breadDiscount = getBreadDiscount(products,soupsSize);
                  discounts.put(BREAD,breadDiscount);
              }

        }

        if(purchaseDate.minusDays(3).isAfter(LocalDate.now()) &&
                purchaseDate.isBefore(purchaseDate.plusMonths(2).withDayOfMonth(1))){
            discounts.put(APPLE,BigDecimal.valueOf(0.9));
        }
        if(!discounts.isEmpty()){
            return getTotalCostWithDiscount(products,discounts);
        }
        return getTotalCostWithoutDiscount();
    }

    private BigDecimal getBreadDiscount(List<Product> products, int size) {
        long breadSize  = products.stream().filter(p -> p.getType().equals(BREAD)).count();
        if(breadSize == 0){
            return BigDecimal.ONE;
        }
        return BigDecimal.ONE.subtract(BigDecimal.valueOf(0.5).multiply(BigDecimal.valueOf(size / 2).divide(BigDecimal.valueOf(breadSize))));
    }

    private BigDecimal getTotalCostWithDiscount(List<Product> products, Map<ProductType, BigDecimal> discounts) {
        BigDecimal totalCost = BigDecimal.ZERO;
        for(ProductType key : discounts.keySet() ){
            BigDecimal discountedSubtotal = products.stream().filter(p -> p.getType().equals(key)).map(p -> p.getCost()).reduce((c1,c2) -> c1.add(c2)).get().multiply(discounts.get(key));
            totalCost = totalCost.add(discountedSubtotal);
        }
        BigDecimal nonDiscountedSubtotal = products.stream().filter(p -> !discounts.containsKey(p.getType())).map(p -> p.getCost()).reduce((c1,c2) -> c1.add(c2)).get();
        totalCost = totalCost.add(nonDiscountedSubtotal);
        return totalCost;
    }



    private Map<ProductType, List<Product>> getProductsByTypeMap(List<Product> products) {
       return products.stream().collect(groupingBy( Product::getType));
    }

}
