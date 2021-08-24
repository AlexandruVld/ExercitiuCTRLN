package ro.java.ctrln.account_utils;

import java.util.Objects;

class AccountDetails {
    private String firstName;
    private String lastName;
    private String email;
    private String address;

    AccountDetails(){
    }

    String getFirstName() {
        return firstName;
    }

    String getLastName(){
        return lastName;
    }

    String getEmail() {
        return email;
    }

    String getAddress() {
        return address;
    }


    void modifyFirstName(String newFirstName){
        this.firstName = newFirstName;
        System.out.println("Prenume: " + newFirstName);
    }

    void modifyLastName(String newLastName){
        this.lastName = newLastName;
        System.out.println("Nume: " + newLastName);
    }

    void modifyEmail(String newEmail){
        this.email = newEmail;
        System.out.println("Email: " + newEmail);
    }

    void modifyAddress(String newAddress){
        this.address = newAddress;
        System.out.println("Adresa: " + newAddress);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDetails user = (AccountDetails) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && address.equals(user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, address);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
