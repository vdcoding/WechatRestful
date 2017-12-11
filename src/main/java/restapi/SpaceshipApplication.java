package restapi;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/*
 * 通过@EnableAsync注解开启spring的异步执行方法的功能，所有以@Async注解的方法都会以线程方式执行，spring默认提供
 * 一个线程池用于异步操作，也可以自定义线程池，详情参考官档。
 * proxyTargetClass=true表名子类也可以使用异步执行，例如：service/WechatMailServiceImpl
 */
@SpringBootApplication()
@EnableAsync(proxyTargetClass=true)
public class SpaceshipApplication {

	public static void main(String[] args) {
//		SpringApplication.run(SpaceshipApplication.class, args);
		SpringApplication app = new SpringApplication(SpaceshipApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}
}
