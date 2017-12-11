package restapi.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import restapi.config.RootConfig;

/*
 *通过覆写 DispatcherServletInitializer来显式指定应用启动时各部分该加载的配置类
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	  
	  @Override
	  protected Class<?>[] getRootConfigClasses() {
	    return new Class<?>[] { RootConfig.class };
	  }

	  @Override
	  protected Class<?>[] getServletConfigClasses() {
	    return new Class<?>[] { WebConfig.class };
	  }

	  @Override
	  protected String[] getServletMappings() {
	    return new String[] { "/" };
	  }

	}
