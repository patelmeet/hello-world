/*
226. Invert Binary Tree

Invert a binary tree.

     4
   /   \
  2     7
 / \   / \
1   3 6   9
to
     4
   /   \
  7     2
 / \   / \
9   6 3   1
Trivia:
This problem was inspired by this original tweet by Max Howell:
Google: 90% of our engineers use the software you wrote (Homebrew), but you can’t invert a binary tree on a whiteboard so fuck off.
*/

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public TreeNode invertTree(TreeNode root) {
        invertTreeUtil(root);
        return root;
    }
    
    public void invertTreeUtil(TreeNode node){
        if(node == null){
            return;}
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
        invertTreeUtil(node.left);
        invertTreeUtil(node.right);
    }
}
