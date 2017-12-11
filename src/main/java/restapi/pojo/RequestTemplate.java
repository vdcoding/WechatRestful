package restapi.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * 微信发过来的请求消息xml模板，controller中通过@RequestBody将xml请求参数直接注入到该类中
 */
@XmlRootElement(name="xml")
public class RequestTemplate implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final String TEXT = "text";
	public static final String EVENT = "event";
	@XmlElement
	private String ToUserName;
	@XmlElement
	private String FromUserName;
	@XmlElement
	private String CreateTime;
	@XmlElement
	private String MsgType;
	@XmlElement
	private String Content;
	@XmlElement
	private String MsgId;
	@XmlElement
	private String Event;
	

	public String getToUserName(){
		return ToUserName;
	}
	
	public String getFromUserName(){
		return FromUserName;
	}

	public String getCreateTime(){
		return CreateTime;
	}
	
	public String getMsgType(){
		return MsgType;
	}
	
	public String getContent(){
		return Content;
	}
	
	public String getMsgId(){
		return MsgId;
	}
	
	public String getEvent() {
		return Event;
	}
	
	@Override
	public String toString(){
		return "From:"+ FromUserName + " To:" + ToUserName + " MsgType:" + MsgType;
	}

}
