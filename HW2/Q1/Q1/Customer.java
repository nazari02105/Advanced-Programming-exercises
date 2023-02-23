import java.util.ArrayList;

public class Customer {
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    private String name;
    private int id;
    private int balance = 0;
    private int discountCode = -1;

    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
        customers.add(this);
    }

    public static Customer getCustomerById(int id) {
        for (int i = 0; i < customers.size(); ++i) {
            if (customers.get(i).id == id) return customers.get(i);
        }
        return null;
    }

    public void increaseCustomerBalance(int balance) {
        this.balance += balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public int getId() {
        return this.id;
    }

    public void setDiscountCode(int discountCode) {
        this.discountCode = discountCode;
    }

    public int getDiscountCode() {
        return this.discountCode;
    }

    public void decreaseBalance(int balance) {
        this.balance = balance;
    }
}
