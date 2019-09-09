package test;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import commands.PrintFileContents;
import commands.RecentCommands;
import commands.StringOutput;
import driver.OutputManager;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class PrintfileContentsTest {
  FileSystemDriver fsd;
  StringOutput stringoutput;
  RecentCommands history;
  StackDriver stack;
  PrintFileContents cat;
  
  @Before
  public void setUp() throws Exception {
    fsd = new FileSystemDriver();
    stringoutput = new StringOutput();
    history = new RecentCommands();
    stack = new StackDriver();
    cat =new PrintFileContents();
  }

  @Test
  public void testExecuteOneFile() {
    String expected = "cat\nfile1 \nPrint Output\n";
    String inputString = "echo \"Print Output\" > file1";
    history.addRecentCommand(inputString);
    String args[] = inputString.split("\\s+");
    String output = stringoutput.execute(Arrays.copyOfRange(args, 0, args.length - 2), fsd, 
        history, stack);
    OutputManager.outputManager(output, args, fsd);
    inputString = "cat file1";
    history.addRecentCommand(inputString);
    args = inputString.split("\\s+");
    
    assertEquals(expected, cat.execute(args, fsd, history, stack));
  }
  
  @Test
  public void testExecuteMultipleFiles() {
    String expected ="cat\nfile1 file2 file3 \nPrint Output\n\n\n\nPrint Output Again\n\n\n\n"
        + "Print Output Last Time\n";
    
    String inputString = "echo \"Print Output\" > file1";
    history.addRecentCommand(inputString);
    String args[] = inputString.split("\\s+");
    String output = stringoutput.execute(Arrays.copyOfRange(args, 0, args.length - 2), fsd, 
        history, stack);
    OutputManager.outputManager(output, args, fsd);
    
    inputString = "echo \"Print Output Again\" > file2";
    history.addRecentCommand(inputString);
    args = inputString.split("\\s+");
    output = stringoutput.execute(Arrays.copyOfRange(args, 0, args.length - 2), fsd, 
        history, stack);
    OutputManager.outputManager(output, args, fsd);
    
    inputString = "echo \"Print Output Last Time\" > file3";
    history.addRecentCommand(inputString);
    args = inputString.split("\\s+");
    output = stringoutput.execute(Arrays.copyOfRange(args, 0, args.length - 2), fsd, 
        history, stack);
    OutputManager.outputManager(output, args, fsd);
    
    inputString = "cat file1 file2 file3";
    history.addRecentCommand(inputString);
    args = inputString.split("\\s+");
    
    assertEquals(expected, cat.execute(args, fsd, history, stack));
  }
  
  @Test
  public void testExecuteInvalidFile() {
    String expected = "ERROR: Input arguments are not valid";
    String inputString = "cat file1";
    history.addRecentCommand(inputString);
    String args[] = inputString.split("\\s+");
    
    assertEquals(expected, cat.execute(args, fsd, history, stack));
  }
  
  @Test
  public void testExecuteNoFile() {
    String expected = "ERROR: Input arguments are not valid";
    String inputString = "cat";
    history.addRecentCommand(inputString);
    String args[] = inputString.split("\\s+");
    
    assertEquals(expected, cat.execute(args, fsd, history, stack));
  }
  
  @Test
  public void testExecuteValidAndInvalidFiles() {
    String expected ="cat\nfile1 file2 file3 \nPrint Output\n\n\n\nERROR:file2 is not a valid "
        + "path\n\n\n\nPrint Output Last Time\n";
    
    String inputString = "echo \"Print Output\" > file1";
    history.addRecentCommand(inputString);
    String args[] = inputString.split("\\s+");
    String output = stringoutput.execute(Arrays.copyOfRange(args, 0, args.length - 2), fsd, 
        history, stack);
    OutputManager.outputManager(output, args, fsd);
    
    inputString = "echo \"Print Output Last Time\" > file3";
    history.addRecentCommand(inputString);
    args = inputString.split("\\s+");
    output = stringoutput.execute(Arrays.copyOfRange(args, 0, args.length - 2), fsd, 
        history, stack);
    OutputManager.outputManager(output, args, fsd);
    
    inputString = "cat file1 file2 file3";
    history.addRecentCommand(inputString);
    args = inputString.split("\\s+");
    
    assertEquals(expected, cat.execute(args, fsd, history, stack));
  }

}
