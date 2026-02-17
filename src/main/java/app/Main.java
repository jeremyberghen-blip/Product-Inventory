package app;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ProductDao;
import services.Services;
import models.Product;
import utils.ScannerUtils;
import dao.MockDao;

public class Main {
    private static final ProductDao myDao = new MockDao();
    private static final Services database = new Services(myDao);
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            int selection = ScannerUtils.getValidInt("""
                    1. Enter a New Product\
                    
                    2. Look up a product\
                    
                    3. Update stock of a product\
                    
                    4. Update a product's information\
                    
                    5. Get low stock items\
                    
                    6. Delete an item\
                    
                    7. Select multiple products by id\
                    
                    8. Select multiple products by sku\
                    
                    9. Delete multiple products by id\
                    
                    10.Delete multiple products by sku\
                    
                    11. Exit""");
            switch(selection){
                case 1  -> enterNewProduct();
                case 2  -> lookUpProduct();
                case 3  -> updateStock();
                case 4  -> updateInfo();
                case 5  -> lowStockProducts();
                case 6  -> deleteProduct();
                case 7  -> selectMultipleById();
                case 8  -> selectMultipleBySku();
                case 9  -> deleteMultiple();
                case 10 -> {return;}
                default -> System.out.println("Invalid selection");
            }
        }
    }

    public static void selectMultipleById(){
        ArrayList<Integer> ids = new ArrayList<>();
        while (true){
            ids.add(ScannerUtils.getValidInt("Enter product ID: "));
            if(ids.getLast() == null || !ScannerUtils.getYesNo("Select another product?: \nY/n")){break;}
        }
        ArrayList<Product> results = new ArrayList<>(database.getListById(ids));
        for (Product product: results){
            System.out.println(product);
        }
    }

    public static void selectMultipleBySku(){
        ArrayList<String> skus = new ArrayList<>();
        while (true){
            skus.add(ScannerUtils.getValidString("Enter product ID: "));
            if(skus.getLast() == null || !ScannerUtils.getYesNo("Select another product?: \nY/n")){break;}
        }
        ArrayList<Product> results = new ArrayList<>(database.getListBySku(skus));
        for (Product product: results){
            System.out.println(product);
        }
    }

    public static void deleteMultiple(){
        ArrayList<Product> productList = new ArrayList<>();
        ArrayList<Integer> confirmedDeleteList = new ArrayList<>();
        while(true){
            productList.addAll(lookUpProduct());
            for (Product product: productList){
                if(ScannerUtils.getYesNo(product + "\nAre you sure? \nY/N")){
                    confirmedDeleteList.add(product.getId());
                } else {productList.remove(product);}
            }
            database.deleteAll(confirmedDeleteList);
            if(!ScannerUtils.getYesNo("Delete more products?")){
                break;
            }
        }
    }

    private static void updateInfo(){
        ArrayList<Product> products = lookUpProduct();
        if(products != null && !products.isEmpty()){
            for (Product product : products) {
                System.out.println("Enter new name(if different): ");
                String tempName = scanner.nextLine();
                if (tempName.isEmpty()) {
                    tempName = product.getName();
                }
                product.setName(tempName);
               System.out.println("Enter new sku(if different): ");
               String tempSku = scanner.nextLine();
               if (tempSku.isEmpty()) {
                   tempSku = product.getSku();
               }
               product.setSku(tempSku);
               double tempPrice = ScannerUtils.getValidPositiveDouble("Enter new price(if different): ");
               if (tempPrice == -1.00) {
                   tempPrice = product.getPrice();
              }
               product.setPrice(tempPrice);
                int tempStock = ScannerUtils.getValidPositiveInt("Enter new quantity in stock(if different): ");
                if (tempStock < 0) {
                        tempStock = product.getStock();
                }
                product.setStock(tempStock);
                database.editProductInfo(product);
            }
        } else {System.out.println("No items returned while updating");}
    }

    private static void lowStockProducts(){
        int threshold = ScannerUtils.getValidPositiveInt("Enter threshold for 'low stock': ");
        ArrayList<Product> results = new ArrayList<>(database.getLowStock(threshold));
        for (Product result : results) {
            System.out.println(result);
        }
    }

    private static void updateStock(){
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Product> updatedInfo = new ArrayList<>();
        while (true){
            products.addAll(lookUpProduct());
            if(!products.isEmpty()){
                for (Product product: products){
                    int newStock = product.getStock() + ScannerUtils.getValidInt("Enter change to stock: ");
                    if(newStock < 0){System.out.println("Insufficient stock. Change canceled.");} else {
                        product.setStock(newStock);
                    }
                    updatedInfo.add(product);
                }
            } else {System.out.println("No product selected to update");}
            if(!ScannerUtils.getYesNo("Update another product?: ")){
                break;
            }
        }
        database.updateAll(updatedInfo);
    }

    private static ArrayList<Product> lookUpProduct(){
        while (true){
            int selection = ScannerUtils.getValidInt("""
                    1. Search by product ID\
                    
                    2. Search by product sku""");
            ArrayList<Product> results = new ArrayList<>();
            switch(selection) {
                case 1 -> {
                    int id = ScannerUtils.getValidInt("Enter product ID");
                    Product product = database.productLookUpById(id);
                    System.out.println(product);
                    results.add(product);
                    return results;
                }
                case 2 -> {
                    System.out.println("Enter product sku");
                    String sku = scanner.nextLine();
                    Product product = database.productLookUpBySku(sku);
                    System.out.println(product);
                    results.add(product);
                    return results;
                }
                case -1 -> {return null;} //exited out without completing the search
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private static void enterNewProduct(){
        Product product = new Product();
        product.setName(ScannerUtils.getValidString( "Enter product name: "));
        product.setSku(ScannerUtils.getValidString("Enter product sku: ").toUpperCase().trim());
        product.setPrice(ScannerUtils.getValidPositiveDouble("Enter product price: "));
        product.setStock(ScannerUtils.getValidPositiveInt("Enter product quantity in stock: "));
        System.out.println(product);
        database.saveProduct(product);
    }

    static private void deleteProduct() {
        ArrayList<Product> products = lookUpProduct();
        if (products != null && !products.isEmpty()){
            for (Product product : products) {
                if (ScannerUtils.getYesNo(product +
                        "\nAre you sure? \nY/N")) {
                    database.deleteProduct(product);
                } else {System.out.println("Deletion cancelled.");}
            }
        }
    }
}
