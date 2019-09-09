package test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import commands.*;
import fileSystem.*;
import stackSystem.*;

public class PopFromStackTest {
  FileSystemDriver drive;
  PopFromStack pop;
  PushToStack pushd;
  StackDriver stack;
  
  @Before
  public void setUp(){
    drive = new FileSystemDriver();
    pop = new PopFromStack();
    stack = new StackDriver();
    pushd = new PushToStack();
  }

  @Test
  public void testPopInvalidArguments() {
    String[] args = {"popd", "we"};
    String out = "ERROR: invalid argumnents for " + args[0] + "\n";
    
    assertEquals(out, pop.execute(args, drive, null, stack)); 
  }
  
  @Test
  public void testPopNoDirInStack() {
    String[] args = {"popd"};
    String out = args[0] + "\nNo directories in stack \n";
    
    assertEquals(out, pop.execute(args, drive, null, stack)); 
  }
  
  @Test
  public void testPopFromStack() {
    String[] args = {"popd"};
    String[] args2 = {"pushd", "a"};
    String out = args[0] + "\n";
    CDir obj = new CDir("a", drive.getCurrentDir());
    
    pushd.execute(args2, drive, null, stack);
    assertEquals(out, pop.execute(args, drive, null, stack)); 
  }

}
