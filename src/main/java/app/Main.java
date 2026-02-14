package app;

import java.util.ArrayList;
import java.util.Scanner;
import services.Services;
import models.Product;

public class Main {
    static Services database = new Services();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            int selection = getValidInt("""
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
        ArrayList<Product> product = lookUpProduct();
        System.out.println("Enter new name(if different)");
        String tempName = scanner.nextLine();
        if(tempName.isEmpty()){tempName = product.getFirst().getName();}
        product.getFirst().setName(tempName);
        System.out.println("Enter new sku(if different)");
        String tempSku = scanner.nextLine();
        if(tempSku.isEmpty()){tempSku = product.getFirst().getSku();}
        product.getFirst().setSku(tempSku);
        double tempPrice = getValidDouble("Enter new price(if different)");
        if(tempPrice == 0.00){tempPrice = product.getFirst().getPrice();}
        product.getFirst().setPrice(tempPrice);
        System.out.println("Enter new quantity in stock(if different)");
        int tempStock = getValidInt("Enter new quantity in stock(if different): ");
        if(tempStock < 0){tempStock = product.getFirst().getQuantity();}
        product.getFirst().setQuantity(tempStock);
        database.editProductInfo(product.getFirst().getId(), product.getFirst());
    }

    private static void lowStockProducts(){
        int threshold = getValidInt("Enter threshold for 'low stock': ");
        scanner.nextLine();
        ArrayList<Product> results = database.getLowStock(threshold);
        for (Product result : results) {
            System.out.println(result);
        }
    }

    private static void updateStock(){
        ArrayList<Product> product = lookUpProduct();
        int newStock = product.getFirst().getQuantity() + getValidInt("Enter change to stock: ");
        product.getFirst().setQuantity(newStock);
        database.editProductInfo(product.getFirst().getId(), product.getFirst());
    }

    private static ArrayList<Product> lookUpProduct(){
        while (true){
            int selection = getValidInt("""
                    1. Search by product ID\
                    
                    2. Search by product name\
                    
                    3. Search by product sku""");
            ArrayList<Product> results = new ArrayList<>();
            switch(selection) {
                case 1 -> {
                    int id = getValidInt("Enter product ID");
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
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private static void enterNewProduct(){
        Product product = new Product();
        System.out.println("Enter product name: ");
        product.setName(scanner.nextLine());
        System.out.println("Enter product sku: ");
        product.setSku(scanner.nextLine());
        System.out.println("Enter product price: ");
        product.setPrice(getValidDouble("Enter product price: "));
        scanner.nextLine();
        product.setQuantity(getValidInt("Enter product quantity in stock: "));
        System.out.println(product);
        database.saveProduct(product);
    }

    static private void deleteProduct() {
        Product product = lookUpProduct().getFirst();
        while (true) {
            System.out.println(product +
                    "Are you sure you want to delete this product from the database?" +
                    "Y/N ");
            String confirmation = scanner.nextLine();
            if (confirmation.equals("Y") || confirmation.equals("y")) {
                database.deleteProduct(product.getId());
                return;
            }
            if(confirmation.equals("N") || confirmation.equals("n")){
                System.out.println("Deletion Canceled");
                return;
            }
        }
    }

    static private int getValidInt(String prompt){
        System.out.println(prompt);
        while (!scanner.hasNextInt()){
            System.out.println("Please enter a whole number");
            scanner.next();
        }
        int entry = scanner.nextInt();
        scanner.nextLine();
        return entry;
    }

    static private Double getValidDouble(String prompt){
        System.out.println(prompt);
        while (!scanner.hasNextDouble()){
            System.out.println("Please enter number with up to two decimal places");
            scanner.next();
        }
        Double entry = scanner.nextDouble();
        scanner.nextLine();
        return entry;
    }
}
