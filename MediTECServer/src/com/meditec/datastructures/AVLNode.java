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
    
    public int height() {
		return height;
	}
    
    public void set_height(int height) {
		this.height = height;
	}

    public T data() {
        return data;
    }

    public void set_data(T data) {
        this.data = data;
    }

    public AVLNode<T> get_left() {
        return left;
    }

    public void set_left(AVLNode<T> left) {
        this.left = left;
    }

    public AVLNode<T> get_right() {
        return right;
    }

    public void set_right(AVLNode<T> right) {
        this.right = right;
    }
}
