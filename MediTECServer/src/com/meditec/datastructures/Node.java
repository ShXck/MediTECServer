package com.meditec.datastructures;

public class Node<T> {

    //What the node contains.
    private T data;
    //Reference to the next node in the list.
    private Node<T> next;

    public Node(T data){
        this.data = data;
        this.next = null;
    }

    public boolean hasNext(){
        return this.getNext() == null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


}
