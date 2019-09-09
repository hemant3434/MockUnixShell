package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import fileSystem.FileSystemDriver;
import fileSystem.Validator;
import stackSystem.StackDriver;

public class ValidatorTest {
  RecentCommands history;
  StackDriver stack;
  FileSystemDriver fsd;
  MakeDir md;
  OutputFile of;

  @Before
  public void setUp() {
    fsd = new FileSystemDriver();
    md = new MakeDir();
    of = new OutputFile();
  }
  
  @Test
  public void testValidateNameValidName() {
    int outputAnswer = 0;
    assertEquals(outputAnswer, Validator.validateName("valid"));
  }

  @Test
  public void testValidateNameInvalidName() {
    int outputAnswer = 1;
    assertEquals(outputAnswer, Validator.validateName("invalid?!"));
  }

  @Test
  public void testValidatePathNonexistingPath() {
    int outputAnswer = 0;
    assertEquals(outputAnswer, Validator.validatePath("test", fsd.getRoot()));
  }

  @Test
  public void testValidatePathFilePath() {
    OutputFile.execute("Test", true, "test", fsd);
    int outputAnswer = 1;
    assertEquals(outputAnswer, Validator.validatePath("test", fsd.getRoot()));
  }

  @Test
  public void testValidatePathDirectoryPath() {
    String inputString = "mkdir test";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    int outputAnswer = 2;
    assertEquals(outputAnswer, Validator.validatePath("test", fsd.getRoot()));
  }

  @Test
  public void testValidateDirPathInvalidPath() {
    String outputAnswer = "/";
    assertEquals(outputAnswer, Validator.validateDirPath("test", fsd));
  }

  @Test
  public void testValidateDirPathImpossiblePath() {
    String outputAnswer = null;
    assertEquals(outputAnswer, Validator.validateDirPath(null, fsd));
  }

  @Test
  public void testValidateDirPathValidPath() {
    String inputString = "mkdir test";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    String outputAnswer = "test";
    assertEquals(outputAnswer, Validator.validateDirPath("test", fsd));
  }
  
  @Test
  public void testValidateCat() {
    int expected = 0;
    String inputString = "mkdir a b";
    String args[] = inputString.split("\\s+");

    
    assertEquals(expected, Validator.validateCat(args, fsd));
  }
  
  @Test
  public void testValidateLs() {
    int expected = 0;
    String inputString = "ls a b";
    String args[] = inputString.split("\\s+");

    assertEquals(expected, Validator.validateCat(args, fsd));
  }
}
