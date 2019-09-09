package driver;


import java.util.Hashtable;
import java.util.Scanner;
import commands.*;
import fileSystem.*;
import stackSystem.*;
import objectStates.*;

/**
 * Responsible for user interaction with the shell
 * 
 *
 */
public class JShell {
  /**
   * Executes the shell commands based on user input
   * 
   * @param cmd String representing the command name given by user
   * @param args String array representing the command arguments
   * @param drive Driver which represents the file system
   * @param history Driver which keeps track of user inputs aka the history
   * @param stack Driver which controls the virtual stack of directories
   * @return String representing output for a given command
   * 
   * @see CurrentDirectory
   * @see MakeDir
   * @see PopFromStack
   * @see PrintContents
   * @see PrintCurrentDir
   * @see PrintDocumentation
   * @see PrintFileContents
   * @see PushToStack
   * @see RecentCommands
   * @see StringOutput
   */
  public static String executeCommand(String cmd, String[] args, FileSystemDriver drive,
      RecentCommands history, StackDriver stack) {
    String output = "";
    Hashtable<String, String> hash = new Hashtable<String, String>();
    fillHashtable(hash);
    try {
      Class<?> cmdClass = Class.forName(hash.get(cmd));
      try {
        Command cmdObj = (Command) cmdClass.newInstance();
        output = cmdObj.execute(args, drive, history, stack);;
      } catch (Exception e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      output = "ERROR: command does not exist";
    }
    LoadState.setBool(false);
    return output;
  }

  public static void fillHashtable(Hashtable<String, String> hash) {
    hash.put("mkdir", "commands.MakeDir");
    hash.put("cd", "commands.CurrentDirectory");
    hash.put("ls", "commands.PrintContents");
    hash.put("pwd", "commands.PrintCurrentDir");
    hash.put("pushd", "commands.PushToStack");
    hash.put("popd", "commands.PopFromStack");
    hash.put("history", "commands.RecentCommands");
    hash.put("cat", "commands.PrintFileContents");
    hash.put("man", "commands.PrintDocumentation");
    hash.put("echo", "commands.StringOutput");
    hash.put("cp", "commands.CopyItem");
    hash.put("mv", "commands.MoveItem");
    hash.put("get", "commands.DataFromUrl");
    hash.put("find", "commands.SearchObjects");
    hash.put("tree", "commands.DisplayFileSystem");
    hash.put("save", "commands.SaveState");
    hash.put("load", "commands.LoadState");

  }

  /**
   * Responsible for interpreting user input and interaction with the shell
   * 
   * @param args
   */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    // Creating an instance of the file system
    FileSystemDriver drive = new FileSystemDriver();
    RecentCommands history = new RecentCommands();
    StackDriver stack = new StackDriver();

    while (true) {
      // printing out the current directory, getting user input, parsing it
      System.out.printf(drive.getCurrentDir().getPath() + "# ");
      String input = in.nextLine();
      String[] inputArgs = input.split("\\s+");
      String cmd = inputArgs[0];
      history.addRecentCommand(input);
      LoadState.setCurr_args(input);
      String output = "";
      if (cmd.equals("exit")) {
        if (inputArgs.length != 1) {
          System.out.println("ERROR: exit takes no arguments.");
          continue;
        }
        break;
      } else {
        output = executeCommand(cmd, OutputManager.inputChecker(inputArgs), drive, history, stack);
      }
      OutputManager.outputManager(output, inputArgs, drive);
    }
    in.close();
  }
}
