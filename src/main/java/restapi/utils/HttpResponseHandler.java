package restapi.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.mysql.fabric.xmlrpc.base.Data;

import ch.qos.logback.core.joran.conditional.IfAction;
import restapi.pojo.Client;

/**
 * 通过覆写ResponseHandler类中的handleResponse实现自定义的响应回调方法，
 * 推荐使用这种方法来处理响应
 */
public class HttpResponseHandler {
	public static ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
        @Override
        public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }

    };
    
    public static ResponseHandler<DataDemo> demoHandler = new ResponseHandler<DataDemo>() {
        @Override
        public DataDemo handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                if(entity != null){
                	String jsonStr = EntityUtils.toString(entity);
                	DataDemo dataDemo = (DataDemo) JSON.parseObject(jsonStr, DataDemo.class);
                	return dataDemo;
                }
                else {
                	throw new NullPointerException("The Http response is null!");
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }

    };

}

class DataDemo {
	private int status;
	private List<Client> data = new ArrayList<Client>();
	
	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public List<Client> getData(){
		return data;
	}
	public void setData(List<Client> data){
		this.data = data;
	}
	
	public void addData(Client client){
		this.data.add(client);
	}
}
