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

    public void addProduct(Product product){
        inventoryById.put(product.getId(), product);
        inventoryBySku.put(product.getSku(), product);
        System.out.println("Product successfully added");
    }

    public Product getProductBySku(String sku){return inventoryBySku.get(sku);}

    public Product getProductById(int id){return inventoryById.get(id);}

    public List<Product> getAllProducts(){
        return new ArrayList<>(inventoryById.values());
    }

    public List<Product> getLowStock(int threshold){
        ArrayList<Product> results = new ArrayList<>();
        for (Product p: getAllProducts()){
            if(p.getStock() <= threshold){results.add(p);}
        }
        return results;
    }

    public List<Product> getListById(List<Integer> ids){
        ArrayList<Product> results = new ArrayList<>();
        for(int i: ids){results.add(inventoryById.get(i));}
        return results;
    }

    public List<Product> getListBySku(List<String> skus){
        ArrayList<Product> results = new ArrayList<>();
        for(String sku: skus){results.add(inventoryBySku.get(sku));}
        return results;
    }

    public void updateProduct(Product product){
        inventoryById.replace(product.getId(), product);
        inventoryBySku.replace(product.getSku(), product);
        System.out.println("Product successfully updated");
    }

    public void updateAll(List<Product> list){
        for(Product p: list){
            inventoryById.replace(p.getId(), p);
            inventoryBySku.replace(p.getSku(), p);
        }
        System.out.println("Products successfully updated");
    }

    public void deleteById(Product product){
        inventoryBySku.remove(inventoryById.get(id).getSku());
        inventoryById.remove(id);
        System.out.println("Product successfully deleted");
    }

    public void deleteBySku(String sku){
        inventoryById.remove(inventoryBySku.get(sku).getId());
        inventoryBySku.remove(sku);
        System.out.println("Product successfully deleted");
    }

    public void deleteAll(List<Product> productList){
        for(int id: productList){
            inventoryBySku.remove(inventoryById.get(id).getSku());
            inventoryById.remove(id);
        }
    }
}
