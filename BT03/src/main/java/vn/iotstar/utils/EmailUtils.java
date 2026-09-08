package vn.iotstar.utils;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtils {

	private static final String EMAIL = "thongledai@gmail.com";
	private static final String APP_PASSWORD = "giem hzlg egrq qezo";

	public static void sendOTP(String toEmail, String otp) throws Exception {

		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL, APP_PASSWORD);
			}
		});

		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress(EMAIL));

		message.setRecipients(
				Message.RecipientType.TO,
				InternetAddress.parse(toEmail));

		message.setSubject("Account verification OTP");

		message.setText("Your OTP Code: " + otp);

		Transport.send(message);
	}
}