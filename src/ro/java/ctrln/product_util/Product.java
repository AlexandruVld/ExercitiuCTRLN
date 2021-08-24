package ro.java.ctrln.product_util;

public class Product {

    private final String name;
    private final double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
      return quantity;
    }

    public void subtractFromQuantity(int amount){
        if(this.quantity > 0){
            this.quantity -= amount;
        } else {
            System.out.println("Out of stock!");
        }

    }

    public void addToQuantity(int amount){
        this.quantity += amount;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
