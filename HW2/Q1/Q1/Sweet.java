import java.util.ArrayList;
import java.util.HashMap;

public class Sweet {
    private String name;
    private int price;
    private int amount = 0;
    private HashMap<String, Integer> materials = new HashMap<String, Integer>();
    private static ArrayList<Sweet> sweets = new ArrayList<Sweet>();


    public Sweet(String name, int price, HashMap<String, Integer> materials) {
        this.name = name;
        this.price = price;
        this.materials = materials;
        sweets.add(this);
    }

    public HashMap<String, Integer> getMaterials() {
        return this.materials;
    }

    public String getName() {
        return this.name;
    }

    public void increaseSweet(int amount) {
        this.amount += amount;
    }

    public void decreaseSweet(int amount) {
        this.amount -= amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return this.price;
    }

    public void decreaseMaterialOfSweetFromWareHouse(int amount) {
        //dont forget to complete this section
    }

    public static Sweet getSweetByName(String name) {
        for (int i = 0; i < sweets.size(); ++i) {
            if (sweets.get(i).name.equals(name)) {
                return sweets.get(i);
            }
        }
        return null;
    }
}
