package directory.watcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import directory.watcher.service.DirectoryWatcherService;

import javax.annotation.PostConstruct;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigurationProperties
public class Application {

    @Autowired
    private DirectoryWatcherService watcher;

    public static void main(String args[]) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

    }

    @PostConstruct
    public void enableDirectoryWatcher(){
        watcher.watch();
    }
}