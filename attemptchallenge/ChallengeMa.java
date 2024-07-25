import java.sql.*;
import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.*;
import java.text.SimpleDateFormat;

public class ChallengeMa {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/nationschools";
    private static final String USER = "root";
    private static final String PASS = "";
    private static final int QUESTION_TIME_LIMIT = 10; // time limit per question in minutes

    public static void attemptChallenge(String challengeTitle, String schoolName, String regNo, String userName, String userEmail) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        Class.forName("com.mysql.cj.jdbc.Driver");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            if (isChallengeActive(conn, challengeTitle)) {
                boolean wantToRetake = true;
                int attempts = 0;
                int bestScore = 0;
                List<String> bestQuestions = new ArrayList<>();
                List<String> bestAnswers = new ArrayList<>();
                List<Long> bestTimesTaken = new ArrayList<>();
                List<String> bestCorrectAnswers = new ArrayList<>();
                long bestTotalTimeTaken = 0;

                while (wantToRetake && attempts < 3) {
                    attempts++;
                    System.out.println("\nAttempt " + attempts);
                    List<String> questions = getRandomQuestions(conn, challengeTitle, 10);
                    List<String> answers = new ArrayList<>();
                    List<Long> timesTaken = new ArrayList<>();
                    List<String> correctAnswers = new ArrayList<>();
                    long totalTimeTaken = 0;
                    int score = takeTest(conn, challengeTitle, scanner, questions, answers, timesTaken, totalTimeTaken, correctAnswers);

                    if (score > bestScore) {
                        bestScore = score;
                        bestQuestions = new ArrayList<>(questions);
                        bestAnswers = new ArrayList<>(answers);
                        bestTimesTaken = new ArrayList<>(timesTaken);
                        bestCorrectAnswers = new ArrayList<>(correctAnswers);
                        bestTotalTimeTaken = totalTimeTaken;
                    }

                    if (attempts < 3) {
                        System.out.print("Do you want to retake the test? (yes/no): ");
                        String response = scanner.nextLine().trim().toLowerCase();
                        wantToRetake = response.equals("yes");
                    } else {
                        wantToRetake = false;
                    }
                }

                System.out.println("\nYour best score is: " + bestScore);
                displayReport(bestQuestions, bestAnswers, bestTimesTaken, bestTotalTimeTaken, bestScore);
                storeChallengeResult(userName, userEmail, challengeTitle, schoolName, regNo, bestQuestions, bestAnswers, bestTimesTaken, bestTotalTimeTaken, bestScore, bestCorrectAnswers);
                System.out.println("Your challenge result has been stored. You will receive an email with your report on the designated day.");
            } else {
                System.out.println("The challenge is not active.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int takeTest(Connection conn, String challengeTitle, Scanner scanner, List<String> questions, List<String> answers, List<Long> timesTaken, long totalTimeTaken, List<String> correctAnswers) throws SQLException {
        int totalQuestions = questions.size();
        int questionNumber = 1;

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
            answers.add(answer);
            questionNumber++;
        }

        return calculateScore(conn, challengeTitle, questions, answers, correctAnswers);
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

        timer.schedule(task, QUESTION_TIME_LIMIT * 60 * 1000);

        System.out.print("Your answer: ");
        String answer = scanner.nextLine();

        timer.cancel();
        System.out.println("Your answer is: " + answer + ". Press Enter to continue...");
        scanner.nextLine();

        return answer;
    }

    private static int calculateScore(Connection conn, String challengeTitle, List<String> questions, List<String> userAnswers, List<String> correctAnswers) throws SQLException {
        int score = 0;
        String query = "SELECT a.answer FROM questionsmore q JOIN answers a ON q.question_id = a.question_id WHERE q.challenge_title = ? AND q.question = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < questions.size(); i++) {
                stmt.setString(1, challengeTitle);
                stmt.setString(2, questions.get(i));
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String correctAnswer = rs.getString("answer");
                    correctAnswers.add(correctAnswer);
                    String userAnswer = userAnswers.get(i);

                    if (userAnswer.equals("-")) {
                        score += 0;
                    } else if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                        score += 5;
                    } else {
                        score -= 3;
                    }
                }
            }
        }

        int maxScore = 10 * 5;
        int finalScore = Math.max(score, 0);

        return (finalScore * 100) / maxScore;
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
            System.out.println("Question " + (i + 1) + ": " + questions.get(i));
            System.out.println("Your answer: " + userAnswers.get(i));
            System.out.println("Time taken: " + formatTime(timesTaken.get(i)));
            System.out.println();
        }
        System.out.println("Total time taken: " + formatTime(totalTimeTaken));
        System.out.println("Your final score: " + finalScore + " out of 100");
        System.out.println("Percentage: " + finalScore + "%");
        System.out.println("----------------------------");
    }

    private static void storeChallengeResult(String userName, String userEmail, String challengeTitle, String schoolName, String regNo, List<String> questions, List<String> answers, List<Long> timesTaken, long totalTimeTaken, int finalScore, List<String> correctAnswers) {
        String fileName = "Challenge_Result_" + userName.replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + ".txt";
        String filePath = ".idea/results/" + fileName;

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("User Name: " + userName);
            writer.println("User Email: " + userEmail);
            writer.println("Challenge Title: " + challengeTitle);
            writer.println("School Name: " + schoolName);
            writer.println("Registration Number: " + regNo);
            writer.println("Final Score: " + finalScore);
            writer.println("Total Time Taken: " + formatTime(totalTimeTaken));
            writer.println("\nQuestions, Answers, and Correct Answers:");
            for (int i = 0; i < questions.size(); i++) {
                writer.println("Question " + (i + 1) + ": " + questions.get(i));
                writer.println("Your Answer: " + answers.get(i));
                writer.println("Correct Answer: " + correctAnswers.get(i));
                writer.println("Time Taken: " + formatTime(timesTaken.get(i)));
                writer.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generatePDFReport(String content, String userName) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        String fileName = "Challenge_Report_" + userName.replaceAll("\\s+", "_") + "_" + System.currentTimeMillis() + ".pdf";
        String filePath = ".idea/reports/" + fileName;

        try {
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PDType0Font font = PDType0Font.load(document, new File("C:/xampp/htdocs/School-Mathematics-Challenge1/fonts/helvetica/Helvetica.ttf"));

            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 725);

            for (String line : content.split("\n")) {
                contentStream.showText(line);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    private static void sendEmailWithAttachment(String to, String subject, String body, String filename) {
        final String from = "hermanssenoga@gmail.com"; // Replace with your email
        final String password = "fead zyzi ivgy afou"; // Replace with your email password

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.starttls.required", true);
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(new File(filename).getName());
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully to " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void sendAllStoredResults() {
        File resultsDir = new File(".idea/results");
        File[] resultFiles = resultsDir.listFiles((dir, name) -> name.startsWith("Challenge_Result_"));

        if (resultFiles != null) {
            for (File file : resultFiles) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String userName = "";
                    String userEmail = "";
                    String challengeTitle = "";
                    String content = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("User Name: ")) userName = line.substring("User Name: ".length());
                        if (line.startsWith("User Email: ")) userEmail = line.substring("User Email: ".length());
                        if (line.startsWith("Challenge Title: "))
                            challengeTitle = line.substring("Challenge Title: ".length());
                        content += line + "\n";
                    }

                    String pdfPath = generatePDFReport(content, userName);
                    sendEmailWithAttachment(userEmail, "Challenge Report: " + challengeTitle, "Please find your challenge report attached.", pdfPath);

                    file.delete(); // Delete the result file after sending the email
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void runOnDesignatedDay() {
        // You can add logic here to check if it's the right day to send emails
        // For example, you might check the current date against a predefined date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new java.util.Date());
        String designatedDate = "2024-07-31"; // Set your designated date here

        if (currentDate.equals(designatedDate)) {
            sendAllStoredResults();
            System.out.println("All challenge reports have been sent on the designated day.");
        } else {
            System.out.println("Today is not the designated day for sending challenge reports.");
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Math Challenge System ---");
            System.out.println("1. Attempt a challenge");
            System.out.println("2. Run designated day process (Admin)");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter your email: ");
                    String userEmail = scanner.nextLine();

                    System.out.print("Enter the challenge title: ");
                    String challengeTitle = scanner.nextLine();

                    System.out.print("Enter the school name: ");
                    String schoolName = scanner.nextLine();

                    System.out.print("Enter the registration number: ");
                    String regNo = scanner.nextLine();

                    System.out.print("Enter your name: ");
                    String userName = scanner.nextLine();

                    attemptChallenge(challengeTitle, schoolName, regNo, userName, userEmail);
                    break;

                case 2:
                    System.out.println("Running designated day process...");
                    runOnDesignatedDay();
                    break;

                case 3:
                    exit = true;
                    System.out.println("Thank you for using the Math Challenge System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}