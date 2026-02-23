package app;

import java.util.ArrayList;
import java.util.List;
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
                    
                    3. Select multiple products by id\
                    
                    4. Select multiple products by sku\
                    
                    5. Get low stock items\
                    
                    6. Update stock of a product\
                    
                    7. Update a product's information\
                    
                    8. Delete an item\
                    
                    9. Exit""");
            switch(selection){
                case 1  -> enterNewProduct();
                case 2  -> lookUpProduct();
                case 3  -> selectMultipleById();
                case 4  -> selectMultipleBySku();
                case 5  -> lowStockProducts();
                case 6  -> updateStock();
                case 7  -> updateInfo();
                case 8  -> delete();
                case 9 -> {return;}
                default -> System.out.println("Invalid selection");
            }
        }
    }

    private static void enterNewProduct(){
        List<Product> entries = new ArrayList();
        while (true){
            Product product = new Product();
            product.setName(ScannerUtils.getValidString( "Enter product name: "));
            product.setSku(ScannerUtils.getValidString("Enter product sku: ").toUpperCase().trim());
            product.setPrice(ScannerUtils.getValidPositiveDouble("Enter product price: "));
            product.setStock(ScannerUtils.getValidPositiveInt("Enter product quantity in stock: "));
            if(ScannerUtils.getYesNo(product + "\nIs this information correct? \nY/N: ")){
                entries.add(product);
                if(!ScannerUtils.getYesNo("Add another product? \nY/N: ")){
                    database.saveProduct(entries);
                    return;
                }
            }
        }
    }

    private static List<Product> lookUpProduct(){
        while (true){
            int selection = ScannerUtils.getValidInt("""
                    1. Search by product ID\
                    
                    2. Search by product sku""");
            switch(selection) {
                case 1 -> {
                    List<Integer> idList = new ArrayList<>();
                    while(true){
                        idList.add(ScannerUtils.getValidInt("Enter product ID"));
                        if(!ScannerUtils.getYesNo("Lookup more products by ID? \nY/N: ")){
                            return database.getListById(idList);
                        }
                    }
                }
                case 2 -> {
                    List<String> skuList= new ArrayList<>();
                    while (true){
                        skuList.add(ScannerUtils.getValidString("Enter sku: "));
                        if(!ScannerUtils.getYesNo("Lookup more products by SKU? \nY/N: ")){
                            return database.getListBySku(skuList);
                        }
                    }
                }
                case -1 -> {} //exited out without completing the search
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

    private static void lowStockProducts(){
        int threshold = ScannerUtils.getValidPositiveInt("Enter threshold for 'low stock': ");
        ArrayList<Product> results = new ArrayList<>(database.getLowStock(threshold));
        for (Product result : results) {
            System.out.println(result);
        }
    }

    private static void updateStock(){
        System.out.println(database.getLowStock(ScannerUtils.getValidInt("Enter threshold for low stock: ")));
    }

    private static void updateInfo(){
        List<Product> products = lookUpProduct();
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
                database.editProductInfo(List.of(product));
            }
        } else {System.out.println("No items returned while updating");}
    }

    public static void delete() {
        List<Product> searchResults = lookUpProduct();
        List<Product> toDelete = new ArrayList<>();

        if (searchResults != null) {
            for (Product product : searchResults) {
                if (ScannerUtils.getYesNo(product + "\nDelete this item? Y/N: ")) {
                    toDelete.add(product);
                }
            }
        }

        if (!toDelete.isEmpty()) {
            database.delete(toDelete);
            System.out.println("Items successfully deleted.");
        }
    }
}
