package restapi.data;

import java.util.List;
import java.util.Map;



public interface JdbcRepository {
	List<Map<String, Object>> findPopularPosts();
	List<Map<String, Object>> findLatestPosts();
	List<Map<String, Object>> searchPosts(String field);
//	List<Map<String, Object>> findPostCatalog();
//	List<Map<String, Object>> findPostCategory();
	
}
