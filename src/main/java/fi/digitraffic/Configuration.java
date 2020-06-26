package fi.digitraffic;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Configuration {
    private final boolean roadworks;

    public Configuration() {
        roadworks = true;
    }

    public String getConfig() {
        return "moi1moi";
    }

    public boolean isRoadworks() {
        return roadworks;
    }
}
