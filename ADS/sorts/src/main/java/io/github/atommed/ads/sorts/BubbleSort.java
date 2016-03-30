package io.github.atommed.ads.sorts;

import java.util.Comparator;

/**
 * Created by Gregory on 30.03.2016.
 */
public class BubbleSort {
    public static <T> void sort(T[] arr, Comparator<T> c){
        for(int i = 0; i < arr.length; i++){
            for(int j = i; j < arr.length - 1; j++){
                if(c.compare(arr[j],arr[j+1]) < 0){
                    T t = arr[j];
                    arr[j] = arr[i];
                    arr[i] = t;
                }
            }
        }
    }

    public static <T extends Comparable<T>> void sort(T[] arr){
        sort(arr, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }
}
