import java.util.ArrayList;

public class Transaction {
    private static int idCounter = 0;
    private int id;
    private int customerId;
    private int amount;
    private int discountCode;
    private int diCountPrice;
    private int finalPayment;
    public int firstPrice;
    public int tedad;
    private boolean isAccepted;
    private static ArrayList<Transaction> transactions = new ArrayList<>();

    public Transaction(int customerId, int amount, int discountCode) {
        this.customerId = customerId;
        this.amount = amount;
        this.discountCode = discountCode;
        transactions.add(this);
        idCounter += 1;
        this.id = idCounter;
    }

    public int getId() {
        return this.id;
    }

    public void setAccepted(boolean accepted) {
        this.isAccepted = accepted;
    }

    public void exchangeMoney() {
        //dont forget to complete this section
    }

    public boolean isTrancactionAccepted() {
        return this.isAccepted;
    }

    public int getDiscountCode() {
        return this.discountCode;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public int getAmount() {
        return amount;
    }

    public int getFinalPayment() {
        return finalPayment;
    }

    public static Transaction getTransactionById(int id) {
        for (int i = 0; i < transactions.size(); ++i) {
            if (transactions.get(i).id == id) return transactions.get(i);
        }
        return null;
    }

    public static ArrayList<Transaction> getTransactiions() {
        return transactions;
    }
}
