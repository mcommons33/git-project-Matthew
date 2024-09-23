import java.nio.file.*;
import java.security.MessageDigest;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class Git {
   public static void main(String[] args) {
      try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("testingFile"))) {
         bufferedWriter.write("look behind you");
      } catch (IOException e) {
         e.printStackTrace();
      }
      File gitFolder = new File("./testingFile");
      System.out.println(gitBlob(gitFolder));
   }

   // initalizes a Git Repo with an "objects" folder and "index" file
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

   public static String gitBlob(File file) {
      
      // converts data in the file into a long string
      StringBuilder sb = new StringBuilder();
      try (BufferedReader bf = new BufferedReader(new FileReader(file));) {
         while (bf.ready()) {
            sb.append(bf.readLine());
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      String input = sb.toString();
      //////

      // hashes the string
      try {
         MessageDigest crypt = MessageDigest.getInstance("SHA-1");
         crypt.update(input.getBytes("UTF-8"));
         BigInteger temp = new BigInteger(1, crypt.digest());
         return temp.toString(16);
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }


      // this is here bc otherwise there is an error message. well can anyone actually bypass the "try"?
      return "error"; 
   }

}
