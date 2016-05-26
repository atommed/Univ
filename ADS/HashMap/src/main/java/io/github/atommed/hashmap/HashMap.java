package io.github.atommed.hashmap;

/**
 * Created by gregory on 25.05.16.
 */

//TODO: Add deleted flag
public class HashMap<K,V> {
    private static final float defaultCriticalLoadFactor = 0.75f;
    private static final int defaultInitialCapacity = (1<<4);

    private float criticalLoadFactor;

    private static class Bucket<K,V>{
        boolean used;
        K key;
        V value;
    }
    int capacity;
    Bucket<K,V>[] buckets;
    int size;

    private int hash(K k){
        return k == null ? 0 : k.hashCode() & (capacity - 1);
    }

    public HashMap(float criticalLoadFactor, int capacity) {
        int realCapacity = 1;
        while (realCapacity < capacity) realCapacity = realCapacity<<1;
        capacity = realCapacity;

        this.size = 0;
        this.criticalLoadFactor = criticalLoadFactor;
        this.capacity = capacity;
        buckets = new Bucket[capacity];
        for(int i = 0; i < capacity; i++)
            buckets[i] = new Bucket<>();
    }

    public HashMap(float criticalLoadFactor) {
        this(criticalLoadFactor, defaultInitialCapacity);
    }

    public HashMap(int capacity) {
        this(defaultCriticalLoadFactor,capacity);
    }

    public HashMap() {
        this(defaultCriticalLoadFactor, defaultInitialCapacity);
    }

    public void clear(){
        for(int i = 0; i  < capacity; i++)
            buckets[i].used = false;//TODO: Set other fields to null?
        this.size = 0;
    }

    public V remove(K key){
        size--;//Hope it will find element to remove
        int startPos = hash(key);
        for(int i = startPos; i < capacity; i++)
            if(buckets[i].used && keyEquals(key, buckets[i].key)){
                buckets[i].used = false; //TODO: Set other fields to null?
                return buckets[i].value;
            }
        for(int i = 0; i < startPos; i++)
            if(buckets[i].used && keyEquals(key, buckets[i].key)){
                buckets[i].used = false; //TODO: Set other fields to null?
                return buckets[i].value;
            }
        size++;//If value not returned before, element wasn't removed
        return null;
    }

    public int size(){
        return size;
    }

    private boolean keyEquals(K k1, K k2){
        return k1 == null ? k2 == null : k1.equals(k2);
    }

    public V get(K key){
        int startPos = hash(key);
        for(int i = startPos; i < capacity; i++)
            if(  buckets[i].used && keyEquals(key,buckets[i].key))
                return buckets[i].value;
        for(int i = 0; i < startPos; i++)
            if(  buckets[i].used && keyEquals(key,buckets[i].key))
                return buckets[i].value;

        return null;
    }

    private V noGrowPut(K key, V val){
        V ret = null;
        size++;
        int startPos = hash(key);
        for(int i = startPos; i < capacity; i++){
            if(!buckets[i].used){
                ret = buckets[i].value;
                buckets[i].key = key;
                buckets[i].value =val;
                buckets[i].used = true;
                return ret;
            } else if(keyEquals(key, buckets[i].key)){
                ret = buckets[i].value;
                buckets[i].key = key;
                buckets[i].value = val;
                return ret;
            }
        }
        for(int i = 0; i < startPos; i++){
            if(!buckets[i].used){
                ret = buckets[i].value;
                buckets[i].key = key;
                buckets[i].value =val;
                buckets[i].used = true;
                return ret;
            } else if(keyEquals(key, buckets[i].key)){
                ret = buckets[i].value;
                buckets[i].key = key;
                buckets[i].value = val;
                return ret;
            }
        }
        size--;
        return ret;
    }

    private void grow(){
        size = 0;
        Bucket<K,V>[] old_buckets = this.buckets;
        this.capacity = capacity * 2;
        this.buckets = new Bucket[this.capacity];
        for(int i = 0; i < this.capacity; i++)
            this.buckets[i] = new Bucket<>();
        for(int i = 0; i < old_buckets.length; i++)
            if(old_buckets[i].used)
                noGrowPut(old_buckets[i].key,old_buckets[i].value);
    }

    public V put(K key, V value){
        while(((size+1)/capacity) > criticalLoadFactor) grow();
        return noGrowPut(key,value);
    }
}
