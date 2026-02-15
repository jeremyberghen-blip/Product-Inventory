package services;

import models.Product;
import dao.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class Services {
    final private ProductDao dao;

    public Services(ProductDao dao){
        this.dao = dao;
    }

    public void saveProduct(Product product){
        if(verifyProduct(product)){
            dao.addProduct(product);
        } else {
            System.out.println("Invalid product. Cannot add to database");
        }
    }

    public void editProductInfo(Product product){
        if(verifyProduct(product)){
            dao.updateProduct(product);
        } else {
            System.out.println("Invalid information");
        }
    }

    public List<Product> getLowStock(int threshold){
        return dao.getLowStock(threshold);
    }

    public Product productLookUpById(int id){
        return dao.getProductById(id);
    }

    public Product productLookUpBySku(String sku){
        if(verifySku(sku)){
            Product product = dao.getProductBySku(sku);
            if(verifyProduct(product)){return product;} else {
                System.out.println("No product found");
                return new Product();
            }
        } else {
            System.out.println("invalid sku");
            return new Product();
        }
    }

    public void deleteProduct(Product product){
        dao.deleteById(product.getId());
    }

    public List<Product> getListById(ArrayList<Integer> list){
        return dao.getListById(list);
    }

    public List<Product> getListBySku(ArrayList<String> list){
        return dao.getListBySku(list);
    }

    public void deleteAll(ArrayList<Integer> list){
        dao.deleteAll(list);
    }

    public void deleteBySku(String sku){
        dao.deleteBySku(sku);
    }

    public void updateAll(ArrayList<Product> list){
        dao.updateAll(list);
    }

    private boolean verifyProduct(Product product){
        return (verifyName(product.getName())
                && verifySku(product.getSku())
                && verifyStock(product.getStock())
                && verifyPrice(product.getPrice()));
    }

    private boolean verifyName(String name){
        return !name.isEmpty();
    }

    private boolean verifySku(String sku){
        return !sku.isEmpty();
    }

    private boolean verifyStock(int stock){
        return (stock >= 0);
    }

    private boolean verifyPrice(double price){
        return (price >= 0);
    }
}
