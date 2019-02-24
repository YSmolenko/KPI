import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lab1 {

    public static void main(String[] args) {

        String line = new String(); //= "qweq,qweq,qwqeq,asasdzx,cxcv,qweq.";

        try {
            System.out.println("Write a line:");
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> stringArray = Stream.of(line.split("[,.]"))
                .collect(Collectors.toList());
        String lastWord = stringArray.get(stringArray.size() - 1);

        System.out.println("Result is:");

        for(String s : stringArray) {
            if (s.equals(lastWord)) {
                continue;
            }
            print(s);
        }
    }

    private static void print(String word) {
        List<Character> usedLetters = new LinkedList<>();

        char[] letters = word.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            if (usedLetters.contains(letters[i])){
                continue;
            }
            System.out.print(letters[i]);
            usedLetters.add(letters[i]);
        }
        System.out.println();
    }
}
