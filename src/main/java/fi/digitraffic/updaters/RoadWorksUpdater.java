package fi.digitraffic.updaters;

import fi.digitraffic.Configuration;
import fi.digitraffic.hass.SensorValueService;
import fi.digitraffic.updaters.data.RoadworkData;
import fi.digitraffic.updaters.model.Announcement;
import fi.digitraffic.updaters.model.Feature;
import fi.digitraffic.updaters.model.FeatureCollection;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RoadWorksUpdater {
    private final Configuration configuration;
    private final RoadWorksClient roadWorksClient;
    private final SensorValueService sensorValueService;

    public RoadWorksUpdater(final Configuration configuration,
                            final RoadWorksClient roadWorksClient,
                            final SensorValueService sensorValueService) {
        this.configuration = configuration;
        this.roadWorksClient = roadWorksClient;
        this.sensorValueService = sensorValueService;
    }

    public void update() {
        System.out.println("update!");

        try {
            final FeatureCollection featureCollection = roadWorksClient.getAllRoadWorks(); //"hass-digitraffic-warnings", "hass-digitraffic-warnings", "gzip");
            final List<RoadworkData> data = convert(featureCollection);

            for(final RoadworkData d : data) {
                System.out.println(d.situationId + ":" + d.title + " " + StringUtils.join(d.features, "-"));

                sensorValueService.postRoadwork(d);
            };
        } catch(final Exception e) {
            System.out.println("got exception " + e);
        }
    }

    private List<RoadworkData> convert(final FeatureCollection fc) {
        return fc.features.stream()
                .map(this::convertFeature)
                .collect(Collectors.toList());
    }

    private RoadworkData convertFeature(final Feature feature) {
        final Announcement a = feature.properties.announcements.get(0);

        return new RoadworkData(
                feature.properties.situationId,
                feature.properties.releaseTime,
                a.title,
                a.comment,
                a.features
                );
    }
}