package com.meditec.datastructures;

public class TreeNode<N> {

    private N data;
    private TreeNode left;
    private TreeNode right;
    private int height;
    private int depth;
    private int key;

    public TreeNode(int key, N data){
        this.data = data;
        this.key = key;
        this.right = null;
        this.left = null;
        this.height = 0;
        this.depth = 0;
    }

    public int key() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int depth() {
        return depth;
    }

    public void set_depth(int depth) {
        this.depth = depth;
    }

    public int height() {
        return height;
    }

    public void set_height(int height) {
        this.height = height;
    }

    public N data() {
        return data;
    }

    public void set_data(N data) {
        this.data = data;
    }

    public TreeNode get_left() {
        return left;
    }

    public void set_left(TreeNode<N> left) {
        this.left = left;
    }

    public TreeNode get_right() {
        return right;
    }

    public void set_right(TreeNode<N> right) {
        this.right = right;
    }

    public void get_height(TreeNode<N> node, int depth){
        if (node != null){
            node.set_depth(depth);
            get_height(node.get_left(), depth + 1);
            get_height(node.get_right(), depth + 1);
        }
    }


}
