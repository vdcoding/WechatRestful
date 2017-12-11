package restapi.data;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;


@Repository
public class BlogRepository implements JdbcRepository {
	/*
	 * 定义jdbctemplate时通过Bean注解显式声明bean的名称，在自动注入引用时，
	 * 通过Qualifier注解标注要引用哪个bean，这样即可实现多数据库的使用
	 */
	@Autowired
	@Qualifier("prodb")
	private JdbcOperations jdbc;
	
	@Autowired
	@Qualifier("testdb")
	private JdbcOperations testjdbc;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public List<Map<String, Object>> findPopularPosts() {
		return jdbc.queryForList("sql statement");
	}
	
	public List<Map<String, Object>> findLatestPosts(){
		return jdbc.queryForList("sql statement");
	}

	public List<Map<String,Object>> searchPosts(String field){
		return jdbc.queryForList(
				"sql statement",
				field);
	}
}
