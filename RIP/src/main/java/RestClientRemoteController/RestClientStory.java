package RestClientRemoteController;

import Category.Model.Category;
import Story.Model.Story;
import Story.Service.StoryService;
import User.Model.Writer;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClientStory {

    private String url;
    private Client restClient;
    private WebTarget webTarget;
    private ObjectMapper mapper;

    public RestClientStory(String url) {
        this.url = url + "/RIP/Story";
        this.mapper = new ObjectMapper();
    }

    public List<Story> searchStoriesByCategories(List<Category> categories) {
        String uri = url + "/search/categories/{categories}";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri).resolveTemplate("categories", categories);
        List<Story> stories = null;
        stories = Arrays.asList(mapper.readValue(webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), Story[].class));
        return stories;

    }

    public List<Story> viewStoriesByWriter(Writer writer) {
        String uri = url + "/viewByWriter/{writer}";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri).resolveTemplate("writer", writer);
        List<Story> stories = null;
        stories = Arrays.asList(mapper.readValue(webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), Story[].class));
        
        return stories;
    }

    public String saveStory(Story story) {
        String uri = url + "/save";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(story)));
        return response.readEntity(String.class);
    }

    public String submitCompletedStory(Story story) {
        String uri = url + "/submit";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(story)));
        return response.readEntity(String.class);
    }

    public Story retrieveStory(Story story) {
        String uri = url + "/retrieve";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(story)));
        return response.readEntity(Story.class);
    }

    public List<Story> searchForStory(String storyParameter) {
        String uri = url + "/search/";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri + storyParameter);
        List<Story> stories = null;
        stories = Arrays.asList(mapper.readValue(webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), Story[].class));
        return stories;
    }

    private String toJsonString(Object o) {
        return mapper.writeValueAsString(o);
    }

}
