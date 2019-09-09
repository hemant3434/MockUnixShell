package test;

import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import commands.*;
import fileSystem.*;

public class CurrentDirectoryTest {
  FileSystemDriver drive;
  FileSystemDriver drive2;
  CurrentDirectory obj;

  @Before
  public void setUp() {
    drive = new FileSystemDriver();
    drive2 = new FileSystemDriver();
    obj = new CurrentDirectory();
  }

  @Test
  public void testCdToDirectory() {
    CDir dir2 = new CDir("a", drive2.getCurrentDir());
    CDir dir1 = new CDir("a", drive.getCurrentDir());
    drive.setCurrentDir(dir1);

    String[] args = {"cd", "/a"};
    String out = args[0] + "\n" + args[1] + "\n";

    assertEquals(out, obj.execute(args, drive2, null, null));
    assertEquals(drive.getCurrentDir().getPath(), drive2.getCurrentDir().getPath());
  }

  @Test
  public void testCdToRootFromDirectory() {
    CDir dir2 = new CDir("a", drive2.getCurrentDir());
    CDir dir1 = new CDir("a", drive.getCurrentDir());
    drive.setCurrentDir(dir1);

    String[] args = {"cd", "a"};
    String out = args[0] + "\n" + args[1] + "\n";

    assertEquals(out, obj.execute(args, drive2, null, null));
    assertEquals(drive.getCurrentDir().getPath(), drive2.getCurrentDir().getPath());

    drive.setCurrentDir(drive.getRoot());
    String[] args2 = {"cd", "/"};
    String out2 = args2[0] + "\n" + args2[1] + "\n";

    assertEquals(out2, obj.execute(args2, drive2, null, null));
    assertEquals(drive.getCurrentDir().getPath(), drive2.getCurrentDir().getPath());
  }

  @Test
  public void testCdToParentDirectory() {
    CDir dir2 = new CDir("a", drive2.getCurrentDir());
    String[] args = {"cd", "/a/.."};
    String out = args[0] + "\n" + args[1] + "\n";
    
    assertEquals(out, obj.execute(args, drive2, null, null));
    assertEquals(drive.getCurrentDir().getPath(), drive2.getCurrentDir().getPath());
  }

  @Test
  public void testCdToFile() {
    CFile dir2 = new CFile("b", drive2.getCurrentDir(), "hello");
    String[] args = {"cd", "b"};
    String out = "ERROR: directory path is invalid\n";

    assertEquals(out, obj.execute(args, drive2, null, null));

    assertEquals(drive.getCurrentDir().getPath(), drive2.getCurrentDir().getPath());
  }

  @Test
  public void testCdInvalidPath() {
    CDir dir2 = new CDir("b", drive2.getCurrentDir());

    String[] args = {"cd", "/a"};
    String out = "ERROR: directory path is invalid\n";

    assertEquals(out, obj.execute(args, drive2, null, null));
    assertEquals(drive.getCurrentDir().getPath(), drive2.getCurrentDir().getPath());
  }
  
  @Test
  public void testCdInvalidArguments() {
    CDir dir2 = new CDir("a", drive2.getCurrentDir());

    String[] args = {"cd", "/a", "dwedw", "dewdw"};
    String out = "ERROR: invalid argumnents for " + args[0] + "\n";
    assertEquals(out, obj.execute(args, drive2, null, null));
  }
  
  @Test
  public void testSetNewDirValidPath() {
    CDir dir2 = new CDir("a", drive2.getCurrentDir());
    CDir dir1 = new CDir("a", drive.getCurrentDir());
    drive.setCurrentDir(dir1);

    String[] args = {"cd", "/a"};
    CurrentDirectory.setNewDir(args[1], drive2);

    assertEquals(drive.getCurrentDir().getPath(), drive2.getCurrentDir().getPath());
  }
}
