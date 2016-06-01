package com.gregory.k.ads.linear_ds;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * FIFO Queue
 * @param <E> type of elements
 */
public class Queue<E> {
    private final double GROW_FACTOR = 2;

    private E[] hld; /** Holder of elements **/
    private int tail; /** End of queue. Elements will be added here **/
    private int head; /** Beginning of queue **/
    private int size; /** Number of elements that queue contains **/

    @SuppressWarnings("unchecked")
    public Queue(int initialCapacity) {
        hld = (E[]) new Object[initialCapacity];
        head = 0;
        tail = 0;
        size = 0;
    }

    public Queue() {
        this(10);
    }

    /**
     * Grows element holder "hld"
     */
    private void grow() {
        assert head == tail;
        E[] old_hld = hld;
        hld = Arrays.copyOfRange(hld, head, head + (int) (hld.length * GROW_FACTOR));
        if (head < tail) { //Queue is not translated through array border
            tail = tail - head;
        } else { //Queue IS translated through array border
            //Count number of elements between head and border
            int head_els = old_hld.length - head;
            //Copy el-s from beginning of array and tail
            System.arraycopy(old_hld, 0, hld, head_els, tail);
            tail = head_els + tail;
        }
        head = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    public boolean add(E e) {
        if (size == hld.length)
            grow();
        hld[tail++] = e;
        size++;
        tail %= hld.length;
        return true;
    }

    public boolean offer(E e) {
        add(e);
        return true;
    }

    /** Can be used only after size > 0 is ensured **/
    private E safeRemove(){
        E ret = hld[head];
        hld[head++] = null;
        size--;
        head %= hld.length;
        return ret;
    }

    public E remove() {
        if (size == 0)
            throw new NoSuchElementException("Queue is empty");
        return safeRemove();
    }

    public E poll() {
        if(size!=0)
            return safeRemove();
        else return null;
    }

    /** Can be used only after size > 0 is ensured **/
    private E safeHeadGet(){
        return hld[head];
    }

    public E element() {
        if(size!=0)
            return safeHeadGet();
        else throw new NoSuchElementException("Queue is empty");
    }

    public E peek() {
        if(size!=0)
            return hld[head];
        else return null;
    }

    @Override
    public String toString(){
        StringBuilder sb=  new StringBuilder();
        sb.append('[');
        if(head<tail){
            for(int i = head; i< size+head;i++){
                sb.append(hld[i%hld.length]);
                sb.append(' ');
            }
        }
        sb.append(']');
        return sb.toString();
    }

}
