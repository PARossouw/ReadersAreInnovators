
package RestClientRemoteController;

import Story.Model.Story;
import User.Model.Reader;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;


public class RestClientRating {

    private String url;
    private Client restClient;
    private WebTarget webTarget;
    private ObjectMapper mapper;

    public RestClientRating(String url) {
        this.url = url + "/RIP/Rating";
        this.mapper = new ObjectMapper();
    }
    
    //has 3 args, can use either a map inside a map or a list?
    //@FormParam must be put in the aruguments on the rest controller side
    public Double rateStory(Story story, Reader reader, Integer rating) {
        String uri = url + "/rateStory";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        HashMap<Reader, Story> rateInfo = new HashMap();
        rateInfo.put(reader, story);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(rateInfo)));
        return response.readEntity(Double.class);
    }
    
    private String toJsonString(Object o) {
        return mapper.writeValueAsString(o);
    }
}

