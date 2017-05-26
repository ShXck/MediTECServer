package com.meditec.datastructures;

public class AVLNode<T> {
	
	private int height;
    private T data;
    private AVLNode<T> left;
    private AVLNode<T> right;
    
    public AVLNode(T data, AVLNode<T> left, AVLNode<T> right){
  
        this.data = data;
        this.left = left;
        this.right = right;
    }
    
    public AVLNode(T data){
    	this(data, null,null);
    }
    
    /**
     * 
     * @return la altura del nodo.
     */
    public int height() {
		return height;
	}
    
    /**
     * asigna la altura al nodo
     * @param height la nueva altura.
     */
    public void set_height(int height) {
		this.height = height;
	}
    
    /**
     * 
     * @return la información del nodo.
     */
    public T data() {
        return data;
    }
    
    /**
     * asigna información al nodo-
     * @param data el contenido.
     */
    public void set_data(T data) {
        this.data = data;
    }
    
    /**
     * 
     * @return el hijo izquierdo del nodo.
     */
    public AVLNode<T> left() {
        return left;
    }
    
    /**
     * Asigna un valor al hijo izquierdo.
     * @param left el contenido.
     */
    public void set_left(AVLNode<T> left) {
        this.left = left;
    }
    
    /**
     * Asigna un valor al hijo derecho.
     * @return el nuevo hijo derecho.
     */
    public AVLNode<T> right() {
        return right;
    }
    
    /**
     * Asigna un valor al hijo derecho.
     * @param right el nuevo hijo.
     */
    public void set_right(AVLNode<T> right) {
        this.right = right;
    }
}
