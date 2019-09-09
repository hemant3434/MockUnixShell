package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.CopyItem;
import commands.CurrentDirectory;
import commands.DisplayFileSystem;
import commands.MakeDir;
import commands.OutputFile;
import commands.RecentCommands;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

public class MakeDirTest {
  RecentCommands history;
  FileSystemDriver fsd;
  StackDriver stack;
  MakeDir md;
  OutputFile of;
  CurrentDirectory cd;
  DisplayFileSystem dfs;


  @Before
  public void setUp() {
    fsd = new FileSystemDriver();
    md = new MakeDir();
    of = new OutputFile();
    history = new RecentCommands();
    cd = new CurrentDirectory();
    dfs = new DisplayFileSystem();
  }

  @Test
  public void testMkdir() {
    String inputString = "mkdir a";
    String[] testArgs = inputString.split("\\s+");
    md.execute(testArgs, fsd, history, stack);
    String outputAnswer = "tree\n\n/\na\n";
    inputString = "tree";
    testArgs = inputString.split("\\s+");
    assertEquals(outputAnswer, dfs.execute(testArgs, fsd, history, stack));
  }

}
