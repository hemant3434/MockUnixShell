package objectStates;

import java.io.*;

/**
 * Represents a file on a local computer system
 * 
 * @author hemant bhanot
 *
 */
public class LocalFile {
  private File myobj = null;

  /**
   * Constructor for a local file on user's computer.
   * 
   * @param path Path of the file on a local computer
   * @param name Name of the file being created on local computer
   */
  public LocalFile(String path, String name) {
    myobj = new File(path, name);
  }
  
  public LocalFile(String name) {
    myobj = new File(name);
  }

  /**
   * Create a actual file based on the path and name of file on a user's local computer
   * 
   * @return 0 if the file creation worked and 1 if it failed due to wrong file path etc.
   */
  public int checkCreate() {
    try {
      this.myobj.createNewFile();
      return 0;
    } catch (Exception e) {
      return 1;
    }
  }

  /**
   * Gets the file object representing user's local system.
   * 
   * @return Returns the file object representing the file on local computer
   */
  public File getFile() {
    return this.myobj;
  }
}
