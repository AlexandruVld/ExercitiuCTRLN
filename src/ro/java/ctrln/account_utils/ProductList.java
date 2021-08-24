package ro.java.ctrln.account_utils;

import ro.java.ctrln.product_util.Product;
import ro.java.ctrln.product_util.ProductUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class ProductList implements Wishlist, ShoppingList{

    private final Map<Product, Integer> shoppingList = new LinkedHashMap<>();
    private final ArrayList<Product> products = new ArrayList<>();
    private final Map<String, Map<Product, Integer>> checkedOut = new LinkedHashMap<>();

    @Override
    public void addToShoppingList(String name, int quantity){
        if(ProductUtils.getProduct(name) != null){
            if(shoppingList.containsKey(ProductUtils.getProduct(name))){
                int initialQuantity = findShoppingListEntry(name).getValue();
                int newQuantity = initialQuantity + quantity;
                this.shoppingList.replace(ProductUtils.getProduct(name), newQuantity);
            } else {
                this.shoppingList.put(ProductUtils.getProduct(name), quantity);
            }
            System.out.println("Produsul: " + name + ", cantitate: " + quantity + " kg, a fost adaugat in ShoppingList");
        } else {
            System.out.println("Produsul " + name + " nu face parte din oferta noastra!");
        }
    }

    @Override
    public void removeShoppingListItem(String name, int quantity) {
        if(shoppingList.isEmpty()){
            System.out.println("Lista este goala!");
        } else if(findShoppingListEntry(name.toLowerCase()) != null){
            int initialQuantity = findShoppingListEntry(name.toLowerCase()).getValue();
            int newQuantity = initialQuantity - quantity;
            if(newQuantity > 0){
                shoppingList.replace(findShoppingListEntry(name.toLowerCase()).getKey(), newQuantity);
                System.out.println("Cantitatea produsului: " + name + " a fost a fost diminuata cu: " +
                        quantity + " kg");
            } else {
                shoppingList.remove(findShoppingListEntry(name.toLowerCase()).getKey());
                System.out.println("Produsul: " + name + " a fost inlaturat din cosul de cumparaturi");
            }
        } else {
            System.out.println("Produsul nu se afla in ShoppingList");
        }
    }

    public void printShoppingList(){
        if(shoppingList.isEmpty()){
            System.out.println("Lista nu contine elemente");
        }

        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        shoppingList.forEach((k, v) -> {
            System.out.println("Produs: " + k.getName() + ", pret: " + k.getPrice() + " per Kg" +
                ", cantitate comandata: " + v + " kg");
            double productValue = k.getPrice() * v;
            total.updateAndGet(v1 -> v1 + productValue);
        });
        System.out.println("Total: " + total);
    }

    @Override
    public void checkingOutShoppingList(){
        if(shoppingList.size() == 0){
            System.out.println("Lista este goala!");
        } else {
            AtomicReference<Double> totalValue = new AtomicReference<>((double) 0);
            Map<Product, Integer> checkedOutList = new HashMap<>();
            shoppingList.forEach((key, value) -> {
                double pricePerOrder = key.getPrice() * value;
                totalValue.updateAndGet(v -> v + pricePerOrder);
                ProductUtils.reduceQuantity(key.getName(), value);
                System.out.println("Produs: " + key.getName() + ", pret per Kg: " + key.getPrice() + ", cantitate comandata: " + value);
                checkedOutList.put(key, value);
            });
            System.out.println("Total plata: " + totalValue + " Ron");
            LocalDateTime localDate = LocalDateTime.now();
            String stringDate = localDate.format(DateTimeFormatter.ofPattern("dd.MMM.yyyy HH.mm.ss"));
            checkedOut.put(stringDate, checkedOutList);
            System.out.println("Comanda dumneavoastra a fost procesata cu succes!");
            shoppingList.clear();
        }
    }

    public void returnProduct(int orderNo, int productPosition, int quantity){

        if(checkedOut.size() < 1){
            System.out.println("Produsul pe care incercati sa-l returnati nu a fost achizitionat de la noi!");
        } else {
            try{
                Map.Entry<String, Map<Product, Integer>> order = findOrder(orderNo);
                Map.Entry<Product, Integer> productEntry = findOrderEntry(order, productPosition);
                int initialQuantity = productEntry.getValue();
                int finalQuantity = initialQuantity - quantity;
                if (productEntry.getValue() > 0){
                    productEntry.setValue(finalQuantity);
                    ProductUtils.addQuantity(productEntry.getKey().getName().toLowerCase(), quantity);
                }else{
                    order.getValue().remove(productEntry.getKey());
                    ProductUtils.addQuantity(productEntry.getKey().getName().toLowerCase(), quantity);
                }

                System.out.println("Produsul a fost returnat!");
            } catch (NullPointerException e){
                System.out.println("Nu exista niciun element la pozitia introdusa! ");
            }

        }
    }

    Map.Entry<String, Map<Product, Integer>> findOrder(int orderNo) {
        int n = 1;
        if (checkedOut.size() > 0 && checkedOut.size() >= orderNo) {
            for (Map.Entry<String, Map<Product, Integer>> entry : checkedOut.entrySet()) {
                if (n == orderNo) {
                    return entry;
                }
                n++;
            }
        }
        return null;
    }


    Map.Entry<Product, Integer> findShoppingListEntry(String name){
        for(Map.Entry<Product, Integer> entry : shoppingList.entrySet()){
            if(entry.getKey().getName().equals(name.toLowerCase())){
                return entry;
            }
        }
        return null;
    }

    Map.Entry<Product, Integer> findOrderEntry(Map.Entry<String,Map<Product, Integer>> order, int productPosition){
        int n = 1;
        Map<Product, Integer> entry = order.getValue();
        for(Map.Entry<Product, Integer> productEntry : entry.entrySet()){
            if(n == productPosition){
                return productEntry;
            }
            n++;
        }
        return null;
    }

    public void printPreviewsOrders(){
        int i = 1;
        for (Map.Entry<String, Map<Product, Integer>> entry : checkedOut.entrySet()){
            int j = 1;
            double totalValuePerOrder = 0;
            System.out.println("=====================================================");
            System.out.println(i + ". Data: " + entry.getKey());
            for(Map.Entry<Product, Integer> product : entry.getValue().entrySet()){
                System.out.println("\t" + j + ". Produs: " + product.getKey().getName() +
                        ", pret/kg: " + product.getKey().getPrice() + ", cantitate comandata: " + product.getValue());
                totalValuePerOrder += product.getKey().getPrice() * product.getValue();
                j++;
            }
            System.out.println("Total comanda: " + totalValuePerOrder);
            System.out.println("=====================================================");
            i++;
        }
    }


    @Override
    public void addToWishlist(String name){
        if(ProductUtils.getProduct(name) != null){
            this.products.add(ProductUtils.getProduct(name.toLowerCase()));
            System.out.println("Produsul " + name + " a fost adaugat la wishlist");
        } else{
            System.out.println("Produsul " + name + " nu exista");
        }
    }

    @Override
    public void removeWishlistItem(String name) {
        if(products.isEmpty()){
            System.out.println("Lista este goala!");
        } else if(findProduct(name) != null){
            products.remove(findProduct(name.toLowerCase()));
            System.out.println("Produsul " + name + " a fost sters din Wishlist");
        } else {
            System.out.println("Produsul nu se afla in WishList");
        }
    }

    Product findProduct(String name){
        return products
                .stream()
                .filter(p -> p.getName().equals(name.toLowerCase()))
                .findAny()
                .orElse(null);
    }

    public void printWishList(){
        if(products.isEmpty()){
            System.out.println("Lista nu contine elemente");
        }
        products.forEach(e -> System.out.println("Produs: " + e.getName() +
                ", pret: " + e.getPrice() +
                ", stoc: " + e.getQuantity()));
    }


}
