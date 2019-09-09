package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.Command;
import commands.PrintCurrentDir;
import commands.RecentCommands;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class PrintCurrentDirTest {
  FileSystemDriver fsd;
  PrintCurrentDir pwd;
  RecentCommands history;
  StackDriver stack;

  @Before
  public void setUp() throws Exception {
    fsd = new FileSystemDriver();
    pwd = new PrintCurrentDir();
    history = new RecentCommands();
    stack = new StackDriver();
  }

  @Test
  public void testInvalidPrintWorkingDir() {
    String expected = "ERROR: pwd takes no arguments";
    String inputString = "pwd a";
    String args[] = inputString.split("\\s+");
    ;
    
    assertEquals(expected, pwd.execute(args, fsd, history, stack));
  }
  
  @Test
  public void testValidPrintWorkingDir() {
    String expected = "pwd\n\n" + "/" + "\n";
    String inputString = "pwd";
    String args[] = inputString.split("\\s+");
    
    assertEquals(expected, pwd.execute(args, fsd, history, stack));
  }

}
