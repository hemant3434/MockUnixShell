package driver;

import java.util.Arrays;
import commands.OutputFile;
import fileSystem.FileSystemDriver;

/**
 * This class is responsible for managing the output that is sent from JShell, which is either
 * redirected to a file or sent to console
 * 
 * @author ivinable2000
 *
 */
public class OutputManager {
  /**
   * 
   * @param args represents the input arguments
   * @return an int; 1 if output is to be written to a file, 2 if output is to be appended to a file
   *         and 0 if it is to be printed to console
   */
  public static int outputChecker(String args[]) {
    if (args.length > 2) {
      if (args[args.length - 2].equals(">")) {
        return 1;
      } else if (args[args.length - 2].equals(">>")) {
        return 2;
      } else {
        return 0;
      }
    }
    return 0;
  }

  /**
   * 
   * @param args represents the input arguments
   * @return a string array that removes the ">" or ">>" and the file that follows it
   */
  public static String[] inputChecker(String args[]) {
    if (args.length > 2) {
      if (args[args.length - 2].equals(">") || args[args.length - 2].equals(">>")) {
        return Arrays.copyOfRange(args, 0, args.length - 2);
      }
    }
    return args;
  }

  /**
   * 
   * @param contents a String that is the output of the command
   * @param args represents the input arguments
   * @param drive represents the FileSystemDriver
   */
  public static void outputManager(String contents, String args[], FileSystemDriver drive) {
    int check = outputChecker(args);
    String consoleOutput, fileOutput, output;
    consoleOutput = fileOutput = output = "";
    if (check == 0) {
      System.out.println(contents);
    } else {
      String[] lines = arrayFixer(contents.split("\n"));
      for (String line : lines) {
        if (line.contains("ERROR:")) {
          consoleOutput = consoleOutput + line + "\n";
        } else {
          fileOutput = fileOutput + line + "\n";
        }
      }
      if (check == 1 && fileOutput != "") {
        output = OutputFile.execute(fileOutput.substring(0, fileOutput.length() - 1), true,
            args[args.length - 1], drive);
      } else if (check == 2 && fileOutput != "") {
        output = OutputFile.execute(fileOutput.substring(0, fileOutput.length() - 1), false,
            args[args.length - 1], drive);
      }
      if (output == "") {
        System.out.println(consoleOutput);
      } else {
        System.out.println(output);
      }
    }
  }

/**
 * 
 * @param lines represents an array that holds output
 * @return A string Array that removes the header if it exists
 */
  private static String[] arrayFixer(String[] lines) {
    if (lines.length >= 2) {
      return Arrays.copyOfRange(lines, 2, lines.length);
    } else {
      return Arrays.copyOfRange(lines, 0, lines.length);
    }
  }
}
