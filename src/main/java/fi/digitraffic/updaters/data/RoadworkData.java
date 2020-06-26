package fi.digitraffic.updaters.data;

import java.util.Date;
import java.util.List;

public class RoadworkData {
    public final String situationId;
    public final Date releaseTime;
    public final String title;
    public final String comment;
    public final List<String> features;

    public RoadworkData(final String situationId,
                        final Date releaseTime,
                        final String title,
                        final String comment,
                        final List<String> features) {
        this.situationId = situationId;
        this.releaseTime = releaseTime;
        this.title = title;
        this.comment = comment;
        this.features = features;
    }
}
