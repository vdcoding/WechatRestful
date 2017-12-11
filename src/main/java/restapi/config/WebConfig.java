package restapi.config;

import java.util.List;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;


/*
 * 相当于web.xml，声明的jsp解析器、log4j日志配置，在默认的HttpMessageConverter添加阿里的fastjson，提高对json
 * 的解析转化速度。
 * 本demo示例是restful接口，没有使用jsp解析器，也没有使用log4j输出日志
 * 也用不到jsonconverter，必须将其注释掉，否则请求无法按照xml解析了
 * 
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

  @Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
	DOMConfigurator.configure("classpath:log4j.xml");
    configurer.enable();
    
  }
  
//  @Override
//  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//      FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
//      //自定义配置...
//      //FastJsonConfig config = new FastJsonConfig();
//      //config.set ...
//      //converter.setFastJsonConfig(config);
//      converters.add(converter);
//  }
  
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // TODO Auto-generated method stub
    super.addResourceHandlers(registry);
  }
  

}
