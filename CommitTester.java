import java.io.IOException;

public class CommitTester {
    public static void main (String [] args) throws IOException {
        Git myGit = new Git();
        myGit.initalizeGitRepo();
        myGit.stage("tester1");
        myGit.stage("tester1");
    }
}