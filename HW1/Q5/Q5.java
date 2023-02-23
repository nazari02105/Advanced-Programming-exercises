import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.HashMap;


public class help {
    public static HashMap<String, String> myHashMap = new HashMap<String, String>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String commands = "";
        ArrayList<String> allFileNames = new ArrayList<String>();
        // HashMap<String, String> myHashMap = new HashMap<String, String>();
        while (true) {
            commands = input.nextLine();
            commands = commands.trim();
            //----------------------------------------------
            if (commands.length() >= 9 && commands.substring(0, 8).compareTo("ADD DOC ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 2) {
                    String FileName = commands.substring(8);
                    if (myHashMap.containsKey(FileName)) {
                        String newStufs = input.nextLine();
                        newStufs = CheckingNotToHave(newStufs);
                        myHashMap.put(FileName, newStufs);
                    } else {
                        allFileNames.add(FileName);
                        String newStufs = input.nextLine();
                        newStufs = CheckingNotToHave(newStufs);
                        myHashMap.put(FileName, newStufs);
                    }
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 9 && commands.substring(0, 8).compareTo("RMV DOC ") == 0) {
                String FileName = commands.substring(8);
                if (myHashMap.containsKey(FileName)) {
                    allFileNames.remove(FileName);
                    myHashMap.remove(FileName);
                } else {
                    System.out.println("invalid file name!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 10 && commands.substring(0, 10).compareTo("RPLC -ALL ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    replaceAll(commands, allFileNames);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 6 && commands.substring(0, 5).compareTo("RPLC ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    replace(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 14 && commands.substring(0, 14).compareTo("RMV WORD -ALL ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    removeAll(commands, allFileNames);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 10 && commands.substring(0, 9).compareTo("RMV WORD ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    remove(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 14 && commands.substring(0, 14).compareTo("ADD WORD -ALL ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    addAll(commands, allFileNames);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 9 && commands.substring(0, 9).compareTo("ADD WORD ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    add(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 9 && commands.substring(0, 9).compareTo("FIND REP ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    findRep(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 4 && commands.substring(0, 4).compareTo("GCD ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 1) {
                    gcd(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 12 && commands.substring(0, 12).compareTo("FIND MIRROR ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    mirror(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 20 && commands.substring(0, 20).compareTo("FIND ALPHABET WORDS ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 3) {
                    findAlphabet(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 6 && commands.substring(0, 6).compareTo("PRINT ") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 1) {
                    print(commands);
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else if (commands.length() >= 3 && commands.substring(0, 3).compareTo("END") == 0) {
                int spaceCounter = 0;
                for (int i = 0; i < commands.length(); i++) {
                    if (commands.charAt(i) == ' ') {
                        spaceCounter += 1;
                    }
                }
                if (spaceCounter == 0) {
                    break;
                } else {
                    System.out.println("invalid command!");
                }
            }
            //----------------------------------------------
            else {
                System.out.println("invalid command!");
            }
        }
    }


    public static String CheckingNotToHave(String functionString) {

        Pattern linkPattern = Pattern.compile("(!?\\[(.+?)\\]\\((.+?)\\))");
        Matcher linkMatcher = linkPattern.matcher(functionString);
        while (linkMatcher.find()) {
            int index = functionString.indexOf(linkMatcher.group(1));
            if (index == 0 && index == functionString.length() - linkMatcher.group(1).length()) {
                functionString = linkMatcher.group(2);
            } else if (index != 0 && index != functionString.length() - linkMatcher.group(1).length()) {
                if (functionString.charAt(index - 1) == ' ' && functionString.charAt(index + linkMatcher.group(1).length()) == ' ') {
                    functionString = functionString.substring(0, index) + linkMatcher.group(2) + functionString.substring(index + linkMatcher.group(1).length());
                }
            } else if (index == 0 && index != functionString.length() - linkMatcher.group(1).length()) {
                if (functionString.charAt(index + linkMatcher.group(1).length()) == ' ') {
                    functionString = linkMatcher.group(2) + functionString.substring(index + linkMatcher.group(1).length());
                }
            } else if (index != 0 && index == functionString.length() - linkMatcher.group(1).length()) {
                if (functionString.charAt(index - 1) == ' ') {
                    functionString = functionString.substring(0, index) + linkMatcher.group(2);
                }
            }
        }


        Pattern starPattern = Pattern.compile("(\\*\\*.*?\\*\\*)");
        Matcher starMatcher = starPattern.matcher(functionString);
        while (starMatcher.find()) {
            int index = functionString.indexOf(starMatcher.group(1));
            if (index == 0 && index == functionString.length() - starMatcher.group(1).length()) {
                functionString = functionString.substring(index + 2, functionString.length() - 2);
            }
            if (index != 0 && index != functionString.length() - starMatcher.group(1).length()) {
                if (functionString.charAt(index - 1) == ' ' && functionString.charAt(index + starMatcher.group(1).length()) == ' ') {
                    functionString = functionString.substring(0, index) + functionString.substring(index + 2, index + starMatcher.group(1).length() - 2) + functionString.substring(index + starMatcher.group(1).length());
                }
            }
            if (index != 0 && index == functionString.length() - starMatcher.group(1).length()) {
                if (functionString.charAt(index - 1) == ' ') {
                    functionString = functionString.substring(0, index) + functionString.substring(index + 2, index + starMatcher.group(1).length() - 2);
                }
            }
            if (index == 0 && index != functionString.length() - starMatcher.group(1).length()) {
                if (functionString.charAt(index + starMatcher.group(1).length()) == ' ') {
                    functionString = functionString.substring(index + 2, index + starMatcher.group(1).length() - 2) + functionString.substring(index + starMatcher.group(1).length());
                }
            }
        }


        int spaceHolder = -1;
        int secondSpaceHolder = functionString.length();
        for (int i = 0; i < functionString.length(); ++i) {
            if (functionString.charAt(i) == ' ') {
                spaceHolder = i;
            }
            if (!Character.isLetter(functionString.charAt(i)) && !Character.isDigit(functionString.charAt(i)) && functionString.charAt(i) != ' ') {
                for (int j = i; j < functionString.length(); ++j) {
                    if (functionString.charAt(j) == ' ') {
                        secondSpaceHolder = j;
                        break;
                    } else if (j == functionString.length() - 1) {
                        secondSpaceHolder = j + 1;
                        break;
                    }
                }
                functionString = functionString.substring(0, spaceHolder + 1) + functionString.substring(secondSpaceHolder);
                i = -1;
            }
        }
        return functionString;
    }


    public static void replace(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[1];
        if (myHashMap.containsKey(fileName)) {
            String[] mustChangeWords = seperated[2].split(",");
            String newWord = seperated[3];
            String fileStufs = "";
            fileStufs += myHashMap.get(fileName);
            for (int i = 0; i < mustChangeWords.length; i++) {
                if (fileStufs.contains(mustChangeWords[i])) {
                    for (int j = fileStufs.length() - 1; j >= 0; --j) {
                        if (fileStufs.charAt(j) == mustChangeWords[i].charAt(0) && fileStufs.length() - j >= mustChangeWords[i].length()) {
                            int counter = 0;
                            for (int k = 0; k < mustChangeWords[i].length(); ++k) {
                                if (mustChangeWords[i].charAt(k) == fileStufs.charAt(j + k)) {
                                    counter++;
                                }
                            }
                            if (j != 0 && counter == mustChangeWords[i].length() && fileStufs.charAt(j - 1) == ' ' && j != fileStufs.length() - mustChangeWords[i].length() && fileStufs.charAt(j + mustChangeWords[i].length()) == ' ') {
                                if (j == fileStufs.length() - mustChangeWords[i].length()) {
                                    fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length() - 1);
                                    j = fileStufs.length() - 1;
                                    break;
                                }
                                fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length());
                                j = fileStufs.length() - 1;
                                break;
                            }
                            if (j == 0 && j != fileStufs.length() - mustChangeWords[i].length() && fileStufs.charAt(j + mustChangeWords[i].length()) == ' ' && counter == mustChangeWords[i].length()) {
                                if (j == fileStufs.length() - mustChangeWords[i].length()) {
                                    fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length() - 1);
                                    j = fileStufs.length() - 1;
                                    break;
                                }
                                fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length());
                                j = fileStufs.length() - 1;
                                break;
                            }
                            if (j != 0 && counter == mustChangeWords[i].length() && fileStufs.charAt(j - 1) == ' ' && j == fileStufs.length() - mustChangeWords[i].length()) {
                                if (j == fileStufs.length() - mustChangeWords[i].length()) {
                                    fileStufs = fileStufs.substring(0, j) + newWord;
                                    j = fileStufs.length() - 1;
                                    break;
                                }
                                fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length());
                                j = fileStufs.length() - 1;
                                break;
                            }
                            if (j == 0 && counter == mustChangeWords[i].length() && j == fileStufs.length() - mustChangeWords[i].length()) {
                                if (j == fileStufs.length() - mustChangeWords[i].length()) {
                                    fileStufs = fileStufs.substring(0, j) + newWord;
                                    j = fileStufs.length() - 1;
                                    break;
                                }
                                fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length());
                                j = fileStufs.length() - 1;
                                break;
                            }
                        }
                    }
                }
            }
            myHashMap.put(fileName, fileStufs);
        } else {
            System.out.println("invalid file name!");
        }
    }


    public static void replaceAll(String functionString, ArrayList<String> allFiles) {
        String[] seperated = functionString.split(" ");
        String[] mustChangeWords = seperated[2].split(",");
        String newWord = seperated[3];
        for (int k = 0; k < allFiles.size(); ++k) {
            String fileStufs = "";
            fileStufs += myHashMap.get(allFiles.get(k));
            for (int i = 0; i < mustChangeWords.length; i++) {
                if (fileStufs.contains(mustChangeWords[i])) {
                    for (int j = fileStufs.length() - 1; j >= 0; --j) {
                        if (fileStufs.charAt(j) == mustChangeWords[i].charAt(0) && fileStufs.length() - j >= mustChangeWords[i].length()) {
                            int counter = 0;
                            for (int q = 0; q < mustChangeWords[i].length(); ++q) {
                                if (mustChangeWords[i].charAt(q) == fileStufs.charAt(j + q)) {
                                    counter++;
                                }
                            }
                            if (j != 0 && counter == mustChangeWords[i].length() && fileStufs.charAt(j - 1) == ' ' && j != fileStufs.length() - mustChangeWords[i].length() && fileStufs.charAt(j + mustChangeWords[i].length()) == ' ') {
                                fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length());
                                j = fileStufs.length() - 1;
                                break;
                            }
                            if (j == 0 && j != fileStufs.length() - mustChangeWords[i].length() && fileStufs.charAt(j + mustChangeWords[i].length()) == ' ' && counter == mustChangeWords[i].length()) {
                                fileStufs = fileStufs.substring(0, j) + newWord + fileStufs.substring(j + mustChangeWords[i].length());
                                j = fileStufs.length() - 1;
                                break;
                            }
                            if (j != 0 && counter == mustChangeWords[i].length() && fileStufs.charAt(j - 1) == ' ' && j == fileStufs.length() - mustChangeWords[i].length()) {
                                fileStufs = fileStufs.substring(0, j) + newWord;
                                j = fileStufs.length() - 1;
                                break;
                            }
                            if (j == 0 && counter == mustChangeWords[i].length() && j == fileStufs.length() - mustChangeWords[i].length()) {
                                fileStufs = fileStufs.substring(0, j) + newWord;
                                j = fileStufs.length() - 1;
                                break;
                            }
                        }
                    }
                }
            }
            myHashMap.put(allFiles.get(k), fileStufs);
        }
    }


    public static void removeAll(String commands, ArrayList<String> allfilenames) {
        String[] seperated = commands.split(" ");
        String wordToDelete = seperated[3];
        for (int k = 0; k < allfilenames.size(); ++k) {
            String fileStufs = "";
            fileStufs += myHashMap.get(allfilenames.get(k));
            while (fileStufs.contains(wordToDelete)) {
                int index = fileStufs.indexOf(wordToDelete);
                if (index == 0 && index != fileStufs.length() - wordToDelete.length() && fileStufs.charAt(index + wordToDelete.length()) == ' ') {
                    fileStufs = fileStufs.substring(0, index) + fileStufs.substring(index + wordToDelete.length());
                } else if (index == fileStufs.length() - wordToDelete.length() && index != 0 && fileStufs.charAt(index - 1) == ' ') {
                    fileStufs = fileStufs.substring(0, index) + fileStufs.substring(index + wordToDelete.length());
                } else if (index != 0 && fileStufs.charAt(index - 1) == ' ' && index != fileStufs.length() - wordToDelete.length() && fileStufs.charAt(index + wordToDelete.length()) == ' ') {
                    fileStufs = fileStufs.substring(0, index) + fileStufs.substring(index + wordToDelete.length());
                } else if (index == 0 && index == fileStufs.length() - wordToDelete.length()) {
                    fileStufs = "";
                } else {
                    fileStufs = fileStufs.substring(0, index) + "*" + fileStufs.substring(index + 1);
                }
            }
            for (int i = 0; i < fileStufs.length(); ++i) {
                if (fileStufs.charAt(i) == '*') {
                    fileStufs = fileStufs.substring(0, i) + wordToDelete.charAt(0) + fileStufs.substring(i + 1);
                }
            }
            myHashMap.put(allfilenames.get(k), fileStufs);
        }
    }


    public static void remove(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[2];
        if (myHashMap.containsKey(fileName)) {
            String wordToDelete = seperated[3];
            String fileStufs = "";
            fileStufs += myHashMap.get(fileName);
            while (fileStufs.contains(wordToDelete)) {
                int index = fileStufs.indexOf(wordToDelete);
                if (index == 0 && index != fileStufs.length() - wordToDelete.length() && fileStufs.charAt(index + wordToDelete.length()) == ' ') {
                    fileStufs = fileStufs.substring(0, index) + fileStufs.substring(index + wordToDelete.length());
                } else if (index == fileStufs.length() - wordToDelete.length() && index != 0 && fileStufs.charAt(index - 1) == ' ') {
                    fileStufs = fileStufs.substring(0, index) + fileStufs.substring(index + wordToDelete.length());
                } else if (index != 0 && fileStufs.charAt(index - 1) == ' ' && index != fileStufs.length() - wordToDelete.length() && fileStufs.charAt(index + wordToDelete.length()) == ' ') {
                    fileStufs = fileStufs.substring(0, index) + fileStufs.substring(index + wordToDelete.length());
                } else if (index == 0 && index == fileStufs.length() - wordToDelete.length()) {
                    fileStufs = "";
                } else {
                    fileStufs = fileStufs.substring(0, index) + "*" + fileStufs.substring(index + 1);
                }
            }
            for (int i = 0; i < fileStufs.length(); ++i) {
                if (fileStufs.charAt(i) == '*') {
                    fileStufs = fileStufs.substring(0, i) + wordToDelete.charAt(0) + fileStufs.substring(i + 1);
                }
            }
            myHashMap.put(fileName, fileStufs);
        } else {
            System.out.println("invalid file name!");
        }
    }


    public static void addAll(String command, ArrayList<String> allFileNames) {
        String[] seperated = command.split(" ");
        String wordToAdd = seperated[3];
        for (int k = 0; k < allFileNames.size(); ++k) {
            String fileStufs = "";
            fileStufs += myHashMap.get(allFileNames.get(k));
            fileStufs += wordToAdd;
            myHashMap.put(allFileNames.get(k), fileStufs);
        }
    }


    public static void add(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[2];
        if (myHashMap.containsKey(fileName)) {
            String wordToAdd = seperated[3];
            String fileStufs = "";
            fileStufs += myHashMap.get(fileName);
            fileStufs += wordToAdd;
            myHashMap.put(fileName, fileStufs);
        } else {
            System.out.println("invalid file name!");
        }
    }


    public static void findRep(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[2];
        if (myHashMap.containsKey(fileName)) {
            String wordToCount = seperated[3];
            String fileStufs = "";
            fileStufs += myHashMap.get(fileName);
            int counter = 0;
            while (fileStufs.contains(wordToCount)) {
                counter += 1;
                fileStufs = fileStufs.substring(0, fileStufs.indexOf(wordToCount)) + "*" + fileStufs.substring(fileStufs.indexOf(wordToCount) + 1);
            }
            System.out.println(wordToCount + " is repeated " + counter + " times in " + fileName);
        } else {
            System.out.println("invalid file name!");
        }
    }


    public static void gcd(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[1];
        if (myHashMap.containsKey(fileName)) {
            String fileStufs = "";
            fileStufs += myHashMap.get(fileName);
            ArrayList<Integer> numbers = new ArrayList<Integer>();
            Pattern gcdPattern = Pattern.compile("(\\d+)");
            Matcher gcdMacher = gcdPattern.matcher(fileStufs);
            while (gcdMacher.find()) {
                numbers.add(Integer.parseInt(gcdMacher.group(1)));
            }
            if (numbers.size() > 0) {
                int answer = findGCD(numbers, numbers.size());
                fileStufs += answer;
                myHashMap.put(fileName, fileStufs);
            }
        } else {
            System.out.println("invalid file name!");
        }
    }


    static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }


    static int findGCD(ArrayList<Integer> arr, int n) {
        int result = 0;
        for (int element : arr) {
            result = gcd(result, element);

            if (result == 1) {
                return 1;
            }
        }

        return result;
    }


    public static void mirror(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[2];
        if (myHashMap.containsKey(fileName)) {
            String mirrorChar = seperated[3];
            if (mirrorChar.length() == 1) {
                String fileStufs = "";
                fileStufs += myHashMap.get(fileName);
                Pattern mirrorPattern = Pattern.compile("(\\d+" + mirrorChar + "\\d+)");
                Matcher mirrorMatcher = mirrorPattern.matcher(fileStufs);
                int counter = 0;
                while (mirrorMatcher.find()) {
                    int index = fileStufs.indexOf(mirrorMatcher.group(1));
                    int equal = 0;
                    String[] everytimeMatch = mirrorMatcher.group(1).split(mirrorChar);
                    if (Integer.parseInt(everytimeMatch[0]) == Integer.parseInt(everytimeMatch[1])) {
                        equal += 1;
                    }
                    if (equal == 1) {
                        if (index != 0 && fileStufs.charAt(index - 1) != ' ' && index != fileStufs.length() - mirrorMatcher.group(1).length() && fileStufs.charAt(index + mirrorMatcher.group(1).length()) != ' ') {
                            counter += 1;
                        }
                        if (index != 0 && fileStufs.charAt(index - 1) == ' ' && index != fileStufs.length() - mirrorMatcher.group(1).length() && fileStufs.charAt(index + mirrorMatcher.group(1).length()) == ' ') {
                            counter += 1;
                        }
                        if (index == 0 && index != fileStufs.length() - mirrorMatcher.group(1).length() && fileStufs.charAt(index + mirrorMatcher.group(1).length()) == ' ') {
                            counter += 1;
                        }
                        if (index != 0 && fileStufs.charAt(index - 1) == ' ' && index == fileStufs.length() - mirrorMatcher.group(1).length()) {
                            counter += 1;
                        }
                        if (index == 0 && index == fileStufs.length() - mirrorMatcher.group(1).length()) {
                            counter += 1;
                        }
                    }
                }
                System.out.println(counter + " mirror words!");
            } else {
                System.out.println("invalid command!");
            }
        } else {
            System.out.println("invalid file name!");
        }
    }


    public static void findAlphabet(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[3];
        if (myHashMap.containsKey(fileName)) {
            String fileStufs = "";
            fileStufs += myHashMap.get(fileName);
            String[] words = fileStufs.split(" ");
            Pattern findAlphabetPattern = Pattern.compile("(\\d)");
            int counter = 0;
            for (int i = 0; i < words.length; ++i) {
                if (words[i].compareTo("") != 0 && words[i].compareTo(" ") != 0) {
                    Matcher findAlphabetMatcher = findAlphabetPattern.matcher(words[i]);
                    if (!findAlphabetMatcher.find()) {
                        counter += 1;
                    }
                }
            }
            System.out.println(counter + " alphabetical words!");
        } else {
            System.out.println("invalid file name!");
        }
    }


    public static void print(String command) {
        String[] seperated = command.split(" ");
        String fileName = seperated[1];
        if (myHashMap.containsKey(fileName)) {
            String fileStufs = "";
            fileStufs += myHashMap.get(fileName);
            System.out.println(fileStufs);
        } else {
            System.out.println("invalid file name!");
        }
    }


}
