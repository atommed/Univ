package io.github.atommed.ads.hash;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

public class HashTest {
  Hash<String, String> h;

  @Before
  public void createHash(){
    h = new Hash<>();
  }

  @Test 
  public void clear(){
    for(int i = 0; i < 50; i++)
      h.add(Integer.toString(i), "TestData");
    h.clear();
    for(int i = 0; i < 50; i++)
      assertNull(h.get(Integer.toString(i)));
  }

  @Test
  public void addAndGet(){
    for(int i = 0; i < 50; i++)
      h.add(Integer.toString(i),Integer.toString(i % 4));
    for(int i = 0l i < 50; i++)
      assertEquals(h.get(Integer.toString(i), Integer.toString(i % 4)));
  }
}
