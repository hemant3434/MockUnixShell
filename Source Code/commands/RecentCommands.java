package commands;

import java.util.ArrayList;
import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;
import java.io.*;

/**
 * Represents the history command in the mock UNIX shell
 */

public class RecentCommands implements Command, Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * An ArrayList of strings which will contain every input in the shell in order
   */
  private static ArrayList<String> commandList = new ArrayList<String>();

  /**
   * Returns the current full history of a user's shell
   * 
   * @return Return array list of all the commands in current jshell
   */
  public static ArrayList<String> getCommandList() {
    return commandList;
  }

  /**
   * Sets the current full history of a user's jshell
   * 
   * @param commandList Sets the arraylist of commands in jshell
   */
  public static void setCommandList(ArrayList<String> commandList) {
    RecentCommands.commandList = commandList;
  }

  /**
   * Adds a string which is the input from the shell into the commandList
   * 
   * @param command A string that is any input in the shell
   */
  public void addRecentCommand(String command) {
    commandList.add(command);
  }

  /**
   * Empties the history
   */
  public void emptyHistory() {
    commandList = new ArrayList<String>();
  }

  /**
   * {@inheritDoc}
   * 
   * Represents the history command.
   * 
   */
  @Override
  public String execute(String[] num, FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    String historyString = num[0];
    if (num.length > 1 && !num[1].contains(">")) {
      try {
        int historyNum = Integer.parseInt(num[1]);
        if (historyNum < 1) {
          return "ERROR: Invalid integer input.";
        }
      } catch (NumberFormatException e) {
        return "ERROR: Argument is not an integer.";
      }
    }
    if (num.length == 2) {
      historyString = historyString + "\n" + num[1] + "\n" + history(num) + "\n";
    } else if (num.length == 1) {
      historyString = historyString + "\n" + history(num) + "\n";
    } else {
      return "ERROR: Invalid arguments.";
    }
    return historyString;
  }

  /**
   * Returns the entire history stored in commandList.
   * 
   * @return Returns a string containing the history, seperated by line breaks
   */
  public String history() {
    // get length of commandList
    int length = commandList.size();
    int counter = 0;
    String historyString = "";
    // looping through every item in history
    while (counter < length) {
      // add every item to the string to be returned using a loop to iterate
      historyString = historyString + ((counter + 1) + ". " + commandList.get(counter) + "\n");
      counter = counter + 1;
    }
    // substring to remove the last line break
    return historyString.substring(0, historyString.length() - 1);
  }

  /**
   * Returns the most recent commands in the history up to the number inputted, if the number
   * exceeds the amount of commands in history, return the entire history.
   * 
   * @param num An array of strings containing the input
   * @return Returns a string containing the most recent history, or the full history.
   */
  public String history(String[] num) {
    int length = commandList.size();
    if (num.length > 1 && !num[1].contains(">")) {
      int counter = Integer.parseInt(num[1]);
      if (counter > length) {
        return history();
      }
      // get number of history
      // if the input is greater than the size of the history, return full history
      String historyString = "";
      while (counter != 0) {
        // return history only towards the most recent, which is length - counter + 1
        historyString = historyString
            + ((length - counter + 1) + ". " + commandList.get(length - counter) + "\n");
        // reduce counter as we are increasing length
        counter = counter - 1;
      }
      // substring to remove line break
      return historyString.substring(0, historyString.length() - 1);
    } else {
      return history();
    }
  }
}
