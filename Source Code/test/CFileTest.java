package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fileSystem.CFile;

public class CFileTest {
  CFile obj;
  CFile obj1;
  
  @Before
  public void setUp() {
    obj = new CFile("obj", null, "objData");
  }

  @Test
  public void testCFileNullString() {
    obj1 = new CFile("obj1", null, null);
    assertEquals(obj1.getData(), null);
  }
  
  @Test
  public void testCFileValidString() {
    assertEquals(obj.getData(), "objData");
  }
}
