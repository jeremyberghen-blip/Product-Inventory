package dao;

import models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MockDao implements ProductDao{
    HashMap<Integer, Product> inventoryById= new HashMap<>();
    HashMap<String, Product> inventoryBySku = new HashMap<>();

    public MockDao(){initializeDB();}

    public void initializeDB() {

    }

    private int getId(){
        int id = 0;
        while (true){
            if(inventoryById.containsKey(id)){id++;} else {return id;}
        }
    }

    public void addProduct(List<Product> productList){
        for(Product product: productList){
            product.setId(getId());
            inventoryById.put(product.getId(), product);
            inventoryBySku.put(product.getSku(), product);
        }
    }

    public List<Product> getLowStock(int stockThreshold){
        List<Product> results = new ArrayList<>();
        for(Product product: inventoryById.values()){
            if (product.getStock() <= stockThreshold){results.add(product);}
        }
        return results;
    }

    public List<Product> getListById(List<Integer> IdList){
        List<Product> results = new ArrayList<>();
        for (int i: IdList){results.add(inventoryById.get(i));}
        return results;
    }

    public List<Product> getListBySku(List<String> skuList){
        List<Product> results = new ArrayList<>();
        for(String sku: skuList){results.add(inventoryBySku.get(sku));}
        return results;
    }

    public void update(List<Product> productList){
        for (Product product: productList){
            if(inventoryById.containsKey(product.getId())){
                inventoryById.replace(product.getId(), product);
            }
            inventoryBySku.values().removeIf(p -> p.getId() == product.getId());
            inventoryBySku.put(product.getSku(), product);
        }
    }

    public void delete(List<Product> productList){
        for (Product product: productList){
            if(inventoryById.containsKey(product.getId())){
                inventoryBySku.remove(product.getSku(), product);
            }
            if(inventoryBySku.containsKey(product.getSku())){
                inventoryById.remove(product.getId(), product);
            }
        }
    }
}
