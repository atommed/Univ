package io.github.atommed.ads.sorts;

import org.junit.BeforeClass;

import java.util.Comparator;

/**
 * Created by Gregory on 30.03.2016.
 */
public class BubbleSortTest extends SortTest {
    @BeforeClass
    public static void setSortAlgorithm(){
        sorter = new SortAlgorithm() {
            @Override
            public <T> void sort(T[] arr, Comparator<T> c) {
                BubbleSort.sort(arr,c);
            }
        };
    }
}
