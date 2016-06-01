package io.github.atommed.ads.sorts;

import java.util.Comparator;

/**
 * Created by Gregory on 31.03.2016.
 */
public class InsertionSort {
    public static <T> void sort(T[] arr, Comparator<T> c){
        for(int i = 1; i < arr.length; i++){
            for(int j = i; j > 0; j--){
                if(c.compare(arr[j-1],arr[j]) > 0){
                    T t = arr[j];
                    arr[j] = arr[j-1];
                    arr[j-1] =t;
                }
                else break;
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
