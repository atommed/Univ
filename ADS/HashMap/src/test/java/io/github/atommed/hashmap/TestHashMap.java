package io.github.atommed.hashmap;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by gregory on 25.05.16.
 */

public class TestHashMap {
    private HashMap<String, String> m;
    private HashMap<String, String> getEmptyMap(){
        return new HashMap<>();
    }
    private HashMap<String, String> getOneElementMap(){
        HashMap<String, String> ret =  new HashMap<>();
        ret.put("Hello,", "HashMap");
        return ret;
    }
    private HashMap<String, String> get10ElementMap(){
        HashMap<String, String> ret = new HashMap<>();
        for(int i = 0; i < 10; i++)
            ret.put(Integer.toString(i),Integer.toString(i*i));
        return ret;
    }

    @Test
    public void emptySizeRetZero(){
        m = getEmptyMap();
        assertEquals(m.size(), 0);
    }
    @Test
    public void OneElementSizeRetOne(){
        m = getOneElementMap();
        assertEquals(m.size(), 1);
    }

    @Test
    public void TenElementSizeRetTen(){
        m = get10ElementMap();
        assertEquals(m.size(), 10);
    }


    @Test
    public void putChangesSize(){
        m = getEmptyMap();
        m.put("Test", "1");
        assertEquals(m.size(), 1);
    }

    @Test
    public void putValuesSaved(){
        m = getEmptyMap();
        int N = 1000;
        for(int i = 0; i < N; i++)
            m.put(Integer.toString(i), Integer.toString(i*i)+"VAL");
        for(int i = N - 1; i >= 0; i--)
            assertEquals(m.get(Integer.toString(i)), Integer.toString(i*i)+"VAL");
    }

    static private class CollisionFull{
        public int val;

        public CollisionFull(int val){
            this.val = val;
        }

        @Override
        public int hashCode(){
            return 42;
        }
        @Override
        public boolean equals(Object o){
            if(o == null) return false;
            return this.val == ((CollisionFull) o).val;
        }
    }

    @Test
    public void collisionsHandled(){
        HashMap<CollisionFull, String> dict = new HashMap<>();
        int N = 500;
        for(int i = 0; i < N; i++)
            dict.put(new CollisionFull(i), Integer.toString(i*i)+"VAL");
        for(int i = N - 1; i >= 0; i--)
            assertEquals(dict.get(new CollisionFull(i)), Integer.toString(i*i)+"VAL");
    }

    @Test
    public void removeWorks(){
        m = getEmptyMap();
        m.put("A", "B");
        assertEquals(m.remove("A"), "B");
        assertEquals(m.get("A"), null);
        assertEquals(m.size(), 0);
    }
}
