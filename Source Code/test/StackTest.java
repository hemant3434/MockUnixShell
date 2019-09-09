package test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import fileSystem.*;
import stackSystem.*;

public class StackTest {
  FileSystemDriver drive;
  
  @Before
  public void setUp(){
    drive = new FileSystemDriver();
  }

  @Test
  public void testStack() {
    Stack st = new Stack();
    assertTrue(st.getHead().isEmpty());
  }

  @Test
  public void testGetHead() {
    CDir obj = new CDir("a", drive.getCurrentDir());
    
    Stack st = new Stack();
    assertFalse(st.getHead().contains(obj));
  }

  @Test
  public void testAddDirToTail() {
    CDir obj = new CDir("a", drive.getCurrentDir());
    CDir obj2 = new CDir("b", drive.getCurrentDir());
    
    Stack st = new Stack();
    st.addDirToTail(obj);
    st.addDirToTail(obj2);
    assertEquals(obj2, st.getHead().getLast());
    
  }

  @Test
  public void testRemoveDirFromTail() {
    CDir obj = new CDir("a", drive.getCurrentDir());
    CDir obj2 = new CDir("b", drive.getCurrentDir());
    
    Stack st = new Stack();
    st.addDirToTail(obj);
    st.addDirToTail(obj2);
    assertEquals(obj2, st.removeDirTail());
  }

}
