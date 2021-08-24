package ro.java.ctrln.product_util;

import java.util.ArrayList;


public class ProductUtils {

    private static final ArrayList<Product> products = new ArrayList<>();


    public static void getAllProducts(){
        products.add(new Product("mere", 5.2, 200));
        products.add(new Product("nectarine", 3.2, 150));
        products.add(new Product("afine", 5.5, 300));
        products.add(new Product("pere", 7.2, 80));
        products.add(new Product("prune", 10, 50));
        products.add(new Product("zmeura", 2, 10));
        products.add(new Product("cirese", 30.5, 20));
        products.add(new Product("piersici", 3.5, 100));
        products.add(new Product("caise", 1.5, 20));
        products.add(new Product("capsuni", 22.5, 30));
    }

    public static Product getProduct(String name){
        return products
                .stream()
                .filter(product -> product.getName().equals(name.toLowerCase()))
                .findAny()
                .orElse(null);
    }

    public static void reduceQuantity(String name, int quantity){
        Product product = getProduct(name);
        product.subtractFromQuantity(quantity);

    }

    public static void addQuantity(String name, int quantity){
        Product product = getProduct(name);
        product.addToQuantity(quantity);
    }

    public static void printAllProducts(){
       products.forEach(product -> System.out.println("Produs: " + product.getName() + ", pret: " +
                       product.getPrice() + ", cantitate stoc: " +
                       product.getQuantity() + " kg"));
    }



}
