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
                    
                    7. Exit""");
            switch(selection){
                case 1 -> enterNewProduct();
                case 2 -> lookUpProduct();
                case 3 -> updateStock();
                case 4 -> updateInfo();
                case 5 -> lowStockProducts();
                case 6 -> deleteProduct();
                case 7 -> {return;}
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private static void updateInfo(){
        ArrayList<Product> products = lookUpProduct();
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
    }

    private static void lowStockProducts(){
        int threshold = ScannerUtils.getValidPositiveInt("Enter threshold for 'low stock': ");
        ArrayList<Product> results = new ArrayList<>(database.getLowStock(threshold));
        for (Product result : results) {
            System.out.println(result);
        }
    }

    private static void updateStock(){
        ArrayList<Product> product = lookUpProduct();
        for(int i = 0; i < product.size(); i++) {
            int newStock = product.getFirst().getStock() + ScannerUtils.getValidInt("Enter change to stock: ");
            product.getFirst().setStock(newStock);
            database.editProductInfo(product.getFirst());
        }
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
        for (Product product : products) {
            boolean goAhead = false;
            while (!goAhead) {
                System.out.println(product +
                        "\nAre you sure you want to delete this product from the database?" +
                        "\nY/N: ");
                String confirmation = scanner.nextLine().trim();
                if (confirmation.equalsIgnoreCase("y")) {
                    database.deleteProduct(product);
                    System.out.println("Product deleted");
                    goAhead = true;
                } else if (confirmation.equalsIgnoreCase("n")) {
                    System.out.println("Deletion Canceled");
                    goAhead = true;
                } else {
                    System.out.println("Invalid entry");
                }
            }
        }
    }
}
