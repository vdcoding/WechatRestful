package restapi.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.core.env.ConfigurableEnvironment;

/*
 * 1、在Bean注解中显式声明bean的名称，方便dao层引用不同的jdbc，实现多数据库访问
 * 2、如果被Profile注解的bean或者component，必须将该profile激活后，才会注册到应用上下文中被使用
 * 激活方法可以通过ConfigurableEnvironment.setActiveProfiles或者在applicaiton.properties中声明属性
 * spring.profiles.active=profile_name
 */
@Configuration
@SuppressWarnings("unused") //用于抑制未使用导入库的告警
public class DataConfig {
	@Primary
	@Bean(name="prodbconfig")
//	@Profile("prod")
	public DataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8");
		ds.setUsername("test");
		ds.setPassword("test");
		ds.setInitialSize(10);
		ds.setMaxActive(10);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("select 1");
		return ds;
	}
	
	@Bean(name="testdbconfig")
//	@Profile("test")
	public DataSource testDataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://172.16.16.92:3306/wptest?useUnicode=true&characterEncoding=UTF-8");
		ds.setUsername("test");
		ds.setPassword("buzhidao");
		ds.setInitialSize(10);
		ds.setMaxActive(10);
		ds.setTestOnBorrow(true);
		ds.setValidationQuery("select 1");
		return ds;
	}
	
	@Primary
	@Bean(name="prodb")
	public JdbcTemplate jdbcTemplate(@Qualifier("prodbconfig") DataSource prods){
		return new JdbcTemplate(prods);
	}
	@Bean(name="testdb")
	public JdbcTemplate testJdbcTemplate(@Qualifier("testdbconfig") DataSource testds){
		return new JdbcTemplate(testds);
	}
	
}
