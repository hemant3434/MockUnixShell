package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fileSystem.*;

public class CDirTest {
  CDir obj;
  CDir obj1;
  CDir obj2;
  CDir obj3;

  @Before
  public void setUp() {
    obj = new CDir("obj", null);
    obj1 = new CDir("obj1", obj);
    obj2 = new CDir("obj2", obj);
    obj3 = new CDir("obj3", null);
  }

  // CDir constructor tested in CObjectTest

  @Test
  public void testGetChildren() {
    assertEquals(0, obj1.getChildren().size());
    assertEquals(2, obj.getChildren().size());
    assertEquals(obj1, obj.getChildren().get("obj1"));
  }

  @Test
  public void testGetChild() {
    assertEquals(obj2, obj.getChild("obj2"));
  }

  @Test
  public void testClearChildren() {
    obj.clearChildren();
    assertEquals(0, obj.getChildren().size());
  }

  @Test
  public void testAddChild() {
    obj1.addChild(obj3);
    assertEquals(obj3, obj1.getChild("obj3"));
  }

  @Test
  public void testRemoveChild() {
    obj.removeChild("obj1");
    assertEquals(null, obj.getChild("obj1"));
  }

}
