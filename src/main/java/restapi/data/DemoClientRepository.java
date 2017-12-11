package restapi.data;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import restapi.pojo.Client;

@Repository
public class DemoClientRepository implements DemoJdbcRepository {
	private JdbcOperations jdbc;
	
	@Autowired
	public DemoClientRepository(JdbcOperations jdbc){
		this.jdbc = jdbc;
	}
	
	public int saveClient(Client client){
		  int affectedRow = jdbc.update(
			"insert into test (client_ip, role, status, slave_count) values (?,?,?,?)",
			client.getClient_ip(),
			client.getRole(),
			client.getStatus(),
			client.getSlave_count());
		  return affectedRow;
	  }
	  
	  public int updateClient(Client client){
		  int affectedRow = jdbc.update(
			"update test set client_ip=?, role=?, status=?, slave_count=? where id=?",
			client.getClient_ip(),
			client.getRole(),
			client.getStatus(),
			client.getSlave_count(),
			client.getID());
		  return affectedRow;
	  }
	  
	  public int deleteClient(String id){
		  int affectedRow = jdbc.update(
			"delete from test where id=?",
			id);
		  return affectedRow;
	  }
	  
	  public List<Map<String, Object>> findClientByID(String id){
		  return jdbc.queryForList(
			"select id, client_ip, role, status, slave_count from test where id=?;",
			id);
	  }

	  public List<Map<String, Object>> findAllClient(){
		  return jdbc.queryForList(
			"select id, client_ip, role, status, slave_count from test");
	  }
}
