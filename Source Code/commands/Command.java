package commands;

import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

/**
 * Represents a class for any command, with the base method to run the command.
 * 
 */
public interface Command {

  /**
   * Runs the command with the specified arguments.
   * 
   * @param args Arguments given by the user for the command.
   * @param system Driver to modify objects of the file system.
   * @param history The command history class for the system.
   * @param stack The stack class for the file system.
   * @return String output of the command.
   */
  public String execute(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack);
}
