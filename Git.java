import java.security.MessageDigest;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;

public abstract class Git {
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
   public static String SHA1Hashing(File file) throws IOException {
      String content;
      if(file.isDirectory())
         content = fileContent(getDirFile(file));
      else
         content = fileContent(file); // getting the content
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
   // creating tree file for a directory
   private static File getDirFile(File file) throws IOException {
      try{
         File tempFile = File.createTempFile("tempFile", null);
         FileWriter fw = new FileWriter(tempFile);
         File [] fileList = file.listFiles();
         for (int i = 0; i < fileList.length; i++){
            String hash = SHA1Hashing(fileList[i]);
            if (i < fileList.length - 1){
               if (fileList[i].isDirectory())
                  fw.write("tree " + hash + " " + fileList[i].getName() + "\n"); // when calling sha1 hash dirFile gets reset to having content so when writing again it goes in the middle
               else
                  fw.write("blob " + hash + " " + fileList[i].getName() + "\n");
            }
            else{
               if (fileList[i].isDirectory())
                  fw.write("tree " + hash + " " + fileList[i].getName());
               else
                  fw.write("blob " + hash + " " + fileList[i].getName());
            }
               
         }
         fw.close();
         return tempFile;
      }
      catch (IOException e){
         e.printStackTrace();
         return File.createTempFile("tempFile", null);
      }
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

   public static void blobCreation(File file) throws IOException {
      if(!file.exists()){
         throw new FileNotFoundException();
      }
      String name;
      if (file.isDirectory()){
         File dirFile = getDirFile(file);
         name = SHA1Hashing(dirFile); //getting hashed name
         File blobFile = new File("./git/objects", name); // the blob file
         // creates the blob file
         try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(blobFile.getPath())); BufferedReader br = new BufferedReader(new FileReader (dirFile))) {
            while (br.ready())
               bufferedWriter.write(br.read());
            br.close();
            bufferedWriter.close();
         }
         catch (IOException e) {
            e.printStackTrace();
         }
         // creating blob files for all subfiles/subdirectories and their respective files/subdirectories etc.
         try{
            File [] files = file.listFiles();
            for (File f : files){
               blobCreation(f);
            }
         }
         catch (IOException e){
            e.printStackTrace();
         }
      }
      else{
         name = SHA1Hashing(file); // getting hashed name
         File blobFile = new File("./git/objects/" + name); // the blob file
         // creates the blob file
         try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(blobFile.getPath())); BufferedReader br = new BufferedReader(new FileReader (file))) {
            while (br.ready())
               bufferedWriter.write(br.read());
            bufferedWriter.close();
            br.close();
         }
         catch (IOException e) {
            e.printStackTrace();
         }
      }
      // adds a new line into the index file
      try {
         FileWriter fw = new FileWriter("./git/index", true);
         BufferedWriter bufferedWriter = new BufferedWriter(fw);
         // writes the content into index by first checking to see if the index file has been written into or not, (to determine if a /n is needed)
         // and then if the file is a directory or not, (to determine if tree or blob) and then if the file is in a subdir or if it is in the home dir
         // (to determine whether to write the parent dir or not)
         FileReader checks = new FileReader("./git/index");
         if (!checks.ready()) {
            if (file.isDirectory()){
               if(isInHomeDir(file))
                  bufferedWriter.write("tree " + name + " " + file.getName());
               else
                  bufferedWriter.write("tree " + name + " " + file.getParentFile().getName() + "/" + file.getName());
            }else{
               if (isInHomeDir(file))
                  bufferedWriter.write("blob " + name + " " + file.getName());
               else
                  bufferedWriter.write("blob " + name + " " + file.getParentFile().getName() + "/" + file.getName());
            }
         } else {
            if (file.isDirectory()){
               if (isInHomeDir(file))
                  bufferedWriter.write("\ntree " + name + " " + file.getName());
               else
                  bufferedWriter.write("\ntree " + name + " " + file.getParentFile().getName() + "/" + file.getName());
            } else {
               if(isInHomeDir(file))
                  bufferedWriter.write("\nblob " + name + " " + file.getName());
               else
                  bufferedWriter.write("\nblob " + name + " " + file.getParentFile().getName() + "/" + file.getName());
            }
         }

         // bufferedWriter.write(name + " " + file.getName() + "\n");
         bufferedWriter.close();
         checks.close();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }
   private static boolean isInHomeDir (File file){
      return file.getParentFile().getPath().equals(".");
   }
}
