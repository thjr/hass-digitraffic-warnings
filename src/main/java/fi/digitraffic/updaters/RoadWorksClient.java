package fi.digitraffic.updaters;

import fi.digitraffic.updaters.model.FeatureCollection;
import org.jboss.resteasy.plugins.interceptors.AcceptEncodingGZIPFilter;
import org.jboss.resteasy.plugins.interceptors.GZIPDecodingInterceptor;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@ApplicationScoped
public class RoadWorksClient {
    private static final String DIGITRAFFIC_BASE_URL = "https://tie-test.digitraffic.fi/api/v2/data/traffic-datex2/";

    private final Client client = ClientBuilder.newBuilder() // Activate gzip compression on client:
            .register(AcceptEncodingGZIPFilter.class)
            .register(GZIPDecodingInterceptor.class)
            .build();

    public FeatureCollection getAllRoadWorks() {
        return client.target(DIGITRAFFIC_BASE_URL + "traffic-incident.json")
                .request()
                .get(FeatureCollection.class);
    }

//    @Path("traffic-incident.json")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @GZIP
//    String getAllRoadWorks(@HeaderParam("user-agent") final String userAgent,
//                                      @HeaderParam("digitraffic-user") final String digitrafficUser,
//                                      @HeaderParam("accept-encoding") final String acceptEncoding);
}
