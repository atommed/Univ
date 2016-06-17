import java.lang.reflect.Array;
import java.util.Arrays;

public class App {

    public static void main(String... args) throws InterruptedException {

        TestDataProvider dp = new TestDataProvider();

        /*
        int[] o_arr = dp.genIntsRandom(5000000);
        long ta;
        int[] std = null;
        int[] my = null;
        for (int i = 0; i < 5; i++) {
            std = Arrays.copyOf(o_arr,o_arr.length);
            Arrays.sort(std);
            my = Arrays.copyOf(o_arr,o_arr.length);
            Sorts.lsd_radix_sort(my);
        }

        std = Arrays.copyOf(o_arr,o_arr.length);
        ta = System.nanoTime();
        Arrays.sort(std);
        System.out.println(System.nanoTime() -  ta);

        my = Arrays.copyOf(o_arr,o_arr.length);
        ta = System.nanoTime();
        Sorts.lsd_radix_sort(my);
        System.out.println(System.nanoTime() - ta);

        assert Arrays.equals(std,my);
        */


        /*
        String[] sa,oo;
        long ta;

        String[] orig_strs = dp.genStringRandom(10_000_000, 2);

        for(int i = 0; i <  20; i++){
            sa = Arrays.copyOf(orig_strs, orig_strs.length);
            Sorts.lsd_radix_sort(sa);
            oo = Arrays.copyOf(orig_strs,orig_strs.length);
            Arrays.sort(oo);
        }

        sa = Arrays.copyOf(orig_strs,orig_strs.length);
        oo = Arrays.copyOf(orig_strs,orig_strs.length);

        ta = System.nanoTime();
        Arrays.sort(oo);
        System.out.println(System.nanoTime() - ta);

        ta = System.nanoTime();
        Sorts.lsd_radix_sort(sa);
        System.out.println(System.nanoTime() - ta);
        assert Arrays.equals(oo,sa);
        */



        /*
        TestDataProvider dm = new TestDataProvider();
        int[] arr = dm.genIntsReverseSorted(40);
        System.out.println(Arrays.toString(arr));
        */
        /*
        HashTableLinear<String> s = new HashTableLinear<>();
        //HashTableQuad<String> s = new HashTableQuad<>();
        for(int i  = 0; i  <100; i++){
            s.put(i,Integer.toString(i));
        }
        for(int i = 0; i < 100; i++){
            if(i % 3 == 0)
                assert s.remove(i).equals(Integer.toString(i));
        }
        for(int i =0;i < 100; i++){
            if(i % 3 == 0)
                assert s.get(i) == null;
            else
                assert s.get(i).equals(Integer.toString(i));
        }

        assert s.get(0) == null;
        assert s.put(0,"Soska") == null;
        assert s.get(0).equals("Soska");
        assert s.remove(0).equals("Soska");
        assert s.get(0) == null;
        */
        //System.out.println(s.get(42));
    }
}
