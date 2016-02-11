package directory.watcher.service;


import directory.watcher.property.DirectoryServiceProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.nio.file.*;


import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Created by Adrian Perez on 2/11/16.
 */
@Service
public class DirectoryWatcherService {

    private WatchService watcher;

    private DirectoryServiceProperties properties;
    private static final Logger LOGGER = Logger.getLogger(DirectoryWatcherService.class);

    @Autowired
    public DirectoryWatcherService(DirectoryServiceProperties properties) {
        this.properties = properties;
        try {
            watcher = FileSystems.getDefault().newWatchService();
        } catch (IOException ea) {
            LOGGER.error(ea);
        }
    }

    public void watch() {
        if (watcher != null) {
            LOGGER.info("Watching: " + properties.getDirectory());
            Path dir = Paths.get(properties.getDirectory());
            try {
                dir.register(watcher, ENTRY_CREATE,
                        ENTRY_MODIFY, ENTRY_DELETE);
                for (; ; ) {

                    // wait for key to be signaled
                    WatchKey key;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException x) {
                        return;
                    }

                    for (WatchEvent<?> event : key.pollEvents()) {
                        WatchEvent.Kind<?> kind = event.kind();
                        WatchEvent<Path> ev = (WatchEvent<Path>) event;
                        Path filename = ev.context();

                        if (kind == OVERFLOW) {
                            LOGGER.error("OVERFLOW!!");
                        } else if (kind == ENTRY_CREATE) {
                            LOGGER.info("Created: " + filename);
                        } else if (kind == ENTRY_MODIFY) {
                            LOGGER.info("Modified: " + filename);
                        } else if (kind == ENTRY_DELETE) {
                            LOGGER.info("Deleted: " + filename);
                        }
                    }

                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            } catch (IOException ae) {
                LOGGER.error(ae);
            }
        }
    }

    public DirectoryServiceProperties getProperties() {
        return properties;
    }

    public void setProperties(DirectoryServiceProperties properties) {
        this.properties = properties;
    }

}
