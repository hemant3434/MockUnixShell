package test;

import static org.junit.Assert.*;
import org.junit.*;
import commands.*;
import fileSystem.CFile;
import fileSystem.CObject;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class MoveItemTest {
  MoveItem mi;
  RecentCommands history;
  FileSystemDriver fsd;
  StackDriver stack;
  MakeDir md;
  DisplayFileSystem dfs;
  OutputFile of;

  @Before
  public void setUp() {
    mi = new MoveItem();
    fsd = new FileSystemDriver();
    md = new MakeDir();
    dfs = new DisplayFileSystem();
    of = new OutputFile();
    history = new RecentCommands();
  }

  @After
  public void tearDown() {
    fsd = new FileSystemDriver();
    history.emptyHistory();
  }

  @Test
  public void testExecuteCopyingDirectory() {
    String inputString = "mkdir a b";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    inputString = "mv a b";
    mi.execute(testArgs, fsd, history, stack);
    testArgs = inputString.split("\\s+");
    String outputAnswer = "tree\n/\nb\n\ta\n";
    inputString = "tree";
    testArgs = inputString.split("\\s+");
    assertEquals(outputAnswer, dfs.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteCopyingFile() {
    String inputString = "mkdir b";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    OutputFile.execute("A", false, "a", fsd);
    inputString = "mv a b";
    testArgs = inputString.split("\\s+");
    mi.execute(testArgs, fsd, history, stack);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b/a"), fsd.getRoot());
    String outputAnswer = "/b/a";
    assertEquals(outputAnswer, testObject.getPath());
    CFile checkString = (CFile) testObject;
    String data = checkString.getData();
    outputAnswer = "A";
    assertEquals(outputAnswer, data);
  }

  @Test
  public void testExecuteCopyingDirectoryWithItemsInside() {
    String inputString = "mkdir a b";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    OutputFile.execute("A", false, "a/test", fsd);
    inputString = "mv a b";
    testArgs = inputString.split("\\s+");
    mi.execute(testArgs, fsd, history, stack);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b/a"), fsd.getRoot());
    String outputAnswer = "/b/a";
    assertEquals(outputAnswer, testObject.getPath());
    CObject testObject2 =
        CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b/a/test"), fsd.getRoot());
    CFile checkString = (CFile) testObject2;
    String data = checkString.getData();
    outputAnswer = "A";
    assertEquals(outputAnswer, data);
    outputAnswer = "tree\n/\nb\n\ta\n\t\ttest\n";
    inputString = "tree";
    testArgs = inputString.split("\\s+");
    assertEquals(outputAnswer, dfs.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteRenameFile() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    OutputFile.execute("A", false, "a/test", fsd);
    inputString = "mv a b";
    testArgs = inputString.split("\\s+");
    mi.execute(testArgs, fsd, history, stack);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b"), fsd.getRoot());
    String outputAnswer = "/b";
    assertEquals(outputAnswer, testObject.getPath());
    CObject testObject2 =
        CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b/test"), fsd.getRoot());
    CFile checkString = (CFile) testObject2;
    String data = checkString.getData();
    outputAnswer = "A";
    assertEquals(outputAnswer, data);
    outputAnswer = "tree\n/\nb\n\ttest\n";
    inputString = "tree";
    testArgs = inputString.split("\\s+");
    assertEquals(outputAnswer, dfs.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteParentPathIntoChildPath() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    inputString = "mkdir a/b";
    testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    inputString = "mv a a/b";
    testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Cannot move file/directory inside itself.";
    assertEquals(outputAnswer, mi.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteInvalidPath() {
    String inputString = "mv test test2";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid path.";
    assertEquals(outputAnswer, mi.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteNoArguments() {
    String inputString = "mv";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Missing arguments.";
    assertEquals(outputAnswer, mi.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteExtraArguments() {
    String inputString = "mv test test test test test";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid arguments.";
    assertEquals(outputAnswer, mi.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testInvalidCharacterInFileName() {
    String inputString = "mv test test?/";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid character in file name.";
    assertEquals(outputAnswer, mi.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testInvalidPathNewPath() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    inputString = "mv a test/test2/test3";
    testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid path.";
    assertEquals(outputAnswer, mi.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testInvalidPathOldPath() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    inputString = "mv test/test2/test3 a";
    testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid path.";
    assertEquals(outputAnswer, mi.execute(testArgs, fsd, history, stack));
  }
}
