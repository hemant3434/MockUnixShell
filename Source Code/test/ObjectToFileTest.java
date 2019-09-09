package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;
import org.junit.Test;
import fileSystem.*;
import stackSystem.*;
import objectStates.*;
import commands.*;

public class ObjectToFileTest {
  UltimateDriver ult;
  FileSystemDriver drive;
  RecentCommands history;
  StackDriver stack;
  
  @Before
  public void setUp(){
    drive = new FileSystemDriver();
    stack = new StackDriver();
    history = new RecentCommands();
    ult = new UltimateDriver(drive, null, stack);
  }

  @Test
  public void testSaveObjectIncorrectFilePath() {
    LocalFile file = new LocalFile("dwedwed","file.txt");
    assertEquals(1, ObjectToFile.saveObject(ult, file));
    
  }
  
  @Test
  public void testSaveObjectCorrectFilePath() {
    LocalFile file = new LocalFile("file.txt");
    assertEquals(0, ObjectToFile.saveObject(ult, file));
  }

  @Test
  public void testLoadObject() {
    LocalFile file = new LocalFile("file.txt");
    assertEquals(0, ObjectToFile.saveObject(ult, file));
    
    UltimateDriver ult2 = ObjectToFile.loadObject("file.txt");
    Boolean bool;
    if(ult2.equals(null)) {
      bool = false;
    }
    else {
      bool = true;
    }
    assertTrue(bool);
  }

}
