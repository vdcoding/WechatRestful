package restapi.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import restapi.data.BlogRepository;
import restapi.pojo.WechatValidationForm;
import restapi.service.WechatMailServiceImpl;
import restapi.pojo.PicTextResponse;
import restapi.pojo.RequestTemplate;
import restapi.pojo.TextResponse;

/*
 * 微信统一的多态接口，根据不同的method或者content-type来实现调用不同的方法来处理请求
 */
@RestController
@RequestMapping("/wechatapi")
public class WechatApiController {
	private BlogRepository blogDao;
	@Autowired
	private WechatMailServiceImpl mailSender;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public WechatApiController(BlogRepository blogDao) {
		this.blogDao = blogDao;
	}
	
	/*
	 * 用于验证请求来自微信服务，首次在微信公众平台绑定服务时会用到
	 */
	@RequestMapping(method=GET)
	public String wechatApi(@RequestParam HashMap<String, String> params){
		WechatValidationForm form = new WechatValidationForm(params);
		if (form.isValid()){
			return form.getEchostr();
		}
		else {
			return "Invalid";
		}
	}
	
	@RequestMapping(method=POST)
	public String wechatApi(@RequestBody WechatValidationForm form){
		if (form.isValid()){
			return form.getEchostr();
		}
		else {
			return "Invalid";
		}
	}
	/*
	 * content-type:text/xml的请求会调用该方法处理，最终返回xml格式的数据
	 */
	@RequestMapping(consumes={"application/xml", "text/xml"},produces={"text/xml"})
	public Object wechatResponse(@RequestBody RequestTemplate request) throws MessagingException{
		String msgType = request.getMsgType().trim();
		String userName = request.getFromUserName();
		String devname = request.getToUserName();
		if (msgType.equals(RequestTemplate.EVENT)){
			mailSender.sendTemplateEmail(userName, request.getEvent());
			return new TextResponse(userName, devname);
		}
		else if (msgType.equals(RequestTemplate.TEXT)){
			String keyWord = request.getContent();
			List<Map<String, Object>> list;
			switch (keyWord) {
			case "0":
				return new TextResponse(userName, devname);
			case "1":
				list = blogDao.findPopularPosts();
				break;
			case "2":
				list = blogDao.findLatestPosts();
				break;
			default:
				list = blogDao.searchPosts(keyWord);
				break;
			}
			if (list.size() > 0){
				return new PicTextResponse(userName, devname, list);
			}
			else {
				return new TextResponse(userName, devname, 0);
			}
		}
		else {
			return "Not supported msgtype:" + msgType;
		}
		
	}
}
