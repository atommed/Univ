import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by gregory on 15.06.16.
 */
public class App {
    static final Integer[] orig_arr = {1,5,4,78,23,64,5,65,5,5,22,67,2,33,44,55,66,34,3,23,15,87,3,44,20};
    static Integer[] sorted_arr;
    static {
        sorted_arr = Arrays.copyOf(orig_arr,orig_arr.length);
        Arrays.sort(sorted_arr);
    }

    public static Integer[] toSort(){
        return Arrays.copyOf(orig_arr,orig_arr.length);
    }

    public static void assertSorted(Integer[] arr){
        assert Arrays.equals(arr, sorted_arr);
    }

    public static void main(String... args){
        Integer[] tmp_arr;

        tmp_arr = toSort();
        Sorts.knuthShellSort(tmp_arr);
        assertSorted(tmp_arr);

        tmp_arr = toSort();
        Sorts.insertionSort(tmp_arr);
        assertSorted(tmp_arr);

        String[] orig_strs = {
                "Vasya",
                "Vanya",
                "Vova",
                "Ars",
                "Zubochek"
        };
        String[] sortedStrings = Arrays.copyOf(orig_strs,orig_strs.length);
        Arrays.sort(sortedStrings);
        String[] mySortStrings = Arrays.copyOf(orig_strs,orig_strs.length);
        Sorts.lsd_radix_sort(mySortStrings);
        assert Arrays.equals(mySortStrings, sortedStrings);
    }
}
