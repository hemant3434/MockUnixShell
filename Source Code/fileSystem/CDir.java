package fileSystem;

import java.util.Hashtable;
import java.io.*;

/**
 * Represents a directory object in the file system.
 * 
 */
public class CDir extends CObject implements Serializable {

  private static final long serialVersionUID = 1L;
  private Hashtable<String, CObject> children = new Hashtable<String, CObject>();

  /**
   * Creates a CDir with the specified name and parent.
   * 
   * @param name The string name of the object.
   * @param parent The CDir's parent directory, must be a CDir.
   * @see CObject#CObject(String, CObject)
   */
  public CDir(String name, CDir parent) {
    super(name, parent);
  }

  /**
   * Gets the hashtable of the CObject children of the directory.
   * 
   * @return A hashtable of the children mapping the object names to their objects.
   */
  public Hashtable<String, CObject> getChildren() {
    return children;
  }

  /**
   * Returns the child object with the string str or null if does not exist.
   * 
   * @param str The name of the child object to get.
   * @return The CObject of the child specified by name.
   */
  public CObject getChild(String str) {
    return children.get(str);
  }

  /**
   * Clears the directory's children.
   */
  public void clearChildren() {
    children = new Hashtable<String, CObject>();
  }

  /**
   * Adds a new child to the directory.
   * 
   * @param child The CObject child to be added to the directory's children.
   */
  public void addChild(CObject child) {
    children.put(child.name, child);
  }

  /**
   * Removes a child from the directory.
   * 
   * @param name The string name of the child object to remove.
   */
  public void removeChild(String name) {
    children.remove(name);
  }

}
