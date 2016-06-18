import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Homework {

    private final int mean_measures_n = 4;
    private final int measures_n = 10;

    TestDataProvider dp = new TestDataProvider();
    long ta;

    private <T> T[] copyArr(T[] arr){
        return Arrays.copyOf(arr,arr.length);
    }
    private int[] copyArr(int[] arr){
        return Arrays.copyOf(arr,arr.length);
    }

    private void beginMeasure(){
        ta = System.nanoTime();
    }

    private long endMeasure(){
        return System.nanoTime() - ta;
    }

    static class HashCollision {
        @Override
        public int hashCode(){
            return 1;
        }

    }

    public void task3() throws IOException {
        HashTableQuad<Integer> q = new HashTableQuad<>();
        HashTableList<Integer,Integer> l = new HashTableList<>();
        HashTableList<HashCollision, Integer> lc = new HashTableList<>();
        CsvWriter w = new CsvWriter("./t3.csv");

        int maxSize = 100_000;
        int sizeStep = maxSize / measures_n;
        for(int i  = 0; i < maxSize; i+=sizeStep){
            System.out.println("Adding elements "+i);
            for(int j = i; j < i + sizeStep; j++) {
                q.put(i, i);
                l.put(i,i);
                lc.put(new HashCollision(),42);
            }
            int pos = i;
            beginMeasure();
            assert q.get(pos).equals(Integer.valueOf(pos));
            long aq = endMeasure();

            beginMeasure();
            assert l.get(pos).equals(Integer.valueOf(pos));
            long bq = endMeasure();

            beginMeasure();
            assert lc.get(new HashCollision()) == null;
            long cq = endMeasure();

            w.write(i,aq,bq,cq);
        }

        w.save();
    }

    private long benchRadixSort(String[] arr){
        long tS = 0;
        for(int i = 0; i < mean_measures_n; i++){
            String[] tmp_a = copyArr(arr);
            beginMeasure();
            Sorts.lsd_radix_sort(tmp_a);
            tS += endMeasure();
            System.out.println(tmp_a[15]);
        }

        return tS/ mean_measures_n;
    }

    private long benchInsertionSort(String[] arr){
        long tS = 0;
        for(int i = 0; i < mean_measures_n; i++){
            String[] tmp_a = copyArr(arr);
            beginMeasure();
            Sorts.insertionSort(tmp_a);
            tS += endMeasure();
            System.out.println(tmp_a[15]);
        }

        return tS/ mean_measures_n;
    }

    public void task2() throws IOException {
        CsvWriter w = new CsvWriter("./t2.csv");

        String[] arr;

        for(int i = 0; i < 5; i++){
            arr = dp.genStringRandom(100_000,50);
            Sorts.lsd_radix_sort(arr);
            arr = dp.genStringRandom(500,50);
            Sorts.insertionSort(arr);
        }

        int minSize = 0_001_000;
        int maxSize = 0_040_000;
        for(int size = minSize; size < maxSize; size+=(maxSize - minSize)/measures_n){
            System.out.println("String sorting array of size "+size);

            arr = dp.genStringRandom(size, 30);
            long rrndTime = benchRadixSort(arr);
            long irndTime = benchInsertionSort(arr);

            arr = dp.genStringRandom(size, 30);
            Collections.sort(Arrays.asList(arr));
            long rsrtTime = benchRadixSort(arr);
            long isrtTime = benchInsertionSort(arr);

            arr = dp.genStringRandom(size, 30);
            Collections.sort(Arrays.asList(arr),Collections.reverseOrder());
            long rrevTime = benchRadixSort(arr);
            long irevTime = benchInsertionSort(arr);

            w.write(size,rrndTime,rsrtTime,rrevTime,irndTime,isrtTime,irevTime);
        }
        w.save();
    }

    private long benchShellSort(int[] arr){
        long tS = 0;
        for(int i = 0; i < mean_measures_n; i++){
            int[] ta = copyArr(arr);
            beginMeasure();
            Sorts.knuthShellSort(ta);
            tS += endMeasure();
            System.out.println(arr[15]);
        }

        return tS/ mean_measures_n;
    }

    public void task1() throws IOException {
        CsvWriter w = new CsvWriter("./t1.csv");

        int[] arr;

        for(int i = 0; i < 20; i++){
            arr = dp.genIntsRandom(500_000);
            Sorts.knuthShellSort(arr);
        }

        int minSize = 1000;
        int maxSize = 1_000_000;
        for(int size = minSize; size < maxSize; size+=(maxSize - minSize)/measures_n){
            System.out.println("Knuth sorting array of size "+size);

            arr = dp.genIntsRandom(size);
            long rndTime = benchShellSort(arr);

            arr = dp.genIntsReverseSorted(size);
            long revTime = benchShellSort(arr);

            arr = dp.genIntsSorted(size);
            long srtTime = benchShellSort(arr);

            w.write(size,rndTime,srtTime,revTime);
        }
        w.save();
    }

    public static void main(String... args) throws IOException {
        Homework hw = new Homework();
        //hw.task1();
        //hw.task2();
        hw.task3();
    }
}
