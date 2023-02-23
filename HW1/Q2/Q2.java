import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Scanner;

public class tamrin2 {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int numberOfDays = input.nextInt();
        input.nextLine();
        if (numberOfDays == 0){
            return;
        }
        HashMap<String, Integer> wordsHashMap = new HashMap<String, Integer>();
        for (int i = 1; i <= numberOfDays; i++) {
            int helper = i;
            String[] everydayStrings = input.nextLine().split("\\s");
            for (int j = 0; j < everydayStrings.length; j++) {
                if (!wordsHashMap.containsKey(everydayStrings[j].toLowerCase())){
                    wordsHashMap.put(everydayStrings[j].toLowerCase(), 1);
                }
                else{
                    wordsHashMap.put(everydayStrings[j].toLowerCase(), wordsHashMap.get(everydayStrings[j].toLowerCase()) + 1);
                }
            }
            wordsHashMap.forEach((key, value) ->{
                if (value >= helper){
                    wordsHashMap.put(key.toLowerCase(), helper);
                }
            });
        }
        ArrayList<String> commenWords = new ArrayList<String>();
        wordsHashMap.forEach((key, value) -> {
            if (value == numberOfDays) commenWords.add(key);
        });
        ArrayList<String> wordsToDelete = new ArrayList<String>();
        for (int i = 0; i < numberOfDays; i++) {
            String[] everydayStrings = input.nextLine().split(" ");
            for (int j = 0; j < everydayStrings.length; j++) {
                if (!wordsToDelete.contains(everydayStrings[j].toLowerCase())){
                    wordsToDelete.add(everydayStrings[j].toLowerCase());
                }
            }
        }
        for (int i = 0; i < wordsToDelete.size(); i++) {
            commenWords.remove(wordsToDelete.get(i));
        }
        if (commenWords.size() == 0) System.out.println("Nothing in common");
        else{
            for (int i = 0; i < commenWords.size(); i++) {
                for (int j = 0; j < commenWords.size()-1; j++) {
                    if (commenWords.get(j).toLowerCase().compareTo(commenWords.get(j+1).toLowerCase()) < 0){
                        String holder = commenWords.get(j);
                        commenWords.set(j, commenWords.get(j+1));
                        commenWords.set(j+1, holder);
                    }
                }
            }
            for (int i = 0; i < commenWords.size(); i++) {
                System.out.print(commenWords.get(i) + " ");
            }
        }
        input.close();
    }
}