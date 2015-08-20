package za.co.mmjmicrosystems.training.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

public class SmtpMailSender implements MailSender {

	private static final Logger logger = LoggerFactory.getLogger(SmtpMailSender.class);

	private JavaMailSender javaMailSender;
	
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	@Async
	public void send(String to, String subject, String body)
			throws MessagingException {
		
		logger.info("Sending SMTP mail from thread " + Thread.currentThread().getName());
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;

		helper = new MimeMessageHelper(message, true); // true indicates
		
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body, true); // true indicates html
		// continue using helper object for more functionalities like adding attachments, etc.  
		logger.info("SMTP Mail sender");
		logger.info("Sending SMTP mail to " + to);
		logger.info("SMTP Subject: " + subject);
		logger.info("SMTP Body: " + body);
		
		javaMailSender.send(message);

	}
}
