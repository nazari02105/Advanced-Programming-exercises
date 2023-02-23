import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        mainMenu();
    }

    //------------------------------------------------------------------------------
    public static String vaslKonandeCommandHa(String command) {
        String[] splitedCommand = command.split(" ");
        ArrayList<String> ajza = new ArrayList<>();
        for (int i = 0; i < splitedCommand.length; ++i) if (!splitedCommand[i].equals("")) ajza.add(splitedCommand[i]);
        String khoroji = "";
        for (int j = 0; j < ajza.size(); ++j) {
            if (j == ajza.size() - 1) khoroji += ajza.get(j);
            else khoroji += ajza.get(j) + " ";
        }
        return khoroji;
    }

    //------------------------------------------------------------------------------
    public static int numberOfAjza(String command) {
        String[] splitedCommand = command.split(" ");
        ArrayList<String> ajza = new ArrayList<>();
        for (int i = 0; i < splitedCommand.length; ++i) if (!splitedCommand[i].equals("")) ajza.add(splitedCommand[i]);
        return ajza.size();
    }

    //------------------------------------------------------------------------------
    public static boolean commandChecker(String command, String regex) {
        Pattern myPattern = Pattern.compile(regex);
        Matcher myMatcher = myPattern.matcher(command);
        return myMatcher.find();
    }
    //------------------------------------------------------------------------------

    public static void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        String command;
        while (true) {
            command = scanner.nextLine();
            if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Register .+? .+?$"))
                register(vaslKonandeCommandHa(command));
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Login .+? .+?$"))
                login(vaslKonandeCommandHa(command), scanner);
            else if (numberOfAjza(command) == 5 && commandChecker(vaslKonandeCommandHa(command), "^Change Password .+? .+? .+?$"))
                changePassword(vaslKonandeCommandHa(command));
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Remove .+? .+?$"))
                remove(vaslKonandeCommandHa(command));
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Show All Usernames$"))
                showAllUsers();
            else if (numberOfAjza(command) == 1 && commandChecker(vaslKonandeCommandHa(command), "^Exit$"))
                System.exit(0);
            else
                System.out.println("invalid command!");
        }
    }

    //------------------------------------------------------------------------------
    public static void register(String command) {
        Pattern myPattern = Pattern.compile("^Register (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String userName = myMatcher.group(1);
        String password = myMatcher.group(2);

        Pattern patternForUserName = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForUserName = patternForUserName.matcher(userName);
        Matcher matcherForPassword = patternForUserName.matcher(password);

        if (!matcherForUserName.find()) System.out.println("invalid username!");
        else {
            if (UserName.doesUserNameExists(userName)) System.out.println("a user exists with this username");
            else {
                if (!matcherForPassword.find()) System.out.println("invalid password!");
                else {
                    if (!isPasswordStrongOrNot(password)) System.out.println("password is weak!");
                    else {
                        System.out.println("register successful!");
                        UserName newUserName = new UserName(userName, password);
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static boolean isPasswordStrongOrNot(String password) {
        int capitalLetter = 0;
        int smallLetter = 0;
        int number = 0;
        for (int i = 0; i < password.length(); ++i) {
            if (Character.isUpperCase(password.charAt(i))) capitalLetter += 1;
            else if (Character.isLowerCase(password.charAt(i))) smallLetter += 1;
            else if (Character.isDigit(password.charAt(i))) number += 1;
        }
        if (password.length() >= 5 && capitalLetter > 0 && smallLetter > 0 && number > 0) return true;
        else return false;
    }

    //------------------------------------------------------------------------------
    public static void login(String command, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^Login (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String userName = myMatcher.group(1);
        String password = myMatcher.group(2);

        Pattern patternForUserName = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForUserName = patternForUserName.matcher(userName);
        Matcher matcherForPassword = patternForUserName.matcher(password);

        if (!matcherForUserName.find()) System.out.println("invalid username!");
        else {
            if (!UserName.doesUserNameExists(userName)) System.out.println("no user exists with this username");
            else {
                if (!matcherForPassword.find()) System.out.println("invalid password!");
                else {
                    if (!UserName.checkingPassword(userName, password)) System.out.println("password is wrong!");
                    else {
                        System.out.println("login successful!");
                        UserName theUser = UserName.getObjectByUserName(userName);
                        if (theUser.getLogedInBefore() == 0) {
                            Taghvim newTaghvim = new Taghvim(userName, userName);
                            theUser.addToUserOwnTaghvims(newTaghvim.getIdOfTaghvim());
                            theUser.setLogedInBefore(1);
                            System.out.println("calendar created successfully!");
                        }
                        afterLogIn(userName, scanner);
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void changePassword(String command) {
        Pattern myPattern = Pattern.compile("^Change Password (.+?) (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String userName = myMatcher.group(1);
        String oldPassword = myMatcher.group(2);
        String newPassword = myMatcher.group(3);

        Pattern patternForUserName = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForUserName = patternForUserName.matcher(userName);
        Matcher matcherForOldPassword = patternForUserName.matcher(oldPassword);
        Matcher matcherForNewPassword = patternForUserName.matcher(newPassword);

        if (!matcherForUserName.find()) System.out.println("invalid username!");
        else {
            if (!UserName.doesUserNameExists(userName)) System.out.println("no user exists with this username");
            else {
                if (!matcherForOldPassword.find()) System.out.println("invalid old password!");
                else {
                    if (!UserName.checkingPassword(userName, oldPassword)) System.out.println("password is wrong!");
                    else {
                        if (!matcherForNewPassword.find()) System.out.println("invalid new password!");
                        else {
                            if (!isPasswordStrongOrNot(newPassword)) System.out.println("new password is weak!");
                            else {
                                System.out.println("password changed successfully!");
                                UserName.changePassword(newPassword, userName);
                            }
                        }
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void remove(String command) {
        Pattern myPattern = Pattern.compile("^Remove (.+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String userName = myMatcher.group(1);
        String password = myMatcher.group(2);

        Pattern patternForUserName = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForUserName = patternForUserName.matcher(userName);
        Matcher matcherForPassword = patternForUserName.matcher(password);

        if (!matcherForUserName.find()) System.out.println("invalid username!");
        else {
            if (!UserName.doesUserNameExists(userName)) System.out.println("no user exists with this username");
            else {
                if (!matcherForPassword.find()) System.out.println("invalid password!");
                else {
                    if (!UserName.checkingPassword(userName, password)) System.out.println("password is wrong!");
                    else {
                        System.out.println("removed successfully!");
                        UserName userNameToDelete = UserName.getObjectByUserName(userName);
                        ArrayList<Integer> allTaghvims = userNameToDelete.getUserOwnTaghvims();
                        for (int i = 0; i < allTaghvims.size(); ++i) {
                            Taghvim taghvim = Taghvim.getTheTaghvimById(allTaghvims.get(i));
                            ArrayList<String> users = taghvim.getArrayOfSharedWith();
                            for (int j = 0; j < users.size(); ++j) {
                                UserName userName1 = UserName.getObjectByUserName(users.get(j));
                                userName1.removeFromSharedTaghvims(allTaghvims.get(i));
                            }
                            Taghvim.removeTaghvim(allTaghvims.get(i));
                        }
                        UserName.removeUser(userName);
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void showAllUsers() {
        ArrayList<String> allUsers = UserName.getAllUserNames();


        if (allUsers.size() == 0) System.out.println("nothing");
        else {
            for (int i = 0; i < allUsers.size(); i++) {
                for (int j = i + 1; j < allUsers.size(); j++) {
                    if (allUsers.get(i).compareTo(allUsers.get(j)) > 0) {
                        String temp = allUsers.get(i);
                        allUsers.set(i, allUsers.get(j));
                        allUsers.set(j, temp);
                    }
                }
            }

            for (int i = 0; i < allUsers.size(); ++i) {
                System.out.println(allUsers.get(i));
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void afterLogIn(String userName, Scanner scanner) {
        int idOfActiveTaghvim = 0;
        UserName theUser = UserName.getObjectByUserName(userName);
        ArrayList<Integer> theUserOwnTaghvim = theUser.getUserOwnTaghvims();
        ArrayList<Integer> theUserSharedTaghvim = theUser.getUserSharedTaghvims();

        int counter1 = 0;
        int counter2 = 0;
        for (int i = 0; i < theUserOwnTaghvim.size(); ++i) {
            if (Taghvim.getTheTaghvimById(theUserOwnTaghvim.get(i)).getTitleOfTaghvim().equals(userName)) {
                counter1 = theUserOwnTaghvim.get(i);
                break;
            }
        }
        for (int i = 0; i < theUserSharedTaghvim.size(); ++i) {
            if (Taghvim.getTheTaghvimById(theUserSharedTaghvim.get(i)).getTitleOfTaghvim().equals(userName)) {
                counter2 = theUserSharedTaghvim.get(i);
                break;
            }
        }

        if (counter1 != 0) {
            if (counter2 == 0)
                idOfActiveTaghvim = counter1;
            if (counter2 != 0 && counter1 <= counter2)
                idOfActiveTaghvim = counter1;
        } else if (counter2 != 0) {
            if (counter1 == 0)
                idOfActiveTaghvim = counter2;
            if (counter1 != 0 && counter2 <= counter1)
                idOfActiveTaghvim = counter2;
        }

        ArrayList<Integer> allEnableTaghvims = new ArrayList<>();
        if (idOfActiveTaghvim != 0 && !allEnableTaghvims.contains(idOfActiveTaghvim))
            allEnableTaghvims.add(idOfActiveTaghvim);


        String command = "";
        while (true) {
            command = scanner.nextLine();
            if (numberOfAjza(command) == 4 && commandChecker(vaslKonandeCommandHa(command), "^Create New Calendar .+?$"))
                createTaghvim(vaslKonandeCommandHa(command), userName);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Open Calendar \\d+?$"))
                openTaghvim(vaslKonandeCommandHa(command), userName, scanner);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Enable Calendar \\d+?$"))
                enableTaghvim(vaslKonandeCommandHa(command), userName, allEnableTaghvims, theUser);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Disable Calendar \\d+$"))
                disableTaghvim(vaslKonandeCommandHa(command), userName, allEnableTaghvims, theUser);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Delete Calendar \\d+?$"))
                deleteTaghvim(vaslKonandeCommandHa(command), userName, allEnableTaghvims, theUser);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Remove Calendar \\d+?$"))
                removeTaghvim(vaslKonandeCommandHa(command), userName, allEnableTaghvims, theUser, scanner);
            else if (numberOfAjza(command) == 4 && commandChecker(vaslKonandeCommandHa(command), "^Edit Calendar \\d+? .+?$"))
                editTaghvim(vaslKonandeCommandHa(command), userName, theUser);
            else if (numberOfAjza(command) == 2 && commandChecker(vaslKonandeCommandHa(command), "^Show Calendars$"))
                showCalendars(theUser);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Show Enabled Calendars$"))
                showEnabledTaghvims(allEnableTaghvims);
            else if (numberOfAjza(command) == 1 && commandChecker(vaslKonandeCommandHa(command), "^Logout$")) {
                System.out.println("logout successful");
                break;
            } else if (numberOfAjza(command) == 2 && commandChecker(vaslKonandeCommandHa(command), "^Show \\d\\d\\d\\d_\\d\\d_\\d\\d$"))
                showEventsAndTasksOfEnabledTaghvims(allEnableTaghvims, vaslKonandeCommandHa(command));
                //write other commands here up of share calendar
            else if (commandChecker(vaslKonandeCommandHa(command), "^(.+? (\\d+?)) (.+?)$"))
                shareTaghvim(command, userName, theUser);
            else
                System.out.println("invalid command!");
        }
    }

    //------------------------------------------------------------------------------
    public static void showEventsAndTasksOfEnabledTaghvims(ArrayList<Integer> allEnabledTaghvims, String command) {
        Pattern myPattern = Pattern.compile("^Show ((\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d))$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        String wholeDate = myMatcher.group(1);
        int year = Integer.parseInt(myMatcher.group(2));
        int month = Integer.parseInt(myMatcher.group(3));
        int day = Integer.parseInt(myMatcher.group(4));

        ArrayList<String> eventsToShow = new ArrayList<>();
        ArrayList<String> tasksToShow = new ArrayList<>();

        if (year < 0 || month > 12 || month < 1 || day > 31 || day < 1) System.out.println("date is invalid!");
        else if (month == 2 && day > 29) System.out.println("date is invalid!");
        else if (month == 4 && day > 30) System.out.println("date is invalid!");
        else if (month == 6 && day > 30) System.out.println("date is invalid!");
        else if (month == 9 && day > 30) System.out.println("date is invalid!");
        else if (month == 11 && day > 30) System.out.println("date is invalid!");
        else {
            for (int i = 0; i < allEnabledTaghvims.size(); ++i) {
                Taghvim taghvim = Taghvim.getTheTaghvimById(allEnabledTaghvims.get(i));
                ArrayList<String> eventsOfTaghvim = taghvim.allEventsOfTaghvim();
                for (int j = 0; j < eventsOfTaghvim.size(); ++j) {
                    Event event = Event.getEventByTitle(eventsOfTaghvim.get(j));
                    Pattern startDatePattern = Pattern.compile("^(\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
                    Matcher startDateMatcher = startDatePattern.matcher(event.getStartDate());
                    startDateMatcher.find();
                    int startDateYear = Integer.parseInt(startDateMatcher.group(1));
                    int startDateMonth = Integer.parseInt(startDateMatcher.group(2));
                    int startDateDay = Integer.parseInt(startDateMatcher.group(3));
                    if (event.getEndDate() != null) {
                        Pattern endDatePattern = Pattern.compile("^(\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
                        Matcher endDateMatcher = endDatePattern.matcher(event.getEndDate());
                        endDateMatcher.find();
                        int endDateYear = Integer.parseInt(endDateMatcher.group(1));
                        int endDateMonth = Integer.parseInt(endDateMatcher.group(2));
                        int endDateDay = Integer.parseInt(endDateMatcher.group(3));

                        if (event.getPeriodOfRepeat().equals("D")) {
                            if (year > startDateYear && year < endDateYear) eventsToShow.add(event.getTitle());
                            else if (year == startDateYear && year == endDateYear) {
                                if (month > startDateMonth && month < endDateMonth) eventsToShow.add(event.getTitle());
                                else if (month == startDateMonth && month == endDateMonth) {
                                    if (day >= startDateDay && day <= endDateDay) eventsToShow.add(event.getTitle());
                                } else if (month == startDateMonth && day >= startDateDay)
                                    eventsToShow.add(event.getTitle());
                                else if (month == endDateMonth && day <= endDateDay) eventsToShow.add(event.getTitle());
                            } else if (year == startDateYear) {
                                if (month > startDateMonth) eventsToShow.add(event.getTitle());
                                else if (month == startDateMonth && day >= startDateDay)
                                    eventsToShow.add(event.getTitle());
                            } else if (year == endDateYear) {
                                if (month < endDateMonth) eventsToShow.add(event.getTitle());
                                else if (month == endDateMonth && day <= endDateDay) eventsToShow.add(event.getTitle());
                            }
                        } else if (event.getPeriodOfRepeat().equals("W")) {
                            String endDate = event.getEndDate();
                            Calendar time = Calendar.getInstance();
                            time.set(startDateYear, startDateMonth - 1, startDateDay);
                            int tureOrNot = 0;
                            if (year > startDateYear && year < endDateYear) tureOrNot = 1;
                            else if (year == startDateYear && year == endDateYear) {
                                if (month > startDateMonth && month < endDateMonth) tureOrNot = 1;
                                else if (month == startDateMonth && month == endDateMonth) {
                                    if (day >= startDateDay && day <= endDateDay) tureOrNot = 1;
                                } else if (month == startDateMonth && day >= startDateDay) tureOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) tureOrNot = 1;
                            } else if (year == startDateYear) {
                                if (month > startDateMonth) tureOrNot = 1;
                                else if (month == startDateMonth && day >= startDateDay) tureOrNot = 1;
                            } else if (year == endDateYear) {
                                if (month < endDateMonth) tureOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) tureOrNot = 1;
                            }
                            if (tureOrNot == 1) {
                                while (time.get(Calendar.YEAR) <= endDateYear) {
                                    String now = "";
                                    if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9)
                                        now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                    else if (time.get(Calendar.MONTH) < 9)
                                        now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                    else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                        now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                    else
                                        now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                    if (now.equals(wholeDate)) {
                                        eventsToShow.add(event.getTitle());
                                        break;
                                    }
                                    time.add(Calendar.WEEK_OF_YEAR, 1);
                                }
                            }
                        } else if (event.getPeriodOfRepeat().equals("M")) {
                            int trueOrNot = 0;
                            if (year > startDateYear && year < endDateYear) trueOrNot = 1;
                            else if (year == startDateYear && year == endDateYear) {
                                if (month > startDateMonth && month < endDateMonth) trueOrNot = 1;
                                else if (month == startDateMonth && month == endDateMonth) {
                                    if (day >= startDateDay && day <= endDateDay) trueOrNot = 1;
                                } else if (month == startDateMonth && day >= startDateDay) trueOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) trueOrNot = 1;
                            } else if (year == startDateYear) {
                                if (month > startDateMonth) trueOrNot = 1;
                                else if (month == startDateMonth && day >= startDateDay) trueOrNot = 1;
                            } else if (year == endDateYear) {
                                if (month < endDateMonth) trueOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) trueOrNot = 1;
                            }
                            if (trueOrNot == 1) {
                                if (startDateDay == day) eventsToShow.add(event.getTitle());
                            }
                        }
                    } else if (event.getNumberOfRepeat() != null) {
                        int repeat = event.getNumberOfRepeat();
                        Calendar time = Calendar.getInstance();
                        time.set(startDateYear, startDateMonth - 1, startDateDay);
                        int trueOrNot = 0;
                        if (startDateYear < year) trueOrNot = 1;
                        else if (startDateYear == year && startDateMonth < month) trueOrNot = 1;
                        else if (startDateYear == year && startDateMonth == month && startDateDay <= day) trueOrNot = 1;
                        if (event.getPeriodOfRepeat().equals("D") && trueOrNot == 1) {
                            for (int k = 0; k < repeat + 1; ++k) {
                                String now = "";
                                if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                if (now.equals(wholeDate)) {
                                    eventsToShow.add(event.getTitle());
                                    break;
                                }
                                time.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        } else if (event.getPeriodOfRepeat().equals("W") && trueOrNot == 1) {
                            for (int k = 0; k < repeat + 1; ++k) {
                                String now = "";
                                if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                if (now.equals(wholeDate)) {
                                    eventsToShow.add(event.getTitle());
                                    break;
                                }
                                time.add(Calendar.WEEK_OF_YEAR, 1);
                            }
                        } else if (event.getPeriodOfRepeat().equals("M") && trueOrNot == 1) {
                            for (int k = 0; k < repeat + 1; ++k) {
                                String now = "";
                                if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9) {
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                } else if (time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                if (now.equals(wholeDate)) {
                                    eventsToShow.add(event.getTitle());
                                    break;
                                }
                                time.add(Calendar.MONTH, 1);
                            }
                        }
                    }
                }
                ArrayList<String> tasksOfTaghvim = taghvim.allTasksOfTaghvim();
                for (int j = 0; j < tasksOfTaghvim.size(); ++j) {
                    //
                    Task task = Task.getTaskByTitle(tasksOfTaghvim.get(j));
                    Pattern startDatePattern = Pattern.compile("^(\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
                    Matcher startDateMatcher = startDatePattern.matcher(task.getStartDate());
                    startDateMatcher.find();
                    int startDateYear = Integer.parseInt(startDateMatcher.group(1));
                    int startDateMonth = Integer.parseInt(startDateMatcher.group(2));
                    int startDateDay = Integer.parseInt(startDateMatcher.group(3));
                    if (task.getEndDate() != null) {
                        Pattern endDatePattern = Pattern.compile("^(\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
                        Matcher endDateMatcher = endDatePattern.matcher(task.getEndDate());
                        endDateMatcher.find();
                        int endDateYear = Integer.parseInt(endDateMatcher.group(1));
                        int endDateMonth = Integer.parseInt(endDateMatcher.group(2));
                        int endDateDay = Integer.parseInt(endDateMatcher.group(3));

                        if (task.getPeriodOfRepeat().equals("D")) {
                            if (year > startDateYear && year < endDateYear) tasksToShow.add(task.getTitle());
                            else if (year == startDateYear && year == endDateYear) {
                                if (month > startDateMonth && month < endDateMonth) tasksToShow.add(task.getTitle());
                                else if (month == startDateMonth && month == endDateMonth) {
                                    if (day >= startDateDay && day <= endDateDay) tasksToShow.add(task.getTitle());
                                } else if (month == startDateMonth && day >= startDateDay)
                                    tasksToShow.add(task.getTitle());
                                else if (month == endDateMonth && day <= endDateDay) tasksToShow.add(task.getTitle());
                            } else if (year == startDateYear) {
                                if (month > startDateMonth) tasksToShow.add(task.getTitle());
                                else if (month == startDateMonth && day >= startDateDay)
                                    tasksToShow.add(task.getTitle());
                            } else if (year == endDateYear) {
                                if (month < endDateMonth) tasksToShow.add(task.getTitle());
                                else if (month == endDateMonth && day <= endDateDay) tasksToShow.add(task.getTitle());
                            }
                        } else if (task.getPeriodOfRepeat().equals("W")) {
                            String endDate = task.getEndDate();
                            Calendar time = Calendar.getInstance();
                            time.set(startDateYear, startDateMonth - 1, startDateDay);
                            int tureOrNot = 0;
                            if (year > startDateYear && year < endDateYear) tureOrNot = 1;
                            else if (year == startDateYear && year == endDateYear) {
                                if (month > startDateMonth && month < endDateMonth) tureOrNot = 1;
                                else if (month == startDateMonth && month == endDateMonth) {
                                    if (day >= startDateDay && day <= endDateDay) tureOrNot = 1;
                                } else if (month == startDateMonth && day >= startDateDay) tureOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) tureOrNot = 1;
                            } else if (year == startDateYear) {
                                if (month > startDateMonth) tureOrNot = 1;
                                else if (month == startDateMonth && day >= startDateDay) tureOrNot = 1;
                            } else if (year == endDateYear) {
                                if (month < endDateMonth) tureOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) tureOrNot = 1;
                            }
                            if (tureOrNot == 1) {
                                while (time.get(Calendar.YEAR) <= endDateYear) {
                                    String now = "";
                                    if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9)
                                        now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                    else if (time.get(Calendar.MONTH) < 9)
                                        now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                    else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                        now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                    else
                                        now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                    if (now.equals(wholeDate)) {
                                        tasksToShow.add(task.getTitle());
                                        break;
                                    }
                                    time.add(Calendar.WEEK_OF_YEAR, 1);
                                }
                            }
                        } else if (task.getPeriodOfRepeat().equals("M")) {
                            int trueOrNot = 0;
                            if (year > startDateYear && year < endDateYear) trueOrNot = 1;
                            else if (year == startDateYear && year == endDateYear) {
                                if (month > startDateMonth && month < endDateMonth) trueOrNot = 1;
                                else if (month == startDateMonth && month == endDateMonth) {
                                    if (day >= startDateDay && day <= endDateDay) trueOrNot = 1;
                                } else if (month == startDateMonth && day >= startDateDay) trueOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) trueOrNot = 1;
                            } else if (year == startDateYear) {
                                if (month > startDateMonth) trueOrNot = 1;
                                else if (month == startDateMonth && day >= startDateDay) trueOrNot = 1;
                            } else if (year == endDateYear) {
                                if (month < endDateMonth) trueOrNot = 1;
                                else if (month == endDateMonth && day <= endDateDay) trueOrNot = 1;
                            }
                            if (trueOrNot == 1) {
                                if (startDateDay == day) tasksToShow.add(task.getTitle());
                            }
                        }
                    } else if (task.getNumberOfRepeat() != null) {
                        int repeat = task.getNumberOfRepeat();
                        Calendar time = Calendar.getInstance();
                        time.set(startDateYear, startDateMonth - 1, startDateDay);
                        int trueOrNot = 0;
                        if (startDateYear < year) trueOrNot = 1;
                        else if (startDateYear == year && startDateMonth < month) trueOrNot = 1;
                        else if (startDateYear == year && startDateMonth == month && startDateDay <= day) trueOrNot = 1;
                        if (task.getPeriodOfRepeat().equals("D") && trueOrNot == 1) {
                            for (int k = 0; k < repeat + 1; ++k) {
                                String now = "";
                                if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                if (now.equals(wholeDate)) {
                                    tasksToShow.add(task.getTitle());
                                    break;
                                }
                                time.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        } else if (task.getPeriodOfRepeat().equals("W") && trueOrNot == 1) {
                            for (int k = 0; k < repeat + 1; ++k) {
                                String now = "";
                                if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                if (now.equals(wholeDate)) {
                                    tasksToShow.add(task.getTitle());
                                    break;
                                }
                                time.add(Calendar.WEEK_OF_YEAR, 1);
                            }
                        } else if (task.getPeriodOfRepeat().equals("M") && trueOrNot == 1) {
                            for (int k = 0; k < repeat + 1; ++k) {
                                String now = "";
                                if (time.get(Calendar.DAY_OF_MONTH) < 10 && time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.MONTH) < 9)
                                    now = time.get(Calendar.YEAR) + "_" + 0 + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                else if (time.get(Calendar.DAY_OF_MONTH) < 10)
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + 0 + time.get(Calendar.DAY_OF_MONTH);
                                else
                                    now = time.get(Calendar.YEAR) + "_" + (time.get(Calendar.MONTH) + 1) + "_" + time.get(Calendar.DAY_OF_MONTH);
                                if (now.equals(wholeDate)) {
                                    tasksToShow.add(task.getTitle());
                                    break;
                                }
                                time.add(Calendar.MONTH, 1);
                            }
                        }
                    }
                    //
                }
            }
            printingShowDate(eventsToShow, tasksToShow, wholeDate);
        }
    }

    //------------------------------------------------------------------------------
    public static void printingShowDate(ArrayList<String> allEvents, ArrayList<String> allTasks, String wholeDate) {
        System.out.println("events on " + wholeDate + ":");
        for (int i = 0; i < allEvents.size(); ++i) {
            for (int j = i + 1; j < allEvents.size(); ++j) {
                if (allEvents.get(i).compareTo(allEvents.get(j)) > 0) {
                    String temp = allEvents.get(i);
                    allEvents.set(i, allEvents.get(j));
                    allEvents.set(j, temp);
                }
            }
        }
        for (int i = 0; i < allEvents.size(); ++i) {
            System.out.println("Event: " + allEvents.get(i) + " " + Event.getEventByTitle(allEvents.get(i)).getDoesItHasLink());
        }

        System.out.println("tasks on " + wholeDate + ":");
        for (int i = 0; i < allTasks.size(); ++i) {
            for (int j = i + 1; j < allTasks.size(); ++j) {
                Task iTask = Task.getTaskByTitle(allTasks.get(i));
                Task jTask = Task.getTaskByTitle(allTasks.get(j));

                Pattern timePattern = Pattern.compile("(\\d\\d)_(\\d\\d)");
                Matcher timeIMatcher = timePattern.matcher(iTask.getStartTime());
                Matcher timeJMatcher = timePattern.matcher(jTask.getStartTime());

                timeIMatcher.find();
                timeJMatcher.find();

                int iStartHour = Integer.parseInt(timeIMatcher.group(1));
                int iStartMinute = Integer.parseInt(timeIMatcher.group(2));
                int jStartHour = Integer.parseInt(timeJMatcher.group(1));
                int jStartMinute = Integer.parseInt(timeJMatcher.group(2));

                if (iStartHour > jStartHour) {
                    String temp = allTasks.get(i);
                    allTasks.set(i, allTasks.get(j));
                    allTasks.set(j, temp);
                } else if (iStartHour == jStartHour && iStartMinute > jStartHour) {
                    String temp = allTasks.get(i);
                    allTasks.set(i, allTasks.get(j));
                    allTasks.set(j, temp);
                }

                if (iStartHour == jStartHour && iStartMinute == jStartMinute && allTasks.get(i).compareTo(allTasks.get(j)) > 0) {
                    String temp = allTasks.get(i);
                    allTasks.set(i, allTasks.get(j));
                    allTasks.set(j, temp);
                }
            }
        }
        for (int i = 0; i < allTasks.size(); ++i) {
            System.out.println("Task: " + allTasks.get(i) + " " + Task.getTaskByTitle(allTasks.get(i)).getDoesItHasLink() + " " + Task.getTaskByTitle(allTasks.get(i)).getStartTime() + " " + Task.getTaskByTitle(allTasks.get(i)).getEndTime());
        }
    }

    //------------------------------------------------------------------------------
    public static void showCalendars(UserName theUser) {
        ArrayList<Integer> allUsersOwnTaghvims = theUser.getUserOwnTaghvims();
        if (allUsersOwnTaghvims.size() == 0) System.out.println("nothing");
        else {
            for (int i = 0; i < allUsersOwnTaghvims.size(); ++i) {
                for (int j = i + 1; j < allUsersOwnTaghvims.size(); ++j) {
                    if (allUsersOwnTaghvims.get(i) > allUsersOwnTaghvims.get(j)) {
                        Integer temp = allUsersOwnTaghvims.get(i);
                        allUsersOwnTaghvims.set(i, allUsersOwnTaghvims.get(j));
                        allUsersOwnTaghvims.set(j, temp);
                    }
                }
            }
        }
        for (int i = 0; i < allUsersOwnTaghvims.size(); ++i) {
            System.out.println("Calendar: " + Taghvim.getTheTaghvimById(allUsersOwnTaghvims.get(i)).getTitleOfTaghvim() + " " + allUsersOwnTaghvims.get(i));
        }
    }

    //------------------------------------------------------------------------------
    public static void showEnabledTaghvims(ArrayList<Integer> allEnabledTaghvims) {
        if (allEnabledTaghvims.size() == 0) System.out.println("nothing");
        else {
            for (int i = 0; i < allEnabledTaghvims.size(); ++i) {
                for (int j = i + 1; j < allEnabledTaghvims.size(); ++j) {
                    if (allEnabledTaghvims.get(i) > allEnabledTaghvims.get(j)) {
                        Integer temp = allEnabledTaghvims.get(i);
                        allEnabledTaghvims.set(i, allEnabledTaghvims.get(j));
                        allEnabledTaghvims.set(j, temp);
                    }
                }
            }
        }
        for (int i = 0; i < allEnabledTaghvims.size(); ++i) {
            if (Taghvim.getTheTaghvimById(allEnabledTaghvims.get(i)) != null) {
                System.out.println("Calendar: " + Taghvim.getTheTaghvimById(allEnabledTaghvims.get(i)).getTitleOfTaghvim() + " " + allEnabledTaghvims.get(i));
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void createTaghvim(String command, String userName) {
        Pattern myPattern = Pattern.compile("^Create New Calendar (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String title = myMatcher.group(1);

        Pattern patternForTitle = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForTitle = patternForTitle.matcher(title);

        if (!matcherForTitle.find()) System.out.println("invalid title!");
        else {
            System.out.println("calendar created successfully!");
            Taghvim newTaghvim = new Taghvim(userName, title);
            UserName.getObjectByUserName(userName).addToUserOwnTaghvims(newTaghvim.getIdOfTaghvim());
        }
    }

    //------------------------------------------------------------------------------
    public static void openTaghvim(String command, String userName, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^Open Calendar (\\d+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        try {
            int id = Integer.parseInt(myMatcher.group(1));

            if (!Taghvim.doesItHasTaghvim(id)) System.out.println("there is no calendar with this ID!");
            else {
                int userContain = 0;
                UserName theUser = UserName.getObjectByUserName(userName);
                if (theUser.doesUserOwnTaghvimsContain(id) || theUser.doesUserSharedTaghvimsContain(id))
                    userContain = 1;
                if (userContain == 0) System.out.println("you have no calendar with this ID!");
                else if (userContain == 1) {
                    System.out.println("calendar opened successfully!");
                    taghvimMenu(userName, scanner, id);
                }
            }
        } catch (Exception e) {
            System.out.println("there is no calendar with this ID!");
        }
    }

    //------------------------------------------------------------------------------
    public static void enableTaghvim(String command, String userName, ArrayList<Integer> allEnabledTaghvims, UserName userObject) {
        Pattern myPattern = Pattern.compile("^Enable Calendar (\\d+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        try {
            int taghvimId = Integer.parseInt(myMatcher.group(1));

            if (!Taghvim.doesItHasTaghvim(taghvimId)) System.out.println("there is no calendar with this ID!");
            else {
                if (!userObject.getUserOwnTaghvims().contains(taghvimId) && !userObject.getUserSharedTaghvims().contains(taghvimId))
                    System.out.println("you have no calendar with this ID!");
                else {
                    System.out.println("calendar enabled successfully!");
                    if (!allEnabledTaghvims.contains(taghvimId)) allEnabledTaghvims.add(taghvimId);
                }
            }
        } catch (Exception e) {
            System.out.println("there is no calendar with this ID!");
        }
    }

    //------------------------------------------------------------------------------
    public static void disableTaghvim(String command, String userName, ArrayList<Integer> allEnabledTaghvims, UserName userObject) {
        Pattern myPattern = Pattern.compile("^Disable Calendar (\\d+)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        try {
            int taghvimId = Integer.parseInt(myMatcher.group(1));

            if (!Taghvim.doesItHasTaghvim(taghvimId)) System.out.println("there is no calendar with this ID!");
            else {
                if (!userObject.getUserOwnTaghvims().contains(taghvimId) && !userObject.getUserSharedTaghvims().contains(taghvimId))
                    System.out.println("you have no calendar with this ID!");
                else {
                    System.out.println("calendar disabled successfully!");
                    if (allEnabledTaghvims.contains(taghvimId)) allEnabledTaghvims.remove((Integer) taghvimId);
                }
            }
        } catch (Exception e) {
            System.out.println("there is no calendar with this ID!");
        }
    }

    //------------------------------------------------------------------------------
    public static void deleteTaghvim(String command, String userName, ArrayList<Integer> allEnabledTaghvims, UserName userObject) {
        Pattern myPattern = Pattern.compile("^Delete Calendar (\\d+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        try {
            int taghvimId = Integer.parseInt(myMatcher.group(1));

            if (!Taghvim.doesItHasTaghvim(taghvimId)) System.out.println("there is no calendar with this ID!");
            else {
                if (!userObject.getUserOwnTaghvims().contains(taghvimId) && !userObject.getUserSharedTaghvims().contains(taghvimId))
                    System.out.println("you have no calendar with this ID!");
                else {
                    if (!userObject.getUserOwnTaghvims().contains(taghvimId))
                        System.out.println("you don't have access to delete this calendar!");
                    else {
                        System.out.println("calendar deleted!");
                        Taghvim myTaghvim = Taghvim.getTheTaghvimById(taghvimId);
                        for (int i = 0; i < myTaghvim.getArrayOfSharedWith().size(); ++i) {
                            UserName.getObjectByUserName(myTaghvim.getArrayOfSharedWith().get(i)).removeFromSharedTaghvims(taghvimId);
                        }
                        Taghvim.removeTaghvim(taghvimId);
                        userObject.removeFromUserOwnTaghvims(taghvimId);
                        if (allEnabledTaghvims.contains(taghvimId)) allEnabledTaghvims.remove((Integer) taghvimId);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("there is no calendar with this ID!");
        }
    }

    //------------------------------------------------------------------------------
    public static void removeTaghvim(String command, String userName, ArrayList<Integer> allEnabledTaghvims, UserName userObject, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^Remove Calendar (\\d+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        try {
            int taghvimId = Integer.parseInt(myMatcher.group(1));

            if (!Taghvim.doesItHasTaghvim(taghvimId)) System.out.println("there is no calendar with this ID!");
            else {
                if (!userObject.getUserOwnTaghvims().contains(taghvimId) && !userObject.getUserSharedTaghvims().contains(taghvimId))
                    System.out.println("you have no calendar with this ID!");
                else {
                    if (userObject.getUserOwnTaghvims().contains(taghvimId)) {
                        System.out.println("do you want to delete this calendar?");
                        String yesOrNo = scanner.nextLine();
                        if (yesOrNo.equals("no")) System.out.println("OK!");
                        else if (yesOrNo.equals("yes"))
                            deleteTaghvim("Delete Calendar " + taghvimId, userName, allEnabledTaghvims, userObject);
                    } else if (userObject.getUserSharedTaghvims().contains(taghvimId)) {
                        System.out.println("calendar removed!");
                        userObject.getUserSharedTaghvims().remove((Integer) taghvimId);
                        Taghvim.getTheTaghvimById(taghvimId).removeFromSharedWith(userName);
                        allEnabledTaghvims.remove((Integer) taghvimId);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("there is no calendar with this ID!");
        }
    }

    //------------------------------------------------------------------------------
    public static void editTaghvim(String command, String userName, UserName userObject) {
        Pattern myPattern = Pattern.compile("^Edit Calendar (\\d+?) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        try {
            int taghvimId = Integer.parseInt(myMatcher.group(1));
            String newTitle = myMatcher.group(2);

            Pattern patternForNewTitle = Pattern.compile("^[A-Za-z0-9_]+$");
            Matcher matcherForNewTititle = patternForNewTitle.matcher(newTitle);

            if (!Taghvim.doesItHasTaghvim(taghvimId)) System.out.println("there is no calendar with this ID!");
            else {
                if (!userObject.getUserOwnTaghvims().contains(taghvimId) && !userObject.getUserSharedTaghvims().contains(taghvimId))
                    System.out.println("you have no calendar with this ID!");
                else {
                    if (!userObject.getUserOwnTaghvims().contains(taghvimId))
                        System.out.println("you don't have access to edit this calendar!");
                    else {
                        if (!matcherForNewTititle.find()) System.out.println("invalid title!");
                        else {
                            System.out.println("calendar title edited!");
                            Taghvim myTaghvim = Taghvim.getTheTaghvimById(taghvimId);
                            myTaghvim.setTitleOfTaghvim(newTitle);
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("there is no calendar with this ID!");
        }
    }

    //------------------------------------------------------------------------------
    public static void shareTaghvim(String command, String userName, UserName userObject) {
        Pattern myPattern = Pattern.compile("^(.+? (\\d+?)) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        if (numberOfAjza(myMatcher.group(1)) != 3 || !commandChecker(vaslKonandeCommandHa(myMatcher.group(1)), "^Share Calendar \\d+?$")) {
            System.out.println("invalid command!");
        } else {

            try {
                int taghvimId = Integer.parseInt(myMatcher.group(2));
                String allUsers = myMatcher.group(3);

                if (!Taghvim.doesItHasTaghvim(taghvimId)) System.out.println("there is no calendar with this ID!");
                else {
                    if (!userObject.getUserOwnTaghvims().contains(taghvimId) && !userObject.getUserSharedTaghvims().contains(taghvimId))
                        System.out.println("you have no calendar with this ID!");
                    else {
                        if (!userObject.getUserOwnTaghvims().contains(taghvimId))
                            System.out.println("you don't have access to share this calendar!");
                        else {
                            String[] allSepragedUsers = allUsers.split(" ");
                            ArrayList<String> users = new ArrayList<>();
                            for (int i = 0; i < allSepragedUsers.length; ++i) {
                                if (!allSepragedUsers[i].equals("") && !users.contains(allSepragedUsers[i]))
                                    users.add(allSepragedUsers[i]);
                            }
                            Pattern patternForUserName = Pattern.compile("^[A-Za-z0-9_]+$");
                            int counter = 0;
                            for (int i = 0; i < users.size(); ++i) {
                                Matcher matcherForUserName = patternForUserName.matcher(users.get(i));
                                if (matcherForUserName.find()) counter += 1;
                            }
                            if (counter != users.size()) System.out.println("invalid username!");
                            else {
                                int counter1 = 0;
                                for (int i = 0; i < users.size(); ++i) {
                                    if (UserName.doesUserNameExists(users.get(i))) counter1 += 1;
                                }
                                if (counter1 != users.size()) System.out.println("no user exists with this username!");
                                else {
                                    System.out.println("calendar shared!");
                                    Taghvim myTaghvim = Taghvim.getTheTaghvimById(taghvimId);
                                    for (int i = 0; i < users.size(); ++i) {
                                        myTaghvim.addToSharedWith(users.get(i));
                                        UserName theUsers = UserName.getObjectByUserName(users.get(i));
                                        theUsers.addToSharedTaghvims(taghvimId);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("there is no calendar with this ID!");
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void taghvimMenu(String userName, Scanner scanner, int id) {
        String command = "";
        while (true) {
            command = scanner.nextLine();
            if ((numberOfAjza(command) == 7 || numberOfAjza(command) == 6) && commandChecker(vaslKonandeCommandHa(command), "^Add Event (.+?) (\\d\\d\\d\\d_\\d\\d_\\d\\d) ([^ ]+?) ([DWM]{1}[ ])?([TF]?)$"))
                addEvent(vaslKonandeCommandHa(command), userName, id, scanner);
            else if ((numberOfAjza(command) == 9 || numberOfAjza(command) == 8) && commandChecker(vaslKonandeCommandHa(command), "^Add Task (.+?) (\\d\\d_\\d\\d) (\\d\\d_\\d\\d) (\\d\\d\\d\\d_\\d\\d_\\d\\d) ([^ ]+?) ([DWM]{1}[ ])?([TF]?)$"))
                addTadk(vaslKonandeCommandHa(command), userName, id, scanner);
            else if ((numberOfAjza(command) == 5 || numberOfAjza(command) == 7) && commandChecker(vaslKonandeCommandHa(command), "^Edit Event .+? .+? .+?$"))
                edetEvent(vaslKonandeCommandHa(command), userName, id, scanner);
            else if ((numberOfAjza(command) == 5 || numberOfAjza(command) == 6 || numberOfAjza(command) == 7) && commandChecker(vaslKonandeCommandHa(command), "^Edit Task .+? .+? .+?$"))
                editTask(vaslKonandeCommandHa(command), userName, id, scanner);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Delete Event .+?$"))
                deleteEvent(vaslKonandeCommandHa(command), userName, id);
            else if (numberOfAjza(command) == 3 && commandChecker(vaslKonandeCommandHa(command), "^Delete Task .+?$"))
                deleteTask(vaslKonandeCommandHa(command), userName, id);
            else if (numberOfAjza(command) == 1 && commandChecker(vaslKonandeCommandHa(command), "^Back$"))
                break;
            else if (numberOfAjza(command) == 2 && commandChecker(vaslKonandeCommandHa(command), "^Show Events$"))
                showEvents(userName, id);
            else if (numberOfAjza(command) == 2 && commandChecker(vaslKonandeCommandHa(command), "^Show tasks$"))
                showTasks(userName, id);
            else
                System.out.println("invalid command!");
        }
    }

    //------------------------------------------------------------------------------
    public static void addEvent(String command, String userName, int id, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^Add Event (.+?) (.+?) (.+?) ([DWM][ ])?(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String title = myMatcher.group(1);
        String startDate = myMatcher.group(2);
        String threeMode = myMatcher.group(3);
        String DWM = myMatcher.group(4);
        if (DWM != null) DWM = DWM.substring(0, DWM.length() - 1);
        String TF = myMatcher.group(5);

        Pattern patternForTitle = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForTitle = patternForTitle.matcher(title);
        Pattern patternForStartDate = Pattern.compile("^(\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
        Matcher matcherForStartDate = patternForStartDate.matcher(startDate);
        Matcher matcherForEndDate = patternForStartDate.matcher(threeMode);

        UserName theUser = UserName.getObjectByUserName(userName);

        if (!theUser.doesUserOwnTaghvimsContain(id)) System.out.println("you don't have access to do this!");
        else {
            if (!matcherForTitle.find()) System.out.println("invalid title!");
            else {
                boolean matcherForStartDateBoolean = matcherForStartDate.find();
                int year = Integer.parseInt(matcherForStartDate.group(1));
                int month = Integer.parseInt(matcherForStartDate.group(2));
                int day = Integer.parseInt(matcherForStartDate.group(3));
                if (!matcherForStartDateBoolean) System.out.println("invalid start date!");
                else if (!doesTheDateValid(matcherForStartDate)) System.out.println("invalid start date!");
                else if (month == 2 && day > 28) System.out.println("invalid start date!");
                else if (month == 4 && day > 30) System.out.println("invalid start date!");
                else if (month == 6 && day > 30) System.out.println("invalid start date!");
                else if (month == 9 && day > 30) System.out.println("invalid start date!");
                else if (month == 11 && day > 30) System.out.println("invalid start date!");
                else {
                    if (threeMode.contains("_")) {
                        matcherForEndDate.find();
                        int year1 = Integer.parseInt(matcherForEndDate.group(1));
                        int month1 = Integer.parseInt(matcherForEndDate.group(2));
                        int day1 = Integer.parseInt(matcherForEndDate.group(3));
                        if (!doesTheDateValid(matcherForEndDate)) System.out.println("invalid end date!");
                        else if (month1 == 2 && day1 > 28) System.out.println("invalid end date!");
                        else if (month1 == 4 && day1 > 30) System.out.println("invalid end date!");
                        else if (month1 == 6 && day1 > 30) System.out.println("invalid end date!");
                        else if (month1 == 9 && day1 > 30) System.out.println("invalid end date!");
                        else if (month1 == 11 && day1 > 30) System.out.println("invalid end date!");
                        else {
                            if (Event.thisEventExists(title))
                                System.out.println("there is another event with this title!");
                            else {
                                System.out.println("event added successfully!");
                                Event newEvent = new Event(title, startDate);
                                newEvent.setEndDate(threeMode);
                                newEvent.setPeriodOfRepeat(DWM);
                                newEvent.setDoesItHasLink(TF);
                                if (TF.equals("T")) {
                                    String link = scanner.nextLine();
                                    newEvent.setLink(link);
                                }
                                Taghvim theTaghvim = Taghvim.getTheTaghvimById(id);
                                theTaghvim.addToTaghvimEvents(title);
//                                System.out.println("event added successfully!");
                            }
                        }
                    } else if (threeMode.equals("None")) {
                        if (DWM != null) System.out.println("invalid command!");
                        else {
                            if (Event.thisEventExists(title))
                                System.out.println("there is another event with this title!");
                            else {
                                System.out.println("event added successfully!");
                                Event newEvent = new Event(title, startDate);
                                newEvent.setDoesItHasLink(TF);
                                if (TF.equals("T")) {
                                    String link = scanner.nextLine();
                                    newEvent.setLink(link);
                                }
                                Taghvim theTaghvim = Taghvim.getTheTaghvimById(id);
                                theTaghvim.addToTaghvimEvents(title);
//                            System.out.println("event added successfully!");
                            }
                        }
                    } else {
                        if (Event.thisEventExists(title)) System.out.println("there is another event with this title!");
                        else {
                            System.out.println("event added successfully!");
                            Event newEvent = new Event(title, startDate);
                            newEvent.setNumberOfRepeat(Integer.parseInt(threeMode));
                            newEvent.setPeriodOfRepeat(DWM);
                            newEvent.setDoesItHasLink(TF);
                            if (TF.equals("T")) {
                                String link = scanner.nextLine();
                                newEvent.setLink(link);
                            }
                            Taghvim theTaghvim = Taghvim.getTheTaghvimById(id);
                            theTaghvim.addToTaghvimEvents(title);
//                            System.out.println("event added successfully!");
                        }
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static boolean doesTheDateValid(Matcher matcherForStartDate) {
        int year = Integer.parseInt(matcherForStartDate.group(1));
        int month = Integer.parseInt(matcherForStartDate.group(2));
        int day = Integer.parseInt(matcherForStartDate.group(3));

        if (year < 0 || month > 12 || month < 1 || day > 31 || day < 1) return false;
        return true;
    }

    //------------------------------------------------------------------------------
    public static void addTadk(String command, String userName, int id, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^Add Task (.+?) (\\d\\d_\\d\\d) (\\d\\d_\\d\\d) (\\d\\d\\d\\d_\\d\\d_\\d\\d) (.+?) ([^ ]*[ ])?(.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        String title = myMatcher.group(1);
        String startTime = myMatcher.group(2);
        String endTime = myMatcher.group(3);
        String startDate = myMatcher.group(4);
        String threeMode = myMatcher.group(5);
        String DWM = myMatcher.group(6);
        if (DWM != null) DWM = DWM.substring(0, DWM.length() - 1);
        String TF = myMatcher.group(7);

        Pattern patternForTitle = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForTitle = patternForTitle.matcher(title);
        Pattern patternForTime = Pattern.compile("^(\\d\\d)_(\\d\\d)$");
        Matcher matcherForStartTime = patternForTime.matcher(startTime);
        Matcher matcherForEndTime = patternForTime.matcher(endTime);
        Pattern patternForStartDate = Pattern.compile("^(-?\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
        Matcher matcherForStartDate = patternForStartDate.matcher(startDate);
        Matcher matcherForEndDate = patternForStartDate.matcher(threeMode);

        if (!UserName.getObjectByUserName(userName).doesUserOwnTaghvimsContain(id))
            System.out.println("you don't have access to do this!");
        else {
            if (!matcherForTitle.find()) System.out.println("invalid title!");
            else {
                matcherForStartDate.find();
                int year = Integer.parseInt(matcherForStartDate.group(1));
                int month = Integer.parseInt(matcherForStartDate.group(2));
                int day = Integer.parseInt(matcherForStartDate.group(3));
                if (!doesTheDateValid(matcherForStartDate)) System.out.println("invalid start date!");
                else if (month == 2 && day > 28) System.out.println("invalid start date!");
                else if (month == 4 && day > 30) System.out.println("invalid start date!");
                else if (month == 6 && day > 30) System.out.println("invalid start date!");
                else if (month == 9 && day > 30) System.out.println("invalid start date!");
                else if (month == 11 && day > 30) System.out.println("invalid start date!");
                else {
                    if (threeMode.contains("_")) {
                        matcherForEndDate.find();
                        int year1 = Integer.parseInt(matcherForEndDate.group(1));
                        int month1 = Integer.parseInt(matcherForEndDate.group(2));
                        int day1 = Integer.parseInt(matcherForEndDate.group(3));
                        if (!doesTheDateValid(matcherForEndDate)) System.out.println("invalid end date!");
                        else if (month1 == 2 && day1 > 28) System.out.println("invalid end date!");
                        else if (month1 == 4 && day1 > 30) System.out.println("invalid end date!");
                        else if (month1 == 6 && day1 > 30) System.out.println("invalid end date!");
                        else if (month1 == 9 && day1 > 30) System.out.println("invalid end date!");
                        else if (month1 == 11 && day1 > 30) System.out.println("invalid end date!");
                        else {
                            if (Task.thisTaskExists(title))
                                System.out.println("there is another task with this title!");
                            else {
                                System.out.println("task added successfully!");
                                Task newTask = new Task(title, startTime, endTime, startDate);
                                newTask.setEndDate(threeMode);
                                newTask.setPeriodOfRepeat(DWM);
                                newTask.setDoesItHasLink(TF);
                                if (TF.equals("T")) {
                                    String link = scanner.nextLine();
                                    newTask.setLink(link);
                                }
                                Taghvim theTaghvim = Taghvim.getTheTaghvimById(id);
                                theTaghvim.addToTaghvimTasks(title);
//                                System.out.println("task added successfully!");
                            }
                        }
                    } else if (threeMode.equals("None")) {
                        if (DWM != null) System.out.println("invalid command!");
                        else {
                            if (Task.thisTaskExists(title))
                                System.out.println("there is another task with this title!");
                            else {
                                System.out.println("task added successfully!");
                                Task newTask = new Task(title, startTime, endTime, startDate);
                                newTask.setDoesItHasLink(TF);
                                if (TF.equals("T")) {
                                    String link = scanner.nextLine();
                                    newTask.setLink(link);
                                }
                                Taghvim theTaghvim = Taghvim.getTheTaghvimById(id);
                                theTaghvim.addToTaghvimTasks(title);
//                            System.out.println("task added successfully!");
                            }
                        }
                    } else {
                        if (Task.thisTaskExists(title)) System.out.println("there is another task with this title!");
                        else {
                            System.out.println("task added successfully!");
                            Task newTask = new Task(title, startTime, endTime, startDate);
                            newTask.setNumberOfRepeat(Integer.parseInt(threeMode));
                            newTask.setPeriodOfRepeat(DWM);
                            newTask.setDoesItHasLink(TF);
                            if (TF.equals("T")) {
                                String link = scanner.nextLine();
                                newTask.setLink(link);
                            }
                            Taghvim theTaghvim = Taghvim.getTheTaghvimById(id);
                            theTaghvim.addToTaghvimTasks(title);
//                            System.out.println("task added successfully!");
                        }
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void edetEvent(String command, String userName, int id, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^Edit Event (.+?) (.+) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        String title = myMatcher.group(1);
        String field = myMatcher.group(2);
        String newValue = myMatcher.group(3);

        Pattern patternForTitle = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForTitle = patternForTitle.matcher(title);
        Matcher matcherForNewTitle = patternForTitle.matcher(newValue);
        Pattern patternForDate = Pattern.compile("^(-?\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
        Matcher matcherForDate = patternForDate.matcher(newValue);

        if (!field.equals("title") && !field.equals("repeat") && !field.equals("kind of repeat") && !field.equals("meet"))
            System.out.println("invalid command!");
        else {
            if (!UserName.getObjectByUserName(userName).doesUserOwnTaghvimsContain(id) || !Taghvim.getTheTaghvimById(id).doesTaghvimEventsContain(title))
                    System.out.println("you don't have access to do this!");
            else {
                if (!Event.thisEventExists(title)) System.out.println("there is no event with this title!");
                else {
                    if (!matcherForTitle.find()) System.out.println("invalid title!");
                    else if (field.equals("title")) {
                        if (!matcherForNewTitle.find()) System.out.println("invalid title!");
                        else {
                            if (Event.thisEventExists(newValue))
                                System.out.println("there is another event with this title!");
                            else {
                                System.out.println("event edited!");
                                Event.getEventByTitle(title).setTitle(newValue);
                                Taghvim.getTheTaghvimById(id).removeFromTaghvimEvents(title);
                                Taghvim.getTheTaghvimById(id).addToTaghvimEvents(newValue);
                                Event backUpEvent = Event.getEventByTitle(title);
                                Event.removeFromAllEvents(title);
                                Event.addToAllEvents(newValue, backUpEvent);
                            }
                        }
                    } else if (field.equals("repeat")) {
                        if (newValue.contains("_")) {
                            matcherForDate.find();
                            if (Event.getEventByTitle(title).getPeriodOfRepeat() == null) {
                                if (!doesTheDateValid(matcherForDate)) System.out.println("invalid end date!");
                                else {
                                    System.out.println("event edited!");
                                    Event.getEventByTitle(title).setEndDate(newValue);
                                    Event.getEventByTitle(title).setPeriodOfRepeat("D");
                                    Event.getEventByTitle(title).setNumberOfRepeat(null);
                                }
                            } else {
                                if (!doesTheDateValid(matcherForDate)) System.out.println("invalid end date!");
                                else {
                                    Event.getEventByTitle(title).setEndDate(newValue);
                                    Event.getEventByTitle(title).setNumberOfRepeat(null);
                                    System.out.println("event edited!");
                                }
                            }
                        } else if (newValue.equals("None")) {
                            Event.getEventByTitle(title).setEndDate(null);
                            Event.getEventByTitle(title).setNumberOfRepeat(null);
                            Event.getEventByTitle(title).setPeriodOfRepeat(null);
                            System.out.println("event edited!");
                        } else {
                            if (Event.getEventByTitle(title).getPeriodOfRepeat() == null) {
                                Event.getEventByTitle(title).setEndDate(null);
                                Event.getEventByTitle(title).setNumberOfRepeat(Integer.parseInt(newValue));
                                Event.getEventByTitle(title).setPeriodOfRepeat("D");
                                System.out.println("event edited!");
                            } else {
                                Event.getEventByTitle(title).setEndDate(null);
                                Event.getEventByTitle(title).setNumberOfRepeat(Integer.parseInt(newValue));
                                System.out.println("event edited!");
                            }
                        }
                    } else if (field.equals("kind of repeat")) {
                        Event.getEventByTitle(title).setPeriodOfRepeat(newValue);
                        System.out.println("event edited!");
                    } else if (field.equals("meet")) {
                        if (newValue.equals("F")) {
                            Event.getEventByTitle(title).setDoesItHasLink("F");
                            Event.getEventByTitle(title).setLink(null);
                            System.out.println("event edited!");
                        } else if (newValue.equals("T")) {
                            String link = scanner.nextLine();
                            Event.getEventByTitle(title).setDoesItHasLink("T");
                            Event.getEventByTitle(title).setLink(link);
                            System.out.println("event edited!");
                        }
                    } else System.out.println("invalid command!");
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void editTask(String command, String userName, int id, Scanner scanner) {
        Pattern myPattern = Pattern.compile("^Edit Task (.+?) (.+) (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();

        String title = myMatcher.group(1);
        String field = myMatcher.group(2);
        String newValue = myMatcher.group(3);

        Pattern patternForTitle = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForTitle = patternForTitle.matcher(title);
        Matcher matcherForNewTitle = patternForTitle.matcher(newValue);
        Pattern patternForDate = Pattern.compile("^(\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)$");
        Matcher matcherForDate = patternForDate.matcher(newValue);

        if (!field.equals("title") && !field.equals("repeat") && !field.equals("kind of repeat") && !field.equals("meet") && !field.equals("start time") && !field.equals("end time"))
            System.out.println("invalid command!");
        else {
            if (!UserName.getObjectByUserName(userName).doesUserOwnTaghvimsContain(id) || !Taghvim.getTheTaghvimById(id).doesTaghvimTasksContain(title))
                    System.out.println("you don't have access to do this!");
            else {
                if (!Task.thisTaskExists(title)) System.out.println("there is no task with this title!");
                else {
                    if (!matcherForTitle.find()) System.out.println("invalid title!");
                    else {
                        if (field.equals("title")) {
                            if (!matcherForNewTitle.find()) System.out.println("invalid title!");
                            else {
                                if (Task.thisTaskExists(newValue))
                                    System.out.println("there is another task with this title!");
                                else {
                                    System.out.println("task edited!");
                                    Task.getTaskByTitle(title).setTitle(newValue);
                                    Taghvim.getTheTaghvimById(id).removeFromTaghvimTasks(title);
                                    Taghvim.getTheTaghvimById(id).addToTaghvimTasks(newValue);
                                    Task backUpTask = Task.getTaskByTitle(title);
                                    Task.removeFromAllTasks(title);
                                    Task.addToAllTasks(newValue, backUpTask);
                                }
                            }
                        } else if (field.equals("repeat")) {
                            if (newValue.contains("_")) {
                                if (Task.getTaskByTitle(title).getPeriodOfRepeat() == null) {
                                    matcherForDate.find();
                                    if (!doesTheDateValid(matcherForDate)) System.out.println("invalid end date!");
                                    else {
                                        System.out.println("task edited!");
                                        Task.getTaskByTitle(title).setEndDate(newValue);
                                        Task.getTaskByTitle(title).setPeriodOfRepeat("D");
                                        Task.getTaskByTitle(title).setNumberOfRepeat(null);
                                    }
                                } else {
                                    matcherForDate.find();
                                    if (!doesTheDateValid(matcherForDate)) System.out.println("invalid end date!");
                                    else {
                                        System.out.println("task edited!");
                                        Task.getTaskByTitle(title).setEndDate(newValue);
                                        Task.getTaskByTitle(title).setNumberOfRepeat(null);
                                    }
                                }
                            } else if (newValue.equals("None")) {
                                System.out.println("task edited!");
                                Task.getTaskByTitle(title).setEndDate(null);
                                Task.getTaskByTitle(title).setNumberOfRepeat(null);
                                Task.getTaskByTitle(title).setPeriodOfRepeat(null);
                            } else {
                                if (Task.getTaskByTitle(title).getPeriodOfRepeat() == null) {
                                    System.out.println("task edited!");
                                    Task.getTaskByTitle(title).setEndDate(null);
                                    Task.getTaskByTitle(title).setNumberOfRepeat(Integer.parseInt(newValue));
                                    Task.getTaskByTitle(title).setPeriodOfRepeat("D");
                                } else {
                                    System.out.println("task edited!");
                                    Task.getTaskByTitle(title).setEndDate(null);
                                    Task.getTaskByTitle(title).setNumberOfRepeat(Integer.parseInt(newValue));
                                }
                            }
                        } else if (field.equals("kind of repeat")) {
                            System.out.println("task edited!");
                            Task.getTaskByTitle(title).setPeriodOfRepeat(newValue);
                        } else if (field.equals("meet")) {
                            if (newValue.equals("F")) {
                                System.out.println("task edited!");
                                Task.getTaskByTitle(title).setDoesItHasLink("F");
                                Task.getTaskByTitle(title).setLink(null);
                            } else if (newValue.equals("T")) {
                                String link = scanner.nextLine();
                                System.out.println("task edited!");
                                Task.getTaskByTitle(title).setDoesItHasLink("T");
                                Task.getTaskByTitle(title).setLink(link);
                            }
                        } else if (field.equals("start time")) {
                            System.out.println("task edited!");
                            Task.getTaskByTitle(title).setStartTime(newValue);
                        } else if (field.equals("end time")) {
                            System.out.println("task edited!");
                            Task.getTaskByTitle(title).setEndTime(newValue);
                        } else System.out.println("invalid command!");
                    }
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void deleteEvent(String command, String userName, int id) {
        Pattern myPattern = Pattern.compile("^Delete Event (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String title = myMatcher.group(1);

        Pattern patternForTitle = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForTitle = patternForTitle.matcher(title);

        if (!UserName.getObjectByUserName(userName).doesUserOwnTaghvimsContain(id))
            System.out.println("you don't have access to do this!");
        else {
            if (!matcherForTitle.find()) System.out.println("invalid title!");
            else {
                if (!Taghvim.getTheTaghvimById(id).doesTaghvimEventsContain(title) || !Event.thisEventExists(title))
                    System.out.println("there is no event with this title!");
                else {
                    System.out.println("event deleted successfully!");
                    Taghvim.getTheTaghvimById(id).removeFromTaghvimEvents(title);
                    Event.removeFromAllEvents(title);
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void deleteTask(String command, String userName, int id) {
        Pattern myPattern = Pattern.compile("^Delete Task (.+?)$");
        Matcher myMatcher = myPattern.matcher(command);
        myMatcher.find();
        String title = myMatcher.group(1);

        Pattern patternForTitle = Pattern.compile("^[A-Za-z0-9_]+$");
        Matcher matcherForTitle = patternForTitle.matcher(title);

        if (!UserName.getObjectByUserName(userName).doesUserOwnTaghvimsContain(id))
            System.out.println("you don't have access to do this!");
        else {
            if (!matcherForTitle.find()) System.out.println("invalid title!");
            else {
                if (!Taghvim.getTheTaghvimById(id).doesTaghvimTasksContain(title) || !Task.thisTaskExists(title))
                    System.out.println("there is no task with this title!");
                else {
                    System.out.println("task deleted successfully!");
                    Taghvim.getTheTaghvimById(id).removeFromTaghvimTasks(title);
                    Task.removeFromAllTasks(title);
                }
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void showEvents(String userName, int id) {
        ArrayList<String> allEvents = Taghvim.getTheTaghvimById(id).allEventsOfTaghvim();

        if (allEvents.size() == 0) System.out.println("nothing");
        else {
            for (int i = 0; i < allEvents.size(); ++i) {
                for (int j = i + 1; j < allEvents.size(); ++j) {
                    if (Event.getEventByTitle(allEvents.get(i)).getTitle().compareTo(Event.getEventByTitle(allEvents.get(j)).getTitle()) > 0) {
                        String temp = allEvents.get(i);
                        allEvents.set(i, allEvents.get(j));
                        allEvents.set(j, temp);
                    }
                }
            }

            System.out.println("events of this calendar:");
            for (int i = 0; i < allEvents.size(); ++i) {
                System.out.println("Event: " + allEvents.get(i) + " " + Event.getEventByTitle(allEvents.get(i)).getDoesItHasLink());
            }
        }
    }

    //------------------------------------------------------------------------------
    public static void showTasks(String userName, int id) {
        ArrayList<String> allTasks = Taghvim.getTheTaghvimById(id).allTasksOfTaghvim();

        if (allTasks.size() == 0) System.out.println("nothing");
        else {
            for (int i = 0; i < allTasks.size(); ++i) {
                for (int j = i + 1; j < allTasks.size(); ++j) {
                    String iStartDate = Task.getTaskByTitle(allTasks.get(i)).getStartDate();
                    String iStartTime = Task.getTaskByTitle(allTasks.get(i)).getStartTime();
                    String jStartDate = Task.getTaskByTitle(allTasks.get(j)).getStartDate();
                    String jStartTime = Task.getTaskByTitle(allTasks.get(j)).getStartTime();
                    Pattern patternForDate = Pattern.compile("(\\d\\d\\d\\d)_(\\d\\d)_(\\d\\d)");
                    Pattern patternForTime = Pattern.compile("(\\d\\d)_(\\d\\d)");
                    Matcher matcherForIStartDate = patternForDate.matcher(iStartDate);
                    Matcher matcherForJStartDate = patternForDate.matcher(jStartDate);
                    Matcher matcherForIStartTime = patternForTime.matcher(iStartTime);
                    Matcher matcherForJStartTime = patternForTime.matcher(jStartTime);
                    matcherForIStartDate.find();
                    matcherForIStartTime.find();
                    matcherForJStartDate.find();
                    matcherForJStartTime.find();
                    int iYear = Integer.parseInt(matcherForIStartDate.group(1));
                    int iMonth = Integer.parseInt(matcherForIStartDate.group(2));
                    int iDay = Integer.parseInt(matcherForIStartDate.group(3));
                    int iHour = Integer.parseInt(matcherForIStartTime.group(1));
                    int iMinute = Integer.parseInt(matcherForIStartTime.group(2));
                    int jYear = Integer.parseInt(matcherForJStartDate.group(1));
                    int jMonth = Integer.parseInt(matcherForJStartDate.group(2));
                    int jDay = Integer.parseInt(matcherForJStartDate.group(3));
                    int jHour = Integer.parseInt(matcherForJStartTime.group(1));
                    int jMinute = Integer.parseInt(matcherForJStartTime.group(2));
                    if (jYear < iYear) {
                        String temp = allTasks.get(i);
                        allTasks.set(i, allTasks.get(j));
                        allTasks.set(j, temp);
                    } else if (jYear == iYear && jMonth < iMonth) {
                        String temp = allTasks.get(i);
                        allTasks.set(i, allTasks.get(j));
                        allTasks.set(j, temp);
                    } else if (jYear == iYear && jMonth == iMonth && jDay < iDay) {
                        String temp = allTasks.get(i);
                        allTasks.set(i, allTasks.get(j));
                        allTasks.set(j, temp);
                    } else if (jYear == iYear && jMonth == iMonth && jDay == iDay && jHour < iHour) {
                        String temp = allTasks.get(i);
                        allTasks.set(i, allTasks.get(j));
                        allTasks.set(j, temp);
                    } else if (jYear == iYear && jMonth == iMonth && jDay == iDay && jHour == iHour && jMinute < iMinute) {
                        String temp = allTasks.get(i);
                        allTasks.set(i, allTasks.get(j));
                        allTasks.set(j, temp);
                    } else if (jYear == iYear && jMonth == iMonth && jDay == iDay && jHour == iHour && jMinute == iMinute && Task.getTaskByTitle(allTasks.get(i)).getTitle().compareTo(Task.getTaskByTitle(allTasks.get(j)).getTitle()) > 0) {
                        String temp = allTasks.get(i);
                        allTasks.set(i, allTasks.get(j));
                        allTasks.set(j, temp);
                    }
                }
            }

            System.out.println("tasks of this calendar:");
            for (int i = 0; i < allTasks.size(); ++i) {
                System.out.println("Task: " + allTasks.get(i) + " " + Task.getTaskByTitle(allTasks.get(i)).getDoesItHasLink() + " " + Task.getTaskByTitle(allTasks.get(i)).getStartTime() + " " + Task.getTaskByTitle(allTasks.get(i)).getEndTime());
            }
        }
    }
    //------------------------------------------------------------------------------
}