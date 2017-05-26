package com.meditec.datastructures;

public class BinaryTree<B extends Comparable<? super B>> {

    private TreeNode<B> root;

    public BinaryTree(){
        root = null;
    }
    
    /**
     * Inserta un nuevo nodo en el árbol.
     * @param key el id del nodo.
     * @param data la información.
     */
    public void insert(int key, B data){
        root = insert(key, data, root);
    }
    
    /**
     * Método auxiliar para inserta un nodo en el árbol.
     * @param key la id del nodo.
     * @param data el contenido.
     * @param node el nodo inicial.
     * @return el nodo insertado.
     */
    private TreeNode<B> insert(int key, B data, TreeNode<B> node){
        if (node == null){
            return new TreeNode<B>(key,data);
        }else if (key < node.key()){
            node.set_left(insert(key,data,node.left()));
        }else if (key > node.key()){
            node.set_right(insert(key,data,node.right()));
        }

        node.set_depth(find_depth(root,0));
        node.set_height(find_height(node));
        return node;
    }
    
    /**
     * Recorre el árbol en orden
     * @param node el nodo inicial.
     */
    public void in_order(TreeNode<B> node) {
        if (node != null) {
            in_order(node.left());
            System.out.println(node.data());
            in_order(node.right());
        }
    }
    
    /**
     * Elimina un nodo del árbol.
     * @param key el id del nodo que se quiere eliminar.
     */
    public void remove(int key){
        root = remove(key,root);
    }
    
    /**
     * Método auxiliar para eliminar un nodo del árbol.
     * @param key el id del nodo.
     * @param node el nodo inicial.
     * @return el nodo eliminado.
     */
    private TreeNode<B> remove(int key, TreeNode<B> node){
        if (node == null){
            return node;
        }
        if (Integer.compare(key,node.key()) < 0){
            node.set_left(remove(key, node.left()));
        }else if (Integer.compare(key,node.key()) > 0){
            node.set_right(remove(key, node.right()));
        }else if (node.left() != null && node.right() != null){
            node.setKey(find_min(node.right()).key());
            node.set_right(remove(node.key(), node.right()));
        }else {
            node = node.left() != null? node.left() : node.right();
        }
        return node;
    }
    
    /**
     * Encuentra el nodo más a la izquierda.
     * @param node el nodo inicial.
     * @return el nodo menor.
     */
    private TreeNode<B> find_min(TreeNode<B> node){
        if (node == null){
            return null;
        }else if (node.left() == null){
            return node;
        }else {
            return find_min(node.left());
        }
    }

    
    /** 
     * @return la raíz del árbol.
     */
    public TreeNode<B> root(){
        return root;
    }
    
    /**
     * Encuentra un nodo en el árbol.
     * @param key el id del nodo.
     * @return el nodo que coincide con el id.
     */
    public TreeNode<B> find(int key){

        TreeNode<B> current = root;

        while (current.key() != key){
            if (key < current.key()){
                current = current.left();
            }else {
                current = current.right();
            }

            if (current == null){
                return null;
            }
        }
        return current;
    }
    
    /**
     * Encuentra la profundidad de un nodo.
     * @param node el nodo inicial.
     * @param depth la profundidad obtenida.
     * @return la profundidad del nodo.
     */
    private int find_depth(TreeNode<B> node, int depth) {
        if (node != null){
            node.set_depth(depth);
            find_depth(node.left(), depth + 1);
            find_depth(node.right(), depth + 1);
        }
        return depth;
    }
    
    /**
     * Encuentra la altura de un nodo.
     * @param node el nodo.
     * @return la altura del nodo.
     */
    private int find_height(TreeNode<B> node){
        if (node == null){
            return -1;
        }
        return 1 + Math.max(find_height(node.left()), find_height(node.right()));
    }

    public boolean is_empty(){
        return root == null;
    }
}
