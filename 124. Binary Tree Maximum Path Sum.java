/*
124. Binary Tree Maximum Path Sum

Given a binary tree, find the maximum path sum.

For this problem, a path is defined as any sequence of nodes from some starting node to any node in the tree along the parent-child connections. The path must contain at least one node and does not need to go through the root.

For example:
Given the below binary tree,

       1
      / \
     2   3
Return 6.
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
    int max = Integer.MIN_VALUE;
       
    public int maxPathSum(TreeNode root) {
        maxPathSumUtil(root);
        return max;
    }
    
    public int maxPathSumUtil(TreeNode node){
        if(node == null){
            return 0;}
        int l = maxPathSumUtil(node.left);
        int r = maxPathSumUtil(node.right);
        int retval = node.val+l > node.val+r ? node.val+l : node.val+r;
        retval = retval > node.val ? retval : node.val;
        max = max > retval ? max : retval;
        max = max > node.val+l+r ? max : node.val+l+r;
        return retval;
    }
}
