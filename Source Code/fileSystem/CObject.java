package fileSystem;

import java.io.*;

/**
 * Represents an arbitrary object in the file system.
 * 
 */
public class CObject implements Serializable {
  private static final long serialVersionUID = 1L;
  protected String name;
  protected CDir parent;

  /**
   * Creates a CObject with the specified name and parent.
   * 
   * @param name The string name of the CObject. Assumes it is valid.
   * @param parent The CObject's parent directory. Must be an instance of CDir.
   */
  public CObject(String name, CObject parent) {
    this.name = name;
    this.parent = null;
    if (parent != null && parent instanceof CDir) {
      this.parent = (CDir) parent;
      this.parent.addChild(this);
    }
  }

  /**
   * Gets the name of the CObject.
   * 
   * @return A string representing the CObject's name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the name of the CObject.
   * 
   * @param A string representing the CObject's name.
   */
  public void setName(String name) {
    if (this.parent != null) {
      this.parent.getChildren().put(name, this);
      this.parent.getChildren().remove(this.name);
    }
    this.name = name;
  }

  /**
   * Gets the parent directory of the CObject.
   * 
   * @return A CDir object representing the CObject's parent.
   */
  public CDir getParent() {
    return this.parent;
  }

  /**
   * Sets the parent directory of the CObject.
   * 
   * @param parent A CDir object representing the CObject's new parent.
   */
  public void setParent(CDir parent) {
    if (this.parent != null) {
      this.parent.removeChild(this.name);
    }
    this.parent = parent;
    if (parent != null) {
      this.parent.addChild(this);
    }
  }

  /**
   * Gets the absolute path of the object from the highest level directory. If the object is the
   * root, it returns an empty string. Otherwise returns a string of the form "/dir_name/obj_names
   * ...".
   * 
   * @return A string representing the absolute path of the object.
   */
  public String getPath() {
    String path = this.name;
    CObject p = this.parent;
    while (p != null) {
      path = p.name + "/" + path;
      p = p.parent;
    }
    if (path.startsWith("//")) {
      return path.substring(1);
    }
    return path;
  }

  /**
   * Returns a CObject representing the object at the path specified relative to the root object
   * specified. Returns null if the object cannot be found.
   * <p>
   * For example if path = "/b" or if path = "b" and the root directory given is a directory called
   * "a" which contains the file "b", the method returns the file "b".
   * 
   * @param path A string which is the relative path of the object from root.
   * @param root The root directory which the path references from.
   * @return The CObject at the specified path, from root, or null.
   */
  public static CObject getObjectAtRelativePath(String path, CObject root) {
    if (!(root instanceof CDir)) {
      return null;
    }
    if (path.equals("/")) {
      return root;
    }
    if (path.charAt(0) == '/') { // to handle '/' at the start of the path
      path = path.substring(1);
    }
    String args[] = path.split("/");
    CObject p = root;
    // looping through the names in the path and getting the appropriate
    // child
    for (int i = 0; i < args.length; i++) {
      if (p == null || !(p instanceof CDir)) {
        return null;
      }
      p = ((CDir) p).getChild(args[i]);
    }
    return p;
  }
}
