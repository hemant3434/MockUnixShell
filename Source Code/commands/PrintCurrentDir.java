package commands;

import fileSystem.*;
import stackSystem.StackDriver;

public class PrintCurrentDir implements Command {

  /**
   * {@inheritDoc}
   * 
   * Represents the pwd command.
   */
  @Override
  public String execute(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack) {
    if (args.length != 1) {
      return "ERROR: pwd takes no arguments";
    }
    return "pwd\n\n" + system.getCurrentDir().getPath() + "\n";
  }
}
