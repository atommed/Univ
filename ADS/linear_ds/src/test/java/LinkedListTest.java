import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.gregory.k.ads.linear_ds.LinkedList;

import java.text.ParseException;
import java.util.Iterator;

public class LinkedListTest {
    LinkedList lst;

    @Before
    public void prepare() {
        lst = new LinkedList();
    }

    @Test
    public void addingElementsFirst() {
        lst.addFirst("1");
        lst.addFirst("2");
        lst.addFirst("3");
        lst.addFirst("4");
        lst.addFirst("5");
        assertTrue(lst.get(0).equals("5"));
        assertTrue(lst.get(1).equals("4"));
        assertTrue(lst.get(2).equals("3"));
        assertTrue(lst.get(3).equals("2"));
        assertTrue(lst.get(4).equals("1"));
    }

    @Test
    public void removingElements() {
        lst.addLast("1");
        lst.addLast("2");
        lst.addLast("3");
        lst.addLast("4");
        lst.addLast("5");
        assertTrue(lst.removeAt(0).equals("1"));
        assertTrue(lst.get(0).equals("2"));
        assertTrue(lst.removeAt(2).equals("4"));
        assertTrue(lst.get(2).equals("5"));
        assertTrue(lst.removeLast().equals("5"));
        assertTrue(lst.removeFirst().equals("2"));
        assertTrue(lst.removeLast().equals("3"));
    }
    @Test
    public void size(){
        assertEquals(lst.size(),0);
        lst.addAt("1",0);
        lst.addAt("2",1);
        lst.addAt("3",2);
        assertEquals(lst.size(),3);
    }
    @Test
    public void update(){
        lst.addLast("1");
        lst.addLast("2");
        assertTrue(lst.update("3",1).equals("2"));
        assertTrue(lst.get(1).equals("3"));
    }
    @Test
    public void iterator(){
        for(int i = 0; i < 50; i++){
            lst.addLast(Integer.toString(i));
        }
        Iterator it = lst.iterator();
        for(int i =0; i< 50; i++){
            assertTrue(it.hasNext());
            String ret = (String) it.next();
            assertTrue(ret.equals(Integer.toString(i)));
        }
    }
    @Test
    public void iteratorRemoveTest(){
        for(int i = 0; i < 50; i++){
            lst.addLast(Integer.toString(i));
        }
        Iterator it = lst.iterator();
        for(int i = 0; i < 50; i++) {
            assertTrue(it.next().equals(Integer.toString(i)));
            if (i % 2 == 0)
                it.remove();
        }
        it  = lst.iterator();
        for(int i = 1;i<50;i+=2) {
            assertTrue(it.next().equals(Integer.toString(i)));
            it.remove();
        }
        assertEquals(lst.size(),0);
    }
    @Test(expected = java.util.NoSuchElementException.class)
    public void emptyListRemoveFirst(){
        lst.removeFirst();
    }
    @Test(expected = java.util.NoSuchElementException.class)
    public void emptyListRemoveLast(){
        lst.removeLast();
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void wrongIndexAdd(){
        lst.addFirst("1");
        lst.addAt("2",2);
    }
    @Test(expected = NumberFormatException.class)
    public void floatAdd(){
        lst.addAt("1.5",0);
    }
    @Test(expected = NumberFormatException.class)
    public void notNumAdd(){
        lst.addAt("02i",0);
    }
}
