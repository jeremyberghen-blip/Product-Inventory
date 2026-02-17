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
        if (productList != null && !productList.isEmpty()){

        }
    }

    public List<Product> getLowStock(int threshold){
        return dao.getLowStock(threshold);
    }

    public List<Product> productLookUpById(int id) {
        return dao.getProductById(List.of(id));
    }

    public List<Product> productLookUpBySku(String sku){
        if(verifySku(sku)) {
            return dao.getProductBySku(List.of(sku));
        } else {return null;}
    }

    public List<Product> getListById(ArrayList<Integer> list){
        return dao.getListById(list);
    }

    public List<Product> getListBySku(ArrayList<String> list){
        return dao.getListBySku(list);
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
