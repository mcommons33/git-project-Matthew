import java.nio.file.*;
import java.security.MessageDigest;
import java.io.*;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;

public class Git {
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

   // hashes the content of a file using SHA1
   public static String SHA1Hashing(File file) {
      String content = fileContent(file); // getting the content
      try {
         MessageDigest crypt = MessageDigest.getInstance("SHA-1");
         crypt.update(content.getBytes("UTF-8"));
         BigInteger temp = new BigInteger(1, crypt.digest());
         return temp.toString(16);
      } catch (NoSuchAlgorithmException e) {
         e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }

      // this is here bc otherwise there is an error message. well can anyone actually
      // bypass the "try"?
      return "error";
   }

   // converts data in the file into a single string
   private static String fileContent(File file) {
      StringBuilder sb = new StringBuilder();
      try (BufferedReader bf = new BufferedReader(new FileReader(file));) {
         while (bf.ready()) {
            sb.append(bf.readLine());
            if (bf.ready()) {
               // if has next line, then add a "\n"
               sb.append("\n");
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
      return sb.toString();
   }

   public static void blobCreation(File file) {
      String name = SHA1Hashing(file); // getting the hashed name
      String content = fileContent(file); // getting the content

      File blobFile = new File("./git/objects", name); // the blob file
      // creates the blob file
      String path = blobFile.getPath();
      try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path))) {
         bufferedWriter.write(content);
      } catch (IOException e) {
         e.printStackTrace();
      }

      // adds a new line into the index file
      try {
         FileWriter fw = new FileWriter("./git/index", true);
         BufferedWriter bufferedWriter = new BufferedWriter(fw);
         // writes the content into index
         bufferedWriter.write(name + " " + file.getName() + "\n");
         bufferedWriter.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

}
