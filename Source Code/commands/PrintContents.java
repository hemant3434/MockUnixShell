package commands;

import fileSystem.CDir;
import fileSystem.CObject;
import fileSystem.FileSystemDriver;
import fileSystem.Validator;
import stackSystem.StackDriver;
import java.util.Hashtable;
import java.util.Set;
import java.util.LinkedList;

/**
 * This class is responsible for the ls command, that prints all the directories and files found at
 * a given location in the file system driver
 * 
 *
 */

public class PrintContents implements Command {

  private LinkedList<CDir> list = new LinkedList<CDir>();

  /**
   * {@inheritDoc}
   * 
   * Represents the cat command
   */
  public String execute(String args[], FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    if ((Validator.validateLs(args, drive)) == 0) {
      return "ERROR: input arguments are invalid";
    }
    if (args.length > 1 && args[1].equals("-R")) {
      return printContentsRecursive(args, drive);
    } else {
      return printContents(args, drive);
    }
  }

  /**
   * 
   * @param path Represents a path in filesystem
   * @param drive FileSystemDriver
   * @return String that represents absolute path
   */
  private static String getPath(String path, FileSystemDriver drive) {
    if (path.indexOf("/") == -1) {
      String relPath = drive.getCurrentDir().getPath() + "/" + path;
      return drive.getAbsolutePath(relPath);
    } else {
      return drive.getAbsolutePath(path);
    }
  }

  /**
   * The recursive implementation for ls
   * 
   * @param args represents the input arguments
   * @param drive FileSystemDriver
   * @return String that represents output
   */
  private String printContentsRecursive(String args[], FileSystemDriver drive) {
    String contents = "";
    String header = "ls\n";
    if (args.length > 2) {
      for (int i = 2; i < args.length; i++) {
        header = header + args[i] + " ";
        String absPath = PrintContents.getPath(args[i], drive);
        if (Validator.validatePath(absPath, drive.getRoot()) == 1) {
          contents = contents + args[i] + "\n";
        } else if (Validator.validatePath(absPath, drive.getRoot()) == 0) {
          contents = contents + "ERROR: " + args[i] + " is not a valid path\n";
        } else {
          this.list.add((CDir) CObject.getObjectAtRelativePath(absPath, drive.getRoot()));
        }
      }
      header = header + "\n";
    } else {
      this.list.add(drive.getCurrentDir());
    }
    while (!(list.isEmpty())) {
      CDir head = list.poll();
      contents = contents + head.getPath() + ":" + printDirectoryContents(head, drive, 1);
    }
    list.clear();
    return header + contents;
  }

  /**
   * 
   * @param args represents the input arguments
   * @param drive FileSystemDriver
   * @return String that represents output
   */
  private String printContents(String args[], FileSystemDriver drive) {
    String contents = "";
    String header = "ls";
    if (args.length < 2) {
      contents = (PrintContents.printCurrentDirectoryContents(drive.getCurrentDir()));
    } else {
      header = header + "\n";
      for (int i = 1; i < args.length; i++) {
        String absPath = PrintContents.getPath(args[i], drive);
        if (Validator.validatePath(PrintContents.getPath(args[i], drive), drive.getRoot()) == 2) {
          CObject object = CObject.getObjectAtRelativePath(absPath, drive.getRoot());
          contents = contents + args[i] + ":" + printDirectoryContents((CDir) object, drive, 0);
        } else if (Validator.validatePath(absPath, drive.getRoot()) == 1) {
          contents = contents + args[i] + "\n";
        } else {
          contents = contents + "ERROR: " + args[i] + " is not a valid path\n";
        }
        header = header + args[i] + " ";
      }
    }
    return header + "\n" + contents;
  }


  /**
   * This method is responsible for executing ls for a given directory. It prints all the files and
   * directories found in the given directory.
   * 
   * @param dir :This is the CDir that is specified
   * @return the String that contains the contents of the directory
   */
  private String printDirectoryContents(CDir dir, FileSystemDriver drive, int check) {

    int rootCheck = 0;
    CObject itemDir;
    if (dir.getPath() == "/") {
      rootCheck = 1;
    }
    String line = "";
    Hashtable<String, CObject> dict = dir.getChildren();
    Set<String> iterator = dict.keySet();
    for (String item : iterator) {
      line = line + " " + item;
      if (rootCheck == 1) {
        itemDir = CObject.getObjectAtRelativePath("/" + item, drive.getRoot());
      } else {
        itemDir = CObject.getObjectAtRelativePath(dir.getPath() + "/" + item, drive.getRoot());
      }
      if (check == 1 && Validator.validatePath(itemDir.getPath(), drive.getRoot()) == 2) {
        this.list.add((CDir) itemDir);
      }
    }
    line = line + "\n";
    return line;
  }


  /**
   * This method is responsible for executing ls when no arguments are given. It prints all the
   * files and directories found in the current working directory.
   * 
   * @param cwd :This is the current working directory
   * @return the String that contains the contents of the current working directory
   */
  private static String printCurrentDirectoryContents(CDir cwd) {

    String line = "";
    Hashtable<String, CObject> dict = cwd.getChildren();
    Set<String> iterator = dict.keySet();
    for (String item : iterator) {
      line = line + item + "\n";
    }
    return line;
  }

}
