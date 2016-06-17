public class HashTableQuad<E> {
    class Entry<T>{
        int key;
        T value;
        boolean deleted = false;

        public Entry(int key, T value) {
            this.key = key;
            this.value = value;
        }
    }

    private Entry<E>[] table;

    /**
     * the number of non-null entries in the hashtable
     */
    private int size;

    /**
     * default size of the hashtable
     */
    private static final int initialCapacity = 11;

    /**
     * The load factor is a measure of how full the hash table is allowed to get
     * / before its capacity is automatically increased
     */
    private double criticalLoadFactor;
    private static final double defaultLoadFactor = 0.75;

    /**
     * A is used in multiplication hash function
     */
    private final double A = Math.sqrt(5) - 2;
    /**
     * The hash function is used to map the search key to an index of this
     * hashtable. Choose hashing method from your variant to laboratory work
     *
     * @param key
     *            key - some integer value
     * @return hash value - index in table
     */
    private int hash(int key) {
        return (int)Math.floor(table.length * (A * key - Math.floor(A*key)));
    }

    /**
     * Constructs a new, empty hashtable with the specified initial capacity and
     * the specified load factor.
     *
     * @param initialCapacity
     *            the initial capacity of the hashtable
     * @param loadFactor
     *            the load factor of the hashtable
     */
    public HashTableQuad(int initialCapacity, double loadFactor) {
        table = new Entry[initialCapacity];
        for(int i = 0; i < initialCapacity; i++)
            table[i] = new Entry<>(0, null);
        this.criticalLoadFactor = loadFactor;
    }


    /**
     * Constructs a new, empty hashtable with the specified initial capacity and
     * default load factor (0.75).
     *
     * @param initialCapacity
     *            the initial capacity of the hashtable
     */
    public HashTableQuad(int initialCapacity) {
        this(initialCapacity,defaultLoadFactor);
    }

    /**
     * Constructs a new, empty hashtable with a default initial capacity (11)
     * and load factor (0.75).
     */
    public HashTableQuad() {
        this(initialCapacity,defaultLoadFactor);
    }

    /**
     * Tests if the hashtable contains no entries.
     *
     * @return true if the hashtable contains no entries; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    private double getLoadFactor() {
        return ((double) size) / table.length;
    }

    /**
     * Returns the number of entries in the hashtable
     *
     * @return the number of entries in the hashtable
     */
    public int size() {
        return size;
    }


    private int hp(int hash, int probe){
        return (hash + probe * probe) % table.length;
    }

    private void putToNewTable(int key, E v){
        int h = hash(key);
        for(int probe =  0; probe < table.length; probe++){
            int probePos = hp(h, probe);
            if(table[probePos].value == null){
                table[probePos] = new Entry<>(key,v);
                break;
            }
        }
    }

    /**
     * Increases the capacity of and internally reorganizes this hashtable, in
     * order to accommodate and access its entries more efficiently. This method
     * is called automatically when the number of keys in the hashtable exceeds
     * this hashtable's capacity and load factor
     */
    private void rehash() {
        Entry<E>[] old_table =  table;
        table = new Entry[table.length * 2];
        for(int i = 0; i < table.length; i++)
            table[i] = new Entry<>(0,null);

        for(int i = 0; i < old_table.length; i++){
            if(old_table[i].value != null && !old_table[i].deleted)
                putToNewTable(old_table[i].key, old_table[i].value);
        }
    }

    /**
     * If hashtable contains an entry e = (k, v), with key equals to k, then
     * return the value v, for e; else return null
     *
     * @param k
     *            the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if this
     *         hashtable contains no mapping for the key
     */
    public E get(int k) {
        int h = hash(k);
        for(int probe = 0; probe < table.length; probe++){
            int probePos = hp(h, probe);
            if(table[probePos].key == k && !table[probePos].deleted)
                return table[probePos].value;
            else if(table[probePos].value == null && !table[probePos].deleted)
                return null;
        }
        return null;
    }

    /**
     * Tests if some key maps to the specified value in this hashtable
     *
     * @param key
     *            possible key (non-null value)
     * @return true if and only if some key maps to the value argument in this
     *         hashtable; false otherwise
     */
    public boolean contains(int key){
        return get(key) != null;
    }

    /**
     * Add new entry e - (key, value) to this hashtable which resolves
     * collisions (according to the variant to the task 2)
     *
     * if table does not have an entry with key, than add entry e = (key, value)
     * to table and return null;
     *
     * else, replace with value the existing value of the entry with key and
     * return old value
     *
     * @param key
     *            the hashtable key (choose what is the key in your variant to
     *            laboratory work)
     * @param value
     *            the value (geometric figure according your variant)
     * @return null if new entry was added; else old value that was replaced
     */
    public E put(int key, E value) {
        size++;
        while (getLoadFactor() >= criticalLoadFactor)
            rehash();

        int h = hash(key);
        for(int probe = 0; probe < table.length; probe++){
            int probePos = hp(h, probe);
            if(table[probePos].value == null || table[probePos].deleted){
                table[probePos] = new Entry<>(key,value);
                return null;
            } else if(table[probePos].key == key){
                E old = table[probePos].value;
                table[probePos] = new Entry<>(key,value);
                return old;
            }
        }

        return null;

    }

    /**
     * Removes the key (and its corresponding value) from this hashtable. This
     * method does nothing if the key is not in the hashtable
     *
     *
     * @param key
     *            the key that needs to be removed
     * @return the value to which the key had been mapped in this hashtable, or
     *         null if the key did not have a mapping
     */
    public E remove(int key) {
        int h = hash(key);
        for(int probe = 0; probe < table.length; probe++){
            int pos  = hp(h,probe);
            if(table[pos].key == key && !table[pos].deleted){
                E tmp = table[pos].value;
                table[pos].value = null;
                table[pos].deleted = true;
                size--;
                return tmp;
            } else if(table[pos].value == null && !table[pos].deleted)
                return null;
        }

        return null;

    }

    public void print(){
        for(int i = 0; i  < table.length; i++){
            if(table[i].value != null){
                System.out.println("On pos "+i+": key \""+table[i].key+"\" value \""+table[i].value+"\"");
            }
        }
    }
}
