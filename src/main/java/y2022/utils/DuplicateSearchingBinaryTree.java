package y2022.utils;

public class DuplicateSearchingBinaryTree {
    public Node root;

    public DuplicateSearchingBinaryTree() {
    }

    public int add(int value, boolean searchDuplicates) {
        if (root == null) {
            root = new Node(value, !searchDuplicates);
            return 0;
        }
        return add(value, root, searchDuplicates);
    }

    private int add(int value, Node node, boolean searchDuplicates) {

        if (node.value > value) {
            if (node.left == null) {
                node.left = new Node(value, !searchDuplicates);
                return 0;
            }
            return add(value, node.left, searchDuplicates);

        } else if (node.value < value) {
            if (node.right == null) {
                node.right = new Node(value, !searchDuplicates);
                return 0;
            }
            return add(value, node.right, searchDuplicates);
        } else {
            if (!searchDuplicates || node.marked || !node.initial) {
                return 0;
            }

            node.marked = true;
            return value;
            
        }
    }
}
