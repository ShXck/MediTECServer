package com.meditec.datastructures;

public class AVLTree<A extends Comparable<? super A>> {
	
	private AVLNode<A> root;
	
	public AVLTree(){
		root = null;
	}
	
	public void insert(A data){
		root = insert(data, root);
	}
	
	private AVLNode<A> insert(A data, AVLNode<A> node) {
		if (node == null) {
			return new AVLNode(data);
		}
		
		int result = data.compareTo(node.data());
		
		if (result < 0) {
			node.set_left(insert(data, node.get_left()));
		}else if(result > 0){
			node.set_right(insert(data, node.get_right()));
		}else{
			//
		}
		return balance(node);
	}
	
	public void remove(A data){
		root = remove(data, root);
	}
	
	private AVLNode<A> remove(A data, AVLNode<A> node){
		
		if (root == null) {
			return node;
		}
		
		int result = data.compareTo(node.data());
		
		if (result < 0) {
			node.set_left(remove(data, node.get_left()));
		}else if(result > 0){
			node.set_right(remove(data, node.get_right()));
		}else if (node.get_left() != null && node.get_right() != null) {
			node.set_data(find_min(node.get_right()).data());
			node.set_right(remove(node.data(), node.get_right()));
		}else{
			if (node.get_left() != null) {
				node = node.get_left();
			}else{
				node = node.get_right();
			}
		}
		
		return balance(node);
	}
	
	private AVLNode<A> balance(AVLNode<A> node){
		
		final int allow_imbalance = 1;
		
		if (node == null) {
			return node;
		}
		if (height(node.get_left()) - height(node.get_right()) > allow_imbalance){
			if (height(node.get_left().get_left()) >= height(node.get_left().get_right())) {
				node = rotate_with_left_child(node);
			}else {
				node = double_rotate_with_left_child(node);
			}
		}else {
			if (height(node.get_right()) - height(node.get_right()) > allow_imbalance){
				if (height(node.get_right()) >= height(node.get_left())) {
					node = rotate_with_right_child(node);
				}else {
					node = double_rotate_with_right_child(node);
				}
			}
		}
		node.set_height(Math.max(height(node.get_left()), height(node.get_right()) + 1));
		return node;	
	}
	
    private AVLNode<A> rotate_with_left_child( AVLNode<A> k2 )
    {
        AVLNode<A> k1 = k2.get_left();
        k2.set_left(k1.get_right());
        k1.set_right(k2);
        k2.set_height(Math.max( height( k2.get_left() ), height( k2.get_right() ) ) + 1);
        k1.set_height(Math.max( height( k1.get_left() ), k2.height()) + 1); 
        return k1;
    }

    private AVLNode<A> rotate_with_right_child( AVLNode<A> k1 )
    {
        AVLNode<A> k2 = k1.get_right();
        k1.set_right(k2.get_left());
        k2.set_left(k1);
        k1.set_height(Math.max( height( k1.get_left() ), height( k1.get_right() ) ) + 1);
        k2.set_height(Math.max( height( k2.get_right() ), k1.height() ) + 1);
        return k2;
    }

    private AVLNode<A> double_rotate_with_left_child( AVLNode<A> k3 )
    {
        k3.set_left(rotate_with_right_child(k3.get_left()));
        return rotate_with_left_child( k3 );
    }

    private AVLNode<A> double_rotate_with_right_child( AVLNode<A> k1 )
    {
        k1.set_right(rotate_with_left_child(k1.get_right()));
        return rotate_with_right_child(k1);
    }
	
	private int height(AVLNode<A> node) {
		return node == null? -1:node.height();
	}
	
	public boolean is_empty(){
		return root == null;
	}
	
	private AVLNode<A> find_min(AVLNode<A> node){
		if (node == null) {
			return node;
		}
		
		while(node.get_left() != null){
			node = node.get_left();
		}
		return node;
	}
	
	private AVLNode<A> find_max(AVLNode<A> node){
		if (node == null) {
			return node;
		}
		
		while(node.get_right() != null){
			node = node.get_right();
		}
		return node;
		
	}
	
	public void print(AVLNode<A> node){
		if(node != null){
			print(node.get_left());
			System.out.println(node.data());
			print(node.get_right());
		}
	}
	
	public boolean find(A data){
        return find(data, this.root);
    }

    private boolean find(A data, AVLNode<A> node) {

        boolean found = false;

        while ((node != null) && !found){

            if (data.compareTo(node.data()) < 0){
                node = node.get_left();
            }else if (data.compareTo(node.data()) > 0){
                node = node.get_right();
            }else {
                found = true;
                break;
            }
            found = find(data,node);
        }
        return found;
    }
    

}
