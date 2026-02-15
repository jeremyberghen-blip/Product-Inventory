package models;

public class Product {
    private int id;
    private int stock;
    private double price;
    private String sku;
    private String name;

    public Product(){    }

    public Product(String name, String sku, int stock, double price){
        this.stock = stock;
        this.price = price;
        this.sku = sku;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public int getStock(){
        return this.stock;
    }

    public double getPrice(){
        return this.price;
    }

    public String getSku(){
        return this.sku;
    }

    public String getName(){
        return this.name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setStock(int stock){
        if(stock < 0){System.out.println("stock cannot be less than 0");}
        else {this.stock = stock;}
    }

    public void setPrice(double price){
        if(price < 0){System.out.println("price cannot be less than 0.00");}
        else {this.price = price;}
    }

    public void setSku(String sku){
        this.sku = sku;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "main.java.models.Product{" +
                "\nname = " + name +
                "\nid = " + id +
                "\nstock = " + stock +
                "\nprice = " + price +
                "\nsku = " + sku;

    }
}
