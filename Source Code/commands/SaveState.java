package commands;

import objectStates.*;
import fileSystem.*;
import stackSystem.*;

/**
 * Performs required operations for the save command which includes saving the current system's onto
 * a file.
 * 
 * @author hemant bhanot
 */
public class SaveState implements Command {
  /**
   * {@inheritDoc}
   * 
   * Represents the save command.
   * 
   * @see ObjectToFile
   * @see LocalFile
   */
  public String execute(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack) {
    UltimateDriver ob1 = new UltimateDriver(system, history, stack);
    if (args.length == 1) {
      return args[0] + "\n";
    }
    if (args.length != 2) {
      return "ERROR: invalid argumnents for " + args[0] + "\n";
    }
    String correct1 = args[1].replaceAll("\\\\", "/").replaceAll("//", "/");
    LocalFile file = null;
    if(correct1.lastIndexOf("/")==-1) {
      file = new LocalFile(correct1);
    }
    else {
      String path = correct1.substring(0, correct1.lastIndexOf("/"));
      String name = correct1.substring(correct1.lastIndexOf("/") + 1);
      file = new LocalFile(path, name);
    }
    if (ObjectToFile.saveObject(ob1, file) == 0) {
      return args[0] + "\n" + args[1] + "\n";
    } else {
      return "ERROR: invalid argumnents for " + args[0] + "\n";
    }
  }
}
