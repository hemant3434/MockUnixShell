package commands;

import fileSystem.*;
import stackSystem.StackDriver;

/**
 * Performs required operations for the tree command which involves traversing the file system.
 * 
 */
public class DisplayFileSystem implements Command {

  /**
   * {@inheritDoc}
   * 
   * Represents the tree command.
   */
  @Override
  public String execute(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack) {
    if (args.length != 1) {
      return "ERROR: tree takes no args";
    }
    CDir root = system.getRoot();
    String str = appendSubObjs(root, "", root.getName() + "\n");
    return "tree\n\n" + str;
  }

  private String appendSubObjs(CDir dir, String tabs, String str) {
    for (CObject obj : dir.getChildren().values()) {
      str += tabs + obj.getName() + "\n";
      if (obj instanceof CDir) {
        str = appendSubObjs((CDir) obj, tabs + "\t", str);
      }
    }
    return str;
  }

}
