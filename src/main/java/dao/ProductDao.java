package dao;

import models.Product;
import java.util.List;

public interface ProductDao {
    void addProduct(Product product);
    Product getProductBySku(String sku);
    Product getProductById(int id);
    List<Product> getAllProducts();
    List<Product> getLowStock(int threshold);
    List<Product> getListById(List<Integer> ids);
    List<Product> getListBySku(List<String> skus);
    void updateProduct(Product product);
    void updateAll(List<Product> list);
    void deleteById(int id);
    void deleteBySku(String sku);
    void deleteAll(List<Integer> ids);
}
