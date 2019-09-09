package test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import objectStates.*;


public class LocalFileTest {

  @Test
  public void testLocalFileConstructor() {
    LocalFile file = new LocalFile("C:/Users/heman/Desktop", "file.txt");
    
    assertEquals("file.txt", file.getFile().getName());
  }
  
  @Test
  public void testLocalFileConstructorWithNoFilePath() {
    LocalFile file = new LocalFile("file.txt");
    
    assertEquals("file.txt", file.getFile().getName());
  }
  
  @Test
  public void testCheckCreateInvalidFilePath() {
    LocalFile file = new LocalFile("wedew", "file.txt");
    
    assertEquals(1, file.checkCreate());
  }

  @Test
  public void testCheckCreateValidFilePath() {
    LocalFile file = new LocalFile("file.txt");
    assertEquals(0, file.checkCreate());
  }
}
