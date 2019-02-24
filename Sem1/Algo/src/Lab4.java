import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Lab4 {

    public static void main(String[] args) {

        CustomList<String> list = new CustomList<>();

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
            list.add(stringArray.get(i));
        }

        System.out.println(list.toString());
        list.task1("d");
        System.out.println(list.toString());
    }
}

class CustomList<E> {

    private class Node<E> {
        E value;
        Node<E> next;
        Node<E> prev;
    }

    private int size = 0;
    private Node<E> current;
    private Node<E> first;
    private Node<E> last;

    public E getfirst() {
        if (first != null) {
            current = first;
            return first.value;
        }
        return null;
    }

    public E getLast() {
        if (last != null) {
            current = last;
            return last.value;
        }
        return null;
    }

    public E prev() {
        if (current != null) {
            Node<E> node =  current.prev;
            if (node != null) {
                current = node;
                return node.value;
            }
        }
        current = null;
        return null;
    }

    public E next() {
        if (current != null) {
            Node<E> node =  current.next;
            if (node != null) {
                current = node;
                return node.value;
            }
        } else if (first != null) {
                Node<E> node =  first;
                current = node;
                return node.value;
            }
        return null;
    }

    public void add(E e) {
        if (e == null) {
            return;
        }
        Node<E> node = new Node<>();
        node.value = e;
        node.next = null;
        if (first == null) {
            node.prev = null;
            first = node;
            last = node;
        } else {
            node.prev = last;
            node.prev.next = node;
            last = node;
        }
        size++;
    }

    public E remove() {
        if (current != null) {
            Node<E> node =  current;
            if (current.prev != null && current.next != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;

            } else if (current.prev == null) {
                first = current.next;
                current.next.prev = null;
            } else if (current.next == null) {
                current.prev.next = null;
                last = current.prev;
            }
            size--;
            return current.value;
        }
        return null;
    }

    private void swap (Node<E> a, Node<E> b) {
        if (a.prev == null) {
            first = b;
        } else {
            a.prev.next = b;
        }

        if (b.next == null) {
            last = a;
        } else {
            b.next.prev = a;
        }
        a.next = b.next;
        b.prev = a.prev;
        b.next = a;
        a.prev = b;
    }

    public void task1(E e) {
        Node<E> node = first;
        while (!node.value.equals(e)) {
            node = node.next;
        }

        current = node;
        remove();

        if (node.next == null) {
            return;
        }
        if (node.next.next == null) {
            return;
        }
        Node<E> a = node.next;
        Node<E> b = node.next.next;

        swap(a, b);
    }

    public void task2() {
        Node<E> lastEmptyNode = last;
        while (!lastEmptyNode.value.equals("")) {
            lastEmptyNode = lastEmptyNode.prev;
            if (lastEmptyNode == null) {
                return;
            }
        }
        Node<E> nextAfterEmpty = lastEmptyNode;
        for (int i = 0; i < 3 && nextAfterEmpty.next != null; i++) {
            nextAfterEmpty = nextAfterEmpty.next;
        }
        if (nextAfterEmpty == null) {
            last = lastEmptyNode;
            lastEmptyNode.next = null;
        } else {
            lastEmptyNode.next = nextAfterEmpty.next;
        }

    }

    public String toString() {
        Node<E> node = first;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (node != null) {
            sb.append(node.value);
            node = node.next;
            if (node != null) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
