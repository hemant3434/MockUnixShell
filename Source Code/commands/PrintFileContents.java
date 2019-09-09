package commands;

import fileSystem.*;
import stackSystem.StackDriver;

/**
 * This class is responsible for the cat command, which prints the contents of a given file to
 * stdout
 * 
 *
 */
public class PrintFileContents implements Command {
  /**
   * {@inheritDoc}
   * 
   * Represents the cat command
   */
  public String execute(String args[], FileSystemDriver drive, RecentCommands history,
      StackDriver stack) {
    String contents = "";
    String header = "cat\n";
    if (Validator.validateCat(args, drive) == 1) {
      for (int i = 1; i < args.length; i++) {
        String absPath = drive.getAbsolutePath(args[i]);
        String relPath;
        if (args[i].indexOf("/") == -1) {
          relPath = drive.getCurrentDir().getPath() + "/" + args[i];
          absPath = drive.getAbsolutePath(relPath);
        }
        header = header + args[i] + " ";
        if (Validator.validatePath(absPath, drive.getRoot()) == 1) {
          CObject object = CObject.getObjectAtRelativePath(absPath, drive.getRoot());
          contents = contents + ((CFile) object).getData() + "\n\n\n\n";
        } else {
          contents = contents + "ERROR:" + args[i] + " is not a valid path\n\n\n\n";
        }
      }
      contents = header + "\n" + contents;
      return contents.substring(0, contents.length() - 3);
    } else {
      return "ERROR: Input arguments are not valid";
    }
  }

}
