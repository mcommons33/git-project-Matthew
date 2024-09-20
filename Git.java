import java.io.IOException;
import java.nio.file.*;
import java.io.*;

public class Git {
   public static void main(String[] args) {
      initalizeGitRepo();
   }

   public static void initalizeGitRepo() {

      // creates the folders git and objects
      File folder = new File("/git/objects");
      if (!folder.exists()) {
         folder.mkdirs();
      }

      File file = new File(folder, "index");
      String filePath = file.getAbsolutePath();
      // System.out.println(file.getAbsolutePath());

      if (!file.exists()) {
         try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      else
      {
         System.out.println("Git Repository already exists");
      }

   }

   public static String gitBlob() {
      BufferedWriter bw = new BufferedWriter();
   }
}
