package io.github.atommed.bst;

import java.util.Stack;

/**
 * Created by gregory on 08.06.16.
 */
public class BSTree<K extends Comparable<K>,V> {
    private static class Node<K extends Comparable<K>,V>{
        Node<K,V> parent;
        Node<K,V> left;
        Node<K,V> right;
        K key;
        V data;

        public Node(K key,V data,Node<K,V> left, Node<K,V> right,Node<K,V> parent) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.data = data;
            this.parent = parent;
        }
    }

    public static interface RemoveCriteria<K,V>{
        boolean toRemove(K key, V value);
    }

    private Node<K,V> head;

    public V insert(K key,V newValue){
        if(head == null) {
            head = new Node<>(key,newValue, null, null,null);
            return null;
        }
        Node<K,V> prev = null;
        Node<K,V> curr = head;
        int cmp = curr.key.compareTo(key);
        while(curr!=null){
            prev = curr;
            cmp = curr.key.compareTo(key);
            if(cmp == 0) {
                V ret = curr.data;
                curr.data = newValue;
                return ret;
            }
            else if(cmp < 0){
                curr = curr.right;
            }
            else if(cmp > 0){
                curr = curr.left;
            }
        }
        Node<K,V> newNode = new Node<>(key,newValue, null, null,prev);
        if(cmp < 0) prev.right = newNode;
        else if(cmp > 0) prev.left = newNode;
        return null;
    }

    private void preorderTraverse(Node<K,V> n,int spacesCount){
        String space = "";
        for(int i = 0; i  < spacesCount;i++)
            space+=".";

        if(n!=null){
            System.out.println(space + n.data.toString());
            if(n.left==null && n.right == null) return;
            preorderTraverse(n.left, spacesCount+1);
            preorderTraverse(n.right, spacesCount+1);
        }
        else {
            System.out.println(space + "(null)");
        }
    }

    public void traverse(){
        preorderTraverse(head, 0);
    }

    public V get(K key){
        Node<K, V> curr = this.head;

        while(curr!=null){
            int cmp = curr.key.compareTo(key);
            if(cmp < 0) curr = curr.right;
            if(cmp > 0) curr = curr.left;
            if(cmp == 0) return curr.data;
        }

        return null;
    }

    private Node<K,V> findSuccessor(Node<K,V> node){
        if(node.right == null) throw new IllegalStateException("No successor"); //return node;
        Node<K, V> curr = node.right;
        while(curr.left != null){
            curr = curr.left;
        }
        return curr;
    }

    private void dangerReplaceNode(Node<K,V> node, Node<K,V> newNode){
        Node<K, V> parent = node.parent;
        if(parent == null){
            head = newNode;
        } else {
            if (node == node.parent.left)
                node.parent.left = newNode;
            else if (node == node.parent.right)
                node.parent.right = newNode;
        }
        if(newNode != null)
            newNode.parent = parent;
    }

    private void removeChildfullNode(Node<K,V> node){
        Node<K, V> successor = findSuccessor(node);
        dangerReplaceNode(successor, successor.right);

        successor.left = node.left;
        successor.right = node.right;

        successor.left.parent = successor;
        successor.right.parent = successor;

        //successor.parent = node.parent;

        dangerReplaceNode(node,successor);
    }

    private void removeSimple(Node<K,V> node){
        if(node.left != null && node.right != null)
            throw new IllegalStateException("not simple");
        if(node.left==null && node.right == null){
            if(node.parent.left == node)
                node.parent.left = null;
            else if(node.parent.right == node)
                node.parent.right = null;
        }
        else if(node.right != null) {
            node.right.parent = node.parent;
            if(node.parent.left == node)
                node.parent.left = node.right;
            else if(node.parent.right == node)
                node.parent.right = node.right;
        }
        else if(node.left != null) {
            node.left.parent = node.parent;
            if(node.parent.left == node)
                node.parent.left = node.left;
            else if(node.parent.right == node)
                node.parent.right = node.left;
        }
    }

    private void removeNode(Node<K,V> node){
        if(node.parent == null) {
            head = null;
            return;
        }

        if(node.left != null && node.right != null) {
            Node<K,V> successor = findSuccessor(node);
            removeSimple(successor);

            successor.left = node.left;
            node.left.parent = successor;
            successor.right = node.right;
            node.right.parent = successor;
            successor.parent = node.parent;
            if(node.parent.left == node)
                node.parent.left = successor;
            else if(node.parent.right == node)
                node.parent.right = successor;
        }
        else removeSimple(node);
    }

    private void findAll(Stack<Node<K,V>> s, Node<K,V> node, RemoveCriteria<K,V> predicate){
        if(node!=null){
            if(predicate.toRemove(node.key,node.data))
                s.push(node);
            findAll(s,node.left,predicate);
            findAll(s,node.right,predicate);
        }
    }

    public void removeAll(RemoveCriteria<K,V> predicate){
        Stack<Node<K, V>> nodesToRemove = new Stack<>();
        findAll(nodesToRemove,head,predicate);
        for(Node<K,V> n : nodesToRemove)
            removeNode(n);
    }
}
