package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.MakeDir;
import commands.OutputFile;
import commands.PrintContents;
import commands.PrintFileContents;
import commands.RecentCommands;
import commands.StringOutput;
import driver.OutputManager;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class OutputManagerTest {
  FileSystemDriver fsd;
  OutputFile outfile;
  PrintFileContents cat;
  RecentCommands history;
  StackDriver stack;

  @Before
  public void setUp() throws Exception {
    fsd = new FileSystemDriver();
    outfile = new OutputFile();
    history = new RecentCommands();
    stack = new StackDriver();
    cat = new PrintFileContents();
  }

  @Test
  public void testOutputCheckerWrite() {
    int expected = 1;
    String input = "ls a b > file";
    String args[] = input.split("\\s+");
    
    assertEquals(expected, OutputManager.outputChecker(args));
  }
  
  @Test
  public void testOutputCheckerAppend() {
    int expected = 2;
    String input = "ls a b >> file";
    String args[] = input.split("\\s+");
    
    assertEquals(expected, OutputManager.outputChecker(args));
  }
  
  @Test
  public void testInputChecker() {
    String expected[] = {"ls", "a", "b"};
    String input = "ls a b >> file";
    String args[] = input.split("\\s+");
    
    assertEquals(expected, OutputManager.inputChecker(args));
  }
  
  @Test
  public void testOutputManagerWrite() {
    String expected = "Write To File";
    String inputString = "ls a b > file";
    String args[] = inputString.split("\\s+");
    OutputManager.outputManager("Write To File", args, fsd);
    
    inputString = "cat file";
    args = inputString.split("\\s+");
    
    assertEquals(expected, cat.execute(args, fsd, history, stack).split("\n")[2]);
  }
  
  @Test
  public void testOutputManagerAppend() {
    String expected = "Append To File";
    String inputString = "ls a b > file";
    String args[] = inputString.split("\\s+");
    OutputManager.outputManager("Append To File", args, fsd);
    
    inputString = "cat file";
    args = inputString.split("\\s+");
    
    assertEquals(expected, cat.execute(args, fsd, history, stack).split("\n")[2]);
  }
  
  

}
