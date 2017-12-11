package restapi.pojo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

/*
 * 验证请求是否来自微信服务的请求体
 */
public class WechatValidationForm {
	private static final String token = "test";
	private String signature;
	private String timestamp;
	private String nonce;
	private String echostr;
	
	public WechatValidationForm(){};
	public WechatValidationForm(Map<String, String> params){
		this.signature = params.get("signature");
		this.timestamp = params.get("timestamp");
		this.nonce = params.get("nonce");
		this.echostr = params.get("echostr");
	}
	
	public String getSignature(){
		return signature;
	}
	public void setSignature(String signature){
		this.signature = signature;
	}
	
	public String getTimestamp(){
		return timestamp;
	}
	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}
	
	public String getNonce(){
		return nonce;
	}
	public void setNonce(String nonce){
		this.nonce = nonce;
	}
	
	public String getEchostr(){
		return echostr;
	}
	public void setEchostr(String echostr){
		this.echostr = echostr;
	}
	
	public boolean isValid(){
		String validatedSignature;
		String[] list = {token,timestamp,nonce};
		Arrays.sort(list);
		validatedSignature = DigestUtils.sha1Hex(String.join("", list));
		return validatedSignature.equals(signature);
	}

	public static void main(String[] args){
		// Test for this class
		Map<String, String> p = new HashMap<>();
		p.put("signature","test");
		p.put("timestamp", "123");
		p.put("nonce", "123");
		p.put("echostr", "hello");
		WechatValidationForm wvm = new WechatValidationForm(p);
		System.out.println(wvm.isValid());
	}
}
