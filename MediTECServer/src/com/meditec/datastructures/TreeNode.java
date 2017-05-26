package com.meditec.datastructures;

public class TreeNode<N> {

    private N data;
    private TreeNode left;
    private TreeNode right;
    private int height;
    private int depth;
    private int key;
    private String path;

    public TreeNode(int key, N data){
        this.data = data;
        this.key = key;
        this.right = null;
        this.left = null;
        this.height = 0;
        this.depth = 0;
        this.path = null;
    }
    
    /**
     * @return el id del nodo.
     */
    public int key() {
        return key;
    }
    
    /**
     * asigna un valor al id del nodo.
     * @param key el nuevo valor.
     */
    public void setKey(int key) {
        this.key = key;
    }
    
    /**
     * @return La profundidad del nodo.
     */
    public int depth() {
        return depth;
    }
    
    /**
     * Asigna un valor a la profundidad del nodo.
     * @param depth la profundidad nueva.
     */
    public void set_depth(int depth) {
        this.depth = depth;
    }
    
    /**
     * @return la altura del nodo.
     */
    public int height() {
        return height;
    }
    
    /**
     * Asigna un valor a la altura del nodo.
     * @param height la altura nueva.
     */
    public void set_height(int height) {
        this.height = height;
    }
    
    /**
     * @return el contenido del nodo.
     */
    public N data() {
        return data;
    }
    
    /**
     * Asigna el contenido del nodo.
     * @param data el nuevo contenido.
     */
    public void set_data(N data) {
        this.data = data;
    }
    
    /**
     * @return el hijo izquierdo del nodo.
     */
    public TreeNode left() {
        return left;
    }
    
    /**
     * Asigna un valor al hijo izquierdo del nodo.
     * @param left el nuevo hijo.
     */
    public void set_left(TreeNode<N> left) {
        this.left = left;
    }
    
    /**
     * @return el hijo derecho del nodo.
     */
    public TreeNode right() {
        return right;
    }
    
    /**
     * Asigna un valor al hijo derecho.
     * @param right el nuevo hijo derecho.
     */
    public void set_right(TreeNode<N> right) {
        this.right = right;
    }
    
    /**
     * @return el camino hacia el nodo.
     */
    public String path(){
    	return this.path;
    }
    
    /**
     * Asigna un valor al camino del nodo.
     * @param path el nuevo camino.
     */
    public void set_path(String path){
    	this.path = path;
    }
    
    /**
     * Asigna la altura del nodo.
     * @param node el nodo inicial.
     * @param depth la altura.
     */
    public void get_height(TreeNode<N> node, int depth){
        if (node != null){
            node.set_depth(depth);
            get_height(node.left(), depth + 1);
            get_height(node.right(), depth + 1);
        }
    }


}
