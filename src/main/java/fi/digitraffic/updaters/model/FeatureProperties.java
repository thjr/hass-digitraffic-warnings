package fi.digitraffic.updaters.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureProperties {
    public Date releaseTime;

    public String situationId;

    public Location locationToDisplay;

    public List<Announcement> announcements;

    public FeatureProperties() {}

    public static class Location {
        public long e;
        public long n;
    }
}