package vn.iotstar.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

public class EmailService {

    private static final String FROM_EMAIL = "your-email@gmail.com";
    private static final String PASSWORD = "your-app-password";

    /**
     * Tao ma OTP ngau nhien 6 chu so
     */
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    /**
     * Gui email voi noi dung bat ky
     */
    public static boolean sendEmail(String toEmail, String subject, String content) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setContent(content, "text/html; charset=utf-8");

            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gui ma OTP qua email
     */
    public static boolean sendOTP(String toEmail, String otp) {
        String subject = "Ma xac thuc OTP";
        String content = "<h2>Ma OTP cua ban la:</h2>" +
                "<h1 style='color: blue;'>" + otp + "</h1>" +
                "<p>Ma nay se het han sau 5 phut.</p>";
        return sendEmail(toEmail, subject, content);
    }

    /**
     * Gui email reset password
     */
    public static boolean sendResetPasswordOTP(String toEmail, String otp) {
        String subject = "Yeu cau dat lai mat khau";
        String content = "<h2>Ma OTP de dat lai mat khau cua ban la:</h2>" +
                "<h1 style='color: red;'>" + otp + "</h1>" +
                "<p>Ma nay se het han sau 5 phut.</p>";
        return sendEmail(toEmail, subject, content);
    }
}
