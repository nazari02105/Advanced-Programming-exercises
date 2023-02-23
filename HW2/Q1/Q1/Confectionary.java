import java.util.HashMap;

public class Confectionary {
    private int balance = 0;
    private HashMap<Integer, Integer> discounts = new HashMap<Integer, Integer>();

    public Confectionary() {
        //dont forget to complete this section
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public void increaseBalance(int balance) {
        this.balance += balance;
    }

    public boolean isDiscountExists(int code) {
        if (discounts.containsKey(code)) return true;
        return false;
    }

    public void addDiscount(int code, int price) {
        this.discounts.put(code, price);
    }

    public int getDiscountPriceByCode(int code) {
        if (discounts.containsKey(code)) return discounts.get(code);
        return -1;
    }
}
