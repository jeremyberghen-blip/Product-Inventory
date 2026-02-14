package models;

public class Product {
    private int id = 0;
    private int quantity = 0;
    private double price = 0.00;
    private String sku = "Enter sku";
    private String name = "Enter name";

    public Product(){
    }

    public Product(int quantity, double price, String sku, String name){
        this.quantity = quantity;
        this.price = price;
        this.sku = sku;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public int getQuantity(){
        return this.quantity;
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

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setPrice(double price){
        this.price = price;
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
                "\nquantity = " + quantity +
                "\nprice = " + price +
                "\nsku = " + sku;

    }
}
