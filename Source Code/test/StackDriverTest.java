package test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import fileSystem.*;
import stackSystem.*;

public class StackDriverTest {
  FileSystemDriver drive;
  @Before
  public void setUp(){
    drive = new FileSystemDriver();
  }

  @Test
  public void testStackDriverConstructor() {
    StackDriver st = new StackDriver();
    assertTrue(st.getDirStack().getHead().isEmpty());
  }

  @Test
  public void testPushTwoDirectoriesToStack() {
    StackDriver st = new StackDriver();
    st.push(drive);
    st.push(drive);
    
    assertEquals(drive.getCurrentDir(), st.getDirStack().getHead().getLast());
  }

  @Test
  public void testPopDirectoryFromStack() {
    StackDriver st = new StackDriver();
    CDir obj = new CDir("a", drive.getCurrentDir());
    drive.setCurrentDir(obj);
    st.push(drive);
    st.pop(drive);
    assertEquals(obj, drive.getCurrentDir());
    assertTrue(st.getDirStack().getHead().isEmpty());
  }

}
