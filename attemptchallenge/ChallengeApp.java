import java.util.*;
public class ChallengeApp {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter challenge title: ");
        String challengeTitle = scanner.nextLine();
        System.out.print("Enter school name: ");
        String schoolName = scanner.nextLine();
        System.out.print("Enter registration number: ");
        String regNo = scanner.nextLine();
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();

        ChallengeChecker.attemptChallenge(challengeTitle, schoolName, regNo, userName);
    }
}
