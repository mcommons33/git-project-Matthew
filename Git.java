import java.io.IOException;
import java.nio.file.*;
import java.security.MessageDigest;
import java.io.*;

public class Git {
   public static void main(String[] args) {
      initalizeGitRepo();
   }

   public static void initalizeGitRepo() {

      File gitFolder = new File("./git"); // git folder
      File objectsFolder = new File("./git/objects"); // objects folder
      File file = new File("./git", "index"); // index file

      if (objectsFolder.exists() && file.exists()) {
         System.out.println("Git Repository already exists");
      }

      // creates the git folder
      if (!gitFolder.exists()) {
         gitFolder.mkdirs();
      }

      // creates the objects folder
      if (!objectsFolder.exists()) {
         objectsFolder.mkdirs();
      }

      // creates the file index
      String filePath = file.getPath();
      try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
         bufferedWriter.write("");
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   // public static String gitBlob(File file) {

   // }
}
