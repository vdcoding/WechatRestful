package restapi.controller;


import java.net.ConnectException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/*
 * AOP面向切面编程
 * 被@ControllerAdvice注解的类会收到所有controller的通知，可以通过参数annotations, basePackageClasses 和
 * basePackages（别名 value）来筛选指定的controller类
 * 最常用的就是用来处理共性异常，通过@ExceptionHandler注解指定某个要处理的异常类，然后定义对应的处理方法
 */
@ControllerAdvice
public class AppWideExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public String dataAccessError(){
		return "no data";
	}
	
	@ExceptionHandler(MessagingException.class)
	public void emailError(){
		logger.error("Send email error!");
	}
	
	@ExceptionHandler(ConnectException.class)
	public void mailServerConnectionTimeoutError(){
		logger.error("Connection to mail server time out!:smtp.163.com");
	}
}
