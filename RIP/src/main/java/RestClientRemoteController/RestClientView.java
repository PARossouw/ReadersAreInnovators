package RestClientRemoteController;

import Story.Model.Story;
import User.Model.Reader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import jdk.internal.org.objectweb.asm.TypeReference;

public class RestClientView {

    private String url;
    private Client restClient;
    private WebTarget webTarget;
    private ObjectMapper mapper;

    public RestClientView(String url) {
        this.url = url + "/RIP/View";
        this.mapper = new ObjectMapper();
    }

    //@FormParam must be put in the aruguments on the rest controller side
    public String viewStory(Story story, Reader reader) {
        String uri = url + "/addToStory";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        HashMap<Story, Reader> viewInfo = new HashMap();
        viewInfo.put(story, reader);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(viewInfo)));
        return response.readEntity(String.class);
    }

    //not sure about this one
    //@FormParam must be put in the aruguments on the rest controller side
    public Map<Story, Integer> getAllStoryViewsInPeriod(Calendar startDate, Calendar endDate) {
        String uri = url + "/AllStoryViewsInPeriod";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        HashMap<Calendar, Calendar> periodInfo = new HashMap();
        periodInfo.put(startDate, endDate);

        Map<Story, Integer> stories = null;
        stories = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<Map<Story, Integer>>() {
        });
        return stories;
    }

    private String toJsonString(Object o) {
        return mapper.writeValueAsString(o);
    }
}
