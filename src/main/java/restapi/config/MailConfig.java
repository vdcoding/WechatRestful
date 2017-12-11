package restapi.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;


@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfig {
	
	/*如果要使用Value注解方式获取properties配置，则必须声明该静态方法，否则无法解析到对应值
	 * 只会输出Value注解中的占位符，例如 Value("${server.host})，会输出${server.host}
	 */
	@Bean 
	public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() { 
		return new PropertySourcesPlaceholderConfigurer(); 
	}
	
	@Bean
	public JavaMailSenderImpl mailSender(Environment env){
		int port = Integer.parseInt(env.getProperty("mailserver.port"));
		Properties properties = new Properties();  
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(env.getProperty("mailserver.host"));
		//以SSL方式连接
		mailSender.setUsername(env.getProperty("mailserver.username"));
		mailSender.setPassword(env.getProperty("mailserver.password"));
        properties.setProperty("mail.smtp.auth", "true");//开启认证  
        properties.setProperty("mail.debug", "false");//启用调试  
        properties.setProperty("mail.smtp.timeout", "1000");//设置链接超时  
        properties.setProperty("mail.smtp.port", Integer.toString(port));//设置端口  
        properties.setProperty("mail.smtp.socketFactory.port", Integer.toString(port));//设置ssl端口  
        properties.setProperty("mail.smtp.socketFactory.fallback", "false");  
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        mailSender.setJavaMailProperties(properties);  
		return mailSender;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}
	
	/**
	 * spring有个默认的模板解析器
	 * spring有多个解析器时，在使用时时必须用Primary注解或是setOrder
	 * 来指定优先级，否则spring不知道使用哪个解析器会启动失败
	 */
	@Primary
	@Bean
  	public ClassLoaderTemplateResolver emailTemplateResolver(){
	   ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
	   resolver.setPrefix("templates/mail/");
//	   resolver.setSuffix(".html");
	   //如果模板文件以html、htm等为后缀则会自动设置模板模式，无需setTemplateMode
	   //resolver.setTemplateMode("HTML5");
	   resolver.setCharacterEncoding("UTF-8");
	   //作用同Primary
	   resolver.setOrder(1);
	   return resolver;
	  
   }
}
