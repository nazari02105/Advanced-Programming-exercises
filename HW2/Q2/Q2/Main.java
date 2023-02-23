import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        signUpCommand(scanner);
    }

    //----------------------------------------------------------------
    public static void signUpCommand(Scanner scanner) {
        String command;
        while (true) {
            command = scanner.nextLine();
            if (patternChecker(command, "^register ([^ ]+?) ([^ ]+?)$")) signUp(command);
            else if (patternChecker(command, "^login ([^ ]+?) ([^ ]+?)$")) logIn(command, scanner);
            else if (patternChecker(command, "^remove ([^ ]+?) ([^ ]+?)$")) remove(command);
            else if (patternChecker(command, "^list_users$")) listUsers();
            else if (patternChecker(command, "^help$")) help();
            else if (patternChecker(command, "^exit$")) {
                System.out.println("program ended");
                System.exit(0);
            } else System.out.println("invalid command");
        }
    }

    //----------------------------------------------------------------
    public static boolean patternChecker(String input, String regex) {
        Pattern myPattern = Pattern.compile(regex);
        Matcher myMatcher = myPattern.matcher(input);
        return myMatcher.find();
    }

    //----------------------------------------------------------------
    public static void signUp(String command) {
        Pattern myPattern = Pattern.compile("^register (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String userName = myMatcher.group(1);
        String password = myMatcher.group(2);
        Pattern myPattern2 = Pattern.compile("^([A-Za-z0-9_]+)$");
        Matcher myMatcherForUserName = myPattern2.matcher(userName);
        Matcher myMatcherForPassword = myPattern2.matcher(password);
        if (!myMatcherForUserName.find()) System.out.println("username format is invalid");
        else {
            if (!myMatcherForPassword.find()) System.out.println("password format is invalid");
            else {
                if (UserName.userExists(userName)) System.out.println("a user exists with this username");
                else {
                    UserName newUSerName = new UserName(userName, password);
                    Grid newGrid = new Grid(userName);
                    System.out.println("register successful");
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void logIn(String command, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^login (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String userName = myMatcher.group(1);
        String password = myMatcher.group(2);
        Pattern myPattern2 = Pattern.compile("^([A-Za-z0-9_]+)$");
        Matcher myMatcherForUserName = myPattern2.matcher(userName);
        Matcher myMatcherForPassword = myPattern2.matcher(password);
        if (!myMatcherForUserName.find()) System.out.println("username format is invalid");
        else {
            if (!myMatcherForPassword.find()) System.out.println("password format is invalid");
            else {
                if (!UserName.userExists(userName)) System.out.println("no user exists with this username");
                else {
                    if (!UserName.isPasswordTrue(userName, password)) System.out.println("incorrect password");
                    else {
                        System.out.println("login successful");
                        String commandInLogIn;
                        while (true) {
                            commandInLogIn = scanner.nextLine();
                            commandInLogIn = commandInLogIn.trim();
                            if (patternChecker(commandInLogIn, "^new_game ([^ ]+?)$"))
                                newGame(commandInLogIn, userName, scanner);
                            else if (patternChecker(commandInLogIn, "^scoreboard$")) scoreBoard();
                            else if (patternChecker(commandInLogIn, "^shop$")) shop(userName, password, scanner);
                            else if (patternChecker(commandInLogIn, "^help$")) helpForLogIn();
                            else if (patternChecker(commandInLogIn, "^list_users$")) listUsers();
                            else if (patternChecker(commandInLogIn, "^logout$")) {
                                System.out.println("logout successful");
                                break;
                            } else System.out.println("invalid command");
                        }
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void logInAfterShop(String userName, String password, Scanner scanner) {
        String commandInLogIn;
        while (true) {
            commandInLogIn = scanner.nextLine();
            if (patternChecker(commandInLogIn, "^new_game ([^ ]+?)$")) newGame(commandInLogIn, userName, scanner);
            else if (patternChecker(commandInLogIn, "^scoreboard$")) scoreBoard();
            else if (patternChecker(commandInLogIn, "^shop$")) shop(userName, password, scanner);
            else if (patternChecker(commandInLogIn, "^help$")) helpForLogIn();
            else if (patternChecker(commandInLogIn, "^list_users$")) listUsers();
            else if (patternChecker(commandInLogIn, "^logout$")) {
                System.out.println("logout successful");
                signUpCommand(scanner);
            } else System.out.println("invalid command");
        }
    }

    //----------------------------------------------------------------
    public static void remove(String command) {
        Pattern myPattern = Pattern.compile("^remove (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String userName = myMatcher.group(1);
        String password = myMatcher.group(2);
        Pattern myPattern2 = Pattern.compile("^([A-Za-z0-9_]+)$");
        Matcher myMatcherForUserName = myPattern2.matcher(userName);
        Matcher myMatcherForPassword = myPattern2.matcher(password);
        if (!myMatcherForUserName.find()) System.out.println("username format is invalid");
        else {
            if (!myMatcherForPassword.find()) System.out.println("password format is invalid");
            else {
                if (!UserName.userExists(userName)) System.out.println("no user exists with this username");
                else {
                    if (!UserName.isPasswordTrue(userName, password)) System.out.println("incorrect password");
                    else {
                        System.out.println("removed " + userName + " successfully");
                        UserName.allUsers.remove(userName);
                        UserName userNameToRemove = UserName.getObjectByUserName(userName);
                        UserName.allUsersObject.remove(userNameToRemove);
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void listUsers() {
        String[] stringArray = new String[UserName.allUsers.size()];
        int counter = 0;
        for (Map.Entry<String, String> entry : UserName.allUsers.entrySet()) {
            String key = entry.getKey();
            stringArray[counter] = key;
            counter += 1;
        }
        for (int i = 0; i < stringArray.length; i++) {
            for (int j = i + 1; j < stringArray.length; j++) {
                if (stringArray[i].compareTo(stringArray[j]) > 0) {
                    String temp = stringArray[i];
                    stringArray[i] = stringArray[j];
                    stringArray[j] = temp;
                }
            }
        }
        // for (int i = 0; i < stringArray.length; i++) {
        //     for (int j = i + 1; j < stringArray.length; j++) {
        //         if (stringArray[i].compareToIgnoreCase(stringArray[j]) == 0 && stringArray[i].compareTo(stringArray[j]) > 0) {
        //             String temp = stringArray[i];
        //             stringArray[i] = stringArray[j];
        //             stringArray[j] = temp;
        //         }
        //     }
        // }
        for (String string : stringArray)
            System.out.println(string);
    }

    //----------------------------------------------------------------
    public static void help() {
        System.out.println("register [username] [password]");
        System.out.println("login [username] [password]");
        System.out.println("remove [username] [password]");
        System.out.println("list_users");
        System.out.println("help");
        System.out.println("exit");
    }

    //----------------------------------------------------------------
    public static void newGame(String command, String userNameWhomStart, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^new_game (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String opponent = myMatcher.group(1);
        Pattern myPattern2 = Pattern.compile("^([A-Za-z0-9_]+)$");
        Matcher myMatcherForUserName = myPattern2.matcher(opponent);
        if (!myMatcherForUserName.find()) System.out.println("username format is invalid");
        else {
            if (opponent.equals(userNameWhomStart))
                System.out.println("you must choose another player to start a game");
            else {
                if (!UserName.userExists(opponent)) System.out.println("no user exists with this username");
                else {
                    System.out.println("new game started successfully between " + userNameWhomStart + " and " + opponent);
                    arragingBoard(userNameWhomStart, opponent, scanner);
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void scoreBoard() {
        ArrayList<UserName> myTempArrayList = UserName.allUsersObject;


        for (int i = 0; i < myTempArrayList.size(); ++i) {
            for (int j = 0; j < myTempArrayList.size() - 1; ++j) {
                if (myTempArrayList.get(j).getScore() < myTempArrayList.get(j + 1).getScore()) {
                    UserName temp = myTempArrayList.get(j);
                    myTempArrayList.set(j, myTempArrayList.get(j + 1));
                    myTempArrayList.set(j + 1, temp);
                }
            }
        }

        for (int i = 0; i < myTempArrayList.size(); ++i) {
            for (int j = 0; j < myTempArrayList.size() - 1; ++j) {
                if (myTempArrayList.get(j).getScore() == myTempArrayList.get(j + 1).getScore()
                        && myTempArrayList.get(j).getWins() < myTempArrayList.get(j + 1).getWins()) {
                    UserName temp = myTempArrayList.get(j);
                    myTempArrayList.set(j, myTempArrayList.get(j + 1));
                    myTempArrayList.set(j + 1, temp);
                }
            }
        }

        for (int i = 0; i < myTempArrayList.size(); ++i) {
            for (int j = 0; j < myTempArrayList.size() - 1; ++j) {
                if (myTempArrayList.get(j).getScore() == myTempArrayList.get(j + 1).getScore()
                        && myTempArrayList.get(j).getWins() == myTempArrayList.get(j + 1).getWins()
                        && myTempArrayList.get(j).getDraws() < myTempArrayList.get(j + 1).getDraws()) {
                    UserName temp = myTempArrayList.get(j);
                    myTempArrayList.set(j, myTempArrayList.get(j + 1));
                    myTempArrayList.set(j + 1, temp);
                }
            }
        }

        for (int i = 0; i < myTempArrayList.size(); ++i) {
            for (int j = 0; j < myTempArrayList.size() - 1; ++j) {
                if (myTempArrayList.get(j).getScore() == myTempArrayList.get(j + 1).getScore()
                        && myTempArrayList.get(j).getWins() == myTempArrayList.get(j + 1).getWins()
                        && myTempArrayList.get(j).getDraws() == myTempArrayList.get(j + 1).getDraws()
                        && myTempArrayList.get(j).getLoses() > myTempArrayList.get(j + 1).getLoses()) {
                    UserName temp = myTempArrayList.get(j);
                    myTempArrayList.set(j, myTempArrayList.get(j + 1));
                    myTempArrayList.set(j + 1, temp);
                }
            }
        }

        for (int i = 0; i < myTempArrayList.size(); ++i) {
            for (int j = 0; j < myTempArrayList.size() - 1; ++j) {
                if (myTempArrayList.get(j).getScore() == myTempArrayList.get(j + 1).getScore()
                        && myTempArrayList.get(j).getWins() == myTempArrayList.get(j + 1).getWins()
                        && myTempArrayList.get(j).getDraws() == myTempArrayList.get(j + 1).getDraws()
                        && myTempArrayList.get(j).getLoses() == myTempArrayList.get(j + 1).getLoses()
                        && myTempArrayList.get(j).getUserName().compareToIgnoreCase(myTempArrayList.get(j + 1).getUserName()) > 0) {
                    UserName temp = myTempArrayList.get(j);
                    myTempArrayList.set(j, myTempArrayList.get(j + 1));
                    myTempArrayList.set(j + 1, temp);
                }
            }
        }

        for (int i = 0; i < myTempArrayList.size(); ++i) {
            System.out.print(myTempArrayList.get(i).getUserName() + " ");
            System.out.print(myTempArrayList.get(i).getScore() + " ");
            System.out.print(myTempArrayList.get(i).getWins() + " ");
            System.out.print(myTempArrayList.get(i).getDraws() + " ");
            System.out.println(myTempArrayList.get(i).getLoses());
        }
    }

    //----------------------------------------------------------------
    public static void helpForLogIn() {
        System.out.println("new_game [username]");
        System.out.println("scoreboard");
        System.out.println("list_users");
        System.out.println("shop");
        System.out.println("help");
        System.out.println("logout");
    }

    //----------------------------------------------------------------
    public static void arragingBoard(String starter, String oponent, Scanner scanner) {
        Grid starterGrid = Grid.allUsersGrid.get(starter);
        starterGrid.setNumberOfSheeps(20);
        Grid oponentGrid = Grid.allUsersGrid.get(oponent);
        oponentGrid.setNumberOfSheeps(20);
        String command;
        while (true) {
            command = scanner.nextLine();
            if (patternChecker(command, "^put S\\d+? \\d+?,\\d+? -[^ ]$")) puttingSheeps(command, starterGrid);
            else if (patternChecker(command, "^put-mine \\d+?,\\d+?$")) puttingMine(command, starterGrid);
            else if (patternChecker(command, "^put-antiaircraft \\d+? -[^ ]$"))
                puttingAntiAirCraft(command, starterGrid);
            else if (patternChecker(command, "^invisible \\d+?,\\d+?$")) puttingInvisable(command, starterGrid);
            else if (patternChecker(command, "^show-my-board$")) showingBoard(starterGrid);
            else if (patternChecker(command, "^help$")) helpInArranging();
            else if (patternChecker(command, "^finish-arranging$")) {
                if (starterGrid.getSheep1() == 0 && starterGrid.getSheep2() == 0 && starterGrid.getSheep3() == 0 && starterGrid.getSheep4() == 0) {
                    System.out.println("turn completed");
                    Grid.allAntiAirCrafts.add("***");
                    arrangingBoaedForSecondPlayer(scanner, oponentGrid, starterGrid);
                } else {
                    System.out.println("you must put all ships on the board");
                }
            } else System.out.println("invalid command");
        }
    }

    //----------------------------------------------------------------
    public static void arrangingBoaedForSecondPlayer(Scanner scanner, Grid opponentGrid, Grid starterGrid) {
        String command;
        while (true) {
            command = scanner.nextLine();
            if (patternChecker(command, "^put S\\d+? \\d+?,\\d+? -[^ ]$")) puttingSheeps(command, opponentGrid);
            else if (patternChecker(command, "^put-mine \\d+?,\\d+?$")) puttingMine(command, opponentGrid);
            else if (patternChecker(command, "^put-antiaircraft \\d+? -[^ ]$"))
                puttingAntiAirCraft(command, opponentGrid);
            else if (patternChecker(command, "^invisible \\d+?,\\d+?$")) puttingInvisable(command, opponentGrid);
            else if (patternChecker(command, "^show-my-board$")) showingBoard(opponentGrid);
            else if (patternChecker(command, "^help$")) helpInArranging();
            else if (patternChecker(command, "^finish-arranging$")) {
                if (opponentGrid.getSheep1() == 0 && opponentGrid.getSheep2() == 0 && opponentGrid.getSheep3() == 0 && opponentGrid.getSheep4() == 0) {
                    System.out.println("turn completed");
                    gameBattle(starterGrid, opponentGrid, scanner);
                } else {
                    System.out.println("you must put all ships on the board");
                }
            } else System.out.println("invalid command");
        }
    }

    //----------------------------------------------------------------
    public static void puttingSheeps(String command, Grid grid) {
        Pattern myPattern = Pattern.compile("^put S(.+?) (.+?),(.+?) -(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String whichS = myMatcher.group(1);
        String x = myMatcher.group(2);
        String y = myMatcher.group(3);
        String hOrV = myMatcher.group(4);
        if (!whichS.equals("1") && !whichS.equals("2") && !whichS.equals("3") && !whichS.equals("4"))
            System.out.println("invalid ship number");
        else {
            if (Integer.parseInt(x) > 10 || Integer.parseInt(x) < 1 || Integer.parseInt(y) > 10 || Integer.parseInt(y) < 1)
                System.out.println("wrong coordination");
            else {
                if (!hOrV.equals("h") && !hOrV.equals("v")) System.out.println("invalid direction");
                else {
                    if (hOrV.equals("h")) {
                        if (Integer.parseInt(x) + Integer.parseInt(whichS) - 1 > 10)
                            System.out.println("off the board");
                        else {
                            if (whichS.equals("1") && grid.getSheep1() == 0)
                                System.out.println("you don't have this type of ship");
                            else if (whichS.equals("2") && grid.getSheep2() == 0)
                                System.out.println("you don't have this type of ship");
                            else if (whichS.equals("3") && grid.getSheep3() == 0)
                                System.out.println("you don't have this type of ship");
                            else if (whichS.equals("4") && grid.getSheep4() == 0)
                                System.out.println("you don't have this type of ship");
                            else {
                                if (!isEmpty(whichS, x, y, hOrV, grid))
                                    System.out.println("collision with the other ship or mine on the board");
                                else {
                                    drawingOnGrid(whichS, x, y, hOrV, grid);
                                }
                            }
                        }
                    } else {
                        if (Integer.parseInt(y) + Integer.parseInt(whichS) - 1 > 10)
                            System.out.println("off the board");
                        else {
                            if (whichS.equals("1") && grid.getSheep1() == 0)
                                System.out.println("you don't have this type of ship");
                            else if (whichS.equals("2") && grid.getSheep2() == 0)
                                System.out.println("you don't have this type of ship");
                            else if (whichS.equals("3") && grid.getSheep3() == 0)
                                System.out.println("you don't have this type of ship");
                            else if (whichS.equals("4") && grid.getSheep4() == 0)
                                System.out.println("you don't have this type of ship");
                            else {
                                if (!isEmpty(whichS, x, y, hOrV, grid))
                                    System.out.println("collision with the other ship or mine on the board");
                                else {
                                    drawingOnGrid(whichS, x, y, hOrV, grid);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static boolean isEmpty(String whichS, String x, String y, String hOrV, Grid grid) {


        if (hOrV.equals("h")) {
            int counter = 0;
            for (int i = (Integer.parseInt(x) - 1) * 3; i < (Integer.parseInt(x) - 1) * 3 + Integer.parseInt(whichS) * 3; ++i) {
                if (i % 3 != 0)
                    if (grid.getTheGrid()[Integer.parseInt(y) - 1][i] == ' ' || grid.getTheGrid()[Integer.parseInt(y) - 1][i] == 'A')
                        counter += 1;
            }
            if (counter == Integer.parseInt(whichS) * 2) return true;
        }


        if (hOrV.equals("v")) {
            int counter = 0;
            for (int i = Integer.parseInt(y) - 1; i < Integer.parseInt(y) - 1 + Integer.parseInt(whichS); ++i) {
                if (grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 1] == ' ' && grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 2] == ' ')
                    counter += 1;
                else if (grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 1] == 'A' && grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 2] == 'A')
                    counter += 1;
            }
            if (counter == Integer.parseInt(whichS)) return true;
        }

        return false;
    }

    //----------------------------------------------------------------
    public static void drawingOnGrid(String whichS, String x, String y, String hOrV, Grid grid) {
        if (whichS.equals("1")) grid.decreaseS1();
        else if (whichS.equals("2")) grid.decreaseS2();
        else if (whichS.equals("3")) grid.decreaseS3();
        else if (whichS.equals("4")) grid.decreaseS4();

        if (hOrV.equals("h")) {
            if (whichS.equals("1")) {
                for (int i = (Integer.parseInt(x) - 1) * 3; i < (Integer.parseInt(x) - 1) * 3 + Integer.parseInt(whichS) * 3; ++i) {
                    if (i % 3 == 1) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = 'S';
                    if (i % 3 == 2) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = '1';
                }
            }
            if (whichS.equals("2")) {
                for (int i = (Integer.parseInt(x) - 1) * 3; i < (Integer.parseInt(x) - 1) * 3 + Integer.parseInt(whichS) * 3; ++i) {
                    if (i % 3 == 1) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = 'S';
                    if (i % 3 == 2) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = '2';
                }
                //
                Grid.secondSheep.add(new ArrayList<String>());
                Grid.secondSheephelp.add(new ArrayList<String>());
                for (int i = Integer.parseInt(x); i < Integer.parseInt(x) + 2; ++i) {
                    Grid.secondSheep.get(Grid.secondIndex).add("(" + i + "," + y + ")");
                    Grid.secondSheephelp.get(Grid.secondIndex).add("(" + i + "," + y + ")");
                }
                Grid.secondIndex += 1;
                //
            }
            if (whichS.equals("3")) {
                for (int i = (Integer.parseInt(x) - 1) * 3; i < (Integer.parseInt(x) - 1) * 3 + Integer.parseInt(whichS) * 3; ++i) {
                    if (i % 3 == 1) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = 'S';
                    if (i % 3 == 2) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = '3';
                }
                //
                Grid.thirdSheep.add(new ArrayList<String>());
                Grid.thirdSheephelp.add(new ArrayList<String>());
                for (int i = Integer.parseInt(x); i < Integer.parseInt(x) + 3; ++i) {
                    Grid.thirdSheep.get(Grid.thirdIndex).add("(" + i + "," + y + ")");
                    Grid.thirdSheephelp.get(Grid.thirdIndex).add("(" + i + "," + y + ")");
                }
                Grid.thirdIndex += 1;
                //
            }
            if (whichS.equals("4")) {
                for (int i = (Integer.parseInt(x) - 1) * 3; i < (Integer.parseInt(x) - 1) * 3 + Integer.parseInt(whichS) * 3; ++i) {
                    if (i % 3 == 1) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = 'S';
                    if (i % 3 == 2) grid.getTheGrid()[Integer.parseInt(y) - 1][i] = '4';
                }
                //
                Grid.forthSheep.add(new ArrayList<String>());
                Grid.forthSheephelp.add(new ArrayList<String>());
                for (int i = Integer.parseInt(x); i < Integer.parseInt(x) + 4; ++i) {
                    Grid.forthSheep.get(Grid.forthIndex).add("(" + i + "," + y + ")");
                    Grid.forthSheephelp.get(Grid.forthIndex).add("(" + i + "," + y + ")");
                }
                Grid.forthIndex += 1;
                //
            }
        } else if (hOrV.equals("v")) {
            if (whichS.equals("1")) {
                for (int i = Integer.parseInt(y) - 1; i < Integer.parseInt(y) - 1 + Integer.parseInt(whichS); ++i) {
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 1] = 'S';
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 2] = '1';
                }
            }
            if (whichS.equals("2")) {
                for (int i = Integer.parseInt(y) - 1; i < Integer.parseInt(y) - 1 + Integer.parseInt(whichS); ++i) {
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 1] = 'S';
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 2] = '2';
                }
                //
                Grid.secondSheep.add(new ArrayList<String>());
                Grid.secondSheephelp.add(new ArrayList<String>());
                for (int i = Integer.parseInt(y); i < Integer.parseInt(y) + 2; ++i) {
                    Grid.secondSheep.get(Grid.secondIndex).add("(" + x + "," + i + ")");
                    Grid.secondSheephelp.get(Grid.secondIndex).add("(" + x + "," + i + ")");
                }
                Grid.secondIndex += 1;
                //
            }
            if (whichS.equals("3")) {
                for (int i = Integer.parseInt(y) - 1; i < Integer.parseInt(y) - 1 + Integer.parseInt(whichS); ++i) {
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 1] = 'S';
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 2] = '3';
                }
                //
                Grid.thirdSheep.add(new ArrayList<String>());
                Grid.thirdSheephelp.add(new ArrayList<String>());
                for (int i = Integer.parseInt(y); i < Integer.parseInt(y) + 3; ++i) {
                    Grid.thirdSheep.get(Grid.thirdIndex).add("(" + x + "," + i + ")");
                    Grid.thirdSheephelp.get(Grid.thirdIndex).add("(" + x + "," + i + ")");
                }
                Grid.thirdIndex += 1;
                //
            }
            if (whichS.equals("4")) {
                for (int i = Integer.parseInt(y) - 1; i < Integer.parseInt(y) - 1 + Integer.parseInt(whichS); ++i) {
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 1] = 'S';
                    grid.getTheGrid()[i][(Integer.parseInt(x) - 1) * 3 + 2] = '4';
                }
                //
                Grid.forthSheep.add(new ArrayList<String>());
                Grid.forthSheephelp.add(new ArrayList<String>());
                for (int i = Integer.parseInt(y); i < Integer.parseInt(y) + 4; ++i) {
                    Grid.forthSheep.get(Grid.forthIndex).add("(" + x + "," + i + ")");
                    Grid.forthSheephelp.get(Grid.forthIndex).add("(" + x + "," + i + ")");
                }
                Grid.forthIndex += 1;
                //
            }
        }
    }

    //----------------------------------------------------------------
    public static void puttingMine(String command, Grid grid) {
        Pattern myPattern = Pattern.compile("^put-mine (.+?),(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        int x = Integer.parseInt(myMatcher.group(1));
        String xString = myMatcher.group(1);
        int y = Integer.parseInt(myMatcher.group(2));
        String yString = myMatcher.group(2);
        if (x > 10 || x < 1 || y > 10 || y < 0) System.out.println("wrong coordination");
        else {
            if (grid.getMine() == 0) System.out.println("you don't have enough mine");
            else {
                if (!isEmpty("1", xString, yString, "h", grid))
                    System.out.println("collision with the other ship or mine on the board");
                else {
                    drawingMineOnGrid(x, y, grid);
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void drawingMineOnGrid(int x, int y, Grid grid) {
        grid.decreaseMine();

        for (int i = (x - 1) * 3; i < (x - 1) * 3 + 3; ++i) {
            if (i % 3 == 1) grid.getTheGrid()[y - 1][i] = 'M';
            if (i % 3 == 2) grid.getTheGrid()[y - 1][i] = 'm';
        }
    }

    //----------------------------------------------------------------
    public static void puttingAntiAirCraft(String command, Grid grid) {
        Pattern myPattern = Pattern.compile("^put-antiaircraft (.+?) -(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String startOfRow = myMatcher.group(1);
        String hOrV = myMatcher.group(2);
        if (Integer.parseInt(startOfRow) > 10 || Integer.parseInt(startOfRow) < 1)
            System.out.println("wrong coordination");
        else {
            if (Integer.parseInt(startOfRow) + 2 > 10) System.out.println("off the board");
            else {
                if (!hOrV.equals("h") && !hOrV.equals("v")) System.out.println("invalid direction");
                else {
                    if (grid.getAntiAirCraft() == 0) System.out.println("you don't have enough antiaircraft");
                    else {
                        Grid.allAntiAirCrafts.add(startOfRow + " " + hOrV);
                        drawingAntiAirCraftOnBoard(startOfRow, hOrV, grid);
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void drawingAntiAirCraftOnBoard(String start, String hOrV, Grid grid) {
        grid.decreaseAntiAirCraft();

        if (hOrV.equals("h")) {
            for (int i = Integer.parseInt(start) - 1; i < Integer.parseInt(start) + 2; ++i) {
                for (int j = 0; j < 31; ++j) {
                    if (grid.getTheGrid()[i][j] == ' ') grid.getTheGrid()[i][j] = 'A';
                }
            }
        } else {
            for (int i = Integer.parseInt(start); i < Integer.parseInt(start) + 3; ++i) {
                for (int j = 0; j < 10; ++j) {
                    if (grid.getTheGrid()[j][(i - 1) * 3 + 1] == ' ' && grid.getTheGrid()[j][(i - 1) * 3 + 2] == ' ') {
                        grid.getTheGrid()[j][(i - 1) * 3 + 1] = 'A';
                        grid.getTheGrid()[j][(i - 1) * 3 + 2] = 'A';
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void puttingInvisable(String command, Grid grid) {
        Pattern myPattern = Pattern.compile("^invisible (.+?),(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        int x = Integer.parseInt(myMatcher.group(1));
        int y = Integer.parseInt(myMatcher.group(2));
        if (x > 10 || x < 1 || y > 10 || y < 1) System.out.println("wrong coordination");
        else {
            if (grid.getInvisible() == 0) System.out.println("you don't have enough invisible");
            else {
                if (grid.getTheGrid()[y - 1][(x - 1) * 3 + 1] != 'S' && grid.getTheGrid()[y - 1][(x - 1) * 3 + 1] != 'I')
                    System.out.println("there is no ship on this place on the board");
                else {
                    if (grid.getTheGrid()[y - 1][(x - 1) * 3 + 1] == 'I')
                        System.out.println("this place has already made invisible");
                    else {
                        grid.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'I';
                        grid.decreaseInvisiable();
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void showingBoard(Grid grid) {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 31; ++j) {
                System.out.print(grid.getTheGrid()[i][j]);
            }
            System.out.println("");
        }
    }

    //----------------------------------------------------------------
    public static void helpInArranging() {
        System.out.println("put S[number] [x],[y] [-h|-v]");
        System.out.println("put-mine [x],[y]");
        System.out.println("put-antiaircraft [s] [-h|-v]");
        System.out.println("invisible [x],[y]");
        System.out.println("show-my-board");
        System.out.println("help");
        System.out.println("finish-arranging");
    }

    //----------------------------------------------------------------
    public static void shop(String userName, String password, Scanner scanner) {
        String command;
        while (true) {
            command = scanner.nextLine();
            if (patternChecker(command, "^show-amount$"))
                System.out.println(UserName.getObjectByUserName(userName).getCoins());
            else if (patternChecker(command, "^buy [^ ]+? \\d+?$")) buySTh(command, userName);
            else if (patternChecker(command, "^help$")) helpInShop();
            else if (patternChecker(command, "^back$")) logInAfterShop(userName, password, scanner);
            else System.out.println("invalid command");
        }
    }

    //----------------------------------------------------------------
    public static void buySTh(String command, String userName) {
        HashMap<String, Integer> stufs = new HashMap<>();
        stufs.put("mine", 1);
        stufs.put("antiaircraft", 30);
        stufs.put("airplane", 10);
        stufs.put("scanner", 9);
        stufs.put("invisible", 20);

        Pattern myPattern = Pattern.compile("^buy (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String name = myMatcher.group(1);
        String number = myMatcher.group(2);
        if (!stufs.containsKey(name)) System.out.println("there is no product with this name");
        else {
            try {
                int numberIntMode = Integer.parseInt(number);
                UserName myUserName = UserName.getObjectByUserName(userName);
                if (myUserName.getCoins() < stufs.get(name) * numberIntMode)
                    System.out.println("you don't have enough money");
                else if (numberIntMode < 1) System.out.println("invalid number");
                else {
                    Grid userGrid = Grid.allUsersGrid.get(userName);
                    if (name.equals("mine")) userGrid.increaseMine(numberIntMode);
                    if (name.equals("antiaircraft")) userGrid.increaseAntiAirCraft(numberIntMode);
                    if (name.equals("airplane")) userGrid.increaseAirPlane(numberIntMode);
                    if (name.equals("scanner")) userGrid.increaseScanner(numberIntMode);
                    if (name.equals("invisible")) userGrid.increaseInvisable(numberIntMode);

                    myUserName.decreaseCoins(stufs.get(name) * numberIntMode);
                }
            } catch (Exception e) {
                System.out.println("invalid number");
            }
        }
    }

    //----------------------------------------------------------------
    public static void helpInShop() {
        System.out.println("buy [product] [number]");
        System.out.println("show-amount");
        System.out.println("help");
        System.out.println("back");
    }

    //----------------------------------------------------------------
    public static int starterScoere = 0;
    public static int oponenetScore = 0;
    public static int whosTurn = 1;
    public static char[][] oponentsGridToShowToStarter = new char[10][31];
    public static char[][] starterGridToShowToOponent = new char[10][31];

    public static void gameBattle(Grid starter, Grid oponent, Scanner scanner) {
        starterScoere = 0;
        oponenetScore = 0;
        whosTurn = 1;
        makeOppositeGridEmpty();
        commandsOfGameBattle(starter, oponent, scanner);
    }

    //----------------------------------------------------------------
    public static void commandsOfGameBattle(Grid starter, Grid oponent, Scanner scanner) {
        String command;
        while (true) {
            if (starter.getNumberOfSheeps() == 0 && oponent.getNumberOfSheeps() == 0) {
                System.out.println("draw");
                UserName starterUserName = UserName.getObjectByUserName(starter.getOwnerOfGrid());
                starterUserName.increaseCoins(starterScoere + 25);
                starterUserName.increaseDraw();
                starterUserName.increaseScore(1);
                UserName oponentUserName = UserName.getObjectByUserName(oponent.getOwnerOfGrid());
                oponentUserName.increaseCoins(oponenetScore + 25);
                oponentUserName.increaseDraw();
                oponentUserName.increaseScore(1);
                starter.emptyGrid();
                oponent.emptyGrid();
                logInAfterShop(starter.getOwnerOfGrid(), starterUserName.getPassword(), scanner);
            } else if (oponent.getNumberOfSheeps() == 0) {
                System.out.println(starter.getOwnerOfGrid() + " is winner");
                UserName starterUserName = UserName.getObjectByUserName(starter.getOwnerOfGrid());
                starterUserName.increaseCoins(starterScoere + 50);
                starterUserName.increaseWin();
                starterUserName.increaseScore(3);

                UserName oponentUserName = UserName.getObjectByUserName(oponent.getOwnerOfGrid());
                oponentUserName.increaseCoins(oponenetScore);
                oponentUserName.increseLose();


                starter.emptyGrid();
                oponent.emptyGrid();


                logInAfterShop(starter.getOwnerOfGrid(), starterUserName.getPassword(), scanner);
            } else if (starter.getNumberOfSheeps() == 0) {
                System.out.println(oponent.getOwnerOfGrid() + " is winner");
                UserName oponentUserName = UserName.getObjectByUserName(oponent.getOwnerOfGrid());
                oponentUserName.increaseCoins(oponenetScore + 50);
                oponentUserName.increaseWin();
                oponentUserName.increaseScore(3);

                UserName starterUserName = UserName.getObjectByUserName(starter.getOwnerOfGrid());
                starterUserName.increaseCoins(starterScoere);
                starterUserName.increseLose();


                starter.emptyGrid();
                oponent.emptyGrid();


                logInAfterShop(starter.getOwnerOfGrid(), starterUserName.getPassword(), scanner);
            }
            command = scanner.nextLine();
            if (patternChecker(command, "^show-turn$")) showTurn(starter.getOwnerOfGrid(), oponent.getOwnerOfGrid());
            else if (patternChecker(command, "^bomb \\d+?,\\d+?$")) bomb(starter, oponent, command);
            else if (patternChecker(command, "^help$")) helpInGame();
            else if (patternChecker(command, "^show-my-board$")) showMyBoard(starter, oponent);
            else if (patternChecker(command, "^show-rival-board$"))
                showRivalBoard(starterGridToShowToOponent, oponentsGridToShowToStarter);
            else if (patternChecker(command, "^forfeit$")) forfeit(starter, oponent, scanner);
            else if (patternChecker(command, "^put-airplane \\d+?,\\d+? -[^ ]$"))
                putAirPlane(starter, oponent, scanner, command);
            else if (patternChecker(command, "^scanner [^ ]+?,[^ ]+?$")) putScanner(starter, oponent, command);
            else System.out.println("invalid command");
        }
    }

    //----------------------------------------------------------------
    public static void forfeit(Grid starter, Grid oponent, Scanner scanner) {
        String forfeited;
        String winner;
        if (whosTurn == 1) {
            forfeited = starter.getOwnerOfGrid();
            winner = oponent.getOwnerOfGrid();
            System.out.println(forfeited + " is forfeited");
            System.out.println(winner + " is winner");
            UserName starterUserName = UserName.getObjectByUserName(starter.getOwnerOfGrid());
            starterUserName.increaseCoins(-50);
            starterUserName.increseLose();
            starterUserName.increaseScore(-1);
            UserName oponentUserName = UserName.getObjectByUserName(oponent.getOwnerOfGrid());
            oponentUserName.increaseCoins(oponenetScore);
            oponentUserName.increaseWin();
            oponentUserName.increaseScore(2);
            starter.emptyGrid();
            oponent.emptyGrid();
            logInAfterShop(starter.getOwnerOfGrid(), starterUserName.getPassword(), scanner);
        } else {
            forfeited = oponent.getOwnerOfGrid();
            winner = starter.getOwnerOfGrid();
            System.out.println(forfeited + " is forfeited");
            System.out.println(winner + " is winner");
            UserName starterUserName = UserName.getObjectByUserName(starter.getOwnerOfGrid());
            starterUserName.increaseCoins(starterScoere);
            starterUserName.increaseWin();
            starterUserName.increaseScore(2);
            UserName oponentUserName = UserName.getObjectByUserName(oponent.getOwnerOfGrid());
            oponentUserName.increaseCoins(-50);
            oponentUserName.increseLose();
            oponentUserName.increaseScore(-1);
            starter.emptyGrid();
            oponent.emptyGrid();
            logInAfterShop(starter.getOwnerOfGrid(), starterUserName.getPassword(), scanner);
        }
    }

    //----------------------------------------------------------------
    public static void makeOppositeGridEmpty() {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 31; ++j) {
                if (j % 3 == 0) {
                    oponentsGridToShowToStarter[i][j] = '|';
                    starterGridToShowToOponent[i][j] = '|';
                } else {
                    oponentsGridToShowToStarter[i][j] = ' ';
                    starterGridToShowToOponent[i][j] = ' ';
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void showTurn(String starter, String oponent) {
        if (whosTurn == 1) System.out.println(starter + "'s turn");
        if (whosTurn == 0) System.out.println(oponent + "'s turn");
    }

    //----------------------------------------------------------------
    public static void bomb(Grid starter, Grid oponent, String command) {
        Pattern myPattern = Pattern.compile("^bomb (.+?),(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        int x = Integer.parseInt(myMatcher.group(1));
        int y = Integer.parseInt(myMatcher.group(2));

        if (x > 10 || x < 1 || y > 10 || y < 1) System.out.println("wrong coordination");
        else {
            if (isDestroyesOrNot(starter, oponent, x, y)) System.out.println("this place has already destroyed");
            else {
                whenBombHeatSheepOrMine(starter, oponent, x, y);
            }
        }
    }

    //----------------------------------------------------------------
    public static boolean isDestroyesOrNot(Grid starter, Grid oponent, int x, int y) {
        Grid forDestroying;
        if (whosTurn == 1) forDestroying = oponent;
        else forDestroying = starter;

        if (forDestroying.getTheGrid()[y - 1][(x - 1) * 3 + 1] == 'D' || forDestroying.getTheGrid()[y - 1][(x - 1) * 3 + 2] == 'X')
            return true;

        return false;
    }

    //----------------------------------------------------------------
    public static void whenBombHeatSheepOrMine(Grid starter, Grid oponent, int x, int y) {
        Grid gridTurn;
        char[][] oppositeGrid;
        if (whosTurn == 1) {
            gridTurn = oponent;
            oppositeGrid = oponentsGridToShowToStarter;
        } else {
            gridTurn = starter;
            oppositeGrid = starterGridToShowToOponent;
        }

        char firstLetter = gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1];
        char secondLetter = gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2];


        if (firstLetter == 'S' || firstLetter == 'I') {
            if (secondLetter == '1') {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = '1';
                if (whosTurn == 1) starterScoere += 1;
                if (whosTurn == 0) oponenetScore += 1;
                System.out.println("the rival's ship1 was destroyed");
                gridTurn.decreaseNumberOfSheeps();
            }
            if (secondLetter == '2') {
                if (whosTurn == 1) {
                    for (int i = 3; i < 6; ++i) {
                        if (Grid.secondSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.secondSheep.get(i).size() == 1) {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship2 was destroyed");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                for (int j = 0; j < 2; ++j) {
                                    String stufs = Grid.secondSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '2';
                                }
                            } else {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship was damaged");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    starterScoere += 1;
                }
                if (whosTurn == 0) {
                    for (int i = 0; i < 3; ++i) {
                        if (Grid.secondSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.secondSheep.get(i).size() == 1) {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship2 was destroyed");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                for (int j = 0; j < 2; ++j) {
                                    String stufs = Grid.secondSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '2';
                                }
                            } else {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship was damaged");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    oponenetScore += 1;
                }
            }
            if (secondLetter == '3') {
                if (whosTurn == 1) {
                    for (int i = 2; i < 4; ++i) {
                        if (Grid.thirdSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.thirdSheep.get(i).size() == 1) {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship3 was destroyed");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                for (int j = 0; j < 3; ++j) {
                                    String stufs = Grid.thirdSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '3';
                                }
                            } else {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship was damaged");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    starterScoere += 1;
                }
                if (whosTurn == 0) {
                    for (int i = 0; i < 2; ++i) {
                        if (Grid.thirdSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.thirdSheep.get(i).size() == 1) {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship3 was destroyed");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                for (int j = 0; j < 3; ++j) {
                                    String stufs = Grid.thirdSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '3';
                                }
                            } else {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                System.out.println("the rival's ship was damaged");
                                gridTurn.decreaseNumberOfSheeps();
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    oponenetScore += 1;
                }
            }
            if (secondLetter == '4') {
                if (whosTurn == 1) {
                    if (Grid.forthSheep.get(1).size() == 1) {
                        Grid.forthSheep.get(1).remove("(" + x + "," + y + ")");
                        System.out.println("the rival's ship4 was destroyed");
                        gridTurn.decreaseNumberOfSheeps();
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                        for (int j = 0; j < 4; ++j) {
                            String stufs = Grid.forthSheephelp.get(1).get(j);
                            Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                            Matcher myMatcher = myPattern.matcher(stufs);
                            myMatcher.find();
                            int xx = Integer.parseInt(myMatcher.group(1));
                            int yy = Integer.parseInt(myMatcher.group(2));
                            oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '4';
                        }
                    } else {
                        Grid.forthSheep.get(1).remove("(" + x + "," + y + ")");
                        System.out.println("the rival's ship was damaged");
                        gridTurn.decreaseNumberOfSheeps();
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                    }
                    starterScoere += 1;
                }
                if (whosTurn == 0) {
                    if (Grid.forthSheep.get(0).size() == 1) {
                        Grid.forthSheep.get(0).remove("(" + x + "," + y + ")");
                        System.out.println("the rival's ship4 was destroyed");
                        gridTurn.decreaseNumberOfSheeps();
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                        for (int j = 0; j < 4; ++j) {
                            String stufs = Grid.forthSheephelp.get(0).get(j);
                            Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                            Matcher myMatcher = myPattern.matcher(stufs);
                            myMatcher.find();
                            int xx = Integer.parseInt(myMatcher.group(1));
                            int yy = Integer.parseInt(myMatcher.group(2));
                            oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '4';
                        }
                    } else {
                        Grid.forthSheep.get(0).remove("(" + x + "," + y + ")");
                        System.out.println("the rival's ship was damaged");
                        gridTurn.decreaseNumberOfSheeps();
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                    }
                    oponenetScore += 1;
                }
            }
        }


        if (firstLetter == 'M' && secondLetter == 'm') {
            if (whosTurn == 1) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'M';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                starterScoere -= 1;
                System.out.println("you destroyed the rival's mine");
                System.out.println("turn completed");
                whosTurn = 0;
                whenBombHeatSheepOrMineAfterHeatingMine(starter, x, y, starterGridToShowToOponent);
            } else if (whosTurn == 0) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'M';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                oponenetScore -= 1;
                System.out.println("you destroyed the rival's mine");
                System.out.println("turn completed");
                whosTurn = 1;
                whenBombHeatSheepOrMineAfterHeatingMine(oponent, x, y, oponentsGridToShowToStarter);
            }
        }

        if (firstLetter == ' ' || firstLetter == 'A') {
            if (whosTurn == 1) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'X';
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                System.out.println("the bomb fell into sea");
                System.out.println("turn completed");
                whosTurn = 0;
            } else if (whosTurn == 0) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'X';
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                System.out.println("the bomb fell into sea");
                System.out.println("turn completed");
                whosTurn = 1;
            }
        }
    }

    //----------------------------------------------------------------
    public static void whenBombHeatSheepOrMineAfterHeatingMine(Grid grid, int x, int y, char[][] oppositeGrid) {
        Grid gridTurn = grid;

        char firstLetter = gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1];
        char secondLetter = gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2];


        if (firstLetter == 'S' || firstLetter == 'I') {
            if (secondLetter == '1') {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = '1';
                gridTurn.decreaseNumberOfSheeps();
                if (whosTurn == 1) starterScoere += 1;
                if (whosTurn == 0) oponenetScore += 1;
            }
            if (secondLetter == '2') {
                if (whosTurn == 1) {
                    for (int i = 3; i < 6; ++i) {
                        if (Grid.secondSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.secondSheep.get(i).size() == 1) {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                gridTurn.decreaseNumberOfSheeps();
                                for (int j = 0; j < 2; ++j) {
                                    String stufs = Grid.secondSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '2';
                                }
                            } else {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                gridTurn.decreaseNumberOfSheeps();
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    starterScoere += 1;
                }
                if (whosTurn == 0) {
                    for (int i = 0; i < 3; ++i) {
                        if (Grid.secondSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.secondSheep.get(i).size() == 1) {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                gridTurn.decreaseNumberOfSheeps();
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                for (int j = 0; j < 2; ++j) {
                                    String stufs = Grid.secondSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '2';
                                }
                            } else {
                                Grid.secondSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                gridTurn.decreaseNumberOfSheeps();
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    oponenetScore += 1;
                }
            }
            if (secondLetter == '3') {
                if (whosTurn == 1) {
                    for (int i = 2; i < 4; ++i) {
                        if (Grid.thirdSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.thirdSheep.get(i).size() == 1) {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                gridTurn.decreaseNumberOfSheeps();
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                for (int j = 0; j < 3; ++j) {
                                    String stufs = Grid.thirdSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '3';
                                }
                            } else {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                gridTurn.decreaseNumberOfSheeps();
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    starterScoere += 1;
                }
                if (whosTurn == 0) {
                    for (int i = 0; i < 2; ++i) {
                        if (Grid.thirdSheep.get(i).contains("(" + x + "," + y + ")")) {
                            if (Grid.thirdSheep.get(i).size() == 1) {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                gridTurn.decreaseNumberOfSheeps();
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                                for (int j = 0; j < 3; ++j) {
                                    String stufs = Grid.thirdSheephelp.get(i).get(j);
                                    Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                                    Matcher myMatcher = myPattern.matcher(stufs);
                                    myMatcher.find();
                                    int xx = Integer.parseInt(myMatcher.group(1));
                                    int yy = Integer.parseInt(myMatcher.group(2));
                                    oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '3';
                                }
                            } else {
                                Grid.thirdSheep.get(i).remove("(" + x + "," + y + ")");
                                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                                gridTurn.decreaseNumberOfSheeps();
                                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                            }
                            break;
                        }
                    }
                    oponenetScore += 1;
                }
            }
            if (secondLetter == '4') {
                if (whosTurn == 1) {
                    if (Grid.forthSheep.get(1).size() == 1) {
                        Grid.forthSheep.get(1).remove("(" + x + "," + y + ")");
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        gridTurn.decreaseNumberOfSheeps();
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                        for (int j = 0; j < 4; ++j) {
                            String stufs = Grid.forthSheephelp.get(1).get(j);
                            Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                            Matcher myMatcher = myPattern.matcher(stufs);
                            myMatcher.find();
                            int xx = Integer.parseInt(myMatcher.group(1));
                            int yy = Integer.parseInt(myMatcher.group(2));
                            oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '4';
                        }
                    } else {
                        Grid.forthSheep.get(1).remove("(" + x + "," + y + ")");
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        gridTurn.decreaseNumberOfSheeps();
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                    }
                    starterScoere += 1;
                }
                if (whosTurn == 0) {
                    if (Grid.forthSheep.get(0).size() == 1) {
                        Grid.forthSheep.get(0).remove("(" + x + "," + y + ")");
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        gridTurn.decreaseNumberOfSheeps();
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                        for (int j = 0; j < 4; ++j) {
                            String stufs = Grid.forthSheephelp.get(0).get(j);
                            Pattern myPattern = Pattern.compile("\\((\\d+),(\\d+)\\)");
                            Matcher myMatcher = myPattern.matcher(stufs);
                            myMatcher.find();
                            int xx = Integer.parseInt(myMatcher.group(1));
                            int yy = Integer.parseInt(myMatcher.group(2));
                            oppositeGrid[yy - 1][(xx - 1) * 3 + 2] = '4';
                        }
                    } else {
                        Grid.forthSheep.get(0).remove("(" + x + "," + y + ")");
                        gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'D';
                        gridTurn.decreaseNumberOfSheeps();
                        oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'D';
                        oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                    }
                    oponenetScore += 1;
                }
            }
        }


        if (firstLetter == 'M' && secondLetter == 'm') {
            if (whosTurn == 1) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'M';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                starterScoere -= 1;
            } else if (whosTurn == 0) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'M';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
                oponenetScore -= 1;
            }
        }

        if (firstLetter == ' ' || firstLetter == 'A') {
            if (whosTurn == 1) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'X';
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
            } else if (whosTurn == 0) {
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 1] = 'X';
                gridTurn.getTheGrid()[y - 1][(x - 1) * 3 + 2] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 1] = 'X';
                oppositeGrid[y - 1][(x - 1) * 3 + 2] = 'X';
            }
        }
    }

    //----------------------------------------------------------------
    public static void helpInGame() {
        System.out.println("bomb [x],[y]");
        System.out.println("put-airplane [x],[y] [-h|-v]");
        System.out.println("scanner [x],[y]");
        System.out.println("show-turn");
        System.out.println("show-my-board");
        System.out.println("show-rival-board");
        System.out.println("help");
        System.out.println("forfeit");
    }

    //----------------------------------------------------------------
    public static void showMyBoard(Grid starter, Grid oponent) {
        Grid theGrid;
        if (whosTurn == 1) theGrid = starter;
        else theGrid = oponent;

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 31; ++j) {
                System.out.print(theGrid.getTheGrid()[i][j]);
            }
            System.out.println("");
        }
    }

    //----------------------------------------------------------------
    public static void showRivalBoard(char[][] starterGridToShowToOponent, char[][] oponentsGridToShowToStarter) {
        char[][] oppositeBoard;
        if (whosTurn == 1) oppositeBoard = oponentsGridToShowToStarter;
        else oppositeBoard = starterGridToShowToOponent;

        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 31; ++j) {
                System.out.print(oppositeBoard[i][j]);
            }
            System.out.println("");
        }
    }

    //----------------------------------------------------------------
    public static void putAirPlane(Grid starter, Grid oponent, Scanner scanner, String command) {
        Pattern myPattern = Pattern.compile("^put-airplane (.+?),(.+?) -(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        String x = myMatcher.group(1);
        int xIntMode = Integer.parseInt(x);
        String y = myMatcher.group(2);
        int yIntMode = Integer.parseInt(y);
        String hOrV = myMatcher.group(3);

        if (xIntMode > 10 || xIntMode < 1 || yIntMode > 10 || yIntMode < 1) System.out.println("wrong coordination");
        else {
            if (!hOrV.equals("h") && !hOrV.equals("v")) System.out.println("invalid direction");
            else {
                if (isItOffTheBoard(xIntMode, yIntMode, hOrV)) System.out.println("off the board");
                else {
                    if (whosTurn == 1 && starter.getAirPlane() == 0) System.out.println("you don't have airplane");
                    else if (whosTurn == 0 && oponent.getAirPlane() == 0) System.out.println("you don't have airplane");
                    else {
                        int coverOrNot = doesAntiAttackAirPlane(x, y, hOrV);
                        if (coverOrNot != -1) antiAirCraftHeatAirPlane(starter, oponent, coverOrNot);
                        if (coverOrNot == -1) airPlaneDestroy(starter, oponent, xIntMode, yIntMode, hOrV);
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static boolean isItOffTheBoard(int x, int y, String hOrV) {
        if (hOrV.equals("h")) {
            if (x + 4 > 10 || y + 1 > 10) return true;
        }
        if (hOrV.equals("v")) {
            if (x + 1 > 10 || y + 4 > 10) return true;
        }

        return false;
    }

    //----------------------------------------------------------------
    public static int doesAntiAttackAirPlane(String x, String y, String hOrV) {
        ArrayList<Integer> allRowsOfAirPlane = new ArrayList<>();
        ArrayList<Integer> allColumsOfAirPlane = new ArrayList<>();

        if (hOrV.equals("h")) {
            allRowsOfAirPlane.add(Integer.parseInt(y));
            allRowsOfAirPlane.add(Integer.parseInt(y) + 1);
            for (int i = Integer.parseInt(x); i < Integer.parseInt(x) + 5; ++i) {
                allColumsOfAirPlane.add(i);
            }
        }

        if (hOrV.equals("v")) {
            allColumsOfAirPlane.add(Integer.parseInt(x));
            allColumsOfAirPlane.add(Integer.parseInt(x) + 1);
            for (int i = Integer.parseInt(y); i < Integer.parseInt(y) + 5; ++i) {
                allRowsOfAirPlane.add(i);
            }
        }

        if (whosTurn == 0) {
            int index = 0;
            while (!Grid.allAntiAirCrafts.get(index).equals("***")) {
                Pattern myPattern = Pattern.compile("^(.+?) (.+?)$");
                Matcher myMatcher = myPattern.matcher(Grid.allAntiAirCrafts.get(index));
                myMatcher.find();
                int start = Integer.parseInt(myMatcher.group(1));
                String antHOrV = myMatcher.group(2);
                if (antHOrV.equals("h")) {
                    ArrayList<Integer> allRowsAnti = new ArrayList<>();
                    for (int i = start; i < start + 3; ++i) {
                        allRowsAnti.add(i);
                    }
                    for (int i = 0; i < allRowsOfAirPlane.size(); ++i) {
                        if (allRowsAnti.contains(allRowsOfAirPlane.get(i))) return index;
                    }
                }
                if (antHOrV.equals("v")) {
                    ArrayList<Integer> allCulomsAnti = new ArrayList<>();
                    for (int i = start; i < start + 3; ++i) {
                        allCulomsAnti.add(i);
                    }
                    for (int i = 0; i < allColumsOfAirPlane.size(); ++i) {
                        if (allCulomsAnti.contains(allColumsOfAirPlane.get(i))) return index;
                    }
                }
                index += 1;
            }
        } else if (whosTurn == 1) {
            int index = 0;
            for (int i = 0; i < Grid.allAntiAirCrafts.size(); ++i) {
                if (Grid.allAntiAirCrafts.get(i).equals("***")) {
                    index = i;
                    break;
                }
            }

            for (int j = index + 1; j < Grid.allAntiAirCrafts.size(); ++j) {
                Pattern myPattern = Pattern.compile("^(.+?) (.+?)$");
                Matcher myMatcher = myPattern.matcher(Grid.allAntiAirCrafts.get(j));
                myMatcher.find();
                int start = Integer.parseInt(myMatcher.group(1));
                String antHOrV = myMatcher.group(2);
                if (antHOrV.equals("h")) {
                    ArrayList<Integer> allRowsAnti = new ArrayList<>();
                    for (int i = start; i < start + 3; ++i) {
                        allRowsAnti.add(i);
                    }
                    for (int i = 0; i < allRowsOfAirPlane.size(); ++i) {
                        if (allRowsAnti.contains(allRowsOfAirPlane.get(i))) return j;
                    }
                }
                if (antHOrV.equals("v")) {
                    ArrayList<Integer> allCulomsAnti = new ArrayList<>();
                    for (int i = start; i < start + 3; ++i) {
                        allCulomsAnti.add(i);
                    }
                    for (int i = 0; i < allColumsOfAirPlane.size(); ++i) {
                        if (allCulomsAnti.contains(allColumsOfAirPlane.get(i))) return j;
                    }
                }
            }
        }

        return -1;
    }

    //----------------------------------------------------------------
    public static void antiAirCraftHeatAirPlane(Grid starter, Grid oponent, int whichIndex) {
        System.out.println("the rival's antiaircraft destroyed your airplane");
        if (whosTurn == 1) {
            starter.decreaseAirPlane();
            String information = Grid.allAntiAirCrafts.get(whichIndex);
            Pattern myPattern = Pattern.compile("^(.+?) (.+?)$");
            Matcher myMatcher = myPattern.matcher(information);
            myMatcher.find();
            int start = Integer.parseInt(myMatcher.group(1));
            String hOrV = myMatcher.group(2);
            if (hOrV.equals("h")) {
                for (int i = start; i < start + 3; ++i) {
                    for (int j = 0; j < 31; ++j) {
                        if (oponent.getTheGrid()[i - 1][j] == 'A') oponent.getTheGrid()[i - 1][j] = ' ';
                    }
                }
                for (int i = whichIndex + 1; i < Grid.allAntiAirCrafts.size(); ++i) {
                    Pattern myPattern2 = Pattern.compile("^(.+?) (.+?)$");
                    Matcher myMatcher2 = myPattern2.matcher(Grid.allAntiAirCrafts.get(i));
                    myMatcher2.find();
                    int start2 = Integer.parseInt(myMatcher2.group(1));
                    String hOrV2 = myMatcher2.group(2);
                    if (hOrV2.equals("h")) {
                        for (int j = start2; j < start2 + 3; ++j) {
                            for (int k = 0; k < 31; ++k) {
                                if (oponent.getTheGrid()[j - 1][k] == ' ') oponent.getTheGrid()[j - 1][k] = 'A';
                            }
                        }
                    }
                    if (hOrV2.equals("v")) {
                        for (int j = 0; j < 10; j++) {
                            for (int k = (start2 - 1) * 3; k < (start2 - 1) * 3 + 9; ++k) {
                                if (oponent.getTheGrid()[j][k] == ' ') oponent.getTheGrid()[j][k] = 'A';
                            }
                        }
                    }
                }
                Grid.allAntiAirCrafts.remove(whichIndex);
            }
            if (hOrV.equals("v")) {
                for (int i = 0; i < 10; ++i) {
                    for (int j = (start - 1) * 3; j < (start - 1) * 3 + 9; ++j) {
                        if (oponent.getTheGrid()[i][j] == 'A') oponent.getTheGrid()[i][j] = ' ';
                    }
                }
                for (int i = whichIndex + 1; i < Grid.allAntiAirCrafts.size(); ++i) {
                    Pattern myPattern2 = Pattern.compile("^(.+?) (.+?)$");
                    Matcher myMatcher2 = myPattern2.matcher(Grid.allAntiAirCrafts.get(i));
                    myMatcher2.find();
                    int start2 = Integer.parseInt(myMatcher2.group(1));
                    String hOrV2 = myMatcher2.group(2);
                    if (hOrV2.equals("h")) {
                        for (int j = start2; j < start2 + 3; ++j) {
                            for (int k = 0; k < 31; ++k) {
                                if (oponent.getTheGrid()[j - 1][k] == ' ') oponent.getTheGrid()[j - 1][k] = 'A';
                            }
                        }
                    }
                    if (hOrV2.equals("v")) {
                        for (int j = 0; j < 10; j++) {
                            for (int k = (start2 - 1) * 3; k < (start2 - 1) * 3 + 9; ++k) {
                                if (oponent.getTheGrid()[j][k] == ' ') oponent.getTheGrid()[j][k] = 'A';
                            }
                        }
                    }
                }
                Grid.allAntiAirCrafts.remove(whichIndex);
            }
        } else if (whosTurn == 0) {
            //
            int what = 0;
            for (int i = 0; i < Grid.allAntiAirCrafts.size(); ++i) {
                if (Grid.allAntiAirCrafts.get(i).equals("***")) what = i;
            }
            //
            oponent.decreaseAirPlane();
            String information = Grid.allAntiAirCrafts.get(whichIndex);
            Pattern myPattern = Pattern.compile("^(.+?) (.+?)$");
            Matcher myMatcher = myPattern.matcher(information);
            myMatcher.find();
            int start = Integer.parseInt(myMatcher.group(1));
            String hOrV = myMatcher.group(2);
            if (hOrV.equals("h")) {
                for (int i = start; i < start + 3; ++i) {
                    for (int j = 0; j < 31; ++j) {
                        if (starter.getTheGrid()[i - 1][j] == 'A') starter.getTheGrid()[i - 1][j] = ' ';
                    }
                }
                for (int i = whichIndex + 1; i < what; ++i) {
                    Pattern myPattern3 = Pattern.compile("^(.+?) (.+?)$");
                    Matcher myMatcher3 = myPattern3.matcher(Grid.allAntiAirCrafts.get(i));
                    myMatcher3.find();
                    int start2 = Integer.parseInt(myMatcher3.group(1));
                    String hOrV2 = myMatcher3.group(2);
                    if (hOrV2.equals("h")) {
                        for (int j = start2; j < start2 + 3; ++j) {
                            for (int k = 0; k < 31; ++k) {
                                if (starter.getTheGrid()[j - 1][k] == ' ') starter.getTheGrid()[j - 1][k] = 'A';
                            }
                        }
                    }
                    if (hOrV2.equals("v")) {
                        for (int j = 0; j < 10; j++) {
                            for (int k = (start2 - 1) * 3; k < (start2 - 1) * 3 + 9; ++k) {
                                if (starter.getTheGrid()[j][k] == ' ') starter.getTheGrid()[j][k] = 'A';
                            }
                        }
                    }
                }
                Grid.allAntiAirCrafts.remove(whichIndex);
            }
            if (hOrV.equals("v")) {
                for (int i = 0; i < 10; ++i) {
                    for (int j = (start - 1) * 3; j < (start - 1) * 3 + 9; ++j) {
                        if (starter.getTheGrid()[i][j] == 'A') starter.getTheGrid()[i][j] = ' ';
                    }
                }
                for (int i = whichIndex + 1; i < what; ++i) {
                    Pattern myPattern4 = Pattern.compile("^(.+?) (.+?)$");
                    Matcher myMatcher4 = myPattern4.matcher(Grid.allAntiAirCrafts.get(i));
                    myMatcher4.find();
                    int start2 = Integer.parseInt(myMatcher4.group(1));
                    String hOrV2 = myMatcher4.group(2);
                    if (hOrV2.equals("h")) {
                        for (int j = start2; j < start2 + 3; ++j) {
                            for (int k = 0; k < 31; ++k) {
                                if (starter.getTheGrid()[j - 1][k] == ' ') starter.getTheGrid()[j - 1][k] = 'A';
                            }
                        }
                    }
                    if (hOrV2.equals("v")) {
                        for (int j = 0; j < 10; j++) {
                            for (int k = (start2 - 1) * 3; k < (start2 - 1) * 3 + 9; ++k) {
                                if (starter.getTheGrid()[j][k] == ' ') starter.getTheGrid()[j][k] = 'A';
                            }
                        }
                    }
                }
                Grid.allAntiAirCrafts.remove(whichIndex);
            }
        }
    }

    //----------------------------------------------------------------
    public static void airPlaneDestroy(Grid starter, Grid Oponent, int x, int y, String hOrV) {
        if (whosTurn == 1) {
            starter.decreaseAirPlane();
            int counter = 0;
            if (hOrV.equals("h")) {
                for (int i = y; i < y + 2; ++i) {
                    for (int j = (x - 1) * 3; j < (x - 1) * 3 + 15; ++j) {
                        if (Oponent.getTheGrid()[i - 1][j] == 'S' || Oponent.getTheGrid()[i - 1][j] == 'I')
                            counter += 1;
                    }
                }
            }
            if (hOrV.equals("v")) {
                for (int i = y; i < y + 5; ++i) {
                    for (int j = (x - 1) * 3; j < (x - 1) * 3 + 6; ++j) {
                        if (Oponent.getTheGrid()[i - 1][j] == 'S' || Oponent.getTheGrid()[i - 1][j] == 'I')
                            counter += 1;
                    }
                }
            }
            if (counter == 0) {
                System.out.println("target not found");
                if (hOrV.equals("h")) {
                    for (int i = y; i < y + 2; ++i) {
                        for (int j = x; j < x + 5; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                            if (Oponent.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 0;
                                whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                                whosTurn = 1;
                            }
                        }
                    }
                }
                if (hOrV.equals("v")) {
                    for (int i = y; i < y + 5; i++) {
                        for (int j = x; j < x + 2; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                            if (Oponent.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 0;
                                whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                                whosTurn = 1;
                            }
                        }
                    }
                }
            } else {
                System.out.println(counter + " pieces of rival's ships was damaged");
                if (hOrV.equals("h")) {
                    for (int i = y; i < y + 2; ++i) {
                        for (int j = x; j < x + 5; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                            if (Oponent.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 0;
                                whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                                whosTurn = 1;
                            }
                        }
                    }
                }
                if (hOrV.equals("v")) {
                    for (int i = y; i < y + 5; i++) {
                        for (int j = x; j < x + 2; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                            if (Oponent.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 0;
                                whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                                whosTurn = 1;
                            }
                        }
                    }
                }
            }
        } else if (whosTurn == 0) {
            Oponent.decreaseAirPlane();
            int counter = 0;
            if (hOrV.equals("h")) {
                for (int i = y; i < y + 2; ++i) {
                    for (int j = (x - 1) * 3; j < (x - 1) * 3 + 15; ++j) {
                        if (starter.getTheGrid()[i - 1][j] == 'S' || starter.getTheGrid()[i - 1][j] == 'I')
                            counter += 1;
                    }
                }
            }
            if (hOrV.equals("v")) {
                for (int i = y; i < y + 5; ++i) {
                    for (int j = (x - 1) * 3; j < (x - 1) * 3 + 6; ++j) {
                        if (starter.getTheGrid()[i - 1][j] == 'S' || starter.getTheGrid()[i - 1][j] == 'I')
                            counter += 1;
                    }
                }
            }
            if (counter == 0) {
                System.out.println("target not found");
                if (hOrV.equals("h")) {
                    for (int i = y; i < y + 2; ++i) {
                        for (int j = x; j < x + 5; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                            if (starter.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 1;
                                whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                                whosTurn = 0;
                            }
                        }
                    }
                }
                if (hOrV.equals("v")) {
                    for (int i = y; i < y + 5; i++) {
                        for (int j = x; j < x + 2; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                            if (starter.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 1;
                                whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                                whosTurn = 0;
                            }
                        }
                    }
                }
            } else {
                System.out.println(counter + " pieces of rival's ships was damaged");
                if (hOrV.equals("h")) {
                    for (int i = y; i < y + 2; ++i) {
                        for (int j = x; j < x + 5; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                            if (starter.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 1;
                                whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                                whosTurn = 0;
                            }
                        }
                    }
                }
                if (hOrV.equals("v")) {
                    for (int i = y; i < y + 5; i++) {
                        for (int j = x; j < x + 2; ++j) {
                            whenBombHeatSheepOrMineAfterHeatingMine(starter, j, i, starterGridToShowToOponent);
                            if (starter.getTheGrid()[i - 1][(j - 1) * 3 + 1] == 'M') {
                                whosTurn = 1;
                                whenBombHeatSheepOrMineAfterHeatingMine(Oponent, j, i, oponentsGridToShowToStarter);
                                whosTurn = 0;
                            }
                        }
                    }
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void putScanner(Grid starter, Grid oponent, String command) {
        Pattern myPattern = Pattern.compile("^scanner (.+?),(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        int x = Integer.parseInt(myMatcher.group(1));
        int y = Integer.parseInt(myMatcher.group(2));

        if (x > 10 || x < 1 || y > 10 || y < 1) System.out.println("wrong coordination");
        else {
            if (x + 2 > 10 || y + 2 > 10) System.out.println("off the board");
            else {
                if (whosTurn == 1 && starter.getScanner() == 0) System.out.println("you don't have scanner");
                else if (whosTurn == 0 && oponent.getScanner() == 0) System.out.println("you don't have scanner");
                else {
                    Grid theGrid;
                    if (whosTurn == 1) theGrid = oponent;
                    else theGrid = starter;
                    if (whosTurn == 1) starter.decreaseScanner();
                    else if (whosTurn == 0) oponent.decreaseScanner();
                    scannerOutPut(theGrid, x, y);
                }
            }
        }
    }

    //----------------------------------------------------------------
    public static void scannerOutPut(Grid grid, int x, int y) {
        char[][] thereeXtheree = new char[3][10];
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 10; ++j) {
                if (j % 3 == 0) thereeXtheree[i][j] = '|';
                else thereeXtheree[i][j] = ' ';
            }
        }

        int rowCounter = 0;
        int columnCounter = 0;
        for (int i = y; i < y + 3; ++i) {
            for (int j = (x - 1) * 3; j < (x - 1) * 3 + 9; ++j) {
                if (grid.getTheGrid()[i - 1][j] == 'S') {
                    thereeXtheree[rowCounter][columnCounter] = 'S';
                    thereeXtheree[rowCounter][columnCounter + 1] = 'X';
                }
                columnCounter += 1;
            }
            rowCounter += 1;
            columnCounter = 0;
        }

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 10; ++j) {
                System.out.print(thereeXtheree[i][j]);
            }
            System.out.println("");
        }
    }
    //----------------------------------------------------------------
}
