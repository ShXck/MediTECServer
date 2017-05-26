package com.meditec.datastructures;

public class AVLTree<A extends Comparable<? super A>> {
	
	private AVLNode<A> root;
	private int count = 0;
	
	public AVLTree(){
		root = null;
	}
	
	/**
	 * Inserta un nuevo valor al árbol.
	 * @param data el contenido.
	 */
	public void insert(A data){
		root = insert(data, root);
	}
	
	/**
	 * método auxiliar para insertar un nuevo nodo.
	 * @param data el contenido del nodo.
	 * @param node el nodo inicial.
	 * @return el nuevo nodo.
	 */
	private AVLNode<A> insert(A data, AVLNode<A> node) {
		if (node == null) {
			return new AVLNode(data);
		}
		
		int result = data.compareTo(node.data());
		
		if (result < 0) {
			node.set_left(insert(data, node.left()));
		}else if(result > 0){
			node.set_right(insert(data, node.right()));
		}else{
			//
		}
		count++;
		return balance(node);
	}
	/**
	 * Elimina un nodo del árbol.
	 * @param data lo que se quiere eliminar.
	 */
	public void remove(A data){
		root = remove(data, root);
	}
	
	/**
	 * Método auxiliar para eliminar un nodo.
	 * @param data lo que se quiere eliminar.
	 * @param node el nodo inicial.
	 * @return el nodo eliminado.
	 */
	private AVLNode<A> remove(A data, AVLNode<A> node){
		
		if (root == null) {
			return node;
		}
		
		int result = data.compareTo(node.data());
		
		if (result < 0) {
			node.set_left(remove(data, node.left()));
		}else if(result > 0){
			node.set_right(remove(data, node.right()));
		}else if (node.left() != null && node.right() != null) {
			node.set_data(find_min(node.right()).data());
			node.set_right(remove(node.data(), node.right()));
		}else{
			if (node.left() != null) {
				node = node.left();
			}else{
				node = node.right();
			}
		}
		count--;
		return balance(node);
	}
	
	/**
	 * Balancea el árbol.
	 * @param node el nodo que se quiere balancear.
	 * @return el nodo.
	 */
	private AVLNode<A> balance(AVLNode<A> node){
		
		final int allow_imbalance = 1;
		
		if (node == null) {
			return node;
		}
		if (height(node.left()) - height(node.right()) > allow_imbalance){
			if (height(node.left().left()) >= height(node.left().right())) {
				node = rotate_with_left_child(node);
			}else {
				node = double_rotate_with_left_child(node);
			}
		}else {
			if (height(node.right()) - height(node.right()) > allow_imbalance){
				if (height(node.right()) >= height(node.left())) {
					node = rotate_with_right_child(node);
				}else {
					node = double_rotate_with_right_child(node);
				}
			}
		}
		node.set_height(Math.max(height(node.left()), height(node.right()) + 1));
		return node;	
	}
	
	/**
	 * Rotación izq-izq
	 * @param k2 el nodo que se debe rotar.
	 * @return el nodo una vez hecha la rotación.
	 */
    private AVLNode<A> rotate_with_left_child( AVLNode<A> k2 )
    {
        AVLNode<A> k1 = k2.left();
        k2.set_left(k1.right());
        k1.set_right(k2);
        k2.set_height(Math.max( height( k2.left() ), height( k2.right() ) ) + 1);
        k1.set_height(Math.max( height( k1.left() ), k2.height()) + 1); 
        return k1;
    }
    
    /**
     * Rotación der-der.
     * @param k1 el nodo que se debe rotar.
     * @return el nodo, una vez hecha la rotación.
     */
    private AVLNode<A> rotate_with_right_child( AVLNode<A> k1 )
    {
        AVLNode<A> k2 = k1.right();
        k1.set_right(k2.left());
        k2.set_left(k1);
        k1.set_height(Math.max( height( k1.left() ), height( k1.right() ) ) + 1);
        k2.set_height(Math.max( height( k2.right() ), k1.height() ) + 1);
        return k2;
    }
    
    /**
     * Rotación doble izquierda.
     * @param k3 el nodo que se debe rotar.
     * @return el nodo una vez hecha la rotación.
     */
    private AVLNode<A> double_rotate_with_left_child( AVLNode<A> k3 )
    {
        k3.set_left(rotate_with_right_child(k3.left()));
        return rotate_with_left_child( k3 );
    }
    
    /**
     * Rotación doble derecha
     * @param k1 el nodo que se debe rotar.
     * @return el nodo una vez hecha la rotación.
     */
    private AVLNode<A> double_rotate_with_right_child( AVLNode<A> k1 )
    {
        k1.set_right(rotate_with_left_child(k1.right()));
        return rotate_with_right_child(k1);
    }
	
    /**
     * Método para saber la altura de un nodo.
     * @param node el nodo que se quiere saber la altura;
     * @return la altura del nodo.
     */
	private int height(AVLNode<A> node) {
		return node == null? -1:node.height();
	}
	
	/**
	 * 
	 * @return si el árbol está vacío.
	 */
	public boolean is_empty(){
		return root == null;
	}
	
	/**
	 * Encuentra el nodo más a la izquierda.
	 * @param node el nodo inicial.
	 * @return el nodo menor.
	 */
	private AVLNode<A> find_min(AVLNode<A> node){
		if (node == null) {
			return node;
		}
		
		while(node.left() != null){
			node = node.left();
		}
		return node;
	}
	
	/**
	 * Encuentra el nodo más a la derecha.
	 * @param node el nodo inicial.
	 * @return el nodo mayor.
	 */
	private AVLNode<A> find_max(AVLNode<A> node){
		if (node == null) {
			return node;
		}
		
		while(node.right() != null){
			node = node.right();
		}
		return node;
		
	}
	
	/**
	 * Imprime todos los valores dentro del árbol.
	 * @param node el nodo inicial.
	 */
	public void print(AVLNode<A> node){
		if(node != null){
			print(node.left());
			System.out.println(node.data());
			print(node.right());
		}
	}
	
	/**
	 * Encuentra un nodo.
	 * @param data la información que queremos encontrar.
	 * @return si el contenido está en el árbol.
	 */
	public boolean find(A data){
        return find(data, this.root);
    }
	
	/**
	 * Método auxiliar para encontrar un valor en el árbol.
	 * @param data la información que se quiere encontrar.
	 * @param node el nodo inicial.
	 * @return si el contenido está en el árbol.
	 */
    private boolean find(A data, AVLNode<A> node) {

        boolean found = false;

        while ((node != null) && !found){

            if (data.compareTo(node.data()) < 0){
                node = node.left();
            }else if (data.compareTo(node.data()) > 0){
                node = node.right();
            }else {
                found = true;
                break;
            }
            found = find(data,node);
        }
        return found;
    }
    
    /**
     * 
     * @return La raíz del árbol.
     */
    public AVLNode<A> root() {
		return root;
	}
    
    /**
     * @return la cantidad de nodos en el árbol.
     */
    public int count(){
    	return count;
    }
    
    /**
     * Elimina todos los valores del árbol.
     */
    public void clear(){
    	root = null;
    }
    

}
