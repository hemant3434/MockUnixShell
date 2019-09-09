package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;
import org.junit.Test;
import fileSystem.*;
import stackSystem.*;
import objectStates.*;

public class UltimateDriverTest {

  @Test
  public void testUltimateDriverConstructor() {
    FileSystemDriver drive = new FileSystemDriver();
    StackDriver stack = new StackDriver();
    ArrayList<String> history = new ArrayList<String>();
    
    UltimateDriver ult = new UltimateDriver(drive, null, stack);
    assertEquals(ult.getDrive(), drive);
    assertEquals(ult.getStack(), stack);
    assertEquals(ult.getHistory(), history);
  }

}
