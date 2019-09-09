package test;

import static org.junit.Assert.*;
import java.lang.reflect.Array;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import fileSystem.*;
import stackSystem.StackDriver;

public class DisplayFileSystemTest {
  String[] args;
  FileSystemDriver fsd;
  RecentCommands history;
  StackDriver stack;
  DisplayFileSystem dfs;
  
  @Before
  public void setUp() {
    args = new String[]{"tree"};
    fsd = new FileSystemDriver();
    history = new RecentCommands();
    stack = new StackDriver();
    dfs = new DisplayFileSystem();
  }
  
  @Test
  public void testTreeExtraArgs() {
    String[] iargs = {"tree", "hello"};
    String res = dfs.execute(iargs, fsd, history, stack);
    assertEquals("ERROR: tree takes no args", res);
  }
  
  @Test
  public void testTreeEmpty() {
    String res = dfs.execute(args, fsd, history, stack);
    assertEquals("tree\n\n/\n", res);
  }
  
  @Test
  public void testTreeSomeObjs() {
    fsd.createCDirAtPath("dir1", "/");
    fsd.createCDirAtPath("dir2", "/dir1");
    fsd.createCFileAtPath("file1", "/", "data1");
    fsd.createCFileAtPath("file2", "/dir1", "data2");
    fsd.createCFileAtPath("file3", "/dir1/dir2", "data3");
    String res = dfs.execute(args, fsd, history, stack);
    assertEquals("tree\n" + 
        "\n" + 
        "/\n" + 
        "file1\n" + 
        "dir1\n" + 
        "\tfile2\n" + 
        "\tdir2\n" + 
        "\t\tfile3\n", res);
  }

}
