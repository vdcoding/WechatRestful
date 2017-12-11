package restapi.pojo;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Client {

	private String id;
	@NotNull
	private String client_ip;
	@NotNull
	private String role;
	@NotNull
	private String status;
	@NotNull
	private String slave_count;
	
	public Client(){};
	public Client(String ip, String role, String status, String slaveCount){
		this(null, ip, role, status, slaveCount);
	}
	
	public Client(String id, String ip, String role, String status, String slaveCount){
		this.id = id;
		this.client_ip = ip;
		this.role = role;
		this.status = status;
		this.slave_count = slaveCount;
	}
	
	public String getID(){
		return id;
	}
	public void setID(String id){
		this.id = id;
	}
	
	public String getClient_ip(){
		return client_ip;
	}
	public void setClient_ip(String ip){
		this.client_ip = ip;
	}
	
	public String getRole(){
		return role;
	}
	public void setRole(String role){
		this.role = role;
	}
	
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	
	public String getSlave_count(){
		return slave_count;
	}
	public void setSlave_count(String slaveCount){
		this.slave_count = slaveCount;
	}
	
	@Override
	public String toString(){
		return new ToStringBuilder(client_ip).toString();
	}
	
	@Override
	public int hashCode(){
		return new HashCodeBuilder().append(client_ip).toHashCode();
	}
	
}


