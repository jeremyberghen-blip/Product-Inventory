package dao; //not safe, do not implement without fixing

import java.sql.*;
import java.util.ArrayList;

import models.Product;

public class SQLiteDAO {
    public void addProduct(){
        String sql = "INSERT INTO inventory (product_name, sku, price, stock)" +
            "VALUES (?, ?, ?, ?)";
        try(
        Connection conn = this.connect();
        PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, product.getName());
            statement.setString(2, product.getSku());
            statement.setDouble(3, product.getPrice());
            statement.setInt(4, product.getQuantity());
            statement.execute();
            System.out.println("main.java.models.Product successfully saved");
        } catch (
        SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public ArrayList<Product> productLookUpName(String name){
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE product_name = ?;";
        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            while(results.next()){
                Product  product = new Product();
                product.setStock(results.getInt("stock"));
                product.setPrice(results.getDouble("price"));
                product.setSku(results.getString("sku"));
                product.setName(results.getString("product_name"));
                product.setId(results.getInt("id"));
                products.add(product);
            }
        } catch (SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
        return products;
    }

    public Product productLookUpSku(String sku){
        Product product = new Product();
        String sql = "SELECT * FROM inventory WHERE sku = ?;";
        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, sku);
            ResultSet results = statement.executeQuery();
            if(results.next()){
                product.setId(results.getInt("id"));
                product.setName(results.getString("product_name"));
                product.setSku(results.getString("sku"));
                product.setPrice(results.getDouble("price"));
                product.setStock(results.getInt("stock"));
            }
        } catch (SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
        return product;
    }

    public Product productLookUpId(int id){
        Product product = new Product();
        String sql = "SELECT * FROM inventory WHERE id = ?;";
        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            if(results.next()){
                product.setId(results.getInt("id"));
                product.setName(results.getString("product_name"));
                product.setSku(results.getString("sku"));
                product.setPrice(results.getDouble("price"));
                product.setStock(results.getInt("stock"));
            }
        } catch (SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
        return product;
    }

    public ArrayList<Product> getLowStock(int threshold){
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE stock = ?";
        for(int i = threshold; i >= 0; i--){
            try (Connection conn = this.connect();
                 PreparedStatement statement = conn.prepareStatement(sql)){
                statement.setInt(1, i);
                ResultSet results = statement.executeQuery();
                while (results.next()){
                    Product product = new Product(
                            results.getString("product_name"),
                            results.getString("sku"),
                            results.getInt("stock"),
                            results.getDouble("price"));
                    product.setId(results.getInt("id"));
                    products.add(product);
                }
            } catch (SQLException e){
                System.out.println("low stock error: " + e.getMessage());
            }
        }
        return products;
    }

    public void editProductInfo(int id, Product updatedInfo){
        Product product = productLookUpId(id);
        String sql = "UPDATE inventory " +
                "SET product_name = ?," +
                "sku = ?," +
                "price = ?," +
                "stock = ?" +
                "WHERE id = ?";

        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, updatedInfo.getName());
            statement.setString(2, updatedInfo.getSku());
            statement.setDouble(3, updatedInfo.getPrice());
            statement.setInt(4, updatedInfo.getStock());
            statement.setInt(5, product.getId());
            statement.execute();
        } catch (SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public Services(){initializeDatabase();}

    private Connection connect(){
        String url = "jdbc:sqlite:inventory.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return conn;
    }

    public void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS inventory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "product_name TEXT NOT NULL, " +
                "sku TEXT UNIQUE NOT NULL, " +
                "price REAL NOT NULL, " +
                "stock INTEGER NOT NULL);";

        try (Connection conn = this.connect();
             Statement statement = conn.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Initialization error: " + e.getMessage());
        }
    }

    public void deleteProduct(int id){
        String sql = "DELETE FROM inventory WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, id);
            statement.execute();
            System.out.println("main.java.models.Product successfully deleted.");
        } catch (SQLException e){
            System.out.println("Delete error: " + e.getMessage());
        }
    }
}
