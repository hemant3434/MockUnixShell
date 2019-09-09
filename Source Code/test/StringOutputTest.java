package test;

import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Test;
import commands.*;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class StringOutputTest {
  StringOutput so1;
  RecentCommands history;
  FileSystemDriver fsd;
  StackDriver stack;

  @Before
  public void setUp() {
    so1 = new StringOutput();
    history = new RecentCommands();
  }

  @Test
  public void testExecuteNormalString() {
    String inputString = "echo \"A\"";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "echo" + "\n" + "\"A\"" + "\n" + "A" + "\n";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteMissingArguments() {
    String inputString = "echo";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: No arguments provided.";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithSpaces() {
    String inputString = "echo \"Test with spaces\"";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "echo\n" + "\"Test with spaces\"" + "\n" + "Test with spaces" + "\n";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithExtraArguments() {
    String inputString = "echo \"Basic String\" test test test";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid arguments provided.";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithFloatingQuotationMarks() {
    String inputString = "echo \"Floating";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Inputted string is invalid.";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void textExecuteWithExtraQuotationMarks() {
    String inputString = "echo \"Extra\" \" \"";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Inputted string is invalid.";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void textExecuteWithValidQuotationMarksUsingEscape() {
    String inputString = "echo \"\\\"Test with valid quotations\\\"\"";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "echo\n" + "\"\\\"Test with valid quotations\\\"\"" + "\n"
        + "\"Test with valid quotations\"" + "\n";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithMultipleSpaces() {
    String inputString = "echo \"Test with    multiple   spaces\"";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "echo\n" + "\"Test with    multiple   spaces\"" + "\n"
        + "Test with    multiple   spaces" + "\n";
    assertEquals(outputAnswer, so1.execute(testArgs, fsd, history, stack));
  }
}
