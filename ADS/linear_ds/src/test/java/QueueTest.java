import org.junit.Test;
import static org.junit.Assert.*;
import com.gregory.k.ads.linear_ds.Queue;

public class QueueTest {
  @Test
  public void testAddRemoveMany(){
    Queue<String> q = new Queue<>(1);
    for(int i = 0;i<500;i++)
      q.add(Integer.toString(i));
    for(int i = 0;i<500;i++)
      assertTrue(q.remove().equals(Integer.toString(i)));
    assertTrue(q.isEmpty());
  }
  @Test
  public void testAddRemove(){
    Queue<String> q = new Queue<>(3);
    q.add("1");
    q.add("2");
    q.add("3");
    assertTrue(q.remove().equals("1"));
    q.add("4");
    assertTrue(q.remove().equals("2"));
    assertTrue(q.remove().equals("3"));
    assertTrue(q.remove().equals("4"));
    q.add("5");
    assertTrue(q.remove().equals("5"));
  }
  @Test
  public void testAddRemoveOne(){
   Queue<String> q = new Queue<>(2); 
   boolean flag = true;
   for(int i = 0; i< 100;i++){
    q.add("1");
    q.add("2");
    if(flag) assertTrue(q.remove().equals("1"));
    else assertTrue(q.remove().equals("2"));
    flag = !flag;
   }
  }

  @Test(expected=java.util.NoSuchElementException.class)
  public void testElementFromEmpty(){
    Queue<String> q = new Queue<>();
    q.element();
  }
  @Test(expected=java.util.NoSuchElementException.class)
  public void testRemoveFromEmpty(){
    Queue<String> q = new Queue<>();
    q.remove();
  }
  @Test
  public void testPollFromEmpty(){
    Queue<String> q = new Queue<>();
    assertEquals(q.poll(),null);
  }
  @Test
  public void testPeekFromEmpty(){
    Queue<String> q = new Queue<>();
    assertEquals(q.peek(),null);
  }
}
