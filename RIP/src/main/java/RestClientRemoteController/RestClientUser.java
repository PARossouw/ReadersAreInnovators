package RestClientRemoteController;

import Category.Model.Category;


import User.Model.Editor;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClientUser {

    private String url;
    private Client restClient;
    private WebTarget webTarget;
    private ObjectMapper mapper;

    public RestClientUser(String url) {
        this.url = url + "/RIP/User";
        this.mapper = new ObjectMapper();
    }

    public User login(User user) {
        String uri = url + "/login";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(user)));
        return response.readEntity(User.class);
    }

    //@FormParam must be put in the aruguments on the rest controller side
    public String addPreferredCategoriesToUser(Reader reader, List<Category> categories) {
        String uri = url + "/categories/add";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        HashMap<Reader, List> preferredCategories = new HashMap();
        preferredCategories.put(reader, categories);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(preferredCategories)));
        return response.readEntity(String.class);
    }

    public String registerUser(User user) {
        String uri = url + "/register";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(user)));
        return response.readEntity(String.class);
    }

    public String blockWriter(Writer writer) {
        String uri = url + "/writer/block";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(writer)));
        return response.readEntity(String.class);
    }

    public String addNewEditor(Editor editor) {
        String uri = url + "/editor/add";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(editor)));
        return response.readEntity(String.class);
    }

    public String removeEditor(Editor editor) {
        String uri = url + "/editor/remove";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(editor)));
        return response.readEntity(String.class);
    }

    public Map<Writer, Integer> topWriters() {
        String uri = url + "/writer/top";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);

        Map<Writer, Integer> writers = null;
        writers = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<Map<Writer, Integer>>() {
        });
        return writers;
    }

    public Map<Writer, Integer> topRejectedWritersForMonth() {
        String uri = url + "/writer/mostRejected";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);

        Map<Writer, Integer> writers = null;
        writers = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<Map<Writer, Integer>>() {
        });
        return writers;
    }

    public Map<Writer, Integer> topApprovingEditors() {
        String uri = url + "/editor/mostApproving";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);

        Map<Writer, Integer> writers = null;
        writers = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<Map<Writer, Integer>>() {
        });
        return writers;
    }

    private String toJsonString(Object o) {
        return mapper.writeValueAsString(o);
    }
}
