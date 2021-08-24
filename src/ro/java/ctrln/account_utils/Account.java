package ro.java.ctrln.account_utils;

import java.util.Objects;

public class Account{

    private final String userName;
    private final String password;
    private final AccountDetails accountDetails = new AccountDetails();
    private final ProductList productList = new ProductList();

    public Account(String name, String password) {
        this.userName = name;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword(){
        return password;
    }

    public void showAccountDetails(){
        System.out.println("Cont: " + getUserName() + "\nNume: " + accountDetails.getFirstName() +
                "\nPrenume: " + accountDetails.getLastName() + "\nEmail: " + accountDetails.getEmail() +
                "\nAdresa: " + accountDetails.getAddress());
    }

    public void modifyAccountDetails(String firstName, String lastName, String email, String address){
        accountDetails.modifyFirstName(firstName);
        accountDetails.modifyLastName(lastName);
        accountDetails.modifyEmail(email);
        accountDetails.modifyAddress(address);
    }
    public void addToWishList(String name){
        productList.addToWishlist(name);
    }

    public void removeWishListItem(String name) {
        productList.removeWishlistItem(name);
    }

    public void printWishList(){
        productList.printWishList();
    }

    public void addToShoppingList(String product, int quantity){
        productList.addToShoppingList(product, quantity);
    }

    public void removeShoppingListItem(String product, int quantity){
        productList.removeShoppingListItem(product, quantity);
    }

    public void printShoppingList(){
        productList.printShoppingList();
    }

    public void checkOut(){
        productList.checkingOutShoppingList();
    }

    public void viewPreviousCheckedOut(){
        productList.printPreviewsOrders();

    }

    public void returnProducts(int orderNo, int productPosition, int quantity) {
        productList.returnProduct(orderNo, productPosition, quantity);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return userName.equals(account.userName) && password.equals(account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }

    @Override
    public String toString() {
        return "Account{" +
                ", User name =" + userName +
                '}';
    }
}
