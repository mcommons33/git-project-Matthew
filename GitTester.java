import java.io.File;

public class GitTester {
    public static void main(String[] args) {

        File folder = new File("/git/objects");
        if (!folder.exists()) {
            folder.delete();
        }
        Git.initalizeGitRepo();

    }
}
