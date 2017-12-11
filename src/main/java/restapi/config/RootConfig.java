package restapi.config;

import java.util.regex.Pattern;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import restapi.config.DataConfig;
import restapi.config.RootConfig.WebPackage;

/*
 * Import注解用于导入以类形式声明的带有Configuration注解的配置类
 * 如果要导入xml或其他文件形式的配置，请使用ImportResource。
 * 在本demo示例中，由于入口主程序使用了@SpringBootApplication注解，该注解包括了EnableAutoConfiguration注解的
 * 功能，会自动加载所有配置，所以即使不通过import也会成功加载所有以Configuration注解的配置类
 */
@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackages={"restapi"}, 
    excludeFilters={
        @Filter(type=FilterType.CUSTOM, value=WebPackage.class)
    })
public class RootConfig {

	public static class WebPackage extends RegexPatternTypeFilter {
	    public WebPackage() {
	      super(Pattern.compile("restapi\\.web"));
	    }    
	  }
}
