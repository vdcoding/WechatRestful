package restapi.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/*
 * 使用xml注解生成微信xml文本响应消息
 */
@XmlRootElement(name="xml")
public class TextResponse extends BaseResponse {

	@XmlElement
	private String MsgType = "text";
	@XmlElement
	private String Content;
	@XmlElement
	private int MsgId = 123;
	
	public TextResponse(){};
	public TextResponse(String ToUserName, String devname) {
		super(ToUserName, devname);
		this.Content = "欢迎来到【玩点Coding】\n发送0查看本条欢迎提示信息\n发送1查看最受欢迎文章\n发送2查看最新文章\n发送其他任意关键字可搜索相关文章\n更多精彩内容请查看主页http://vdcoding.com";
	}
	public TextResponse(String ToUserName, String devname, Object object){
		super(ToUserName, devname);
		this.Content = "很抱歉,未搜索到相关内容\n更多精彩内容请查看主页http://vdcoding.com";
	}
	
	public String getContent(){
		return Content;
	}
	
	public int getMsgId() {
		return MsgId;
	}
	
}
