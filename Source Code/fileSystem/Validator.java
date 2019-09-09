package fileSystem;

import commands.PrintContents;

/**
 * This class is responsible for validating the input arguments for the commands that are used, if
 * needed.
 * 
 *
 */
public class Validator {
  /**
   * This method checks if the given string is a valid word
   * 
   * @param name Is the string to be validated
   * @return an int that is 0 or 1; 0 means it is valid and 1 means it is invalid
   */
  public static int validateName(String name) {
    for (int i = 0; i < name.length(); i++) {
      char c = name.charAt(i);
      if (("/.!@#$%^&*(){}~|<>?").contains("" + c) || Character.isWhitespace(c)) {
        return 1;
      }
    }
    return 0;
  }

  /**
   * This method checks if the given path is found in the current file system driver and what is
   * found at the path.
   * 
   * @param path represents the given path
   * @param root represents the root of the file system driver
   * @return an int that is 0,1,2; 0 means it DNE, 1 means it is a CFile and 2 means it is a CDir
   */
  public static int validatePath(String path, CObject root) {
    CObject obj = CObject.getObjectAtRelativePath(path, root);
    // String top = (obj == null) ? "null" : obj.getName();
    // System.out.println("obj got: " + top);
    if (obj == null) {
      return 0;
    } else if (obj instanceof CFile) {
      return 1;
    } else {
      return 2;
    }
  }

  /**
   * This method is used in PrintFileContents to validate the input arguments, it checks if each
   * argument is a file.
   * 
   * @param args Is an array of strings, where each string is an argument that the user typed.
   * @param drive Is the file system driver that is being used.
   * @return An int that is 0 or 1; 0 means that it is invalid, and 1 means that it is valid
   */
  public static int validateCat(String args[], FileSystemDriver drive) {
    int check = 1;
    if (args.length < 2) {
      return 0;
    }
    for (int i = 1; i < args.length; i++) {
      String absPath = drive.getAbsolutePath(args[i]);
      String relPath;
      if (args[i].indexOf("/") == -1) {
        relPath = drive.getCurrentDir().getPath() + "/" + args[i];
        absPath = drive.getAbsolutePath(relPath);
      }
      if (Validator.validatePath(absPath, drive.getRoot()) == 0) {
        check = 0;
      } else {
        check = 1;
        break;
      }
    }
    return check;
  }

  /**
   * This method validates a path given to a directory, it checks if it is valid and returns the
   * portion of the path that is correct. if it exists.
   * 
   * @param path Represents the path given.
   * @param system Represents the file system driver in use.
   * @return A String that is the object's path if valid, otherwise the parent's path if valid
   *         otherwise null
   */
  public static String validateDirPath(String path, FileSystemDriver system) {
    if (path == null || path.equals("") || path.equals("/")) {
      return null;
    }
    String[] pathArgs = path.split("/");
    if (path.split("//").length > 1) {
      return null;
    }
    String name = pathArgs[pathArgs.length - 1];
    int endOfFirstIndex = path.length() - name.length() - 1;
    endOfFirstIndex = (endOfFirstIndex < 0) ? 0 : endOfFirstIndex;
    String parentPath = path.substring(0, endOfFirstIndex);
    if (parentPath.equals("")) { // i.e. if root
      parentPath = "/";
    }
    if (Validator.validatePath(path, system.getRoot()) != 0) {
      return path;
    }
    if (Validator.validatePath(parentPath, system.getRoot()) == 2) {
      return parentPath;
    }
    return null;
  }

  // return 0 if the path is invalid for cd and pushd
  // return 1 if the path is valid for cd and pushd
  /**
   * This method is responsible for checking if the given path for the cd or pushd commands is valid
   * 
   * @param drive Represents the file system driver in use
   * @param path Represents the path given
   * @return An int that is 0 or 1; 0 means that it is invalid, and 1 means that it is valid
   */
  public static int validateCd(FileSystemDriver drive, String path) {
    CDir temp = null;
    if (path.equals("/")) {
      return 1;
    }
    temp = drive.getCurrentDir();
    if (path.substring(0, 1).equals("/")) {
      temp = drive.getRoot();
      path = path.substring(1);
    }
    String[] pathArgs = path.split("/");
    int len = pathArgs.length;
    for (int i = 0; i < len; i++) {
      if (pathArgs[i].equals("..")) {
        temp = temp.getParent();
        if (temp == null) {
          return 0;
        }
      } else if (pathArgs[i].equals(".")) {
      } else if (temp.getChild(pathArgs[i]) == null
          || !(temp.getChild(pathArgs[i]) instanceof CDir)) {
        return 0;
      } else {
        temp = (CDir) temp.getChild(pathArgs[i]);
      }
    }
    return 1;
  }

  /**
   * This method checks if every single argument given to ls is invalid. This is a special case for
   * ls since otherwise it will print the output of the correct arguments and print seperate error
   * messages for the incorrect ones.
   * 
   * @param args Is an array of strings, where each string is an argument that the user typed.
   * @param drive Represents the file system driver in use
   * @return
   */
  public static int validateLs(String args[], FileSystemDriver drive) {
    int check = 1;
    String absPath;
    int count = 1;
    if (args.length > 1 && args[1].equals("-R")) {
      count = 2;
    }
    while (count < args.length) {
      if (args[count].indexOf("/") == -1) {
        String relPath = drive.getCurrentDir().getPath() + "/" + args[count];
        absPath = drive.getAbsolutePath(relPath);
      }
      else {
        absPath = drive.getAbsolutePath(args[count]);
      }
      if (Validator.validatePath(absPath, drive.getRoot()) == 0) {
        check = 0;
      } else {
        check = 1;
        break;
      }
      count++;
    }
    return check;
  }

}
