package io.github.atommed.ads.sorts;

import java.util.Comparator;

/**
 * Created by Gregory on 31.03.2016.
 */
public abstract class SortAlgorithm {
    abstract public <T> void sort(T[] arr, Comparator<T> c);
    public  <T extends Comparable<T>> void sort(T[] arr){
        sort(arr, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }
        });
    }
}
