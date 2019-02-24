import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lab3 {

    public static void main(String[] args) {
        String line = new String();

        try {
            System.out.println("Write a line:");
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> stringArray = Stream.of(line.split(" "))
                .collect(Collectors.toList());

        printWord(stringArray.get(1).toCharArray(), 0);

    }

    private static void printWord(char[] letters, int index) {
        if (index < letters.length) {
            System.out.print(letters[index]);
            printWord(letters, index + 1);
            System.out.print(letters[index]);
        } else {
            System.out.println();
        }
    }
}
