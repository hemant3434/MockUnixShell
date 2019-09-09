package fileSystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;

/**
 * Represents a file system of CObjects with methods to work on the system.
 * 
 */
public class FileSystemDriver implements Serializable {

  private static final long serialVersionUID = 1L;
  private CDir root;
  private CDir cwd;

  /**
   * Creates a new FileSystemDriver, with a CDir root '/' and the current working directory pointing
   * to the root.
   * 
   * @see CObject
   * @see CDir
   * @see CFile
   */
  public FileSystemDriver() {
    root = new CDir("/", null);
    cwd = root;
  }

  /**
   * Gets the root object of the FileSystemDriver.
   * 
   * @return the CDir
   */
  public CDir getRoot() {
    return root;
  }

  /**
   * Sets a new root of the FileSystemDriver.
   * 
   * @param root The CDir to set the root to.
   */
  public void setRoot(CDir root) {
    this.root = root;
  }


  /**
   * Set the current working directory of the file system driver.
   * 
   * @param dir The CDir to set the current directory to.
   */
  public void setCurrentDir(CDir dir) {
    this.cwd = dir;
  }

  /**
   * Gets the current working directory of the file system driver.
   * 
   * @return The CDir representing the current working directory.
   */
  public CDir getCurrentDir() {
    return this.cwd;
  }

  /**
   * Creates a CDir directory object of the given name at the given path. Requires path to be
   * referring to a valid CDir object.
   * <p>
   * For example: name = "Desktop" and path = "Users/person" then a directory called "Desktop" is
   * created in "/Users/person" assuming that "/Users/person" is a valid directory that already
   * exists.
   * 
   * @param name The string name of the CDir to create.
   * @param path The string path of the CDir to create.
   * @return The created CDir object.
   * @see CDir#CDir(String, CDir)
   */
  public CDir createCDirAtPath(String name, String path) {
    CDir parent = (CDir) CObject.getObjectAtRelativePath(path, root);
    CDir dir = new CDir(name, parent);
    return dir;
  }

  /**
   * Creates a CFile object of the given name at the given path with the given data. Requires path
   * to refer to a valid CDir object.
   * 
   * @param name The string name of the CFile to create.
   * @param path The string path of the CFile to create.
   * @param data The string data for the CFile to contain.
   * @return The created CFile object. CFile#CFile(String, CDir, String)
   */
  public CFile createCFileAtPath(String name, String path, String data) {
    CDir parent = (CDir) CObject.getObjectAtRelativePath(path, root);
    CFile file = new CFile(name, parent, data);
    return file;
  }

  /**
   * Moves the CObject located at a path to a new path. Requires oldPath to refer to a valid and
   * existing file system object. Requires newPath to refer to a valid directory object.
   * 
   * @param oldPath The string path of the file system object to move.
   * @param newPath The string path of the directory to move the object to.
   */
  public void moveCObject(String oldPath, String newPath) {
    CObject obj = CObject.getObjectAtRelativePath(oldPath, root);
    CDir dir = (CDir) CObject.getObjectAtRelativePath(newPath, root);
    obj.setParent(dir);
  }

  /**
   * Gets the absolute path string given a relative path string from the current working directory.
   * 
   * @param relPath The relative string path from the current working directory.
   * @return The absolute string path from the root.
   */
  public String getAbsolutePath(String relPath) {
    if (relPath.equals("/")) {
      return relPath;
    }
    if (relPath.equals(".") || relPath.equals("./")) {
      return this.cwd.getPath();
    }
    relPath = getAbsolutePathHelp(relPath);
    if (relPath == null) {
      return null;
    }
    List<String> relArgs = new ArrayList<String>(Arrays.asList(relPath.split("/")));
    for (int i = 0; i < relArgs.size(); i++) {
      if (relArgs.get(i).equals(".")) {
        relArgs.remove(i);
        i--;
      }
      if (relArgs.get(i).equals("..")) {
        if (i <= 0) {
          return null;
        }
        relArgs.remove(i);
        relArgs.remove(i - 1);
        i = (i - 2 >= 0) ? i - 2 : 0;
      }
    }
    return String.join("/", relArgs);
  }

  /**
   * Performs getAbsolutePath operations on the start of the string and returns the new updated
   * string path.
   * 
   * @param relPath Represents the path to perform the operations on.
   * @return The string with initial getAbsolutePath operations done.
   */
  private String getAbsolutePathHelp(String relPath) {
    if (relPath.equals("")) {
      return null;
    }
    if (Validator.validateName(relPath.split("/")[0]) == 0) {
      relPath = "./" + relPath;
    }
    if (relPath.startsWith("./")) {
      String cwdPath = cwd.getPath();
      if (cwdPath.equals("/")) {
        cwdPath = "";
      }
      relPath = cwd.getPath() + relPath.substring(1);
    }
    if (relPath.startsWith("../")) {
      if (cwd.getParent() == null) {
        return null;
      }
      String cwdParentPath = cwd.parent.getPath();
      if (cwdParentPath == "/") {
        cwdParentPath = "";
      }
      relPath = cwdParentPath + relPath.substring(2);
    }
    if (relPath.startsWith("//")) {
      relPath = relPath.substring(1);
    }
    return relPath;
  }
}
