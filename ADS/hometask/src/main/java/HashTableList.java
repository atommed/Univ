import java.util.LinkedList;
import java.util.ListIterator;

public class HashTableList<K,V> {
    private static final float defaultCriticalLoadFactor = 0.75f;
    private static final int defaultInitialCapacity = (1<<4);

    private float criticalLoadFactor;

    private static class Bucket<K,V>{
        K key;
        V value;

        public Bucket(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    int capacity;
    //Bucket<K,V>[] buckets;
    int size;
    LinkedList<Bucket<K,V>>[] table;

    private int hash(K k){
        return k.hashCode() & (capacity - 1);
    }

    public HashTableList(float criticalLoadFactor, int capacity) {
        int realCapacity = 1;
        while (realCapacity < capacity) realCapacity = realCapacity<<1;
        capacity = realCapacity;

        this.size = 0;
        this.criticalLoadFactor = criticalLoadFactor;
        this.capacity = capacity;

        table = new LinkedList[capacity];
        for(int i = 0; i < capacity; i++)
            table[i] = new LinkedList<>();
    }

    public HashTableList(float criticalLoadFactor) {
        this(criticalLoadFactor, defaultInitialCapacity);
    }

    public HashTableList(int capacity) {
        this(defaultCriticalLoadFactor,capacity);
    }

    public HashTableList() {
        this(defaultCriticalLoadFactor, defaultInitialCapacity);
    }

    public void clear(){
        for(int i = 0; i  < capacity; i++)
            table[i].clear();
        this.size = 0;
    }

    public V remove(K key){
        int h = hash(key);
        ListIterator<Bucket<K, V>> iter = table[h].listIterator();
        while (iter.hasNext()){
            Bucket<K, V> e = iter.next();
            if(e.key.equals(key)) {
                size--;
                V ret = e.value;
                iter.remove();
                return ret;
            }
        }
        return null;
    }

    public int size(){
        return size;
    }

    private boolean keyEquals(K k1, K k2){
        return k1 == null ? k2 == null : k1.equals(k2);
    }

    public V get(K key){
        int h = hash(key);
        ListIterator<Bucket<K, V>> iter = table[h].listIterator();
        while (iter.hasNext()){
            Bucket<K, V> e = iter.next();
            if(e.key.equals(key))
                return e.value;
        }
        return null;
    }

    private V noGrowPut(K key, V val){
        int h = hash(key);
        ListIterator<Bucket<K, V>> iter = table[h].listIterator();
        while (iter.hasNext()){
            Bucket<K, V> e = iter.next();
            if(e.key.equals(key)){
                V ret = e.value;
                e.value = val;
                return ret;
            }
        }
        table[h].add(new Bucket<K, V>(key,val));
        return null;
    }

    private void grow(){
        LinkedList<Bucket<K, V>>[] old_table = this.table;
        this.capacity = capacity * 2;
        table = new LinkedList[capacity];
        for(int i = 0; i < capacity; i++)
            table[i] = new LinkedList<>();
        for (LinkedList<Bucket<K,V>> list :
                old_table) {
            for (Bucket<K, V> b :
                    list) {
                noGrowPut(b.key,b.value);
            }
        }
    }

    public V put(K key, V value){
        while(((size+1)/capacity) > criticalLoadFactor) grow();
        return noGrowPut(key,value);
    }
}