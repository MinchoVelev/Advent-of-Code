package y2022.utils;

public class Node {
    public int value;
    public Node left;
    public Node right;
    boolean marked = false;
    boolean initial = false;

    Node(int value, boolean initial) {
        this.value = value;
        this.initial = initial;
    }
}
