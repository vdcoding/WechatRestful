package restapi.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

import restapi.pojo.Client;
import restapi.utils.DataDemo;

/**
 * 基于HttpClient 4.5.2的发送HTTP请求的工具类，包括post和get方法
 */
public class HttpClientUtil {
	/**
	 * 支持回调的post方法
	 * @param url	
	 * @param params	post参数
	 * @param rh	处理response的回调方法
	 * @throws IOException
	 */
	public static String doPost(URI url, Map<String, String> params, ResponseHandler<String> rh) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = makeHttpPost(url, params);
		try {
			return httpClient.execute(httpPost, rh);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			httpClient.close();
		}
		return null;
	}
	
	/**
	 * 普通的只带参数的post方法
	 * @param url
	 * @param params
	 * @throws IOException
	 */
	public static String doPost(URI url, Map<String, String> params) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = makeHttpPost(url, params);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
			httpClient.close();
		}
		return null;
	}
	
	public static String doGet(URI url) throws Exception {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			return EntityUtils.toString(response.getEntity());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			httpClient.close();
		}
		return null;
	}
	
	public static String doGet(URI url, ResponseHandler<String> rh) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			return httpClient.execute(httpGet, rh);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			httpClient.close();
		}
		return null;
	}
	
	/**
	 * 
	 * @param url
	 * @param rh
	 * @return	返回通过json解析成的DataDemo对象

	 */
	public static DataDemo doDemoGet(URI url, ResponseHandler<DataDemo> rh) throws IOException{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		try {
			return httpClient.execute(httpGet, rh);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			httpClient.close();
		}
		return null;
	}
	 
	private static HttpPost makeHttpPost(URI url, Map<String, String> params){
		UrlEncodedFormEntity entity = null;
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> form = new ArrayList<NameValuePair>();
		for (Entry<String, String> entry: params.entrySet()){
			form.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		if(form.size() > 0){
			entity = new UrlEncodedFormEntity(form, Consts.UTF_8);
		}
		httpPost.setEntity(entity);
		return httpPost;
	}
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		try{
			//response data sample: 
			//{"status": 1, "data": [{"status": "1", "client_ip": "172.16.16.72", "role": "1", "id": 1, "slave_count": 0}]
			URI url = new URI("http://172.16.16.92/clientlist");
			ResponseHandler<String> rHandler = HttpResponseHandler.responseHandler;
			ResponseHandler<DataDemo> demoHandler = HttpResponseHandler.demoHandler;
			DataDemo demoRsp = doDemoGet(url, demoHandler);
			List<Client> clients = demoRsp.getData();
			for(Client client: clients){
				System.out.println(client.getClient_ip());
			}
			//华丽丽的分割线
			String result = doGet(url, rHandler);
			System.out.println(result);
			//正常情况应该定义个对应的的数据类，用parseObject转换为该数据类，方便操作,如上所示
			Object ret = JSON.parse(result);
			System.out.println(ret.getClass());
			if (ret instanceof Map){
				Map<String, Object> map = (Map) ret;
				System.out.println("ret is map and map size is " + map.size());
				for(Entry<String, Object> entry: map.entrySet()){
					System.out.println(entry.getKey() + ":" + entry.getValue());
				}
				List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
				System.out.println(data.toArray().length);
			}
			else {
				System.out.println("ret is not map");
			}
			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
