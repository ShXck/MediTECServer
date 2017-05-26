package com.meditec.datastructures;

import com.meditec.datastructures.Node;

public class SplayTree<S extends Comparable<S>> {

    private SplayNode root;
    private int count = 0;

    public SplayTree()
    {
        root = null;
    }

    /**
     * @return si el árbol está vacío o no.
     */
    public boolean isEmpty()
    {
        return root == null;
    }

    /**
     * Elimina todo el contenido del árbol.
     */
    public void clear()
    {
        root = null;
    }

    /**
     * Inserta un nuevo elemento en el árbol.
     * @param data el contenido.
     * @param key el id del nodo.
     */
    public void insert(S data, int key)
    {
        SplayNode node = root;
        SplayNode secondary_node = null;
        while (node != null)
        {
            secondary_node = node;
            if (key < secondary_node.key)
                node = node.right;
            else
                node = node.left;
        }
        node = new SplayNode(data,key);
        node.element = data;
        node.parent = secondary_node;
        if (secondary_node == null)
            root = node;
        else if (key < secondary_node.key)
            secondary_node.right = node;
        else
            secondary_node.left = node;
        splay(node);
        count++;
    }

    /**
     * Rotación izq-izq
     * @param c el nodo hijo.
     * @param p el nodo padre.
     */
    public void makeLeftChildParent(SplayNode c, SplayNode p)
    {
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
            throw new RuntimeException("WRONG");

        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.right != null)
            c.right.parent = p;

        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    /**
     * Rotación der-der
     * @param c el nodo hijo.
     * @param p el nodo padre.
     */
    public void makeRightChildParent(SplayNode c, SplayNode p)
    {
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
            throw new RuntimeException("WRONG");
        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.left != null)
            c.left.parent = p;
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }

    /**
     * Función que se encarga de balancear el árbol.
     * @param x el nodo que se quiere balancear.
     */
    private void splay(SplayNode x)
    {
        while (x.parent != null)
        {
            SplayNode Parent = x.parent;
            SplayNode GrandParent = Parent.parent;
            if (GrandParent == null)
            {
                if (x == Parent.left)
                    makeLeftChildParent(x, Parent);
                else
                    makeRightChildParent(x, Parent);
            }
            else
            {
                if (x == Parent.left)
                {
                    if (Parent == GrandParent.left)
                    {
                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    }
                    else
                    {
                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                }
                else
                {
                    if (Parent == GrandParent.left)
                    {
                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    }
                    else
                    {
                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        root = x;
    }

    /**
     * Elimina un nodo del árbol
     * @param key el id del nodo.
     */
    public void remove(int key)
    {
        SplayNode node = find(key);
        remove(node);
    }

    /**
     * Método auxiliar para eliminar un nodo.
     * @param node el nodo inicial.
     */
    private void remove(SplayNode node)
    {
        if (node == null)
            return;

        splay(node);
        if( (node.left != null) && (node.right !=null))
        {
            SplayNode min = node.left;
            while(min.right!=null)
                min = min.right;

            min.right = node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
        }
        else if (node.right != null)
        {
            node.right.parent = null;
            root = node.right;
        }
        else if( node.left !=null)
        {
            node.left.parent = null;
            root = node.left;
        }
        else
        {
            root = null;
        }
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        count--;
    }

    /**
     * @return el número de nodos del árbol.
     */
    public int countNodes()
    {
        return count;
    }

    /**
     * Busca un elemento en el árbol.
     * @param key el id del nodo.
     * @return si el contenido está en el árbol.
     */
    public boolean search(int key)
    {
        return find(key) != null;
    }
    
    /**
     * Método auxiliar para encontrar un nodo en el árbol.
     * @param key el id del nodo.
     * @return el nodo con el contenido si es que existe.
     */
    private SplayNode find(int key){
        SplayNode z = root;
        while (z != null)
        {
            if (key < z.key)
                z = z.right;
            else if (key > z.key)
                z = z.left;
            else
                return z;
        }
        return null;
    }

    /**
     * Recorre el árbol en orden.
     */
    public void inorder()
    {
        inorder(root);
    }
    /**
     * Método auxiliar para recorrer el árbol en orden.
     * @param r el nodo inicial.
     */
    private void inorder(SplayNode r)
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.element +" ");
            inorder(r.right);
        }
    }

    /**
     * Recorre el árbol en pre orden.
     */
    public void preorder()
    {
        preorder(root);
    }
    
    /**
     * Método auxiliar para recorrer en pre orden.
     * @param r el nodo inicial.
     */
    private void preorder(SplayNode r)
    {
        if (r != null)
        {
            System.out.print(r.element +" ");
            preorder(r.left);
            preorder(r.right);
        }
    }

    /**
     * Recorre el árbol en post orden.
     */
    public void postorder()
    {
        postorder(root);
    }
    
    /**
     * Método auxiliar para recorrer el árbol en post orden.
     * @param r el nodo inicial.
     */
    private void postorder(SplayNode r)
    {
        if (r != null)
        {
            postorder(r.left);
            postorder(r.right);
            System.out.print(r.element +" ");
        }
    }
    
    /**
     * @return la raíz del árbol.
     */
    public SplayNode<S> root(){
    	return root;
    }
    
    
}
