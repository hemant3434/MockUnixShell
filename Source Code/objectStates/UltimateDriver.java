package objectStates;

import commands.*;
import fileSystem.*;
import stackSystem.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Represents entire drivers that need to be implemented inside the file that user saves last
 * jshell's states to.
 * 
 * @see FileSystemDriver
 * @see StackDriver
 * @see RecentCommands
 * @author hemant bhanot
 *
 */
public class UltimateDriver implements Serializable {

  private static final long serialVersionUID = 1L;
  private FileSystemDriver drive = null;
  private StackDriver stack = null;
  private ArrayList<String> history = new ArrayList<String>();

  /**
   * Constructor for the driver that contains all the drivers that contain a shell's state.
   * 
   * @param drive Driver for file system in a shell
   * @param history Driver for the history in a shell
   * @param stack Driver for the stack in a shell
   */
  public UltimateDriver(FileSystemDriver drive, RecentCommands history, StackDriver stack) {
    this.drive = drive;
    this.history = RecentCommands.getCommandList();
    this.stack = stack;
  }

  /**
   * Gets the file system driver.
   * 
   * @see FileSystemDriver
   * @return Driver representing a shell's file system state
   */
  public FileSystemDriver getDrive() {
    return this.drive;
  }

  /**
   * Gets the array list of user's history
   * 
   * @see RecentCommands
   * @return array list representing a shell's history
   */
  public ArrayList<String> getHistory() {
    return this.history;
  }

  /**
   * Gets the stack driver
   * 
   * @see StackDriver
   * @return Driver representing a shell's stack driver
   */
  public StackDriver getStack() {
    return this.stack;
  }
}
