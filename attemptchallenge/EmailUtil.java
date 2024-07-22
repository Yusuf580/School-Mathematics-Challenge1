import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import javax.activation.*;

public class EmailUtil {
    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "your-email@gmail.com";
    private static final String PASSWORD = "your-email-password"; // or app-specific password

    public static void sendEmailWithAttachment(String toEmail, String subject, String body, String filePath) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", SMTP_SERVER);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(new File(filePath));

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Email sent successfully with attachment.");

        } catch (MessagingException | java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
