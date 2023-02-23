import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private String ownerOfGrid;
    private char[][] theGrid = new char[10][31];
    private int sheep1 = 4;
    private int sheep2 = 3;
    private int sheep3 = 2;
    private int sheep4 = 1;
    private int mine = 0;
    private int airPlane = 0;
    private int scanner = 0;
    private int antiAirCraft = 0;
    private int invisible = 0;
    private int numberOfSheeps = 20;
    public static HashMap<String, Grid> allUsersGrid = new HashMap<>();

    //
    public static ArrayList<ArrayList<String>> secondSheep = new ArrayList<>();
    public static ArrayList<ArrayList<String>> secondSheephelp = new ArrayList<>();
    public static int secondIndex = 0;
    public static ArrayList<ArrayList<String>> thirdSheep = new ArrayList<>();
    public static ArrayList<ArrayList<String>> thirdSheephelp = new ArrayList<>();
    public static int thirdIndex = 0;
    public static ArrayList<ArrayList<String>> forthSheep = new ArrayList<>();
    public static ArrayList<ArrayList<String>> forthSheephelp = new ArrayList<>();
    public static int forthIndex = 0;
    public static ArrayList<String> allAntiAirCrafts = new ArrayList<>();
    //


    public Grid(String userName) {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 31; ++j) {
                if (j % 3 == 0) {
                    theGrid[i][j] = '|';
                } else {
                    theGrid[i][j] = ' ';
                }
            }
        }
        this.ownerOfGrid = userName;
        allUsersGrid.put(userName, this);
    }

    public void emptyGrid() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 31; ++j) {
                if (j % 3 == 0) {
                    theGrid[i][j] = '|';
                } else {
                    theGrid[i][j] = ' ';
                }
            }
        }
        this.sheep1 = 4;
        this.sheep2 = 3;
        this.sheep3 = 2;
        this.sheep4 = 1;
        this.numberOfSheeps = 20;
        secondSheep = new ArrayList<>();
        secondSheephelp = new ArrayList<>();
        thirdSheep = new ArrayList<>();
        thirdSheephelp = new ArrayList<>();
        forthSheep = new ArrayList<>();
        forthSheephelp = new ArrayList<>();
        secondIndex = 0;
        thirdIndex = 0;
        forthIndex = 0;
        allAntiAirCrafts = new ArrayList<>();
    }


    public int getSheep1() {
        return sheep1;
    }

    public int getSheep2() {
        return sheep2;
    }

    public int getSheep3() {
        return sheep3;
    }

    public int getSheep4() {
        return sheep4;
    }

    public int getMine() {
        return mine;
    }

    public int getAntiAirCraft() {
        return antiAirCraft;
    }

    public int getInvisible() {
        return invisible;
    }

    public char[][] getTheGrid() {
        return theGrid;
    }

    public String getOwnerOfGrid() {
        return ownerOfGrid;
    }

    public void decreaseS1() {
        this.sheep1 -= 1;
    }

    public void decreaseS2() {
        this.sheep2 -= 1;
    }

    public void decreaseS3() {
        this.sheep3 -= 1;
    }

    public void decreaseS4() {
        this.sheep4 -= 1;
    }

    public void decreaseMine() {
        this.mine -= 1;
    }

    public void decreaseAntiAirCraft() {
        this.antiAirCraft -= 1;
    }

    public void decreaseInvisiable() {
        this.invisible -= 1;
    }

    public void decreaseScanner() {
        this.scanner -= 1;
    }


    public Grid getGreedByUserName(String userName) {
        return allUsersGrid.get(userName);
    }

    public void increaseMine(int number) {
        this.mine += number;
    }

    public void increaseAirPlane(int number) {
        this.airPlane += number;
    }

    public void increaseAntiAirCraft(int number) {
        this.antiAirCraft += number;
    }

    public void increaseScanner(int number) {
        this.scanner += number;
    }

    public void increaseInvisable(int number) {
        this.invisible += number;
    }

    public void setNumberOfSheeps(int numberOfSheeps) {
        this.numberOfSheeps = numberOfSheeps;
    }

    public int getNumberOfSheeps() {
        return numberOfSheeps;
    }

    public void decreaseNumberOfSheeps() {
        this.numberOfSheeps -= 1;
    }

    public int getAirPlane() {
        return airPlane;
    }

    public int getScanner() {
        return scanner;
    }

    public void decreaseAirPlane() {
        this.airPlane -= 1;
    }
}
