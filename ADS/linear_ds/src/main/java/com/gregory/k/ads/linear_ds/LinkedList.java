package com.gregory.k.ads.linear_ds;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.*;

public class LinkedList implements Iterable<String> {
    ListNode head;
    ListNode tail;
    int size = 0;
    public LinkedList() {
        //Signal nodes
        head = new ListNode();
        tail = new ListNode();

        head.prev = null;
        head.next = tail;
        tail.prev = head;
        tail.next = null;
    }

    public int size() {
        return size;
    }

    /**
     * No checks. Use only after checking node
     **/

    protected String safeRemoveNode(ListNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return node.data;
    }

    protected void addAfter(String data, ListNode node) {
        ListNode new_node = new ListNode(data);
        new_node.prev = node;
        new_node.next = node.next;
        node.next.prev = new_node;
        node.next = new_node;
        size++;
    }

    protected ListNode getNodeAt(int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException();
        ListNode at = head.next;
        for (int i = 0; i < index; i++)
            at = at.next;
        return at;
    }

    @Override
    public Iterator<String> iterator() {
        return new LinkedListIterator(this);
    }

    public void addLast(String val) {
        addAfter(val, tail.prev);
    }

    public String removeLast() {
        if (tail.prev == head)
            throw new NoSuchElementException("List is empty");
        return safeRemoveNode(tail.prev);
    }

    public void addFirst(String val) {
        addAfter(val, head);
    }

    public String removeFirst() {
        if (head.next == tail)
            throw new NoSuchElementException("List is empty");
        return safeRemoveNode(head.next);
    }

    public void addAt(String val, int index) {
        if(index == 0) addFirst(val);
        else addAfter(val, getNodeAt(index-1));
    }

    public String get(int index) {
        ListNode el = getNodeAt(index);
        if (el == head || el == tail)
            throw new NoSuchElementException();
        return el.data;
    }

    public String removeAt(int index) {
        return safeRemoveNode(getNodeAt(index));
    }

    public String update(String val, int index) {
        ListNode el = getNodeAt(index);
        if (el == head || el == tail)
            throw new NoSuchElementException();
        String ret = el.data;
        el.data = val;
        return ret;
    }

    class ListNode {
        String data;
        ListNode prev;
        ListNode next;

        private void tryParse(String orig){
            //throw new NullPointerException();
            /*
                NumberFormat format = NumberFormat.getInstance();
                ParsePosition position = new ParsePosition(0);
                Number num = format.parse(orig,position);
                Class<? extends Number> cls = num.getClass();
                List suitableClasses =
                        Arrays.asList(Integer.class,Byte.class,Short.class,Long.class);
                if(!suitableClasses.contains(cls) || orig.length() != position.getIndex())
                    throw new IllegalArgumentException("This list can hold only numbers");
                    */
            Long.parseLong(orig);
        }

        public ListNode(String data) {
            if(data!=null) tryParse(data);
            this.data = data;
        }

        public ListNode() {
            this(null);
        }
    }

    class LinkedListIterator implements Iterator<String> {
        LinkedList list;
        ListNode current;

        public LinkedListIterator(LinkedList list) {
            this.list = list;
            current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current.next != list.tail;
        }

        @Override
        public String next() {
            if (current.next == tail)
                throw new NoSuchElementException("Reached end of list");
            current = current.next;
            return current.data;
        }

        @Override
        public void remove() {
            if (current == list.head)
                throw new IllegalStateException("Can't remove list head");
            ListNode new_current = current.prev;
            safeRemoveNode(current);
            current = new_current;
        }
    }
}
