package commands;

import fileSystem.*;
import objectStates.*;
import stackSystem.*;;

/**
 * Performs the required operations for the load command which includes loading the system state
 * from a file.
 * 
 *
 */
public class LoadState implements Command {
  private static boolean bool = true;
  private static String curr_args = null;

  /**
   * {@inheritDoc}
   * 
   * Represents the load command.
   * 
   * @see ObjectToFile
   * @see LocalFile
   */
  public String execute(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack) {
    UltimateDriver ob1 = null;
    if (args.length == 1) {
      return args[0] + "\n";
    }
    if (args.length != 2) {
      return "ERROR: invalid argumnents for " + args[0] + "\n";
    }
    if (bool == false) {
      return "ERROR: " + args[0] + " only works for new shell \n";
    }
    String correct1 = args[1].replaceAll("\\\\", "/").replaceAll("//", "/");
    ob1 = ObjectToFile.loadObject(correct1);
    if (ob1 != null) {
      LoadState.replace(args, system, history, stack, ob1);
      return args[0] + "\n" + args[1] + "\n";
    } else {
      return "ERROR: invalid argumnents for " + args[0] + "\n";
    }
  }

  /**
   * {@inheritDoc}
   * 
   * Method replaces the current state of the shell to the one loaded from a file.
   * 
   */
  private static void replace(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack, UltimateDriver ob1) {
    RecentCommands.setCommandList(ob1.getHistory());
    history.addRecentCommand(curr_args);
    stack.setDirStack(ob1.getStack().getDirStack());
    system.setCurrentDir(ob1.getDrive().getCurrentDir());
    system.setRoot(ob1.getDrive().getRoot());
  }

  /**
   * Gets the boolean that represents load executor.
   * 
   * @return Return boolean value representing whether load can be executed
   */
  public static boolean getBool() {
    return bool;
  }

  /**
   * Sets the boolean to represent the load executor.
   * 
   * @param bool Boolean parameter to set the variable representing whether load can be executed
   */
  public static void setBool(boolean bool) {
    LoadState.bool = bool;
  }

  /**
   * Sets the raw user input straight from shell into a string.
   * 
   * @param curr_args String representing the user input from Jshell for history
   * 
   */
  public static void setCurr_args(String curr_args) {
    LoadState.curr_args = curr_args;
  }

}
