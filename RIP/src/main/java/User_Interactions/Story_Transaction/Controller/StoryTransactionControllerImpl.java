package User_Interactions.Story_Transaction.Controller;

import Story.Dao.StoryRepoImpl;
import Story.Model.Story;
import User.Dao.UserRepoImpl;
import User.Model.Editor;
import User.Model.Writer;
import User_Interactions.Story_Transaction.Dao.StoryTransactionRepoImpl;
import User_Interactions.Story_Transaction.Service.Story_TransactionService;
import User_Interactions.Story_Transaction.Service.Story_TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;

@Path("/StoryTransaction")
public class StoryTransactionControllerImpl {

    private final Story_TransactionService storyTransactionService;
    ObjectMapper mapper = new ObjectMapper();

    public StoryTransactionControllerImpl() {
        this.storyTransactionService = new Story_TransactionServiceImpl(new StoryRepoImpl(), new StoryTransactionRepoImpl(), new UserRepoImpl());
    }

    @Path("/approve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response approvePendingStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(
                storyTransactionService.approvePendingStory(
                        mapper.convertValue(jsonObject.get("editor"), Editor.class),
                        mapper.convertValue(jsonObject.get("story"), Story.class))).build();
    }

    @Path("/reject")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectPendingStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(storyTransactionService.rejectPendingStory(
                mapper.convertValue(jsonObject.get("editor"), Editor.class),
                mapper.convertValue(jsonObject.get("story"), Story.class))).build();
    }

    @Path("/removeWriter")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeStoryByWriter(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(storyTransactionService.removeStoryByWriter(
                mapper.convertValue(jsonObject.get("writer"), Writer.class),
                mapper.convertValue(jsonObject.get("story"), Story.class))).build();
    }

}
