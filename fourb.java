class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class fourb {

    public LinkedList<Integer> closestKValues(TreeNode root, double target, int k) {
        LinkedList<Integer> result = new LinkedList<>();
        inorder(root, target, k, result);
        return result;
    }

    private void inorder(TreeNode root, double target, int k, LinkedList<Integer> result) {
        if (root == null)
            return;

        inorder(root.left, target, k, result);

        if (result.size() == k) {
            if (Math.abs(target - root.val) < Math.abs(target - result.peekFirst()))
                result.pollFirst();
            else
                return;
        }

        result.add(root.val);

        inorder(root.right, target, k, result);
    }

    public static void main(String[] args) {
        // Example usage:
        TreeNode root = new TreeNode(4);
        root.left = new TreeNode(2);
        root.right = new TreeNode(5);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(3);

        double target = 3.8;
        int x = 2;

        ClosestValuesInBST solution = new ClosestValuesInBST();
        List<Integer> closestValues = solution.closestKValues(root, target, x);

        System.out.println("Closest values to " + target + ": " + closestValues);
    }
}
