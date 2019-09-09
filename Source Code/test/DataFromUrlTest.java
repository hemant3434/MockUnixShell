package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import commands.*;
import fileSystem.*;
import stackSystem.*;

public class DataFromUrlTest {
  FileSystemDriver fsd;
  RecentCommands history;
  StackDriver stack;
  DataFromUrl dau;

  @Before
  public void setUp() {
    fsd = new FileSystemDriver();
    history = new RecentCommands();
    stack = new StackDriver();
    dau = new DataFromUrl("");
  }

  @Test
  public void testExecuteNoUrl() {
    String[] args = {"get"};
    String res = dau.execute(args, fsd, history, stack);
    assertEquals("ERROR: get requires 1 argument", res);
  }

  @Test
  public void testExecuteInvalidUrl() {
    String[] args = {"get", "invalidUrl"};
    String res = dau.execute(args, fsd, history, stack);
    assertEquals("ERROR: invalid URL to get", res);
  }

  @Test
  public void testExecuteValidUrlInvalidName() {
    String[] args = {"get", "validUrl?=test"};
    String res = dau.execute(args, fsd, history, stack);
    assertEquals("ERROR: URL has invalid file", res);
  }

  @Test
  public void testExecuteValidUrl() {
    String[] args = {"get", "validUrl"};
    String res = dau.execute(args, fsd, history, stack);
    assertEquals("get\nvalidUrl\n", res);
    assertEquals("valid data", ((CFile) fsd.getRoot().getChild("validUrl")).getData());
  }

}
