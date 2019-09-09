package commands;

import fileSystem.FileSystemDriver;
import stackSystem.StackDriver;

/**
 * Represents the man command in the mock UNIX shell
 */
public class PrintDocumentation implements Command {
  /**
   * {@inheritDoc}
   * 
   * Represents the man command.
   * 
   */
  @Override
  public String execute(String[] cmd, FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    String manString = cmd[0] + "\n";
    String input = "";
    if (cmd.length > 2) {
      return "ERROR: Invalid Paramaters.";
    } else if (cmd.length > 1) {
      input = cmd[1];
      manString = manString + cmd[1] + "\n";
    } else {
      return "ERROR: Missing command.";
    }
    if (input.equals("man") || input.equals("echo") || input.equals("get")) {
      manString = manString + manualManEchoGet(input);
    } else if (input.equals("exit") || input.equals("mkdir") || input.equals("save")) {
      manString = manString + manualExitMkdirSave(input);
    } else if (input.equals("cd") || input.equals("ls") || input.equals("load")) {
      manString = manString + manualCdLsLoad(input);
    } else if (input.equals("pwd") || input.equals("pushd") || input.equals("find")) {
      manString = manString + manualPwdPushdFind(input);
    } else if (input.equals("popd") || input.equals("history") || input.equals("tree")) {
      manString = manString + manualPopdHistoryTree(input);
    } else if (input.equals("cat") || input.equals("mv") || input.equals("cp")) {
      manString = manString + manualCatMvCp(input);
    } else {
      return "ERROR: Inputted command is not in manual.";
    }
    return manString;
  }

  /**
   * Returns the manual for redirection.
   * 
   * @return A string containing redirection
   */
  private String manualOutfile() {
    return ("> OUTFILE, >> OUTFILE" + "\n" + "> OUTFILE: Replace file contents with STRING." + "\n"
        + ">> OUTFILE: Append STRING to file contents." + "\n"
        + "If command does not return a string, does not create OUTFILE." + "\n");
  }

  /**
   * Checks the input, then returns either the correct manual.
   * 
   * @param input A string containing either "man" or "echo" or "get"
   * @return A string containing the correct manual
   */
  private String manualManEchoGet(String input) {
    if (input.equals("man")) {
      return ("Print documentation for inputted command." + "\n" + manualOutfile());
    } else if (input.equals("echo")) {
      return ("Prints the inputted string." + "\n" + manualOutfile());
    } else {
      return ("Retrives file at given URL and adds it to the current working directory." + "\n"
          + manualOutfile());
    }
  }

  /**
   * Checks the input, then returns either the correct manual.
   * 
   * @param input A string containing either "exit" or "mkdir" or "save"
   * @return A string containing the correct manual
   */
  private String manualExitMkdirSave(String input) {
    if (input.equals("exit")) {
      return ("Quit the program." + "\n");
    } else if (input.equals("mkdir")) {
      return ("Creates a directory relative to current diectory or in a given path." + "\n"
          + manualOutfile());
    } else {
      return ("Saves the current state of the shell into an actual file in your computer."
          + " This file can then be later loaded with load." + "\n" + manualOutfile());
    }
  }

  /**
   * Checks the input, then returns either the correct manual.
   * 
   * @param input A string containing either "cd" or "ls" or "load"
   * @return A string containing the correct manual
   */
  private String manualCdLsLoad(String input) {
    if (input.equals("cd")) {
      return ("Change directory relative to current directory or full path." + "\n"
          + manualOutfile());
    } else if (input.equals("ls")) {
      return ("If no paths are given, print the contents of file or " + "current directory." + "\n"
          + "If a path is given, if" + " the path specifies a file, print the name of file" + "\n"
          + "If a path is given and it is a directory, "
          + "prints the name of the directory, and its contents" + "\n"
          + "If -R is present, recurively list all subdirectories." + "\n" + manualOutfile());
    } else {
      return ("Loads the state of the shell from a file on your computer."
          + " This command is disabled if it is not the first command inputted into shell." + "\n"
          + manualOutfile());
    }
  }

  /**
   * Checks the input, then returns either the correct manual.
   * 
   * @param input A string containing either "pwd" or "pushd" or "find"
   * @return A string containing the correct manual
   */
  private String manualPwdPushdFind(String input) {
    if (input.equals("pwd")) {
      return ("Print current working directory (whole path)." + "\n" + manualOutfile());
    } else if (input.equals("pushd")) {
      return ("Saves current working directory into a stack, then "
          + "change new current working directory to input." + "\n"
          + "If input directory does not exist, returns an error message." + "\n"
          + manualOutfile());
    } else {
      return ("find path ... -type [f|d] -name expression" + "\n"
          + "Find will find files (given by type, f = file, d = directory) "
          + "in the given path(s) to find ALL the files with the inputted name." + "\n"
          + manualOutfile());
    }
  }

  /**
   * Checks the input, then returns either the correct manual.
   * 
   * @param input A string containing either "popd" or "history" or "tree"
   * @return A string containing the correct manual
   */
  private String manualPopdHistoryTree(String input) {
    if (input.equals("popd")) {
      return ("Returns the topmost item in the pushd stack, then "
          + "changes the current directory to it." + "\n"
          + "If stack is empty, returns an error message." + "\n" + manualOutfile());
    } else if (input.equals("history")) {
      return ("Returns the most recent commands done from inputted number"
          + "(if given). Otherwise, return all commands in history." + "\n" + manualOutfile());
    } else {
      return ("Returns a tree diagram of the state of the shell, starting from root." + "\n"
          + manualOutfile());
    }
  }

  /**
   * Checks the input, then returns either the correct manual.
   * 
   * @param input A string containing either "cat" or "mv" or "cp"
   * @return A string containing the correct manual
   */
  private String manualCatMvCp(String input) {
    if (input.equals("cat")) {
      return ("Displays contents of given file. If more than one file is "
          + "provided, also display those file contents, " + "seperated by line breaks." + "\n"
          + manualOutfile());
    } else if (input.equals("mv")) {
      return ("mv OLDPATH NEWPATH" + "\n" + "Moves the file or directory given in OLDPATH"
          + "(including the files inside the directory itself) into NEWPATH. The file in "
          + "OLDPATH will no longer be there." + "\n" + manualOutfile());
    } else {
      return ("cp OLDPATH NEWPATH" + "\n" + "Copies the file or directory given in OLDPATH"
          + "(including the directory itself if given, and the files inside directory) "
          + "into NEWPATH. The file in OLDPATH will still exist." + "\n" + manualOutfile());
    }
  }
}
