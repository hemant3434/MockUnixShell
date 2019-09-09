package test;

import static org.junit.Assert.*;
import org.junit.*;
import commands.RecentCommands;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class RecentCommandsTest {
  RecentCommands history;
  FileSystemDriver fsd;
  StackDriver stack;

  @Before
  public void setUp() {
    history = new RecentCommands();
  }

  @After
  public void tearDown() {
    history.emptyHistory();
  }

  @Test
  public void testExecuteBasicCommand() {
    String inputString = "history";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "history\n" + "1. history" + "\n";
    assertEquals(outputAnswer, history.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithRandomInput() {
    String inputString = "a";
    history.addRecentCommand(inputString);
    inputString = "history";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "history\n" + "1. a" + "\n" + "2. history" + "\n";
    assertEquals(outputAnswer, history.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithNonIntegerArgument() {
    String inputString = "history test";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Argument is not an integer.";
    assertEquals(outputAnswer, history.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithMultipleInputsAndIntegerInputBelowTotalInputs() {
    String inputString = "a";
    for (int i = 0; i < 10; i++) {
      history.addRecentCommand(inputString);
    }
    inputString = "history 4";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "history\n" + "4" + "\n" + "8. a" + "\n" + "9. a" + "\n" + "10. a" + "\n"
        + "11. history 4" + "\n";
    assertEquals(outputAnswer, history.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithInvalidIntegerInput() {
    String inputString = "history -1";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid integer input.";
    assertEquals(outputAnswer, history.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithMultipleInputsAndIntegerInputAboveTotalInputs() {
    String inputString = "a";
    for (int i = 0; i < 3; i++) {
      history.addRecentCommand(inputString);
    }
    inputString = "history 100";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "history\n" + "100" + "\n" + "1. a" + "\n" + "2. a" + "\n" + "3. a\n"
        + "4. history 100" + "\n";
    assertEquals(outputAnswer, history.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteWithExtraArguments() {
    String inputString = "history 1 test test";
    history.addRecentCommand(inputString);
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid arguments.";
    assertEquals(outputAnswer, history.execute(testArgs, fsd, history, stack));
  }
}
