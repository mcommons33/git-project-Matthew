import java.nio.file.*;
import java.security.MessageDigest;
import java.io.*;

public class GitTester {
    // compete wip
    // need to know why delete is not working
    public static void main(String[] args) {
        //testing file creation
        deleteGitFolder();
        Git.initalizeGitRepo();

        // testing blob creation
        File tester1 = fileCreation("test1", "look behind you");
        File tester2 = fileCreation("tester2", "hi\nthis is sonarii");
        Git.blobCreation(tester1);
        Git.blobCreation(tester2);
        File tester3 = fileCreation("tester3", "hmmm");
        Git.blobCreation(tester3);

    }

    private static File fileCreation(String name, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(name))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File("./"+name);
    }

    private static void deleteGitFolder() {
        // deletes the git Repo
        String path = "./git";
        File toBeDelete = new File(path + "/objects");
        deleteHelper(toBeDelete);
        toBeDelete = new File(path + "/index");
        deleteHelper(toBeDelete);
        toBeDelete = new File(path);
        deleteHelper(toBeDelete);
    }

    private static void deleteHelper(File toBeDelete) {
        // if file exist, then delete
        if (toBeDelete.exists()) {
            toBeDelete.delete();
        }
    }
}
