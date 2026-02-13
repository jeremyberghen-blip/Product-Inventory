package app;

import models.Product;
import services.Services;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Services database = new Services();
    static Scanner scanner = new Scanner(System.in);
    static void main(String[] args) {
        while (true) {
            System.out.println("""
                    1. Enter a New Product\
                    
                    2. Look up a product\
                    
                    3. Update stock of a product\
                    
                    4. Update a product's information\
                    
                    5. Get low stock items\
                    
                    6. Exit""");
            int selection;
            selection = scanner.nextInt();
            scanner.nextLine();
            switch(selection){
                case 1 -> enterNewProduct();
                case 2 -> System.out.println(lookUpProduct());
                case 3 -> updateStock();
                case 4 -> updateInfo();
                case 5 -> lowStockProducts();
                case 6 -> {return;}
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private static void updateInfo(){
        ArrayList<Product> product = lookUpProduct();
        System.out.println("Enter new name(if different)");
        String tempName = scanner.nextLine();
        if(tempName.isEmpty()){tempName = product.getFirst().getName();}
        product.getFirst().setName(tempName);
        System.out.println("Enter new sku(if different)");
        String tempSku = scanner.nextLine();
        if(tempSku.isEmpty()){tempSku = product.getFirst().getSku();}
        product.getFirst().setSku(tempSku);
        System.out.println("Enter new price(if different)");
        double tempPrice = scanner.nextDouble();
        scanner.nextLine();
        if(tempPrice == 0.00){tempPrice = product.getFirst().getPrice();}
        product.getFirst().setPrice(tempPrice);
        System.out.println("Enter new quantity in stock(if different)");
        int tempStock = scanner.nextInt();
        scanner.nextLine();
        if(tempStock < 0){tempStock = product.getFirst().getQuantity();}
        product.getFirst().setQuantity(tempStock);
        database.editProductInfo(product.getFirst().getId(), product.getFirst());
    }

    private static void lowStockProducts(){
        System.out.println("Enter threshold for 'low stock': ");
        int threshold = scanner.nextInt();
        scanner.nextLine();
        ArrayList<Product> results = database.getLowStock(threshold);
        for (Product result : results) {
            System.out.println(result);
        }
    }

    private static void updateStock(){
        ArrayList<Product> product = lookUpProduct();
        System.out.println("Enter difference in stock");
        int newStock = product.getFirst().getQuantity() + scanner.nextInt();
        scanner.nextLine();
        product.getFirst().setQuantity(newStock);
        database.editProductInfo(product.getFirst().getId(), product.getFirst());
    }

    private static ArrayList<Product> lookUpProduct(){
        while (true){
            System.out.println("""
                    1. Search by product ID\
                    
                    2. Search by product name\
                    
                    3. Search by product sku""");
            int selection = scanner.nextInt();
            scanner.nextLine();
            ArrayList<Product> results = new ArrayList<>();
            switch(selection) {
                case 1 -> {
                    System.out.println("Enter product ID");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Product product = database.productLookUpId(id);
                    System.out.println(product);
                    results.add(product);
                    return results;
                }
                case 2 -> {
                    System.out.println("Enter product name");
                    String name = scanner.nextLine();
                    ArrayList<Product> product = database.productLookUpName(name);
                    System.out.println(product);
                    return product;
                }
                case 3 -> {
                    System.out.println("Enter product sku");
                    String sku = scanner.nextLine();
                    Product product = database.productLookUpSku(sku);
                    System.out.println(product);
                    results.add(product);
                    return results;
                }
                case 4 -> {
                    return new ArrayList<>();
                }
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private static void enterNewProduct(){
        Product product = new Product();
        System.out.println("Enter product name");
        product.setName(scanner.nextLine());
        System.out.println("Enter product sku");
        product.setSku(scanner.nextLine());
        System.out.println("Enter product price");
        product.setPrice(scanner.nextDouble());
        scanner.nextLine();
        System.out.println("Enter product quantity in stock");
        product.setQuantity(scanner.nextInt());
        scanner.nextLine();
        System.out.println(product);
        database.saveProduct(product);
    }
}
