
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AttemptChallenge {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/nationschools";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final int QUESTION_TIME_LIMIT = 1;//time limit per question in minutes
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter challenge Title e.g CH001: ");
        String challengeTitle = scanner.nextLine();

        try(Connection conn= new DriverManager.getConnection(DB_URL, USER, PASS)){
            checkAndPromptQuestions(conn, challengeTitle, scanner);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private static void checkAndPromptQuestions(Connection conn, String challengeTitle, Scanner scanner) throws SQLException{
        if (isChallengeActive(conn, challengeTitle)){
            List<String> questions = getRandomQuestions(conn,challengeTitle, 10);
            for (String question : questions){
                boolean timeup = promptQuestion(question, scanner);
                if (timeup){
                    System.out.println("Time is up! The quiz is now closed");
                    break;
                }
            }
        }else {
            System.out.println("The challenge is not active.");
        }
    }
    private static boolean isChallengeActive(Connection conn, String challengeTitle)throws SQLException{
        String query = "SELECT is_active from challenges WHERE challengeTitle = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, challengeTitle);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return rs.getBoolean("is_active");
            }
        }
        return false;
    }
    private static List<String> getRandomQuestions(Connection conn, String challengeTitle, int limit) throws SQLException{
        List<String> questions = new ArrayList<>();
        String query = "SELECT question FROM questionsmore WHERE challenge_title = ? ORDER BY RAND() LIMIT ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, challengeTitle);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                questions.add(rs.getString("question"));
            }
        }
        return questions;
    }
    private static boolean promptQuestion(String question, Scanner scanner){
        System.out.println(question);

        Timer timer = new Timer();
        TimerTask task = new TimerTask(){
            public void run(){
                System.out.println("Time is up for this question!");
                System.exit(0);
            }
        };
        timer.schedule(task, QUESTION_TIME_LIMIT * 60 * 1000);// SET TIMER FOR THE QUESTION

        System.out.print("Your answer: ");
        String answer = scanner.nextLine();
        timer.cancel();

        return false;
    }
}
