import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import models.Product; // Import your actual product class

public class ProductTest {

    @Test
    void testNegativePriceThrowsException() {
        Product product = new Product();

        // This says: "I expect an IllegalArgumentException when I try to do this"
        assertThrows(IllegalArgumentException.class, () -> {
            product.setPrice(-5.00);
        });
    }

    @Test
    void testValidPriceIsSet() {
        Product product = new Product();
        product.setPrice(19.99);

        // This says: "The price should definitely be 19.99"
        assertEquals(19.99, product.getPrice());
    }
}