package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fileSystem.*;

public class CObjectTest {
  public CDir objPar;
  public CDir objPar1;
  public CObject obj;

  @Before
  public void setUp() {
    objPar = new CDir("testParent", null);
    objPar1 = new CDir("testParent1", null);
    obj = new CObject("test", objPar);
  }

  @Test
  public void testCObjectInvalidParent() {
    CObject obj1 = new CObject("hello", obj);
    assertEquals(null, obj1.getParent());
  }

  @Test
  public void testSetName() {
    obj.setName("newName");
    assertEquals("newName", obj.getName());
    assertEquals(obj, objPar.getChildren().get("newName"));
  }

  @Test
  public void testSetParent() {
    obj.setParent(objPar1);
    assertEquals(objPar1, obj.getParent());
    assertEquals(true, objPar1.getChildren().contains(obj));
  }

  @Test
  public void testSetParentNull() {
    obj.setParent(null);
    assertEquals(null, obj.getParent());
  }

  @Test
  public void testGetPath() {
    obj.setParent(objPar);
    objPar.setParent(objPar1);
    assertEquals(objPar1.getName() + "/" + objPar.getName() + "/" + obj.getName(), obj.getPath());
  }

  @Test
  public void testGetPathNullParent() {
    obj.setParent(null);
    assertEquals(obj.getName(), obj.getPath());
  }

  @Test
  public void testGetObjectAtRelativePath() {
    obj.setParent(objPar);
    objPar.setParent(objPar1);
    CObject testObj =
        CObject.getObjectAtRelativePath(objPar.getName() + "/" + obj.getName(), objPar1);
    assertEquals(obj, testObj);
  }

}
