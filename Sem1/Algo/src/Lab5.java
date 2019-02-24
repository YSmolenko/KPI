import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lab5 {
    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();

        String line = new String();

        try {
            System.out.println("Write a words delimited with spaces");
            BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
            line = bf.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> stringArray = Stream.of(line.split(","))
                .collect(Collectors.toList());


        for (int i = 0; i < stringArray.size(); i++) {
            stack.push(stringArray.get(i));
        }

        char ch;
        String s;
        int value = 0;
        while ((s = stack.pop()) != null) {
            ch = s.charAt(0);
            if (ch >= 'A' && ch <= 'Z') {
                value = 1;
            }
        }

        Stack<Integer> newStack = new Stack<>();
        for (int i = 0; i < 4; i++){
            newStack.push(value);
        }

        System.out.println(newStack);
    }
}


class Stack<E> {
    private CustomList<E> list = new CustomList<>();

    public void push(E e) {
        list.add(e);
    }

    public E pop() {
        return list.next();
    }

    public String toString() {
        return list.toString();
    }
}