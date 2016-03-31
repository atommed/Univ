package io.github.atommed.ads.hash;

public class Hash<K, V> {
  private static int initialDataLength = 30;
  static class Pair<K,V> {
    K key;
    V value;
    boolean used = false;
  }

  private Pair<K, V>[] data;
  int capacity;
  int length;

  private Hash(int dataLength){
    length = 0;
    capacity = 1;
    while(capacity < dataLength)
      capacity = capacity << 1;

    data = new Pair[capacity];
  }

  private static int getIndex(Key k){
      int hash = k.hashCode() & (data.length - 1);
      for(int i = hash; i < data.length; i++){
	if(!data[i].used || data[i].key.equals(k))
	  return i;
      }
      for(int i = 0; i < hash; i++)
	if(!data[i].used || data[i].key.equals(k))
	  return i;

      throw new IllegalStateException("Data holder is too small");
  }

  public Hash(){
    this(initialDataLength);
  }

  public boolean add(K key, V value){
    int pos = getIndex(key);

    data[pos].key = key;
    data[pos].value = value;
    data[pos].used = true;
  }
  public V get(K key){
    int pos
  }
  public boolean remove(K key){
   return false;
  }
  public void clear(){
  }
}
