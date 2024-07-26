import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ChallengeCheckers12 {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/nationschools";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final int QUESTION_TIME_LIMIT = 10; // time limit per question in minutes

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter challenge title: ");
        String challengeTitle = scanner.nextLine();
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            checkAndPromptQuestions(conn, challengeTitle, scanner);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void checkAndPromptQuestions(Connection conn, String challengeTitle, Scanner scanner) throws SQLException {
        if (isChallengeActive(conn, challengeTitle)) {
            List<String> questions = getRandomQuestions(conn, challengeTitle, 10);
            List<Long> timesTaken = new ArrayList<>();
            int totalQuestions = questions.size();
            int questionNumber = 1;
            long totalTimeTaken = 0;
            List<String> userAnswers = new ArrayList<>();

            for (String question : questions) {
                System.out.println("\nQuestion " + questionNumber + " of " + totalQuestions);
                if (questionNumber > 1) {
                    System.out.println("Total time taken so far: " + formatTime(totalTimeTaken));
                }

                long startTime = System.currentTimeMillis();
                String answer = promptQuestion(question, scanner, totalQuestions - questionNumber + 1);
                long endTime = System.currentTimeMillis();
                long timeTaken = endTime - startTime;
                totalTimeTaken += timeTaken;
                timesTaken.add(timeTaken);

                if (answer == null) {
                    System.out.println("Time is up! The quiz is now closed.");
                    break;
                }
                userAnswers.add(answer);
                questionNumber++;
            }

            int finalScore = calculateScore(conn, challengeTitle, questions, userAnswers);
            System.out.println("Your final score is: " + finalScore);
            displayReport(questions, userAnswers, timesTaken, totalTimeTaken, finalScore);
        } else {
            System.out.println("The challenge is not active.");
        }
    }

    private static boolean isChallengeActive(Connection conn, String challengeTitle) throws SQLException {
        String query = "SELECT is_active FROM challenges WHERE title = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, challengeTitle);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_active");
            }
        }
        return false;
    }

    private static List<String> getRandomQuestions(Connection conn, String challengeTitle, int limit) throws SQLException {
        List<String> questions = new ArrayList<>();
        String query = "SELECT question FROM questionsmore WHERE challenge_title = ? ORDER BY RAND() LIMIT ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, challengeTitle);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(rs.getString("question"));
            }
        }
        return questions;
    }

    private static String promptQuestion(String question, Scanner scanner, int remainingQuestions) {
        System.out.println("Questions remaining: " + remainingQuestions);
        System.out.println(question);

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {
                System.out.println("\nTime is up for this question!");
                System.exit(0);
            }
        };

        timer.schedule(task, QUESTION_TIME_LIMIT * 60 * 1000); // set timer for the question

        System.out.print("Your answer: ");
        String answer = scanner.nextLine();

        timer.cancel(); // cancel the timer if the user answers within the time limit
        System.out.println("Your answer is: " + answer + ". Press Enter to continue...");
        scanner.nextLine(); // wait for the user to press Enter to continue

        return answer;
    }

    private static int calculateScore(Connection conn, String challengeTitle, List<String> questions, List<String> userAnswers) throws SQLException {
        int score = 0;
        String query = "SELECT a.answer FROM questionsmore q JOIN answers a ON q.question_id = a.question_id WHERE q.challenge_title = ? AND q.question = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < questions.size(); i++) {
                stmt.setString(1, challengeTitle);
                stmt.setString(2, questions.get(i));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String correctAnswer = rs.getString("answer");
                    String userAnswer = userAnswers.get(i);

                    if (userAnswer.equals("-")) {
                        // User skipped the question
                        score += 0;
                    } else if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                        // Correct answer
                        score += 5;
                    } else {
                        // Wrong answer
                        score -= 3;
                    }
                }
            }
        }

        // Ensure the score doesn't go below 0
        return Math.max(score, 0);
    }

    private static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private static void displayReport(List<String> questions, List<String> userAnswers, List<Long> timesTaken, long totalTimeTaken, int finalScore) {
        System.out.println("\n----- Challenge Report -----");
        for (int i = 0; i < questions.size(); i++) {
            String question = questions.get(i);
            String answer = userAnswers.get(i);
            long timeTaken = timesTaken.get(i);

            System.out.println("Question " + (i + 1) + ": " + question);
            System.out.println("Your answer: " + answer);
            System.out.println("Time taken: " + formatTime(timeTaken));
            System.out.println();
        }
        System.out.println("Total time taken: " + formatTime(totalTimeTaken));
        System.out.println("Your final score: " + finalScore);
        System.out.println("Percentage: " + (finalScore * 2) + "%");
        System.out.println("----------------------------");
    }
}
