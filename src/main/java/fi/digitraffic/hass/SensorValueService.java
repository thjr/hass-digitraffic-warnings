package fi.digitraffic.hass;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.digitraffic.updaters.data.RoadworkData;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

@ApplicationScoped
public class SensorValueService {
    private static final String HASSIO_ADDRESS = "hassio/homeassistant";
    private static final Logger LOG = LoggerFactory.getLogger(SensorValueService.class);

    private final boolean skipWrite;
    private final String hassioToken;

    public SensorValueService(@ConfigProperty(name = "digitraffic.hass.token") final String token,
                              @ConfigProperty(name = "digitraffic.skip_write") final boolean skipWrite) {
        this.skipWrite = skipWrite;
        this.hassioToken = token;
    }

    public int postRoadwork(final RoadworkData d) throws MalformedURLException {
        final URL url = new URL(String.format("http://%s/api/states/sensor.digitraffic_roadwork_%s", HASSIO_ADDRESS, d.situationId));
        final Map<String, String> attributes = new HashMap<>();
        final HassStateData data = new HassStateData(d, attributes);

        return post(url, data);
    }

    private int post(final URL url, final HassStateData data) {
        LOG.info("posting to {}", url.getPath());

        if(skipWrite) {
            return 200;
        }
        try {
            final ObjectMapper objectMapper = new ObjectMapper();

            final String message = objectMapper.writeValueAsString(data);
            final HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("X-HA-Access", hassioToken);
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getOutputStream().write(message.getBytes());
            con.connect();

            final int httpCode = con.getResponseCode();

            if(httpCode != HTTP_OK) {
                LOG.error("Posting to {} returned {}", url.getPath(), httpCode);
            }

            return httpCode;
        } catch(final Exception e) {
            LOG.error("Exception posting to " + url.getPath(), e);

            return -1;
        }
    }

    private static class HassStateData {
        final Object state;
        final Map<String, String> attributes;

        private HassStateData(final Object state, final Map<String, String> attributes) {
            this.state = state;
            this.attributes = attributes;
        }
    }
}
