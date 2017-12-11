package restapi.pojo;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

/*
 * xml响应消息的基类，子类包括文本消息和图文消息，封装了公共属性
 */
public class BaseResponse {
	@XmlElement
	private String FromUserName;
	@XmlElement
	private String ToUserName;
	@XmlElement
	private Long CreateTime = new Date().getTime();
	@XmlElement
	private String MsgType;
	
	public BaseResponse(){};
	public BaseResponse(String username, String devname){
		this.ToUserName = username;
		this.FromUserName = devname;
	}
	
	public String getToUserName() {
		return ToUserName;
	}
	
	public String getFromUserName() {
		return FromUserName;
	}
	
	public long getCreateTime() {
		return CreateTime;
	}
	
	public String getMsgType(){
		return MsgType;
	}
	
	@Override
	public String toString(){
		return " From:"+ FromUserName + " To:" + ToUserName + " MsgType" + MsgType;
	}

}
