
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

public class RestClientLike {

    private String url;
    private Client restClient;
    private WebTarget webTarget;
    private ObjectMapper mapper;

    public RestClientLike(String url) {
        this.url = url + "/RIP/Like";
        this.mapper = new ObjectMapper();
    }

    //@FormParam must be put in the aruguments on the rest controller side
    public String likeStory(Reader reader, Story story) {
        String uri = url + "/likeStory";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        HashMap<Reader, Story> likeInfo = new HashMap();
        likeInfo.put(reader, story);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(likeInfo)));
        return response.readEntity(String.class);
    }

    public Map<Story, Integer> getAllLikesInPeriod(Calendar startDate) {
        String uri = url + "/allLikes";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);

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
