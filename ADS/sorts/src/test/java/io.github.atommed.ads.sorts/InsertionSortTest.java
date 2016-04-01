package io.github.atommed.ads.sorts;

import org.junit.BeforeClass;

import java.util.Comparator;

/**
 * Created by Gregory on 31.03.2016.
 */
public class InsertionSortTest extends SortTest {
    @BeforeClass
    public static void setSortAlgortithm(){
        sorter  = new SortAlgorithm() {
            @Override
            public <T> void sort(T[] arr, Comparator<T> c) {
                InsertionSort.sort(arr,c);
            }
        };
    }
}
