package directory.watcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import directory.watcher.service.DirectoryWatcherService;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties
public class Application {

    public static void main(String args[]) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        DirectoryWatcherService watcher = (DirectoryWatcherService) ctx.getBean("directoryWatcherService");
        watcher.watch();
    }
}