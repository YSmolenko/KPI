import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lab2 {

    private static final char[] LETTERS = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public static void main(String[] args) {

        String line = new String();

        try {
            System.out.println("Write a line:");
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        char[] word = line.toCharArray();


        boolean flag;
        for (int i = 0; i < LETTERS.length; i++) {
            flag = true;
            for (int j = 0; j < word.length; j++) {
                if (LETTERS[i] == word[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag)
            System.out.print(LETTERS[i]);
        }
        System.out.println();
    }
}
