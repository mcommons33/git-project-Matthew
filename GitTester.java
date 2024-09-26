import java.nio.file.*;
import java.security.MessageDigest;
import java.io.*;

public class GitTester {
    public static void main(String[] args) throws IOException {
        // testing file creation
        deleteGitFolder();
        Git.initalizeGitRepo();
        // testing blob creation
        File tester1 = fileCreation("tester1", "look behind you");
        File tester2 = fileCreation("tester2", "hi\nthis is sonarii");
        Git.blobCreation(tester1);
        Git.blobCreation(tester2);
        File tester3 = fileCreation("tester3", "hmmm");
        Git.blobCreation(tester3);
        // testing blob creation: empty dir
        File testDir = new File ("./testDir");
        testDir.mkdir();
        Git.blobCreation(testDir);
        // testing blob creation: dir with files
        File testDirFiles = new File ("./testDirFiles");
        new File ("./testDirFiles/test1");
        new File ("./testDirFiles/test2");
        new File ("./testDirFiles/test3");
        Git.blobCreation(testDirFiles);
        // testing blob creation: dir with files and subdirs
        File testDirDirs = new File ("./testDirDirs");
        File subDirs = new File ("./testDirDirs/test/testSub");
        testDirDirs.mkdir();
        subDirs.mkdirs();
        new File ("./testDirDirs/test1");
        Git.blobCreation(testDirDirs);
        // testing file deletion
        deleteTesters();

        }

    // creates a file with given name and content
    private static File fileCreation(String name, String content) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(name))) {
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File("./" + name);
    }

    // deletes the git Repo (including index and objects)
    private static void deleteGitFolder() {
        String path = "./git";
        File toBeDelete = new File(path + "/objects");
        deleteHelper(toBeDelete);
        toBeDelete = new File(path + "/index");
        deleteHelper(toBeDelete);
        toBeDelete = new File(path);
        deleteHelper(toBeDelete);
    }

    // delete the 3 testers created (tester1, tester2, tester3) + its content in
    // index and objects folder
    private static void deleteTesters() {
        File toBeDelete = new File("./tester1");
        deleteHelper(toBeDelete);
        toBeDelete = new File("./tester2");
        deleteHelper(toBeDelete);
        toBeDelete = new File("./tester3");
        deleteHelper(toBeDelete);
        toBeDelete = new File("./testDir");
        deleteHelper(toBeDelete);
        toBeDelete = new File("./git/index");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./git/index"))) {
            bufferedWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File objectesFolder = new File("./git/objects");
        for (File subfile : objectesFolder.listFiles()) {
            subfile.delete();
        }
    }

    private static void deleteHelper(File toBeDelete) {
        // if file exist, then delete
        if (toBeDelete.exists()) {
            toBeDelete.delete();
        }
    }
}
