package com.meditec.datastructures;

public class List<T> {

    private Node head;

    private int size;

    public List(){
        this.head = null;
        this.size = 0;
    }

    /**
     * Revisa si la lista esta vacia.
     * @return si la lista esta vacia.
     */
    public boolean is_empty(){
        return this.head == null;
    }

    /**
     * Añade un nodo al inicio.
     * @param data dato del nodo.
     */
    public void add_first(T data){

        Node newNode = new Node(data);

        if(is_empty()){
            set_head(newNode);
        }else{
            newNode.setNext(peek());
            set_head(newNode);
        }
        size++;
    }

    /**
     * Añade un nuevo nodo al final de la lista..
     * @param data datos del nodo.
     */
    public void add_last(T data){

        Node newNode = new Node(data);

        if(is_empty()){
            set_head(newNode);
        }else{
            Node current = this.head;

            while(current.getNext() != null){
                current = current.getNext();
            }
            current.setNext(newNode);
        }
        size++;
    }

    /**
     * remueve la cabeza
     */
    public void remove_head(){
        set_head(peek().getNext());
        size--;
    }


    /**
     * Limpia la lista.
     */
    public void clear_list(){

        Node current = this.head;

        while (current.getNext() != null){
            current.setData(null);
            current = current.getNext();
        }

    }

    /**
     * imprime la lista.
     */
    public void print_list() {
        Node current = this.head;
        while (current != null) {
            System.out.print(current.getData() + " ");
            current = current.getNext();
        }
    }

    public boolean contains(T element){

        Node current = this.head;

        while (current.getNext() != null){
            if(current.getData() == element){
                return true;
            }else{
                current = current.getNext();
            }
        }
        return false;
    }

    public Node peek() {
        return head;
    }

    public int get_size() {
        return size;
    }

    public void set_head(Node head) {
        this.head = head;
    }

    public Node get(int posicion){

        Node current = head;

        int i = 0;

        while (i < posicion){
            current = current.getNext();
            i++;
        }

        return current;
    }

    public void remove(T data){
        Node current = head;

        while (current.getData() != null){
            if (current.getData() == data || current.getData().equals(data)){
                current.setNext(current.getNext().getNext());
                return;
            }else {
                current = current.getNext();
            }
        }
    }

}
