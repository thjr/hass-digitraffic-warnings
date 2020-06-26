package fi.digitraffic.updaters.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Announcement {
    public String title;
    public String comment;

    public List<String> features;
}
