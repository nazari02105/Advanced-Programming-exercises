import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class tamrin4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        StringBuilder theInformations = new StringBuilder(input.nextLine());
        String startDate = input.nextLine();
        String endDate = input.nextLine();
        //
        String cordinateStringType = input.nextLine();
        //
        double cordinate = Double.parseDouble(cordinateStringType);

        int shouldIterate = 1;
        Pattern valid = Pattern.compile("(Message\\{ messageId=.*?, from=User\\{ firstName='.*?', isBot=.*?, lastName='.*?', userName='.*?' \\}, date=.*?, text='.*?', location=.*? \\})");
        Matcher validMatcher = valid.matcher(theInformations);
        int counter123 = 0;
        int skip = 0;
        while (validMatcher.find()) {
            counter123 += 1;
            Pattern dateRegex = Pattern.compile("date=(.*?),");
            Matcher dateMatcher = dateRegex.matcher(validMatcher.group(1));
            Pattern locationRegex = Pattern.compile("location=(.*?) }");
            Matcher locationMatcher = locationRegex.matcher(validMatcher.group(1));
            Pattern messageIdRegex = Pattern.compile("messageId=(.+?),");
            Matcher messageIdMatcher = messageIdRegex.matcher(validMatcher.group(1));
            Pattern firstNameRegex = Pattern.compile("firstName=('.*?'),");
            Matcher firstNameMatcher = firstNameRegex.matcher(validMatcher.group(1));
            Pattern isBotRegex = Pattern.compile("isBot=(.*?),");
            Matcher isBotMatcher = isBotRegex.matcher(validMatcher.group(1));
            Pattern lastNameRegex = Pattern.compile("lastName=('.*?'),");
            Matcher lastNameMatcher = lastNameRegex.matcher(validMatcher.group(1));
            Pattern userNameRegex = Pattern.compile("userName=('.*?') }");
            Matcher userNameMatcher = userNameRegex.matcher(validMatcher.group(1));
            Pattern textRegex = Pattern.compile("text='(.*?)', ");
            Matcher textMatcher = textRegex.matcher(validMatcher.group(1));
            //--------------------------------------------------------

            int howManyPasses = 0;
            for (int i = 1; dateMatcher.find(); ++i) {
                int numberOfPassed = 0;
                if (betweenTheDate(startDate, endDate, dateMatcher.group(1))) {
                    numberOfPassed += 1;
                }
                isBotMatcher.find();
                String help = isBotMatcher.group(1);
                if (help.compareTo("true") == 0 || help.compareTo("false") == 0) {
                    numberOfPassed += 1;
                }


                int counter = 0;
                for (int j = 0; j < dateMatcher.group(1).length(); ++j) {
                    if (Character.isDigit(dateMatcher.group(1).charAt(j))) counter += 1;
                }
                if (counter == 14) numberOfPassed += 1;


                locationMatcher.find();
                Pattern locationHint = Pattern.compile("(^-?\\d+\\.*\\d*$)");
                Matcher checkingLocationHint = locationHint.matcher(locationMatcher.group(1));
                if (checkingLocationHint.find() && locationMatcher.group(1).charAt(locationMatcher.group(1).length() - 1) != '.') {
                    numberOfPassed += 1;

                    String qwe = Double.toString(cordinate);
                    if (cordinateDistance(qwe, locationMatcher.group(1))) numberOfPassed += 1;
                }

                userNameMatcher.find();
                if (userNameMatcher.group(1).compareTo("''") == 0) {
                    numberOfPassed += 1;
                } else {
                    Pattern userNameHint = Pattern.compile("'([A-Za-z][A-Za-z0-9_]{3,30}[A-Za-z0-9])'");
                    Matcher checkingUserNameHint = userNameHint.matcher(userNameMatcher.group(1));
                    if (checkingUserNameHint.find()) numberOfPassed += 1;
                    ;
                }

                Pattern messageIdHint = Pattern.compile("(%[0-9 ]+?-[B-GI-LNP-RU-Zb-gi-lnp-ru-z]{5}\\$(?<!\\d)(\\d{2}|\\d{4})(?!\\d)%)");
                messageIdMatcher.find();
                Matcher checkingMessageIdHint = messageIdHint.matcher(messageIdMatcher.group(1));
                if (checkingMessageIdHint.find()) numberOfPassed += 1;


                firstNameMatcher.find();
                if (firstNameMatcher.group(1).compareTo("''") != 0) {
                    numberOfPassed += 1;
                }
                lastNameMatcher.find();
                textMatcher.find();
                if (numberOfPassed == 8) {
                    if (isBotMatcher.group(1).compareTo("true") == 0 && shouldIterate == 1) {
                        shouldIterate = 0;
                        skip = counter123;
                    } else if (isBotMatcher.group(1).compareTo("true") == 0 && shouldIterate == 0) {
                        shouldIterate = 1;
                    } else if (isBotMatcher.group(1).compareTo("false") == 0 && shouldIterate == 1) {

                        System.out.println("--------------------");
                        System.out.println("*" + firstNameMatcher.group(1).substring(1, firstNameMatcher.group(1).length() - 1) + " " + lastNameMatcher.group(1).substring(1, lastNameMatcher.group(1).length() - 1) + "*");
                        System.out.println(textMatcher.group(1).substring(0, textMatcher.group(1).length()));
                        System.out.println("_" + dateMatcher.group(1).substring(8, 10) + ":" + dateMatcher.group(1).substring(10, 12) + "_");
                        System.out.println("--------------------");
                    } else if (isBotMatcher.group(1).compareTo("false") == 0 && shouldIterate == 0) {
                        shouldIterate = 1;
                    }
                }
            }

        }


    }


    public static boolean betweenTheDate(String myStart, String myEnd, String mazeTime) {
        boolean isItBetween = false;
        int myStartYear = 0;
        int myStartMonth = 0;
        int myStartDay = 0;
        int myendYear = 0;
        int myendMonth = 0;
        int myendDay = 0;
        int mazeYear = 0;
        int mazeMonth = 0;
        int mazeDay = 0;
        String everytimeString = "";
        for (int i = 0; i < myStart.length(); i++) {
            everytimeString += myStart.charAt(i);
            if (i == 3) {
                myStartYear = Integer.parseInt(everytimeString);
                everytimeString = "";
            }
            if (i == 5) {
                myStartMonth = Integer.parseInt(everytimeString);
                everytimeString = "";
            }
            if (i == 7) {
                myStartDay = Integer.parseInt(everytimeString);
            }
        }
        String everytimeString2 = "";
        for (int i = 0; i < myEnd.length(); i++) {
            everytimeString2 += myEnd.charAt(i);
            if (i == 3) {
                myendYear = Integer.parseInt(everytimeString2);
                everytimeString2 = "";
            }
            if (i == 5) {
                myendMonth = Integer.parseInt(everytimeString2);
                everytimeString2 = "";
            }
            if (i == 7) {
                myendDay = Integer.parseInt(everytimeString2);
            }
        }
        String everytimeString3 = "";
        for (int i = 0; i < mazeTime.length(); i++) {
            everytimeString3 += mazeTime.charAt(i);
            if (i == 3) {
                mazeYear = Integer.parseInt(everytimeString3);
                everytimeString3 = "";
            }
            if (i == 5) {
                mazeMonth = Integer.parseInt(everytimeString3);
                everytimeString3 = "";
            }
            if (i == 7) {
                mazeDay = Integer.parseInt(everytimeString3);
            }
        }
        if (mazeYear > myStartYear && mazeYear < myendYear) {
            isItBetween = true;
        } else if (mazeYear < myStartYear || mazeYear > myendYear) {
            isItBetween = false;
        } else if (mazeYear == myStartYear) {
            if (myStartMonth < mazeMonth) {
                isItBetween = true;
            }
            if (myStartMonth == mazeMonth) {
                if (myStartDay <= mazeDay) {
                    isItBetween = true;
                }
            }
        } else if (mazeYear == myendYear) {
            if (myendMonth > mazeMonth) {
                isItBetween = true;
            }
            if (myendMonth == mazeMonth) {
                if (myStartDay >= mazeDay) {
                    isItBetween = true;
                }
            }
        }
        return isItBetween;
    }


    public static boolean cordinateDistance(String myCordinate, String userCordinate) {
        boolean isItClose = false;
        double userCordinateDoubleType = Double.parseDouble(userCordinate);
        float myCordinate2 = Float.parseFloat(myCordinate);
        if (userCordinateDoubleType - myCordinate2 <= 1 || myCordinate2 - userCordinateDoubleType <= 1) {
            isItClose = true;
        }
        return isItClose;
    }
}