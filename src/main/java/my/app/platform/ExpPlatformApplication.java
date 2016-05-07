package my.app.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 夏之阳
 * 创建时间：2016-03-14 21:41
 * 创建说明：应用程序启动类
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("my.app")
public class ExpPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExpPlatformApplication.class, args);
    }
}

//public class ExpPlatformApplication extends SpringBootServletInitializer {
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ExpPlatformApplication.class);
//    }
//
//    public static void main(String[] args) throws Exception {
//        SpringApplication.run(ExpPlatformApplication.class, args);
//    }
//
//}