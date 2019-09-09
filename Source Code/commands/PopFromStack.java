package commands;

import stackSystem.*;
import fileSystem.*;

/**
 * Performs the required operations for the popd command
 * 
 */
public class PopFromStack implements Command {

  /**
   * {@inheritDoc}
   * 
   * Runs the popd command with the required paramters and user inputs.
   * 
   * @see StackDriver
   * @see FileSystemDriver
   */
  public String execute(String[] args, FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    String output = "";
    if (args.length != 1) {
      return "ERROR: invalid argumnents for " + args[0] + "\n";
    }
    if (stack.getDirStack().getHead().isEmpty()) {
      return args[0] + "\nNo directories in stack \n";
    }
    output = args[0] + "\n";
    stack.pop(drive);
    return output;
  }
}
