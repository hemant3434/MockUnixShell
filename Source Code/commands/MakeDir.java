package commands;

import java.util.ArrayList;
import java.util.Arrays;
import fileSystem.*;
import stackSystem.StackDriver;

/**
 * Performs required operations for the mkdir command which involves creating directories at
 * specified paths.
 * 
 */
public class MakeDir implements Command {

  /**
   * {@inheritDoc}
   * 
   * Represents the mkdir command.
   * 
   * @see CDir
   * @see FileSystemDriver
   */
  @Override
  public String execute(String[] argsf, FileSystemDriver system, RecentCommands history,
      StackDriver stack) {
    if (argsf.length == 1) {
      return "ERROR: mkdir requires at least one argument";
    }
    ArrayList<String> args = new ArrayList<String>(Arrays.asList(argsf));
    args.remove(0);
    String validArgs = "";
    String errors = "";
    for (int i = 0; i < args.size(); i++) {
      String path = args.get(i);
      if (Validator.validateName(path) == 0) {
        path = "./" + path;
      }
      path = system.getAbsolutePath(path);
      if (!path.startsWith("/")) {
        path = "./" + path;
        path = system.getAbsolutePath(path);
      }
      String objPath = Validator.validateDirPath(path, system);
      if (objPath != null) {
        String pathName = path.split("/")[path.split("/").length - 1];
        if (Validator.validateName(pathName) == 0 && !(objPath.equals(path))) {
          system.createCDirAtPath(pathName, objPath);
          validArgs += args.get(i) + " ";
          continue;
        }
      }
      errors += "ERROR: argument #" + (i + 1) + " is invalid for mkdir\n";
    }
    return (validArgs == "") ? errors : "mkdir\n" + validArgs + "\n" + errors;
  }


}
