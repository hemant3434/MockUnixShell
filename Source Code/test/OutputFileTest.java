package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import fileSystem.CFile;
import fileSystem.CObject;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class OutputFileTest {
  RecentCommands history;
  FileSystemDriver fsd;
  StackDriver stack;
  MakeDir md;
  DisplayFileSystem dfs;
  OutputFile of;

  @Before
  public void setUp() {
    fsd = new FileSystemDriver();
    dfs = new DisplayFileSystem();
    history = new RecentCommands();
    stack = new StackDriver();
    md = new MakeDir();
  }

  @After
  public void tearDown() {
    fsd = new FileSystemDriver();
    history.emptyHistory();
  }

  @Test
  public void testExecuteBasicFile() {
    OutputFile.execute("Test", true, "a", fsd);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("a"), fsd.getRoot());
    CFile file = (CFile) testObject;
    String fileData = file.getData();
    String outputAnswer = "Test";
    assertEquals(outputAnswer, fileData);
  }

  @Test
  public void testExecuteCreatingFileIntoPath() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    OutputFile.execute("Test2", true, "a/b", fsd);
    CObject testObj = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("a/b"), fsd.getRoot());
    CFile file = (CFile) testObj;
    String fileData = file.getData();
    String outputAnswer = "Test2";
    assertEquals(outputAnswer, fileData);
  }

  @Test
  public void testExecuteInvalidPath() {
    String outputAnswer = "ERROR: File could not be created (invalid path or file"
        + " already exists as a directory)";
    assertEquals(outputAnswer, OutputFile.execute("Test", true, "a/test/test2", fsd));
  }

  @Test
  public void testExecutePathIsADirectory() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    String outputAnswer = "ERROR: File could not be created (invalid path or file"
        + " already exists as a directory)";
    assertEquals(outputAnswer, OutputFile.execute("Test", true, "a", fsd));
  }

  @Test
  public void testExecuteInvalidCharacterInName() {
    String outputAnswer = "ERROR: Invalid character in file name.";
    assertEquals(outputAnswer, OutputFile.execute("Test", true, "a?", fsd));
  }

  @Test
  public void testExecuteAppendIntoFile() {
    OutputFile.execute("Test", true, "a", fsd);
    OutputFile.execute("Test2", false, "a", fsd);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("a"), fsd.getRoot());
    CFile file = (CFile) testObject;
    String fileData = file.getData();
    String outputAnswer = "Test\nTest2";
    assertEquals(outputAnswer, fileData);
  }

  @Test
  public void testExecuteWriteIntoFile() {
    OutputFile.execute("Test", true, "a", fsd);
    OutputFile.execute("Test2", true, "a", fsd);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("a"), fsd.getRoot());
    CFile file = (CFile) testObject;
    String fileData = file.getData();
    String outputAnswer = "Test2";
    assertEquals(outputAnswer, fileData);
  }
}
