import java.io.File;
import java.nio.file.Files;

public class GitTester {
    // compete wip
    // need to know why delete is not working
    public static void main(String[] args) {
        deleteGitFolder();
        Git.initalizeGitRepo();

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
