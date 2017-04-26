package com.meditec.datastructures;

public class BinaryTree<B extends Comparable<? super B>> {

    private TreeNode<B> root;

    public BinaryTree(){
        root = null;
    }

    public void insert(int key, B data){
        root = insert(key, data, root);
    }

    private TreeNode<B> insert(int key, B data, TreeNode<B> node){
        if (node == null){
            return new TreeNode<B>(key,data);
        }else if (key < node.key()){
            node.set_left(insert(key,data,node.get_left()));
        }else if (key > node.key()){
            node.set_right(insert(key,data,node.get_right()));
        }

        node.set_depth(find_depth(root,0));
        node.set_height(find_height(node));
        return node;
    }

    public void in_order(TreeNode<B> node) {
        if (node != null) {
            in_order(node.get_left());
            System.out.println(node.data());
            in_order(node.get_right());
        }
    }

    public void remove(int key){
        root = remove(key,root);
    }

    private TreeNode<B> remove(int key, TreeNode<B> node){
        if (node == null){
            return node;
        }
        if (Integer.compare(key,node.key()) < 0){
            node.set_left(remove(key, node.get_left()));
        }else if (Integer.compare(key,node.key()) > 0){
            node.set_right(remove(key, node.get_right()));
        }else if (node.get_left() != null && node.get_right() != null){
            node.setKey(find_min(node.get_right()).key());
            node.set_right(remove(node.key(), node.get_right()));
        }else {
            node = node.get_left() != null? node.get_left() : node.get_right();
        }
        return node;
    }

    private TreeNode<B> find_min(TreeNode<B> node){
        if (node == null){
            return null;
        }else if (node.get_left() == null){
            return node;
        }else {
            return find_min(node.get_left());
        }
    }


    public TreeNode<B> root(){
        return root;
    }

    public TreeNode<B> find(int key){

        TreeNode<B> current = root;

        while (current.key() != key){
            if (key < current.key()){
                current = current.get_left();
            }else {
                current = current.get_right();
            }

            if (current == null){
                return null;
            }
        }
        return current;
    }

    private int find_depth(TreeNode<B> node, int depth) {
        if (node != null){
            node.set_depth(depth);
            find_depth(node.get_left(), depth + 1);
            find_depth(node.get_right(), depth + 1);
        }
        return depth;
    }

    private int find_height(TreeNode<B> node){
        if (node == null){
            return -1;
        }
        return 1 + Math.max(find_height(node.get_left()), find_height(node.get_right()));
    }

    public boolean is_empty(){
        return root == null;
    }
}
