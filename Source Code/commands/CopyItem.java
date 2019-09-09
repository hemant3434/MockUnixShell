package commands;

import java.util.Hashtable;
import fileSystem.*;
import stackSystem.StackDriver;

/**
 * Represents the cp command in the mock UNIX shell.
 * 
 *
 */
public class CopyItem implements Command {

  /**
   * {@inheritDoc}
   * 
   * Represents the cp command.
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
      return args[0] + "\n" + args[1] + " " + args[2] + "\n";
    }
    int successOrFail = copyItemToLocation(args, drive);
    if (successOrFail == 1) {
      return args[0] + "\n" + args[1] + " " + args[2] + "\n";
    } else if (successOrFail == 2) {
      return "ERROR: Cannot copy file/directory inside itself.";
    } else {
      return "ERROR: Invalid path.";
    }
  }

  /**
   * Copies files/directory from oldpath into newpath. If oldpath or newpath is invalid, throws an
   * error. Returns an integer depending on which error occured, or if the files were copied
   * successfully.
   * 
   * @param args Arguments given by the user for the command.
   * @param drive Driver to modify objects of the file system.
   * @return An integer, 0 - invalid path, 1 - success, 2 - moving parent directory into child
   */
  private int copyItemToLocation(String[] args, FileSystemDriver drive) {
    String oldPath = args[args.length - 2];
    String newPath = args[args.length - 1];
    String fileName = getName(oldPath);
    String fixOldPath = fixPath(oldPath, drive);
    String fixNewPath = fixPath(newPath, drive);
    String[] oldPathValidate = fixOldPath.split("/");
    String[] newPathValidate = fixNewPath.split("/");
    if (isParent(oldPathValidate, newPathValidate) == 0) {
      return 2;
    }
    String validateNewPath = Validator.validateDirPath(fixNewPath, drive);
    if (validateNewPath == null || Validator.validatePath(fixOldPath, drive.getRoot()) == 0
        || (Validator.validatePath(validateNewPath, drive.getRoot()) == 0)) {
      return 0;
    }
    String oldObjPath = Validator.validateDirPath(fixOldPath, drive);
    String newObjPath = Validator.validateDirPath(fixNewPath, drive);
    String objFullName = (newObjPath.equals("/")) ? "/" : fixPath(newObjPath, drive);
    CObject file = CObject.getObjectAtRelativePath(oldObjPath, drive.getRoot());
    if (Validator.validatePath(fixNewPath, drive.getRoot()) == 0) {
      copyAndRenameItem(fixOldPath, fixNewPath, objFullName, file, drive);
      return 1;
    }
    copyAndMoveItem(file, fixOldPath, objFullName, fileName, drive);
    return 1;
  }

  /**
   * Copies a file and renames in the case that newPath is valid but file name does not exist in
   * newpath.
   * 
   * @param oldFilePath Name gotten from oldFilePath to be used
   * @param newPath Name gotten from newPath to be used
   * @param objFullName Location to be moved
   * @param file File to be moved
   * @param drive Driver to modify objects of the file system.
   */
  private void copyAndRenameItem(String oldFilePath, String newPath, String objFullName,
      CObject file, FileSystemDriver drive) {
    if (file instanceof CDir) {
      CDir copyDir = (CDir) file;
      String location = file.getParent().getPath();
      file.setName(getName(newPath));
      drive.moveCObject(fixPath(getName(newPath), drive), objFullName);
      CDir newCDir = drive.createCDirAtPath(getName(oldFilePath), location);
      populateCDir(copyDir, newCDir, drive);
    } else {
      CFile copyFile = (CFile) file;
      file.setName(getName(newPath));
      drive.moveCObject(fixPath(getName(newPath), drive), objFullName);
      drive.createCFileAtPath(newPath, oldFilePath, copyFile.getData());
    }
  }

  /**
   * Moves item(s) to newpath, then recreates the item(e) that was in oldpath.
   * 
   * @param file Object to be checked if file or directory
   * @param oldFilePath OldPath to get the file to be moved
   * @param objFullName Location where to move file
   * @param fileName Name of the file
   * @param drive Driver to modify objects of the file system.
   */
  private void copyAndMoveItem(CObject file, String oldFilePath, String objFullName,
      String fileName, FileSystemDriver drive) {
    if (file instanceof CDir) {
      CDir copyDir = (CDir) file;
      String location = file.getParent().getPath();
      drive.moveCObject(oldFilePath, objFullName);
      CDir newCDir = drive.createCDirAtPath(fileName, location);
      populateCDir(copyDir, newCDir, drive);
    } else {
      CFile copyFile = (CFile) file;
      drive.moveCObject(oldFilePath, objFullName);
      String newOldFilePath = Validator.validateDirPath(oldFilePath, drive);
      drive.createCFileAtPath(fileName, newOldFilePath, copyFile.getData());
    }
  }

  /**
   * Recursively populates the newly created CDir with its contents to make a copy of the moved
   * CDir.
   * 
   * @param copyDir A copy of the directory to get keys from
   * @param newDir The new directory that was moved to new location to be copied from
   * @param drive Driver to modify objects of the file system.
   */
  private void populateCDir(CDir copyDir, CDir newDir, FileSystemDriver drive) {
    Hashtable<String, CObject> children = copyDir.getChildren();
    if (!children.isEmpty()) {
      for (String key : children.keySet()) {
        CObject copyObject = copyDir.getChild(key);
        if (copyObject instanceof CDir) {
          drive.createCDirAtPath(key, newDir.getPath());
          CDir newNextCDir = (CDir) newDir.getChild(key);
          CDir nextCDir = (CDir) copyDir.getChild(key);
          populateCDir(nextCDir, newNextCDir, drive);
        } else {
          CFile copyFile = (CFile) copyDir.getChild(key);
          drive.createCFileAtPath(key, newDir.getPath(), copyFile.getData());
        }
      }
    }
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
    for (int i = 0; i < oldPath.length; i++) {
      if (i > newPath.length - 1) {
        break;
      }
      if (!oldPath[i].equals(newPath[i])) {
        check = 1;
      }
    }
    return check;
  }
}
