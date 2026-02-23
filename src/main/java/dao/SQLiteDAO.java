package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import models.Product;

public class SQLiteDAO {
    public void initializeDB(){
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

    public SQLiteDAO(){initializeDB();}

    public void addProduct(List<Product> productList){
        String sql = "INSERT INTO inventory (product_name, sku, price, stock)" +
                "VALUES (?, ?, ?, ?)";
        try(
                Connection conn = this.connect();
                PreparedStatement statement = conn.prepareStatement(sql)){
            conn.setAutoCommit(false);
            for (Product product: productList) {
                statement.setString(1, product.getName());
                statement.setString(2, product.getSku());
                statement.setDouble(3, product.getPrice());
                statement.setInt(4, product.getStock());
                statement.addBatch();
            }
            statement.executeBatch();
            System.out.println("main.java.models.Product successfully saved");
        } catch (
                SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public List<Product> getLowStock(int threshold){
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM inventory WHERE stock <= ?";
        try (Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setInt(1, threshold);
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
        return products;
    }

    List<Product> getListById(List<Integer> ids){
        List<Product> productList = new ArrayList<>();

        if(ids == null || ids.isEmpty()){System.out.println("invalid ID list"); return productList;}
        String placeHolders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT * FROM inventory WHERE id IN (" + placeHolders + ")";

        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i: ids){
                statement.setInt(1, i);
            }
            ResultSet results = statement.executeQuery();
            while(results.next()){
                Product product = new Product(results.getString("product_name"),
                        results.getString("sku"),
                        results.getInt("stock"),
                        results.getDouble("price"));
                product.setId(results.getInt("id"));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
        return productList;
    }

    List<Product> getListBySku(List<String> skus){
        List<Product> productList = new ArrayList<>();

        if(skus == null || skus.isEmpty()){System.out.println("invalid ID list"); return productList;}
        String placeHolders = String.join(",", Collections.nCopies(skus.size(), "?"));
        String sql = "SELECT * FROM inventory WHERE id IN (" + placeHolders + ")";

        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            for (String s: skus){
                statement.setString(1, s);
            }
            ResultSet results = statement.executeQuery();
            while(results.next()){
                Product product = new Product(results.getString("product_name"),
                        results.getString("sku"),
                        results.getInt("stock"),
                        results.getDouble("price"));
                product.setId(results.getInt("id"));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
        return productList;
    }

    void update(List<Product> list){
        String sql = "UPDATE inventory " +
                "SET product_name = ?," +
                "sku = ?," +
                "price = ?," +
                "stock = ?" +
                "WHERE id = ?";

        try(Connection conn = this.connect();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            for (Product product: list){
                statement.setString(1, product.getName());
                statement.setString(2, product.getSku());
                statement.setDouble(3, product.getPrice());
                statement.setInt(4, product.getStock());
                statement.setInt(5, product.getId());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e){
            System.out.println("Save error: " + e.getMessage());
        }
    }

    void delete(List<Product> productList){
        String sql = "DELETE FROM inventory WHERE id = ?";

        try (Connection conn = this.connect();
             PreparedStatement statement = conn.prepareStatement(sql)){
            for (Product product: productList){
            statement.setInt(1, product.getId());
            statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e){
            System.out.println("Delete error: " + e.getMessage());
        }
    }

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
}
