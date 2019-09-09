package test;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import commands.MakeDir;
import commands.PrintContents;
import commands.PrintFileContents;
import commands.RecentCommands;
import commands.StringOutput;
import driver.OutputManager;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class PrintContentsTest {
  FileSystemDriver fsd;
  MakeDir mkdir;
  StringOutput stringoutput;
  RecentCommands history;
  StackDriver stack;
  PrintContents ls;
    
  @Before
  public void setUp() throws Exception {
    fsd = new FileSystemDriver();
    mkdir = new MakeDir();
    stringoutput = new StringOutput();
    history = new RecentCommands();
    stack = new StackDriver();
    ls =new PrintContents();
  }
  

  @Test
  public void ExecuteCurrentDirectory() {
    String expected = "ls\nb\na\n";
    String inputString = "mkdir a b";
    String args[] = inputString.split("\\s+");
    String output = mkdir.execute(args, fsd, history, stack);
    
    inputString = "ls";
    args = inputString.split("\\s+");
    
    assertEquals(expected, ls.execute(args, fsd, history, stack));
  }
  
  @Test
  public void ExecuteOneDirectory() {
    String expected = "ls\na \na: b c\n";
    String inputString = "mkdir a a/b a/c";
    String args[] = inputString.split("\\s+");
    mkdir.execute(args, fsd, history, stack);
    
    inputString = "ls a";
    args = inputString.split("\\s+");
    
    assertEquals(expected, ls.execute(args, fsd, history, stack));
  }
  
  @Test
  public void ExecuteMultipleDirectory() {
    String expected = "ls\na b \na: b c\nb: d\n";
    String inputString = "mkdir a b a/b a/c b/d";
    String args[] = inputString.split("\\s+");
    mkdir.execute(args, fsd, history, stack);
    
    inputString = "ls a b";
    args = inputString.split("\\s+");
    
    assertEquals(expected, ls.execute(args, fsd, history, stack));
  }
  
  @Test
  public void ExecuteDirectoryAndFile() {
    String expected = "ls\na file1 \na: b c\nfile1\n";
    String inputString = "mkdir a a/b a/c";
    String args[] = inputString.split("\\s+");
    mkdir.execute(args, fsd, history, stack);
    
    inputString = "echo \"Print Output\" > file1";
    history.addRecentCommand(inputString);
    args = inputString.split("\\s+");
    String output = stringoutput.execute(Arrays.copyOfRange(args, 0, args.length - 2), fsd, 
        history, stack);
    OutputManager.outputManager(output, args, fsd);
    
    inputString = "ls a file1";
    args = inputString.split("\\s+");
    
    assertEquals(expected, ls.execute(args, fsd, history, stack));
  }
  
  public void ExecuteInvalidDirectory() {
    String expected = "ERROR: input arguments are invalid";
    
    String inputString = "ls a";
    String args[] = inputString.split("\\s+");
    
    assertEquals(expected, ls.execute(args, fsd, history, stack));
  }
  

}
