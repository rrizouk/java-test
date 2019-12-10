package com.ryad.shoppingbasket.service;


import com.ryad.shoppingbasket.domain.Product;
import com.ryad.shoppingbasket.domain.ProductType;
import com.ryad.shoppingbasket.domain.Unit;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.ryad.shoppingbasket.domain.ProductType.*;
import static com.ryad.shoppingbasket.domain.Unit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingBasketTest {

    private ShoppingBasket underTest;

    @Before
    public void setUp()  {
        underTest = new ShoppingBasket();
    }

    @Test
    public void should_get_total_cost_for_basket_without_discounts() {

        Product apple = createProduct(APPLE, SINGLE,BigDecimal.valueOf(0.1));
        Product milk = createProduct(MILK, BOTTLE,BigDecimal.valueOf(1.3));
        List<Product> apples = createProducts(apple,6);
        List<Product> bottlesOfMilk = createProducts(milk,1);

        List<Product> products = new ArrayList<>(apples);
        products.addAll(bottlesOfMilk);

        underTest.addProducts(products);


       BigDecimal totalCost =  underTest.getTotalCost();

       assertNotNull(totalCost);
       assertEquals(BigDecimal.valueOf(1.9), totalCost);

    }


    @Test
    public void should_get_total_cost_for_basket_with_one_discount() {

        Product soup = createProduct(SOUP, TIN,BigDecimal.valueOf(0.65));
        Product bread = createProduct(BREAD, LOAF,BigDecimal.valueOf(0.8));
        List<Product> soups = createProducts(soup,3);
        List<Product> breads = createProducts(bread,2);

        List<Product> products = new ArrayList<>(soups);
        products.addAll(breads);

        underTest.addProducts(products);


        BigDecimal totalCost =  underTest.getTotalCost();

        assertNotNull(totalCost);
        assertEquals(BigDecimal.valueOf(3.15), totalCost);

    }



    private List<Product> createProducts(Product product, int productsSize) {
        List<Product> products = new ArrayList<>(productsSize);
        for(int i = 0; i< productsSize; i++){
            products.add(product);
        }
        return products;
    }

    private Product createProduct(ProductType type, Unit unit, BigDecimal cost) {
        return new Product(type, unit,cost);
    }
}