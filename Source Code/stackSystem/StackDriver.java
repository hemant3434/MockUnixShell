package stackSystem;

import commands.*;
import fileSystem.*;
import java.io.*;

/**
 * Drives a virtual stack of directories (CDir) with LIFO behavior.
 * 
 * @see CDir
 * @author Hemant Bhanot
 */
public class StackDriver implements Serializable {

  private static final long serialVersionUID = 1L;
  private Stack dirStack;

  /**
   * Creates a new directory stack.
   * 
   * @see Stack#Stack()
   */
  public StackDriver() {
    dirStack = new Stack();
  }

  /**
   * Returns the directory stack.
   * 
   * @see Stack
   * @return returns the directory stack.
   */
  public Stack getDirStack() {
    return dirStack;
  }

  /**
   * Sets the directory stack
   * 
   * @see Stack
   * @param stack Linked Lists of directories to set the drive to
   */
  public void setDirStack(Stack stack) {
    this.dirStack = stack;
  }

  /**
   * Appends the current directory to the directory stack.
   * 
   * @param drive driver to get the current directory of the file system.
   * @see Stack#addDirToTail(CDir)
   */
  public void push(FileSystemDriver drive) {
    dirStack.addDirToTail(drive.getCurrentDir());
  }

  /**
   * Removes the last directory in the stack and cd into it.
   * 
   * @param drive driver to change the current working directory.
   * @see Stack#removeDirTail()
   * @see CurrentDirectory#setNewDir(String, FileSystemDriver)
   */
  public void pop(FileSystemDriver drive) {
    String dir = dirStack.removeDirTail().getPath();
    CurrentDirectory.setNewDir(dir, drive);
  }

}
