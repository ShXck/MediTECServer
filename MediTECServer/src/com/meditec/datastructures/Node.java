package com.meditec.datastructures;

public class Node<T> {

    
    private T data;
    private Node<T> next;

    public Node(T data){
        this.data = data;
        this.next = null;
    }
    
    /** 
     * @return si se tiene una referencia al siguiente.
     */
    public boolean hasNext(){
        return this.next() == null;
    }
    
    /**
     * @return la información del nodo.
     */
    public T data() {
        return data;
    }
    
    /**
     * Asigna un valor al contenido del nodo.
     * @param data el contenido.
     */
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * @return la referencia al nodo siguiente.
     */
    public Node next() {
        return next;
    }
    
    /**
     * Asigna un valor a la referencia siguiente.
     * @param next
     */
    public void setNext(Node next) {
        this.next = next;
    }


}
