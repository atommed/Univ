/**
 * Created by gregory on 15.06.16.
 */
public class App {

    public static void main(String... args) throws InterruptedException {
        /*
        TestDataProvider dp = new TestDataProvider();
        String[] sa,sb,oo;
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

        ta = System.nanoTime();
        Sorts.insertionSort(sb);
        System.out.println(System.nanoTime() - ta);
        assert Arrays.equals(oo,sb);
        */

        /*
        TestDataProvider dm = new TestDataProvider();
        int[] arr = dm.genIntsReverseSorted(40);
        System.out.println(Arrays.toString(arr));
        */
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

        //System.out.println(s.get(42));
    }
}
