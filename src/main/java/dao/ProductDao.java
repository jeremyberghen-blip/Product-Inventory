package dao;

import models.Product;
import java.util.List;

public interface ProductDao {
    void initializeDB();
    void addProduct(List<Product> productList);
    List<Product> getProductBySku(List<String> skuList);
    List<Product> getProductById(List<Integer> idList);
    List<Product> getAllProducts();
    List<Product> getLowStock(int threshold);
    List<Product> getListById(List<Integer> ids);
    List<Product> getListBySku(List<String> skus);
    void update(List<Product> list);
    void delete(List<Product> productList);
}
