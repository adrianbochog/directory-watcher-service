package directory.watcher.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Adrian Perez on 2/11/16.
 */
@Component
@ConfigurationProperties(prefix = "directoryService")
public class DirectoryServiceProperties {
    private String directory;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
}
