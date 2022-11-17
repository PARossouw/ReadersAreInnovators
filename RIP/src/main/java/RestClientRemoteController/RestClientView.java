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

    //@FormParam must be put in the aruguments on the rest controller side
    public Map<Story, Integer> getAllStoryViewsInPeriod(Calendar startDate, Calendar endDate) {
        String uri = url + "/AllStoryViewsInPeriod/{periodInfo}";
        restClient = ClientBuilder.newClient();
        HashMap<Calendar, Calendar> periodInfo = new HashMap();
        periodInfo.put(startDate, endDate);
        webTarget = restClient.target(uri).resolveTemplate("periodInfo", periodInfo);

        Map<Story, Integer> stories = null;
        Response response = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<Map<Story, Integer>>() {
        });
        return response.readEntity(Map.class);
    }

    private String toJsonString(Object o) {
        return mapper.writeValueAsString(o);
    }
}
