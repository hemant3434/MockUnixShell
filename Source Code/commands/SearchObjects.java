package commands;

import fileSystem.*;
import stackSystem.StackDriver;

/**
 * Performs required operations for the find command which involves searching the file system.
 * 
 */
public class SearchObjects implements Command {

  /**
   * {@inheritDoc}
   * 
   * Represents the find command.
   */
  @Override
  public String execute(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack) {
    int argl = args.length;
    if (argl < 6) {
      return "ERROR: find requires at least 5 args";
    }
    if (!args[argl - 4].equals("-type") || !args[argl - 2].equals("-name")) {
      return "ERROR: invalid options provided";
    }
    String type = args[argl - 3];
    String name = args[argl - 1];
    if (!(name.charAt(0) == '"') || !(name.charAt(name.length() - 1) == '"')
        || (!type.equals("f") && !type.equals("d"))) {
      return "ERROR: invalid type/name provided";
    }
    name = name.substring(1, name.length() - 1);
    String validArgs = "";
    String out = "";
    for (int i = 1; i < argl - 4; i++) {
      String path = args[i];
      CObject obj = CObject.getObjectAtRelativePath(system.getAbsolutePath(path), system.getRoot());
      if (!(obj instanceof CDir)) {
        out += "ERROR: path #" + i + " does not refer to a valid directory\n";
        continue;
      }
      validArgs += path + " ";
      out += path + " : \n" + findObjs((CDir) obj, name, type.charAt(0), "");
    }
    return validArgs.equals("") ? out : "find\n" + validArgs + "\n" + out;
  }

  private String findObjs(CDir root, String name, char type, String out) {
    for (CObject p : root.getChildren().values()) {
      if (name.equals(p.getName())
          && ((type == 'd' && p instanceof CDir) || (type == 'f' && p instanceof CFile))) {
        out += p.getPath() + "\n";
      }
      if (p instanceof CDir) {
        out = findObjs((CDir) p, name, type, out);
      }
    }
    return out;
  }
}
