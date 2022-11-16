package RestClientRemoteController;

import Category.Model.Category;
import Story.Model.Story;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClientCategory {
    private String url;
    private Client restClient;
    private WebTarget webTarget;
    private ObjectMapper mapper;

    public RestClientCategory(String url) {
        this.url = url + "/RIP/Category";
        this.mapper = new ObjectMapper();
    }

    public List<Category> displayAllCategories() {
        String uri = url + "/displayAll";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        
        List<Category> allCategories = null;
        allCategories = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<List<Category>>() {
        });
        return allCategories;
    }

    public String addCategoriesToStory(Story story, List<Category> categories) {
        String uri = url + "/addToStory";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Map<Story, List> categoriesToAdd = new HashMap();
        categoriesToAdd.put(story, categories);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(categoriesToAdd)));
        return response.readEntity(String.class);
    }

    public List<Category> topCategoriesForTheMonth() {
        String uri = url + "/topForMonth";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);

        List<Category> topCategories = null;
        topCategories = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<List<Category>>() {
        });
        return topCategories;
    }
    
    private String toJsonString(Object o) {
        return mapper.writeValueAsString(o);
    }
}
