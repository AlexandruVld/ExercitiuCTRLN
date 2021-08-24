package ro.java.ctrln.account_utils;

interface ShoppingList {

    void addToShoppingList(String name, int quantity);

    void checkingOutShoppingList();

    void removeShoppingListItem(String name, int quantity);

}

