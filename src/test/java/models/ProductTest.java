package models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import models.Product;

public class ProductTest {
    Product product;
    @BeforeEach
    void setUp(){product = new Product();}

    @Test
    void testNegativePriceThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.setPrice(-5.00);
        });
    }

    @Test
    void testValidPriceIsSet() {
        product.setPrice(19.99);

        assertEquals(19.99, product.getPrice());
    }

    @Test
    void testNegativeStockThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            product.setQuantity(-5);
        });
    }

    @Test
    void testValidStockSaved() {
        product.setQuantity(42);

        assertEquals(42, product.getQuantity());
    }
}