package test;

import static org.junit.Assert.*;
import org.junit.*;
import commands.*;
import fileSystem.CFile;
import fileSystem.CObject;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class CopyItemTest {
  CopyItem ci;
  RecentCommands history;
  FileSystemDriver fsd;
  StackDriver stack;
  MakeDir md;
  DisplayFileSystem dfs;
  OutputFile of;
  CurrentDirectory cd;

  @Before
  public void setUp() {
    ci = new CopyItem();
    fsd = new FileSystemDriver();
    md = new MakeDir();
    dfs = new DisplayFileSystem();
    of = new OutputFile();
    history = new RecentCommands();
    cd = new CurrentDirectory();
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
    inputString = "cp a b";
    ci.execute(testArgs, fsd, history, stack);
    testArgs = inputString.split("\\s+");
    String outputAnswer = "tree\n/\nb\n\ta\na\n";
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
    inputString = "cp a b";
    testArgs = inputString.split("\\s+");
    ci.execute(testArgs, fsd, history, stack);
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
    inputString = "cp a b";
    testArgs = inputString.split("\\s+");
    ci.execute(testArgs, fsd, history, stack);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b/a"), fsd.getRoot());
    String outputAnswer = "/b/a";
    assertEquals(outputAnswer, testObject.getPath());
    CObject testObject2 =
        CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b/a/test"), fsd.getRoot());
    CFile checkString = (CFile) testObject2;
    String data = checkString.getData();
    outputAnswer = "A";
    assertEquals(outputAnswer, data);
    CObject testObject3 = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("a"), fsd.getRoot());
    outputAnswer = "/a";
    assertEquals(outputAnswer, testObject3.getPath());
    CObject testObject4 =
        CObject.getObjectAtRelativePath(fsd.getAbsolutePath("a/test"), fsd.getRoot());
    outputAnswer = "A";
    CFile checkString2 = (CFile) testObject4;
    assertEquals(outputAnswer, checkString2.getData());
    // to confirm there are no floating extra files
    outputAnswer = "tree\n/\nb\n\ta\n\t\ttest\na\n\ttest\n";
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
    inputString = "cp a b";
    testArgs = inputString.split("\\s+");
    ci.execute(testArgs, fsd, history, stack);
    CObject testObject = CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b"), fsd.getRoot());
    String outputAnswer = "/b";
    assertEquals(outputAnswer, testObject.getPath());
    CObject testObject2 =
        CObject.getObjectAtRelativePath(fsd.getAbsolutePath("b/test"), fsd.getRoot());
    CFile checkString = (CFile) testObject2;
    String data = checkString.getData();
    outputAnswer = "A";
    assertEquals(outputAnswer, data);
    // to confirm that original file is still in place
    outputAnswer = "tree\n/\nb\n\ttest\na\n\ttest\n";
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
    inputString = "cp a a/b";
    testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Cannot copy file/directory inside itself.";
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
    inputString = "cp a/b a";
    testArgs = inputString.split("\\s+");
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteInvalidPath() {
    String inputString = "cp test test2";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid path.";
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteNoArguments() {
    String inputString = "cp";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Missing arguments.";
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteExtraArguments() {
    String inputString = "cp test test test test test";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid arguments.";
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testInvalidPathNewPath() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    inputString = "cp a test/test2/test3";
    testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid path.";
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testInvalidPathOldPath() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    inputString = "cp test/test2/test3 a";
    testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid path.";
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testInvalidCharacterInFileName() {
    String inputString = "cp test test?/";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid character in file name.";
    assertEquals(outputAnswer, ci.execute(testArgs, fsd, history, stack));
  }
}
