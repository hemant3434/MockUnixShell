package commands;

import fileSystem.*;
import stackSystem.*;

/**
 * Performs required operations for the get command which involves getting data from URLs.
 * 
 */
public class DataFromUrl implements Command {
  private UrlDataFetcher df;
  
  public DataFromUrl() {
    df = new RealUrlDataFetcher();
  }
  
  public DataFromUrl(String str) {
    df = new test.MockUrlDataFetcher();
  }
  
  /**
   * {@inheritDoc}
   * 
   * Represents the get command.
   */
  @Override
  public String execute(String[] args, FileSystemDriver system, RecentCommands history,
      StackDriver stack) {
    if (args.length != 2) {
      return "ERROR: get requires 1 argument";
    }
    String url = args[1];
    String data = this.df.getUrlData(url);
    if (data.equals("")) {
      return "ERROR: invalid URL to get";
    }
    String name = url.substring(1 + url.lastIndexOf('/', url.length() - 2));
    name = fixName(name);
    if (Validator.validateName(name) == 1) {
      return "ERROR: URL has invalid file";
    }
    system.createCFileAtPath(name, system.getCurrentDir().getPath(), data);
    return "get\n" + url + "\n";
  }

  private String fixName(String name) {
    if (name.endsWith("/")) {
      name = name.substring(0, name.length() - 1);
    }
    int i = -1;
    if ((i = name.indexOf(".")) != -1) {
      name = name.substring(0, i);
    }
    return name;
  }
}

