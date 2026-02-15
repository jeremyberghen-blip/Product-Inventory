package dao;

import models.Product;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class MockDao {
    HashMap<Integer, Product> inventoryById= new HashMap<>();
    HashMap<String, Product> inventoryBySku = new HashMap<>();

    public MockDao(){}

    void addProduct(Product product){
        inventoryById.put(product.getId(), product);
        inventoryBySku.put(product.getSku(), product);
        System.out.println("Product successfully added");
    }

    Product getProductBySku(String sku){return inventoryBySku.get(sku);}

    Product getProductById(int id){return inventoryById.get(id);}

    List<Product> getAllProducts(){
        return new ArrayList<>(inventoryById.values());
    }

    List<Product> getLowStock(int threshold){
        ArrayList<Product> results = new ArrayList<>();
        for (Product p: getAllProducts()){
            if(p.getQuantity() <= threshold){results.add(p);}
        }
        return results;
    }

    List<Product> getListById(List<Integer> ids){
        ArrayList<Product> results = new ArrayList<>();
        for(int i: ids){results.add(inventoryById.get(i));}
        return results;
    }

    List<Product> getListBySku(List<String> skus){

        ArrayList<Product> results = new ArrayList<>();
        for(String sku: skus){results.add(inventoryBySku.get(sku));}
        return results;
    }

    void updateProduct(Product product){
        inventoryById.replace(product.getId(), product);
        inventoryBySku.replace(product.getSku(), product);
        System.out.println("Product successfully updated");
    }

    void updateAll(List<Product> list){
        for(Product p: list){
            inventoryById.replace(p.getId(), p);
            inventoryBySku.replace(p.getSku(), p);
        }
        System.out.println("Products successfully updated");
    }

    void deleteById(int id){
        inventoryBySku.remove(inventoryById.get(id).getSku());
        inventoryById.remove(id);
        System.out.println("Product successfully deleted");
    }

    void deleteBySku(String sku){
        inventoryById.remove(inventoryBySku.get(sku).getId());
        inventoryBySku.remove(sku);
        System.out.println("Product successfully deleted");
    }

    void deleteAll(List<Integer> ids){
        for(int id: ids){
            inventoryBySku.remove(inventoryById.get(id).getSku());
            inventoryById.remove(id);
        }
    }
}
