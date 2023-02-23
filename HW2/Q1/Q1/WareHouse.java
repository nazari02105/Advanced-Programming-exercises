import java.util.ArrayList;

public class WareHouse {
    private int amount = 0;
    private String materialName;
    private static ArrayList<WareHouse> wareHouses = new ArrayList<WareHouse>();

    public WareHouse(String materialName, int amount) {
        this.materialName = materialName;
        this.amount = amount;
        wareHouses.add(this);
    }

    public void increaseMaterial(int amount) {
        this.amount += amount;
    }

    public String getName() {
        return this.materialName;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public void decreaseMaterial(int amount) {
        this.amount -= amount;
    }

    public static WareHouse getWareHouseByName(String name) {
        for (int i = 0; i < wareHouses.size(); ++i) {
            if (wareHouses.get(i).getName().equals(name)) {
                return wareHouses.get(i);
            }
        }
        return null;
    }
}
