package commands;

import fileSystem.*;
import stackSystem.StackDriver;

/**
 * Performs required operations for the cd command
 * 
 * @author Hemant Bhanot
 */
public class CurrentDirectory implements Command {

  /**
   * {@inheritDoc}
   * 
   * Runs the cd command with the required parameters and user inputs.
   * 
   * @return String representing the output for the cd command.
   */
  public String execute(String[] args, FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    String output = "";
    if (args.length == 1) {
      return args[0] + "\n";
    }
    if (args.length != 2) {
      return "ERROR: invalid argumnents for " + args[0] + "\n";
    }
    int valid = Validator.validateCd(drive, args[1]);
    if (valid == 0) {
      return "ERROR: directory path is invalid\n";
    }
    output = args[0] + "\n" + args[1] + "\n";
    CurrentDirectory.setNewDir(args[1], drive);
    return output;
  }

  /**
   * Assumes that a valid path has been given and changes directory.
   * 
   * @param dir Path given by the user to cd into.
   * @param drive Driver to change directories in the file system.
   */
  public static void setNewDir(String dir, FileSystemDriver drive) {
    String[] pathArgs = null;
    if (dir.equals("/")) {
      drive.setCurrentDir(drive.getRoot());
      return;
    }
    if (dir.substring(0, 1).equals("/")) {
      drive.setCurrentDir(drive.getRoot());
      dir = dir.substring(1);
    }
    pathArgs = dir.split("/");
    int len = pathArgs.length;
    for (int i = 0; i < len; i++) {
      if (pathArgs[i].equals(".")) {
        drive.setCurrentDir(drive.getCurrentDir());
      } else if (pathArgs[i].equals("..")) {
        drive.setCurrentDir(drive.getCurrentDir().getParent());
      } else {
        drive.setCurrentDir((CDir) drive.getCurrentDir().getChild(pathArgs[i]));
      }
    }
  }
}
