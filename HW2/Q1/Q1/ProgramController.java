import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProgramController {
    public static Confectionary confectionary;

    public void run() {
        Scanner myScanner = new Scanner(System.in);
        while (true) {
            String command = new String();
            command = myScanner.nextLine();
            command = command.trim();
            if (getCommandMatcher(command, "^create confectionary$")) {
                confectionary = new Confectionary();
            } else if (getCommandMatcher(command, "^end$")) {
                break;
            } else if (confectionary != null) {
                if (getCommandMatcher(command, "^add customer id (\\d+?) name ([A-Za-z ]+)$")) addCustomer(command);
                else if (getCommandMatcher(command, "^increase balance customer (\\d+?) amount (\\d+?)$"))
                    chargeCustomerBalance(command);
                else if (getCommandMatcher(command, "^add warehouse material ([A-Za-z ]+) amount (\\d+?)$"))
                    addWareHouse(command);
                else if (getCommandMatcher(command, "^increase warehouse material ([A-Za-z ]+) amount (\\d+?)$"))
                    increaseWareHouseMaterial(command);
                else if (getCommandMatcher(command, "^add sweet name ([A-Za-z ]+) price (\\d+?) materials: (.+)$"))
                    addSweet(command);
                else if (getCommandMatcher(command, "^increase sweet ([A-Za-z ]+) amount (\\d+?)$"))
                    increaseSweet(command);
                else if (getCommandMatcher(command, "^add discount code (\\d+?) price (\\d+?)$")) addDiscount(command);
                else if (getCommandMatcher(command, "^add discount code code (\\d+?) to customer id (\\d+?)$"))
                    addDiscountToCustomer(command);
                else if (getCommandMatcher(command, "^sell sweet ([A-Za-z ]+) amount (\\d+?) to customer (\\d+?)$"))
                    sellSweet(command);
                else if (getCommandMatcher(command, "^accept transaction (\\d+?)$")) acceptTransaction(command);
                else if (getCommandMatcher(command, "^print transactions list$")) printTransaction();
                else if (getCommandMatcher(command, "^print income$")) getIncome();
                else System.out.println("invalid command");
            } else {
                System.out.println("invalid command");
            }
        }
    }

    //-------------------------------------------------
    private boolean getCommandMatcher(String input, String regex) {
        Pattern commandPattern = Pattern.compile(regex);
        Matcher commandMatcher = commandPattern.matcher(input);
        if (commandMatcher.find()) return true;
        else return false;
    }

    //-------------------------------------------------
    private void addCustomer(String command) {
        Pattern addCustomerPattern = Pattern.compile("^add customer id (\\d+?) name ([A-Za-z ]+)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        int id = Integer.parseInt(addCustomerMatcher.group(1));
        String name = addCustomerMatcher.group(2);
        if (Customer.getCustomerById(id) == null) {
            Customer newCustomer = new Customer(name, id);
        } else System.out.println("customer with this id already exists");
    }

    //-------------------------------------------------
    private void chargeCustomerBalance(String command) {
        Pattern addCustomerPattern = Pattern.compile("^increase balance customer (\\d+?) amount (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        int id = Integer.parseInt(addCustomerMatcher.group(1));
        int balance = Integer.parseInt(addCustomerMatcher.group(2));
        Customer customerToCharge = Customer.getCustomerById(id);
        if (customerToCharge == null) System.out.println("customer not found");
        else {
            customerToCharge.increaseCustomerBalance(balance);
        }
    }

    //-------------------------------------------------
    private void addWareHouse(String command) {
        Pattern addCustomerPattern = Pattern.compile("^add warehouse material ([A-Za-z ]+) amount (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        String materialName = addCustomerMatcher.group(1);
        int amount = Integer.parseInt(addCustomerMatcher.group(2));
        if (WareHouse.getWareHouseByName(materialName) == null) {
            WareHouse newWareHouse = new WareHouse(materialName, amount);
        } else System.out.println("warehouse having this material already exists");
    }

    //-------------------------------------------------
    private void increaseWareHouseMaterial(String command) {
        Pattern addCustomerPattern = Pattern.compile("^increase warehouse material ([A-Za-z ]+) amount (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        String materialName = addCustomerMatcher.group(1);
        int amount = Integer.parseInt(addCustomerMatcher.group(2));
        WareHouse forIncreaseWareHouse = WareHouse.getWareHouseByName(materialName);
        if (forIncreaseWareHouse == null) System.out.println("warehouse not found");
        else {
            forIncreaseWareHouse.increaseMaterial(amount);
        }
    }

    //-------------------------------------------------
    private void addSweet(String command) {
        Pattern addCustomerPattern = Pattern.compile("^add sweet name ([A-Za-z ]+) price (\\d+?) materials: (.+)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        String name = addCustomerMatcher.group(1);
        int price = Integer.parseInt(addCustomerMatcher.group(2));
        String materials = addCustomerMatcher.group(3);
        String[] allMaterials = materials.split(",");
        ArrayList<String> seperateMaterial = new ArrayList<>();
        ArrayList<String> seperateAmount = new ArrayList<>();
        for (int i = 0; i < allMaterials.length; ++i) {
//            seperateMaterial.add(allMaterials[i].trim().split(" ")[0]);
//            seperateAmount.add(allMaterials[i].trim().split(" ")[1]);
            String everyTime = allMaterials[i].trim();
            Pattern everyTimePattern = Pattern.compile("^([A-Za-z ]+) (\\d+)$");
            Matcher everyTimeMatcher = everyTimePattern.matcher(everyTime);
            if (everyTimeMatcher.find()) {
                seperateMaterial.add(everyTimeMatcher.group(1));
                seperateAmount.add(everyTimeMatcher.group(2));
            } else {
                System.out.println("invalid command");
                return;
            }
        }

        ArrayList<String> notExist = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < seperateMaterial.size(); ++i) {
            if (WareHouse.getWareHouseByName(seperateMaterial.get(i)) != null) {
                counter += 1;
            } else {
                notExist.add(seperateMaterial.get(i));
            }
        }

        if (counter == seperateMaterial.size()) {
            HashMap<String, Integer> materialAmount = new HashMap<>();
            for (int i = 0; i < seperateAmount.size(); ++i) {
                materialAmount.put(seperateMaterial.get(i), Integer.parseInt(seperateAmount.get(i)));
            }
            Sweet newSweet = new Sweet(name, price, materialAmount);
        } else {
            System.out.print("not found warehouse(s): ");
            for (int i = 0; i < notExist.size(); ++i) {
                if (i != notExist.size() - 1) {
                    System.out.print(notExist.get(i) + " ");
                } else {
                    System.out.println(notExist.get(i));
                }
            }
        }
    }

    //-------------------------------------------------
    private void increaseSweet(String command) {
        Pattern addCustomerPattern = Pattern.compile("^increase sweet ([A-Za-z ]+) amount (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        String name = addCustomerMatcher.group(1);
        int amount = Integer.parseInt(addCustomerMatcher.group(2));
        Sweet mySweet = Sweet.getSweetByName(name);

        if (mySweet == null) System.out.println("sweet not found");
        else {
            int counter = 0;
            ArrayList<String> notEnough = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : mySweet.getMaterials().entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();
                int amountInWareHouse = WareHouse.getWareHouseByName(key).getAmount();
                if (value * amount <= amountInWareHouse) counter += 1;
                else notEnough.add(key);
            }

            if (counter < mySweet.getMaterials().size()) {
                System.out.print("insufficient material(s): ");
                for (int j = 0; j < notEnough.size(); ++j) {
                    if (j == notEnough.size() - 1) System.out.println(notEnough.get(j));
                    else System.out.print(notEnough.get(j) + " ");
                }
            } else {
                mySweet.increaseSweet(amount);
                for (Map.Entry<String, Integer> entry : mySweet.getMaterials().entrySet()) {
                    String key = entry.getKey();
                    int value = entry.getValue();
                    int shouldDecrease = value * amount;
                    WareHouse myWareHouse = WareHouse.getWareHouseByName(key);
                    myWareHouse.decreaseMaterial(shouldDecrease);
                }
            }
        }
    }

    //-------------------------------------------------
    private void addDiscount(String command) {
        Pattern addCustomerPattern = Pattern.compile("^add discount code (\\d+?) price (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        int code = Integer.parseInt(addCustomerMatcher.group(1));
        int price = Integer.parseInt(addCustomerMatcher.group(2));
        if (confectionary.getDiscountPriceByCode(code) == -1) confectionary.addDiscount(code, price);
        else System.out.println("discount with this code already exists");
    }

    //-------------------------------------------------
    private void addDiscountToCustomer(String command) {
        Pattern addCustomerPattern = Pattern.compile("^add discount code code (\\d+?) to customer id (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        int code = Integer.parseInt(addCustomerMatcher.group(1));
        int customerId = Integer.parseInt(addCustomerMatcher.group(2));

        if (!confectionary.isDiscountExists(code)) System.out.println("discount code not found");
        else {
            if (Customer.getCustomerById(customerId) == null) System.out.println("customer not found");
            else {
                Customer myCustomer = Customer.getCustomerById(customerId);
                myCustomer.setDiscountCode(code);
            }
        }
    }

    //-------------------------------------------------
    private void sellSweet(String command) {
        Pattern addCustomerPattern = Pattern.compile("^sell sweet ([A-Za-z ]+) amount (\\d+?) to customer (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        String sweetName = addCustomerMatcher.group(1);
        int amount = Integer.parseInt(addCustomerMatcher.group(2));
        int customerId = Integer.parseInt(addCustomerMatcher.group(3));


        if (Sweet.getSweetByName(sweetName) == null) System.out.println("sweet not found");
        else {
            if (Sweet.getSweetByName(sweetName).getAmount() < amount) System.out.println("insufficient sweet");
            else {
                if (Customer.getCustomerById(customerId) == null) System.out.println("customer not found");
                else {
                    int discountCose = Customer.getCustomerById(customerId).getDiscountCode();
                    int discountPrice = 0;
                    if (discountCose != -1) discountPrice = confectionary.getDiscountPriceByCode(discountCose);
                    if (Customer.getCustomerById(customerId).getBalance() < Sweet.getSweetByName(sweetName).getPrice() * amount - discountPrice)
                        System.out.println("customer has not enough money");
                    else {
                        Sweet.getSweetByName(sweetName).decreaseSweet(amount);
                        if (Sweet.getSweetByName(sweetName).getPrice() * amount >= discountPrice) {
                            Transaction newTransaction = new Transaction(customerId, Sweet.getSweetByName(sweetName).getPrice() * amount - discountPrice, discountCose);
                            newTransaction.tedad = amount;
                            newTransaction.firstPrice = Sweet.getSweetByName(sweetName).getPrice() * amount;
                            System.out.println("transaction " + newTransaction.getId() + " successfully created");
                            Customer.getCustomerById(customerId).setDiscountCode(-1);
                        } else {
                            Transaction newTransaction = new Transaction(customerId, 0, discountCose);
                            newTransaction.tedad = amount;
                            newTransaction.firstPrice = Sweet.getSweetByName(sweetName).getPrice() * amount;
                            System.out.println("transaction " + newTransaction.getId() + " successfully created");
                            Customer.getCustomerById(customerId).setDiscountCode(-1);
                        }
                    }
                }
            }
        }
    }

    //-------------------------------------------------
    private void acceptTransaction(String command) {
        Pattern addCustomerPattern = Pattern.compile("^accept transaction (\\d+?)$");
        Matcher addCustomerMatcher = addCustomerPattern.matcher(command);
        addCustomerMatcher.find();
        int transactionId = Integer.parseInt(addCustomerMatcher.group(1));

        Transaction myTransaction = Transaction.getTransactionById(transactionId);
        if (myTransaction == null || myTransaction.isTrancactionAccepted())
            System.out.println("no waiting transaction with this id was found");
        else {
            int firstPrice = myTransaction.firstPrice;
            int customerId = myTransaction.getCustomerId();
            Customer myCustomer = Customer.getCustomerById(customerId);
            int discountCode = myTransaction.getDiscountCode();
            int discountPrice = 0;
            if (discountCode != -1) discountPrice = confectionary.getDiscountPriceByCode(discountCode);
            if (firstPrice >= discountPrice) {
                myCustomer.decreaseBalance(myTransaction.getAmount());
                confectionary.increaseBalance(myTransaction.getAmount());
                myTransaction.setAccepted(true);
            }
            if (firstPrice < discountPrice) {
                myTransaction.setAccepted(true);
            }
        }
    }

    //-------------------------------------------------
    private void printTransaction() {
        ArrayList<Transaction> allTransactions = Transaction.getTransactiions();
        for (int i = 0; i < allTransactions.size(); ++i) {
            if (allTransactions.get(i).isTrancactionAccepted()) {
                System.out.print("transaction ");
                System.out.print(allTransactions.get(i).getId() + ": ");
                System.out.print(allTransactions.get(i).getCustomerId() + " ");
                System.out.print(allTransactions.get(i).firstPrice + " ");
                System.out.print(allTransactions.get(i).getDiscountCode() + " ");
                System.out.println(allTransactions.get(i).getAmount());

            }
        }
    }

    //-------------------------------------------------
    private void getIncome() {
        System.out.println(confectionary.getBalance());
    }
    //-------------------------------------------------
}
