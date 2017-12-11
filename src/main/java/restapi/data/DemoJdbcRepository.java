package restapi.data;

import java.util.List;
import java.util.Map;

import restapi.pojo.Client;

public interface DemoJdbcRepository {
	List<Map<String, Object>> findClientByID(String id);
	List<Map<String, Object>> findAllClient();
	int saveClient(Client client);
	int deleteClient(String id);
	int updateClient(Client client);
}
