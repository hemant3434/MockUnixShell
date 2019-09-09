package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import fileSystem.*;

public class FileSystemDriverTest {
  FileSystemDriver fsd;

  @Before
  public void setUp() {
    fsd = new FileSystemDriver();
    fsd.createCDirAtPath("dir1", "/");
    fsd.createCDirAtPath("dir2", "/dir1");
    fsd.createCFileAtPath("file1", "/", "data1");
    fsd.createCFileAtPath("file2", "/dir1", "data2");
    fsd.createCFileAtPath("file3", "/dir1/dir2", "data3");
  }

  @Test
  public void testFileSystemDriver() {
    assertEquals("/", fsd.getRoot().getName());
    assertEquals(fsd.getRoot(), fsd.getCurrentDir());
  }

  @Test
  public void testCreateCDirAtPath() {
    assertEquals(2, fsd.getRoot().getChildren().size());
    assertEquals(2, ((CDir) fsd.getRoot().getChild("dir1")).getChildren().size());
  }

  @Test
  public void testCreateCFileAtPath() {
    assertEquals("data1", ((CFile) fsd.getRoot().getChild("file1")).getData());
    assertEquals("data2",
        ((CFile) CObject.getObjectAtRelativePath("/dir1/file2", fsd.getRoot())).getData());
    assertEquals("data3",
        ((CFile) CObject.getObjectAtRelativePath("/dir1/dir2/file3", fsd.getRoot())).getData());
  }

  @Test
  public void testMoveCObjectToDir() {
    fsd.moveCObject("/file1", "/dir1/dir2");
    assertEquals("data1",
        ((CFile) CObject.getObjectAtRelativePath("/dir1/dir2/file1", fsd.getRoot())).getData());
  }

  @Test
  public void testMoveCObjectToRoot() {
    fsd.moveCObject("/dir1/dir2/file3", "/");
    assertEquals("data1",
        ((CFile) CObject.getObjectAtRelativePath("/file1", fsd.getRoot())).getData());
  }

  @Test
  public void testGetAbsolutePathRoot() {
    assertEquals("/", fsd.getAbsolutePath("/"));
  }

  @Test
  public void testGetAbsolutePathAbsPath() {
    assertEquals("/dir1/dir2/file3", fsd.getAbsolutePath("./dir1/dir2/file3"));
  }

  @Test
  public void testGetAbsolutePathDir() {
    fsd.setCurrentDir(((CDir) CObject.getObjectAtRelativePath("/dir1/dir2", fsd.getRoot())));
    assertEquals("/dir1/dir2/file3", fsd.getAbsolutePath("file3"));
    assertEquals("/dir1/dir2/file3", fsd.getAbsolutePath("./file3"));
  }
}
