package com.meditec.datastructures;


public class SplayNode<S> {

    public SplayNode left;
	public SplayNode right;
	public SplayNode parent;
    public int key;
    public S element;

    /** Constructor **/
    public SplayNode(S data, int key)
    {
        this(data, key, null, null, null);
    }
    /** Constructor **/
    public SplayNode(S element,int key, SplayNode left, SplayNode right, SplayNode parent)
    {
        this.left = left;
        this.right = right;
        this.parent = parent;
        this.element = element;
        this.key = key;
    }
}
