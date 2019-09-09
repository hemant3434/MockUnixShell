package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import fileSystem.*;
import stackSystem.StackDriver;

public class SearchObjectsTest {
  String[] args;
  FileSystemDriver fsd;
  RecentCommands history;
  StackDriver stack;
  SearchObjects so;

  @Before
  public void setUp() {
    args = new String[] {"find"};
    fsd = new FileSystemDriver();
    history = new RecentCommands();
    stack = new StackDriver();
    so = new SearchObjects();
    fsd.createCDirAtPath("dir1", "/");
    fsd.createCDirAtPath("dir2", "/dir1");
    fsd.createCFileAtPath("file1", "/", "data1");
    fsd.createCFileAtPath("file2", "/dir1", "data2");
    fsd.createCFileAtPath("file3", "/dir1/dir2", "data3");
  }

  @Test
  public void testNoArgs() {
    assertEquals("ERROR: find requires at least 5 args", so.execute(args, fsd, history, stack));
  }

  @Test
  public void testExecuteInvalidArgs() {
    args = new String[] {"find", "a", "a", "a", "a", "a", "a"};
    assertEquals("ERROR: invalid options provided", so.execute(args, fsd, history, stack));
  }

  @Test
  public void testExecuteInvalidName() {
    args = new String[] {"find", "path", "-type", "f", "-name", "..."};
    assertEquals("ERROR: invalid type/name provided", so.execute(args, fsd, history, stack));
  }

  @Test
  public void testExecuteInvalidType() {
    args = new String[] {"find", "path", "-type", "a", "-name", "hello"};
    assertEquals("ERROR: invalid type/name provided", so.execute(args, fsd, history, stack));
  }

  @Test
  public void testExecuteInvalidDirs() {
    args = new String[] {"find", "invalidPath", "-type", "d", "-name", "\"hello\""};
    assertEquals("ERROR: path #1 does not refer to a valid directory\n",
        so.execute(args, fsd, history, stack));
  }

}
