package test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import commands.*;
import fileSystem.*;
import stackSystem.*;

public class PushToStackTest {
  FileSystemDriver drive;
  PushToStack pushd;
  StackDriver stack;
  
  @Before
  public void setUp() {
    drive = new FileSystemDriver();
    pushd = new PushToStack();
    stack = new StackDriver();
  }

  @Test
  public void testPushInvalidArguments() {
    String[] args = {"pushd", "we", "dwedwe"};
    String out = "ERROR: invalid argumnents for " + args[0] + "\n";
    
    assertEquals(out, pushd.execute(args, drive, null, stack));
  }
  
  @Test
  public void testPushInvalidDirectoryPath() {
    String[] args = {"pushd", "abdcd"};
    String out = "ERROR: directory path is invalid\n";
    
    assertEquals(out, pushd.execute(args, drive, null, stack));
  }

  @Test
  public void testPushWithCorrectPath() {
    String[] args = {"pushd", "a"};
    CDir obj = new CDir("a", drive.getCurrentDir());
    
    String out = args[0] + "\n" + args[1] + "\n";
    assertEquals(out, pushd.execute(args, drive, null, stack));
    assertEquals(drive.getRoot(), stack.getDirStack().getHead().getLast());
    assertEquals(obj, drive.getCurrentDir());
  }


}
