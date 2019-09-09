package commands;

import fileSystem.*;
import stackSystem.StackDriver;

/**
 * Represents the echo command in the mock UNIX shell
 */
public class StringOutput implements Command {

  /**
   * {@inheritDoc}
   * 
   * Represents the echo command.
   * 
   * @see CDir
   * @see CFile
   * @see FileSystemDriver
   */
  @Override
  public String execute(String[] args, FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    if (args.length < 2) {
      return "ERROR: No arguments provided.";
    }
    String commandString = args[0];
    int end = endString(args);
    String tempString = "history 1";
    String[] historyString = tempString.split(" ");
    String str = fixArgs(history.execute(historyString, drive, history, stack));
    String noQuoteStr = "";
    if (str.contains("\"")) {
      noQuoteStr = str.substring(1, str.length() - 1);
    } else {
      return "ERROR: Inputted string is invalid.";
    }
    if (end != 0 && validString(args)) {
      commandString = commandString + "\n" + str + "\n" + echo(noQuoteStr) + "\n";
    } else {
      return "ERROR: Inputted string is invalid.";
    }
    if (args.length > end) {
      return "ERROR: Invalid arguments provided.";
    }
    return (commandString);
  }

  /**
   * A function that connects multiple strings in an array together into a single string.
   * 
   * @param str A string containing the input
   * @return A string that contains the full sentence/phrase
   */
  private String fixArgs(String str) {
    String temp = "";
    int count = str.length() - str.replace("\"", "").length();
    int previousIndex = str.indexOf("\"");
    int endIndex = str.indexOf("\"", previousIndex + 1);
    int counter = 2;
    while (counter < count) {
      endIndex = str.indexOf("\"", endIndex + 1);
      counter = counter + 1;
    }
    if (endIndex != -1) {
      temp = str.substring(previousIndex, endIndex + 1);
    }
    return temp;
  }

  /**
   * Returns the string inputted without the quotation marks
   * 
   * @param str A string containing the string to be outputted
   * @return The same string inputted without the quotation marks
   */
  public String echo(String str) {
    // remove quotation marks
    String echoString = str.replace("\\" + "\"", "\"");
    return (echoString);
  }

  /**
   * Returns the position of the last string in an array that ends with a quotation mark. If there
   * are double quotation marks the input is invalid and will return a 0.
   * 
   * @param args An array of strings that contains the input with its arguments
   * @return An int that contains the last position of the string with quotations
   */
  private int endString(String[] args) {
    int size = args.length;
    String startsWith = args[1];
    int intEnd;
    if (startsWith.startsWith("\"")) {
      boolean end = false;
      int counter = 1;
      if (startsWith.equals("\"")) {
        counter = counter + 1;
      }
      while (end == false && counter < size) {
        String argString = args[counter].replace("\\" + "\"", "");
        if (argString.endsWith("\"")) {
          end = true;
        }
        counter = counter + 1;
      }
      if (end == false) {
        intEnd = 0;
      } else {
        // this counter determines the position in the array where string ends
        intEnd = counter;
      }
    } else {
      intEnd = 0;
    }
    return intEnd;
  }

  /**
   * A function that checks the amount of quotation marks in input. If there is more than 2
   * quotation marks, or less than two quotation marks, the input is invalid.
   * 
   * @param args An array of string containing the full input
   * @return a boolean determining if the input is valid
   */
  private boolean validString(String[] args) {
    String fullString = "";
    int counter = 0;
    // put everything in the array into one string
    while (counter < args.length) {
      fullString = fullString + args[counter];
      counter = counter + 1;
    }
    fullString = fullString.replace("\\" + "\"", "");
    // this counts the amount of quotation marks inside the full string
    int count = fullString.length() - fullString.replace("\"", "").length();
    // if there are more than two quotation marks, or less than 2, the string is invalid
    if (count > 2) {
      return false;
    } else if (count < 2) {
      return false;
    } else {
      return true;
    }
  }
}
