package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.*;
import org.junit.Test;
import fileSystem.*;
import stackSystem.*;
import objectStates.*;
import commands.*;


public class SaveAndLoadStateTest {
  SaveState save;
  LoadState load;
  FileSystemDriver drive;
  StackDriver stack;
  RecentCommands history;

  @Before
  public void setUp() {
    save = new SaveState();
    load = new LoadState();
    drive = new FileSystemDriver();
    history = new RecentCommands();
    stack = new StackDriver();
  }

  @Test
  public void testSaveWithInvalidPathWithLoad() {
    CDir test1 = new CDir("a", drive.getRoot());
    stack.push(drive);

    String[] args = {"save", "C:/User/dweowbfoubeod"};
    String[] args2 = {"load", "wefwieufw"};
    String out = "ERROR: invalid argumnents for " + args[0] + "\n";
    String out2 = "ERROR: invalid argumnents for " + args2[0] + "\n";
    assertEquals(out, save.execute(args, drive, history, stack));
    assertEquals(out2, load.execute(args2, drive, history, stack));
  }

  @Test
  public void testSaveandLoadConcurrentlyWithCorrectInputs() {
    CDir test1 = new CDir("a", drive.getRoot());
    stack.push(drive);

    String[] args = {"save", "file.txt"};
    String out = args[0] + "\n" + args[1] + "\n";
    String[] args2 = {"load", "file.txt"};
    String out2 = args2[0] + "\n" + args[1] + "\n";

    assertEquals(out, save.execute(args, drive, history, stack));
    assertEquals(out2, load.execute(args2, drive, history, stack));

  }
}
