package fileSystem;

import java.io.*;

/**
 * Represents a file object in the file system.
 * 
 */
public class CFile extends CObject implements Serializable {

  private static final long serialVersionUID = 1L;
  private String data;

  /**
   * Creates a CFile with the specified name, parent, and string data.
   * 
   * @param name The string name of the object.
   * @param parent The CFile's parent directory, must be a CDir.
   * @param data The CFile's string data.
   * @see CObject#CObject(String, CObject)
   */
  public CFile(String name, CDir parent, String data) {
    super(name, parent);
    this.setData(data);
  }

  /**
   * Gets the string data of the CFile.
   * 
   * @return The string representing the data of the CFile.
   */
  public String getData() {
    return this.data;
  }

  /**
   * Sets the data of CFile.
   * 
   * @param data The string to set the CFile's data to.
   */
  public void setData(String data) {
    this.data = data;
  }


}
