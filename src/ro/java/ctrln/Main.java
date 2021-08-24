package ro.java.ctrln;

import ro.java.ctrln.account_utils.Account;
import ro.java.ctrln.product_util.ProductUtils;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static final ArrayList<Account> accounts = new ArrayList<>();


    public static void main(String[] args) {
        ProductUtils.getAllProducts();
        Scanner scanner = new Scanner(System.in);
            boolean quit = false;
            while (!quit) {
                menu();
                System.out.print("Introduceti o optiune: ");
                try {
                    int action = scanner.nextInt();
                    switch (action) {
                        case 0:
                            quit = true;
                            break;
                        case 1:
                            Account account = logIn();
                            if (account != null) {
                                dashBoardActions(account);
                                break;
                            } else {
                                System.out.println("Eroare de logare! Incercati din nou");
                            }
                            break;
                        case 2:
                            createAccount();
                            break;
                        default:
                            System.out.println("Selectati o optiune de la 0 la 2");
                            break;
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Input incorect! Introduceti o valoare de la 0 la 2");
                    scanner.nextLine();
                }
            }

    }

    public static void menu() {
        System.out.println("Lista optiuni: ");
        System.out.println("0 -> Exit\n" +
                "1 -> Log in\n" +
                "2 -> Creare cont\n");
    }

    public static void accountOptions(){
        System.out.println("Lista optiuni: ");
        System.out.println("0  -> Exit\n" +
                "1  -> Listeaza detaliile contului\n" +
                "2  -> Modificare detalii cont\n" +
                "3  -> Afisarea stocului produselor\n" +
                "4  -> Adauga la wishlist\n" +
                "5  -> Eliminare produs din wishlist\n" +
                "6  -> Afisare wishlist\n" +
                "7  -> Adaugare produs la cosul de cumparaturi\n" +
                "8  -> Eliminare produs din cosul de cumparaturi\n" +
                "9  -> Afisarea cosului de cumparaturi\n" +
                "10 -> Check out\n" +
                "11 -> Istoric comenzi\n" +
                "12 -> Retur produs\n");
    }

    public static void dashBoardActions(Account account){
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while (!quit) {
            accountOptions();
            try {
                System.out.print("Introduceti o optiune: ");
                int action = scanner.nextInt();
                switch (action) {
                    case 0:
                        quit = true;
                        break;
                    case 1:
                        account.showAccountDetails();
                        break;
                    case 2:
                        updateAccountDetails(account);
                        break;
                    case 3:
                        ProductUtils.printAllProducts();
                        break;
                    case 4:
                        addWishListItem(account);
                        break;
                    case 5:
                        removeWishListItem(account);
                        break;
                    case 6:
                        account.printWishList();
                        break;
                    case 7:
                        addShoppingListItem(account);
                        break;
                    case 8:
                        removeShoppingListItem(account);
                        break;
                    case 9:
                        account.printShoppingList();
                        break;
                    case 10:
                        account.checkOut();
                        break;
                    case 11:
                        account.viewPreviousCheckedOut();
                        break;
                    case 12:
                        account.viewPreviousCheckedOut();
                        returnProduct(account);
                        break;
                    default:
                        System.out.println("Optiunea introdusa nu este valida. Introduceti un numar de la 0 la 12.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Input incorect! Introduceti o valoare de la 0 la 12!");
                scanner.nextLine();
            }
        }

    }

    public static Account logIn(){
        String userName;
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti numele de utilizator: ");
        userName = scanner.nextLine().toLowerCase();
        System.out.print("Introduceti parola: ");
        password = scanner.nextLine().toLowerCase();

        return accounts
                .stream()
                .filter(account -> account.getUserName().equals(userName) && account.getPassword().equals(password))
                .findAny()
                .orElse(null);
    }

    public static void createAccount(){
        String userName;
        String password;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti detaliile");
        System.out.print("Utilizator: ");
        userName = scanner.nextLine().toLowerCase();
        System.out.print("Password: ");
        password = scanner.nextLine().toLowerCase();
        Account account = new Account(userName, password);
        if(accounts.contains(account)){
            System.out.println("Cont existent!");
        }
        accounts.add(account);
    }

    public static void updateAccountDetails(Account account){
        String firstName;
        String lastName;
        String email;
        String address;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti detaliile");
        System.out.print("Prenume: ");
        firstName = scanner.nextLine();
        System.out.print("Nume: ");
        lastName = scanner.nextLine();
        System.out.print("Email: ");
        email = scanner.nextLine();
        System.out.print("Adresa: ");
        address = scanner.nextLine();

        account.modifyAccountDetails(firstName, lastName, email, address);
    }

    public static void addWishListItem(Account account){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti numele produsului: ");
        account.addToWishList(scanner.nextLine());

    }

    public static void removeWishListItem(Account account){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduceti numele produsului pe care vreti sa-l eliminati: ");
        account.removeWishListItem(scanner.nextLine());

    }

    public static void addShoppingListItem(Account account){
        Scanner scanner = new Scanner(System.in);
        int quantity = 0;
        System.out.print("Introduceti numele produsului: ");
        String name = scanner.nextLine();
        System.out.print("Introduceti cantitatea: ");
        try{
            quantity = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.print("Introduceti in cifre, cantitatea: ");
        }

        account.addToShoppingList(name, quantity);

    }

    public static void removeShoppingListItem(Account account){
        Scanner scanner = new Scanner(System.in);
        int quantity = 0;
        System.out.print("Introduceti numele produsului pe care vreti sa-l eliminati: ");
        String name = scanner.nextLine();
        System.out.print("Introduceti in cifre, cantitatea: ");
        try{
            quantity = scanner.nextInt();
        }catch (InputMismatchException e){
            System.out.print("Introduceti in cifre, cantitatea: ");
        }
        account.removeShoppingListItem(name, quantity);
    }

    public static void returnProduct(Account account){
        Scanner scanner = new Scanner(System.in);
        int orderNo = 0;
        int productPosition = 0;
        int quantity = 0;

        try{
            System.out.print("Introduceti numarul comenzii: ");
            orderNo = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Introduceti numarul produsului pe care vreti sa-l returnati: ");
            productPosition = scanner.nextInt();
            System.out.print("Introduceti in cifre, cantitatea pe care vreti sa o returnati: ");
            scanner.nextLine();
            quantity = scanner.nextInt();
        } catch (InputMismatchException e){
            System.out.print("Introduceti doar cifre! ");
        }


       account.returnProducts(orderNo, productPosition, quantity);
    }

}
