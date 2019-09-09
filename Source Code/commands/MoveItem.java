package commands;

import fileSystem.*;
import stackSystem.StackDriver;

/**
 * Represents the mv command in the mock UNIX shell.
 * 
 *
 */
public class MoveItem implements Command {

  /**
   * {@inheritDoc}
   * 
   * Represents the mv command.
   * 
   * @see CDir
   * @see CFile
   * @see FileSystemDriver
   */
  @Override
  public String execute(String[] args, FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    if (args.length < 3) {
      return "ERROR: Missing arguments.";
    } else if (args.length > 3) {
      return "ERROR: Invalid arguments.";
    }
    String newPath = args[args.length - 1];
    if (Validator.validateName(getName(newPath)) == 1) {
      return "ERROR: Invalid character in file name.";
    }
    if (args[args.length - 1].equals(args[args.length - 2])) {
      return args[0] + "\n" + args[1] + " " + args[2];
    }
    int successOrFail = moveItemToLocation(args, drive);
    if (successOrFail == 1) {
      return args[0] + "\n" + args[1] + " " + args[2];
    } else if (successOrFail == 2) {
      return "ERROR: Cannot move file/directory inside itself.";
    } else {
      return "ERROR: Invalid path.";
    }
  }

  /**
   * Moves file/directory from oldpath into newpath (Moving means oldpath will no longer exist). If
   * oldpath or newpath is invalid, throws an error. Returns an integer depending on which error
   * occured, or if the files were copied successfully.
   * 
   * @param args Arguments given by the user for the command.
   * @param drive Driver to modify objects of the file system.
   * @return int 0 - Invalid path, 1 - Success, 2 - moving parent directory into child
   */
  private int moveItemToLocation(String[] args, FileSystemDriver drive) {
    String oldPath = args[args.length - 2];
    String newPath = args[args.length - 1];
    String fixOldPath = fixPath(oldPath, drive);
    String fixNewPath = fixPath(newPath, drive);
    String[] oldPathValidate = fixOldPath.split("/");
    String[] newPathValidate = fixNewPath.split("/");
    if (isParent(oldPathValidate, newPathValidate) == 0){
      return 2;
    }
    if (Validator.validateDirPath(fixNewPath, drive) == null) {
      return 0;
    }
    String validateNewPath = fixPath(Validator.validateDirPath(fixNewPath, drive), drive);
    if (Validator.validatePath(fixOldPath, drive.getRoot()) == 0
        || (Validator.validatePath(validateNewPath, drive.getRoot()) == 0)) {
      return 0;
    }
    String newFilePath = fixPath(newPath, drive) + "/" + getName(oldPath);
    String objPath = Validator.validateDirPath(newFilePath, drive);
    objPath = Validator.validateDirPath(fixPath(newPath, drive), drive);
    String objFullName = (objPath.equals("/")) ? "/" : fixPath(objPath, drive);
    if (Validator.validatePath(fixNewPath, drive.getRoot()) == 0) {
      renameItem(fixOldPath, newPath, objFullName, drive);
      return 1;
    }
    drive.moveCObject(fixOldPath, objFullName);
    return 1;
  }

  /**
   * Moves Item and renames them in the case that newpath is valid but item name does not exist in
   * newpath.
   * 
   * @param fixOldPath A String containing the oldpath (abosolute)
   * @param newPath A string containing the newpath (for the name of the item)
   * @param objFullName A string containing the location to be moved to
   * @param drive Driver to modify objects of the file system.
   */
  private void renameItem(String fixOldPath, String newPath, String objFullName,
      FileSystemDriver drive) {
    String oldObjPath = Validator.validateDirPath(fixOldPath, drive);
    CObject file = CObject.getObjectAtRelativePath(oldObjPath, drive.getRoot());
    file.setName(getName(newPath));
    drive.moveCObject(drive.getAbsolutePath(getName(newPath)), objFullName);
  }

  /**
   * A function that removes the path and returns the name of the file only.
   * 
   * @param name A String containing a path or a file name
   * @return A string containing the file name only
   */
  private String getName(String name) {
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
  private String fixPath(String pathName, FileSystemDriver drive) {
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

  /**
   * Returns whether or not the input is trying to insert a parent file into its child.
   * 
   * @param oldPath Array of string containing the path split by /
   * @param newPath Array of string containing the path split by /
   * @return int 0 if check failed, 1 if check passed
   */
  private int isParent(String[] oldPath, String[] newPath) {
    int check = 0;
    for(int i = 0; i < oldPath.length; i++) {
      if(i > newPath.length - 1) {
        break;
      }
      if(!oldPath[i].equals(newPath[i])) {
        check = 1;
      }
    }
    return check;
  }
}
