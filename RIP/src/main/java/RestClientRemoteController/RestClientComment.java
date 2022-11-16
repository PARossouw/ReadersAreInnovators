
package RestClientRemoteController;
import Story.Model.Story;
import User_Interactions.Comment.Model.Comment;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RestClientComment {

    private String url;
    private Client restClient;
    private WebTarget webTarget;
    private ObjectMapper mapper;

    public RestClientComment(String url) {
        this.url = url + "/RIP/Comment";
        this.mapper = new ObjectMapper();
    }
    
     public String commentOnAStory(Comment comment) {
        
       String uri = url + "/add";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);
        Response response = null;
        response = webTarget.request().post(Entity.json(toJsonString(comment)));
        return response.readEntity(String.class);
    }

  
     // Not sure about this one **
    public List<Comment> getAllComments(Story story) {
        
        String uri = url + "/getAll";
        restClient = ClientBuilder.newClient();
        webTarget = restClient.target(uri);

        List<Comment> comments = null;
    
        comments = mapper.readValue(
                webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class), new TypeReference<List<Comment>>() {
        });
        return comments;
    }
    
}
