package User_Interactions.Story_Transaction.Controller;

import Story.Model.Story;
import User.Model.Editor;
import User.Model.Writer;
import User_Interactions.Story_Transaction.Service.Story_TransactionService;
import User_Interactions.Story_Transaction.Service.Story_TransactionServiceImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;

@Path("/StoryTransaction")
public class StoryTransactionControllerImpl {
    
    private final Story_TransactionService storyService;

    public StoryTransactionControllerImpl() {
        this.storyService = new Story_TransactionServiceImpl();
    }

    @Path("/approve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response approvePendingStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(storyService.approvePendingStory((Editor)jsonObject.get("editor"), (Story)jsonObject.get("story"))).build();
        
        
//        JSONObject jObject = new JSONObject();
//        Editor editor = new Editor();
//        Story story = new Story();
//        
//        jObject.put("editor", editor);
//        jObject.put("story", story);
//        return Response.status(Response.Status.OK).entity(storyService.approvePendingStory((Editor)jObject.get("editor"), (Story)jObject.get("story"))).build();
        
        
        
    }

    @Path("/reject")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectPendingStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(storyService.rejectPendingStory((Editor)jsonObject.get("editor"), (Story)jsonObject.get("story"))).build();
    }

    @Path("/removeWriter")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeStoryByWriter(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(storyService.removeStoryByWriter((Writer)jsonObject.get("writer"), (Story)jsonObject.get("story"))).build();
    }
    
}
