package objectStates;

import java.io.*;
import commands.*;

/**
 * Performs the file operations for save and load command.
 * 
 * @author hemant bhanot
 *
 */
public class ObjectToFile {
  /**
   * Method that performs operations required for save command.
   * 
   * @see SaveState
   * @see UltimatDriver
   * @see LocalFile
   * @param ultimate Driver containing all the states of a shell
   * @param file Represents a file on local computer
   * @return 0 if saving works or 1 if saving does not the work
   */
  public static int saveObject(UltimateDriver ultimate, LocalFile file) {
    try {
      FileOutputStream file1 = new FileOutputStream(file.getFile());
      ObjectOutputStream out = new ObjectOutputStream(file1);
      out.writeObject(ultimate);
      if (file.checkCreate() == 1) {
        return 1;
      }
      out.close();
      file1.close();

    } catch (Exception e) {
      return 1;
    }
    return 0;
  }

  /**
   * Method that performs operations required for load command.
   * 
   * @see UltimateDriver
   * @param file String representing the file user wants to load from
   * @return null if load failed due to incorrect file or actual state of shell loaded
   */
  public static UltimateDriver loadObject(String file) {
    UltimateDriver ob1 = null;
    try {
      FileInputStream file1 = new FileInputStream(file);
      ObjectInputStream in = new ObjectInputStream(file1);

      ob1 = (UltimateDriver) in.readObject();
      in.close();
      file1.close();

    } catch (Exception e) {
      return null;
    }
    return ob1;
  }
}
