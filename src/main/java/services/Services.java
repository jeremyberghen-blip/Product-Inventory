package services;

import models.Product;
import dao.ProductDao;
import java.util.List;

public class Services {
    final private ProductDao dao;

    public Services(ProductDao dao){
        this.dao = dao;
    }

    public void saveProduct(List<Product> productList){
        for (Product product: productList){
            if(!verifyProduct(product)){
                System.out.println(product + "\nInvalid product. Cannot add to database");
                productList.remove(product);
            }
        }
        dao.addProduct(productList);
    }

    public void editProductInfo(List<Product> productList){
        if (!productList.isEmpty()){
            productList.removeIf(product -> !verifyProduct(product));
            dao.update(productList);
        }
    }

    public List<Product> getLowStock(int threshold){
        return dao.getLowStock(threshold);
    }

    public List<Product> getListById(List<Integer> list){
        return dao.getListById(list);
    }

    public List<Product> getListBySku(List<String> list){
        return dao.getListBySku(list);
    }

    public void delete(List<Product> list){dao.delete(list);

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
