package commands;

import fileSystem.*;

/**
 * Represents redirection in the mock UNIX shell.
 * 
 *
 */
public class OutputFile {

  /**
   * Runs the command with the specified arguments.
   * 
   * @param str A string that will be put into the to be created file.
   * @param appendOrWrite A boolean that determines if method is appending or writing to file.
   * @param path A String containing the path to create/append/write a file.
   * @param drive Driver to modify objects of the file system.
   * @return A string containing either an error, or empty string if file was successfully created.
   */
  public static String execute(String str, boolean appendOrWrite, String path,
      FileSystemDriver drive) {
    String name = getName(path);
    if (Validator.validateName(name) == 1) {
      return "ERROR: Invalid character in file name.";
    } else {
      if (outputFile(str, appendOrWrite, path, drive) != 0) {
        return "";
      } else {
        return "ERROR: File could not be created (invalid path or file"
            + " already exists as a directory)";
      }
    }
  }

  /**
   * Redirection, this method creates a file or appends/writes into a file if valid path is given.
   * If path exists, but file name does not currently exist, creates a file. If invalid path is
   * given, returns an error. If path exists, but is a directory and not a file, returns an error.
   * If path exists, and file exists, append/write inputted string into the file.
   * 
   * @param str A string that will be put into the to be created file.
   * @param appendOrWrite A boolean that determines if method is appending or writing to file.
   * @param path A String containing the path to create/append/write a file.
   * @param drive Driver to modify objects of the file system.
   * @return int - 0 if file was unable to be made, 1 if file was created/write/appended
   */
  private static int outputFile(String str, boolean appendOrWrite, String filePath,
      FileSystemDriver drive) {
    if (pathExists(filePath, drive) == 0) {
      return 0;
    }
    String path = fixPath(filePath, drive);
    String objPath = Validator.validateDirPath(path, drive);
    if (objPath != null) {
      String name = path.split("/")[path.split("/").length - 1];
      String objName =
          (objPath.equals("/")) ? "/" : objPath.split("/")[objPath.split("/").length - 1];
      if (objName.equals(name)) {
        CObject file = CObject.getObjectAtRelativePath(path, drive.getRoot());
        if (file != null) {
          if (file instanceof CDir) {
            return 0;
          }
          if (appendOrWrite) {
            stringWrite(str, (CFile) file);
            return 1;
          } else {
            stringAppend(str, (CFile) file);
            return 1;
          }
        }
      }
      String objFullName = (objPath.equals("/")) ? "/" : drive.getAbsolutePath(objPath);
      drive.createCFileAtPath(name, objFullName, str);
    }
    return 1;
  }

  /**
   * Writes (deletes previous content in file) into a file with the inputted string.
   * 
   * @param str String to be inputted.
   * @param file File to be overwritten.
   */
  private static void stringWrite(String str, CFile file) {
    file.setData(str);
  }

  /**
   * Appends (adds onto content in file with to a new line) into file with inputted String.
   * 
   * @param str String to be inputted.
   * @param file File to be edited.
   */
  private static void stringAppend(String str, CFile file) {
    String appendString = file.getData() + "\n" + str;
    // then overwrite file
    file.setData(appendString);
  }

  /**
   * 
   * @param filePath A string containing the path to be checked
   * @param drive Driver to modify objects of the file system.
   * @return An int, 0 - Invalid Path, 1 - Valid Path
   */
  private static int pathExists(String filePath, FileSystemDriver drive) {
    String pathName = filePath;
    String path = pathName;
    int exists;
    // if / is in name
    if (Validator.validateName(path) == 0) {
      // add to path
      path = "./" + path;
    }
    if (path.startsWith("./") | path.startsWith("../")) {
      path = drive.getAbsolutePath(path);
    }
    if (!path.startsWith("/")) {
      path = "./" + path;
      path = drive.getAbsolutePath(path);
    }
    // this validates if the path exists or does not exist
    if (Validator.validateDirPath(path, drive) == null) {
      exists = 0;
    } else {
      exists = 1;
    }
    return exists;
  }

  /**
   * A function that removes the path and returns the name of the file only.
   * 
   * @param name A String containing a path or a file name
   * @return A string containing the file name only
   */
  private static String getName(String name) {
    String fileName = name;
    // check if the inputted name contains /
    if (name.contains("/")) {
      // split / and grab only the string after the last /
      String[] splitName = name.split("/");
      int length = splitName.length;
      fileName = splitName[length - 1];
    }
    return fileName;
  }

  /**
   * Changes inputted path into an absolute path.
   * 
   * @param pathName Path
   * @param drive Driver containing files
   * @return String containing the absolute path
   */
  private static String fixPath(String pathName, FileSystemDriver drive) {
    String path = pathName;
    if (Validator.validateName(path) == 0) {
      path = "./" + path;
    }
    if (path.startsWith("./") | path.startsWith("../")) {
      path = drive.getAbsolutePath(path);
    }
    if (!path.startsWith("/")) {
      path = "./" + path;
      path = drive.getAbsolutePath(path);
    }
    return path;
  }

}
