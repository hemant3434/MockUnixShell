package stackSystem;

import java.util.LinkedList;
import fileSystem.CDir;
import java.io.*;

/**
 * Represents a virtual stack of directories.
 * 
 * @see CDir
 * @author Hemant Bhanot
 */
public class Stack implements Serializable {

  private static final long serialVersionUID = 1L;
  private LinkedList<CDir> dirStack;

  /**
   * Creates a directory stack made out of linked list.
   * 
   * @see CDir
   */
  public Stack() {
    dirStack = new LinkedList<CDir>();
  }

  /**
   * Returns the head of the linked list of directories.
   * 
   * @return the head of the linked list of directories.
   */
  public LinkedList<CDir> getHead() {
    return dirStack;
  }

  /**
   * Adds a directory to the tail of the linked list.
   * 
   * @param dir The directory to add to the tail of linked list.
   */
  public void addDirToTail(CDir dir) {
    dirStack.add(dir);
  }

  /**
   * Removes the directory at the tail of the linked list.
   * 
   * @return directory at the tail of linked list
   */
  public CDir removeDirTail() {
    return (CDir) dirStack.pollLast();
  }

}
