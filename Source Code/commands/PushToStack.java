package commands;

import stackSystem.*;
import fileSystem.*;

/**
 * Performs the required operations for the pushd command
 * 
 * @author Hemant Bhanot
 */
public class PushToStack implements Command {

  /**
   * {@inheritDoc}
   * 
   * Runs the pushd command with the required parameters and user inputs.
   * 
   * @see StackDriver
   * @see FileSystemDriver
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
    stack.push(drive);
    CurrentDirectory.setNewDir(args[1], drive);
    return output;
  }

}
