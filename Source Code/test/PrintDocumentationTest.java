package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.PrintDocumentation;
import commands.RecentCommands;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class PrintDocumentationTest {
  PrintDocumentation printDoc;
  RecentCommands history;
  FileSystemDriver fsd;
  StackDriver stack;

  @Before
  public void setUp() {
    printDoc = new PrintDocumentation();
  }

  @Test
  public void testExecuteMan() {
    String inputString = "man man";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\nman\nPrint documentation for inputted command.\n"
        + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteExit() {
    String inputString = "man exit";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\nexit\nQuit the program." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteMkdir() {
    String inputString = "man mkdir";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\nmkdir\nCreates a directory relative to current diectory or in a given path.\n"
            + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING."
            + "\n" + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteLs() {
    String inputString = "man ls";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\nls\n" + "If no paths are given, print the contents of file or " + "current directory."
            + "\n" + "If a path is given, if" + " the path specifies a file, print the name of file"
            + "\n" + "If a path is given and it is a directory, "
            + "prints the name of the directory, and its contents" + "\n"
            + "If -R is present, recurively list all subdirectories." + "\n"
            + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING."
            + "\n" + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteCd() {
    String inputString = "man cd";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\ncd\nChange directory relative to current directory or full path.\n"
        + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecutePwd() {
    String inputString = "man pwd";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\npwd\nPrint current working directory (whole path).\n"
        + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteMv() {
    String inputString = "man mv";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\nmv\n" + "mv OLDPATH NEWPATH" + "\n" + "Moves the file or directory given in OLDPATH"
            + "(including the files inside the directory itself) into NEWPATH. The file in "
            + "OLDPATH will no longer be there." + "\n" + "> OUTFILE, >> OUTFILE" + "\n"
            + "> OUTFILE: Replace file contents with STRING." + "\n"
            + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteCp() {
    String inputString = "man cp";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\ncp\n" + "cp OLDPATH NEWPATH" + "\n" + "Copies the file or directory given in OLDPATH"
            + "(including the directory itself if given, and the files inside directory) "
            + "into NEWPATH. The file in OLDPATH will still exist.\n" + "> OUTFILE, >> OUTFILE"
            + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
            + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteCat() {
    String inputString = "man cat";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\ncat\n" + "Displays contents of given file. If more than one file is "
            + "provided, also display those file contents, " + "seperated by line breaks." + "\n"
            + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING."
            + "\n" + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteGet() {
    String inputString = "man get";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\nget\n"
        + "Retrives file at given URL and adds it to the current working directory." + "\n"
        + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteEcho() {
    String inputString = "man echo";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\necho\n" + "Prints the inputted string." + "\n"
        + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecutePushd() {
    String inputString = "man pushd";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\npushd\n" + "Saves current working directory into a stack, then "
        + "change new current working directory to input." + "\n"
        + "If input directory does not exist, returns an error message." + "\n"
        + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecutePopd() {
    String inputString = "man popd";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\npopd\n" + "Returns the topmost item in the pushd stack, then "
        + "changes the current directory to it." + "\n"
        + "If stack is empty, returns an error message." + "\n" + "> OUTFILE, >> OUTFILE" + "\n"
        + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteHistory() {
    String inputString = "man history";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\nhistory\n" + "Returns the most recent commands done from inputted number"
            + "(if given). Otherwise, return all commands in history." + "\n"
            + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING."
            + "\n" + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteSave() {
    String inputString = "man save";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\nsave\n" + "Saves the current state of the shell into an actual file in your computer."
            + " This file can then be later loaded with load." + "\n" + "> OUTFILE, >> OUTFILE"
            + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
            + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteLoad() {
    String inputString = "man load";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\nload\n" + "Loads the state of the shell from a file on your computer."
            + " This command is disabled if it is not the first command inputted into shell.\n"
            + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING."
            + "\n" + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteFind() {
    String inputString = "man find";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "man\nfind\n" + "find path ... -type [f|d] -name expression" + "\n"
        + "Find will find files (given by type, f = file, d = directory) "
        + "in the given path(s) to find ALL the files with the inputted name." + "\n"
        + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteTree() {
    String inputString = "man tree";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer =
        "man\ntree\n" + "Returns a tree diagram of the state of the shell, starting from root.\n"
            + "> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING."
            + "\n" + ">> OUTFILE: Append STRING to file contents." + "\n"
            + "If command does not return a string, does not create OUTFILE." + "\n";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteInvalidCommand() {
    String inputString = "man trese";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Inputted command is not in manual.";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteNoCommand() {
    String inputString = "man";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Missing command.";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }

  @Test
  public void testExecuteExtraArguments() {
    String inputString = "man echo 1 1 test";
    String[] testArgs = inputString.split("\\s+");
    String outputAnswer = "ERROR: Invalid Paramaters.";
    assertEquals(outputAnswer, printDoc.execute(testArgs, fsd, history, stack));
  }
}
