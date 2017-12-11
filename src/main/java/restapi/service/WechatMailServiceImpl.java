package restapi.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Component
public class WechatMailServiceImpl implements MailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private SpringTemplateEngine thymeleaf;
	
	//通过Value注解获取mail.properties中对应的值并注入到对应的变量
	@Value("${mailserver.username}")
	private String from;
	@Value("${mailclient.touser}")
	private String to;
	@Value("${mailclient.subject01}")
	private String subject01;
	@Value("${mailclient.subject02}")
	private String subject02;
	
	public String getSubject(String event){
		if (event.equals("subscribe")) return subject01;
		else return subject02;
	}
	
	@Async
	public void sendSimpleEmail(String text){
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject01);
		mailMessage.setText("<div>test</div>");
		mailSender.send(mailMessage);
	}
	
	@Async
	public void sendEmailWithAttachment() throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		ClassPathResource file = new ClassPathResource("mail/attach.txt");
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject01);
		helper.setText("test");
		helper.addAttachment("test.txt", file);
		mailSender.send(message);
		
	}
	
	@Async
	public void sendTemplateEmail(String user, String event) throws MessagingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		Context context = new Context();
		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(this.getSubject(event));
		context.setVariable("openid", user);
		context.setVariable("event", event);
		String emailText = thymeleaf.process("subscribe.html", context);
		//发送html邮件，将html标识设为true，则会使用content-type：text/html，否则使用默认的text/plain
		helper.setText(emailText, true);
		mailSender.send(message);
		
	}
}
