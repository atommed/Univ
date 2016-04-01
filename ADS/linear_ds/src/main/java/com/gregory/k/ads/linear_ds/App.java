package com.gregory.k.ads.linear_ds;

import java.util.Iterator;

public class App {
    public static void main(String... args) {
        Queue<String> queue = new Queue<>();
        LinkedList ll = new LinkedList();
        for(int i = 0; i < 50; i++)
            ll.addLast(Integer.toString(i));
        Iterator<String> it = ll.iterator();
        while(it.hasNext()){
            String new_s = it.next();
            long l = Long.parseLong(new_s);
            if(l%2==0) {
                it.remove();
                queue.add(new_s);
            }
        }
        System.out.println(queue);
        System.out.print("[");
        for(String x: ll)
            System.out.print(x+" ");
        System.out.print("]");
    }
}
