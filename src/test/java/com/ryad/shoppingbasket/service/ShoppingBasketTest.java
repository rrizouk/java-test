package com.ryad.shoppingbasket.service;


import com.ryad.shoppingbasket.domain.Product;
import com.ryad.shoppingbasket.domain.ProductType;
import com.ryad.shoppingbasket.domain.Unit;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.ryad.shoppingbasket.domain.ProductType.*;
import static com.ryad.shoppingbasket.domain.Unit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ShoppingBasketTest {

    private ShoppingBasket underTest;


    @Test
    public void should_get_total_cost_for_basket_without_discounts() {

        Product apple = createProduct(APPLE, SINGLE,BigDecimal.valueOf(0.1));
        Product milk = createProduct(MILK, BOTTLE,BigDecimal.valueOf(1.3));
        List<Product> apples = createProducts(apple,6);
        List<Product> bottlesOfMilk = createProducts(milk,1);

        List<Product> products = new ArrayList<>(apples);
        products.addAll(bottlesOfMilk);
        LocalDate today = LocalDate.now();

        underTest = new ShoppingBasket(products,today);

        BigDecimal expected = BigDecimal.valueOf(1.9);

        BigDecimal totalCost =  underTest.getTotalShoppingBasketCost();

        assertNotNull(totalCost);
        assertEquals(0, totalCost.compareTo(expected));

    }


    @Test
    public void should_get_total_cost_for_basket_with_bread_discount() {

        Product soup = createProduct(SOUP, TIN,BigDecimal.valueOf(0.65));
        Product bread = createProduct(BREAD, LOAF,BigDecimal.valueOf(0.8));
        List<Product> soups = createProducts(soup,3);
        List<Product> breads = createProducts(bread,2);

        List<Product> products = new ArrayList<>(soups);
        products.addAll(breads);

        LocalDate today = LocalDate.now();

        underTest = new ShoppingBasket(products,today);

        BigDecimal expected = BigDecimal.valueOf(3.15);

        BigDecimal totalCost =  underTest.getTotalShoppingBasketCost();

        assertNotNull(totalCost);
        assertEquals(0, totalCost.compareTo(expected));

    }

    @Test
    public void should_get_total_cost_for_basket_with_apple_discount() {

        Product apple = createProduct(APPLE, SINGLE,BigDecimal.valueOf(0.1));
        Product milk = createProduct(MILK, BOTTLE,BigDecimal.valueOf(1.3));
        List<Product> apples = createProducts(apple,6);
        List<Product> bottlesOfMilk = createProducts(milk,1);

        List<Product> products = new ArrayList<>(apples);
        products.addAll(bottlesOfMilk);

        LocalDate inFiveDays = LocalDate.now().plusDays(5);

        underTest = new ShoppingBasket(products,inFiveDays);

        BigDecimal expected = BigDecimal.valueOf(1.84);

        BigDecimal totalCost =  underTest.getTotalShoppingBasketCost();

        assertNotNull(totalCost);
        assertEquals(0, totalCost.compareTo(expected));

    }

    @Test
    public void should_get_total_cost_for_basket_with_apple_and_bread_discount() {

        Product apple = createProduct(APPLE, SINGLE,BigDecimal.valueOf(0.1));
        Product soup = createProduct(SOUP, TIN,BigDecimal.valueOf(0.65));
        Product bread = createProduct(BREAD, LOAF,BigDecimal.valueOf(0.8));

        List<Product> apples = createProducts(apple,3);
        List<Product> soups = createProducts(soup,2);
        List<Product> loaves = createProducts(bread,1);

        List<Product> products = new ArrayList<>(apples);
        products.addAll(soups);
        products.addAll(loaves);

        LocalDate inFiveDays = LocalDate.now().plusDays(5);

        underTest = new ShoppingBasket(products,inFiveDays);

        BigDecimal expected = BigDecimal.valueOf(1.97);

        BigDecimal totalCost =  underTest.getTotalShoppingBasketCost();

        assertNotNull(totalCost);
        assertEquals(expected, totalCost);
        assertEquals(0, totalCost.compareTo(expected));

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