package fi.digitraffic;

import fi.digitraffic.updaters.RoadWorksClient;
import fi.digitraffic.updaters.RoadWorksUpdater;
import io.quarkus.runtime.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@Startup
public class Scheduler {
    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);

    private final RoadWorksUpdater roadWorksUpdater;
    private final Configuration configuration;

    public Scheduler(final RoadWorksUpdater roadWorksUpdater,
                     final Configuration configuration) {
        this.roadWorksUpdater = roadWorksUpdater;
        this.configuration = configuration;
    }

    @PostConstruct
    void init() {
        if(configuration.isRoadworks()) {
            initRoadWorks();
        }
    }

    private void initRoadWorks() {
        executor.scheduleAtFixedRate(this::updateRoadWorks, 0, 1, TimeUnit.MINUTES);
    }

    private void updateRoadWorks() {
        System.out.println("updateRoadWorks!");

        roadWorksUpdater.update();
    }
}
