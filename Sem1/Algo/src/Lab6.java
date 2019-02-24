import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Lab6 {
    public static void main(String[] args) {

        CustomTree customTree;
        BTreePrinter printer = new BTreePrinter();

        customTree = createBinaryTree();
        customTree.toString();
        printer.printNode(customTree.getHead());


        customTree.changeLeafs();
        customTree.toString();
        printer.printNode(customTree.getHead());
    }

    private static CustomTree createBinaryTree() {
        CustomTree bt = new CustomTree();

        bt.add(10);
        bt.add(9);
        bt.add(12);
        bt.add(11);
        bt.add(7);
        bt.add(6);
        bt.add(8);
        bt.add(14);
        bt.add(13);
        bt.add(16);

        return bt;
    }
}

class CustomTree {

    Node head;

    class Node {
        int value;
        Node left;
        Node right;

        Node(int e) {
            value = e;
            left = null;
            right = null;
        }
    }

    public void add(int e) {
        head = addRecursive(head, e);
    }

    private Node addRecursive(Node current, int e) {
        if (current == null) {
            return new Node(e);
        }

        if (e < current.value) {
            current.left = addRecursive(current.left, e);
        } else if (e > current.value) {
            current.right = addRecursive(current.right, e);
        } else {
            return current;
        }
        return current;
    }

    public boolean containsNode(int value) {
        return containsNodeRecursive(head, value);
    }

    private boolean containsNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }
        if (value == current.value) {
            return true;
        }
        return value < current.value
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public String toString(){
        print(head);
        System.out.println();
        return null;
    }

    public void print(Node node) {
        if (node != null) {
            print(node.left);
            print(node.right);
            System.out.print(" " + node.value);
        }
    }

    public void changeLeafs() {
        changeLeafs(head);
    }

    private void changeLeafs(Node node) {
        if (node != null) {
            if (node.right == null && node.left == null) {
                node.value = 100;
            }
            changeLeafs(node.left);
            changeLeafs(node.right);
        }
    }

    public Node getHead() {
        return head;
    }
}

class Node<T extends Comparable<?>> {
    Node<T> left, right;
    T data;

    public Node(T data) {
        this.data = data;
    }
}

class BTreePrinter {

    public static void printNode(CustomTree.Node root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static void printNodeInternal(List<CustomTree.Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<CustomTree.Node> newNodes = new ArrayList<CustomTree.Node>();
        for (CustomTree.Node node : nodes) {
            if (node != null) {
                System.out.print(node.value);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(CustomTree.Node node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}