package fi.digitraffic.updaters.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeatureCollection {
    public List<Feature> features;

    public FeatureCollection() {}
}
