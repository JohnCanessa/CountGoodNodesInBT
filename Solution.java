import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSplitPaneUI;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
 
    // **** class members ****
    int         val;
    TreeNode    left;
    TreeNode    right;
 
    // **** constructors ****
    TreeNode() {}
 
    TreeNode(int val) { this.val = val; }
 
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}


/**
 * 1448. Count Good Nodes in Binary Tree
 * https://leetcode.com/problems/count-good-nodes-in-binary-tree/
 */
public class Solution {


    /**
     * Enumerate which child in the node at the head of the queue 
     * (see populateTree function) should be updated.
     */
    enum Child {
        LEFT,
        RIGHT
    }


    // **** child turn to insert on node at head of queue ****
    static Child  insertChild = Child.LEFT;
    
    
    /**
     * This function inserts the next value into the specified BST.
     * This function is called repeatedly from the populateTree method.
     * This function supports 'null' value.
     */
    static TreeNode insertValue(TreeNode root, String strVal, Queue<TreeNode> q) {
    
        // **** node to add to the BST in this pass ****
        TreeNode node = null;
    
        // **** create a node (if needed) ****
        if (!strVal.equals("null"))
            node = new TreeNode(Integer.parseInt(strVal));
    
        // **** check is the BST is empty (this becomes the root node) ****
        if (root == null)
            root = node;
    
        // **** add node to left child (if possible) ****
        else if (insertChild == Child.LEFT) {
        
            // **** add this node as the left child ****
            if (node != null)
                q.peek().left = node; 
            
            // **** for next pass ****
            insertChild = Child.RIGHT;
        }
    
        // **** add node to right child (if possible) ****
        else if (insertChild == Child.RIGHT) {
        
            // **** add this node as a right child ****
            if (node != null)
                q.peek().right = node;
    
            // **** remove node from queue ****
            q.remove();
    
            // **** for next pass ****
            insertChild = Child.LEFT;
        }
    
        // **** add this node to the queue (if NOT null) ****
        if (node != null)
            q.add(node);
        
        // **** return the root of the BST ****
        return root;
    }

    /**
     * This function populates a BT in level order as 
     * specified by the array.
     * This function supports 'null' values.
     */
    static TreeNode populateTree(String[] arr) {
    
        // **** root for the BT ****
        TreeNode root = null;
    
        // **** auxiliary queue ****
        Queue<TreeNode> q = new LinkedList<TreeNode>();
    
        // **** traverse the array of values inserting nodes 
        //      one at a time into the BST ****
        for (String strVal : arr)
            root = insertValue(root, strVal, q);
    
        // **** clear the queue (the garbage collector will do this) ****
        q = null;
    
        // **** return the root of the BST ****
        return root;
    }


    /**
     * Traverse the specified BST displaying the values in order.
     * This method is used to verify that the BST was properly populated.
     */
    static void inOrder(TreeNode root) {
    
        // **** end condition ****
        if (root == null)
            return;
    
        // **** visit the left sub tree ****
        inOrder(root.left);
    
        // **** display the value of this node ****
        System.out.print(root.val + " ");
    
        // **** visit the right sub tree ****
        inOrder(root.right);
    }


    /**
     * Given a binary tree root, a node X in the tree is named good if 
     * in the path from root to X there are no nodes with a value greater than X.
     * Return the number of good nodes in the binary tree.
     */  
    static int goodNodes(TreeNode root) {
        
        // **** check for null BT ****
        if (root == null)
            return 0;

        // **** return count ****
        return  1 +                                     // count BT root
                goodNodesRec(root.left, root.val) +     // count left tree 
                goodNodesRec(root.right, root.val);     // count right tree
    }


    /**
     * Given a binary tree root, a node X in the tree is named good if 
     * in the path from root to X there are no nodes with a value greater than X.
     * Return the number of good nodes in the binary tree.
     * This is a recursive call.
     */
    static int goodNodesRec(TreeNode root, int maxVal) {

        // **** base condition ****
        if (root == null)
            return 0;

        // **** initialize count ****
        int count = 0;

        // **** update count & max value (if needed) ****
        if (root.val >= maxVal) {
            count++;
            maxVal = root.val;
        }

        // **** traverse left tree ****
        count += goodNodesRec(root.left, maxVal);

        // **** traverse right tree ****
        count += goodNodesRec(root.right, maxVal);

        // **** return count ****
        return count;
    }


    /**
     * Recursive call.
     */
    static int dfs(TreeNode root, int max) {

        // **** base case ****
        if (root == null)
            return 0;

        // **** initialize count ****
        int count = 0;

        // **** update count & max value (if needed) ****
        if (root.val >= max) {
            count++;
            max = root.val;
        }

        // **** recurse ****
        count += dfs(root.left, max);
        count += dfs(root.right, max);

        // **** return count ****
        return count;
    }


    /**
     * Call dfs to get the result.
     */
    static int goodNodes2(TreeNode root) {
        return dfs(root, Integer.MIN_VALUE);
    }


    /**
     * Test scaffolding.
     */
    public static void main(String[] args) {

        // **** open scanner ****
        Scanner sc = new Scanner(System.in);

        // **** read and split line describing the BT ****
        String[] arr = sc.nextLine().split(",");

        // **** close scanner ****
        sc.close();

        // **** binary tree root ****
        TreeNode root = null;

        // **** populate the BT ****
        root = populateTree(arr);

        // ???? ????
        System.out.print("main <<< root: ");
        inOrder(root);
        System.out.println();

        // **** generate and display the result ****
        System.out.println("main <<< output: " + goodNodes(root));

        // **** generate and display the result ****
        System.out.println("main <<< output: " + goodNodes2(root));
    }

}