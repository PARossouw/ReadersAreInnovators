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
    public Response approvePendingStory(Editor editor, Story story) {
        return Response.status(Response.Status.OK).entity(storyService.approvePendingStory(editor, story)).build();
    }

    @Path("/reject")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rejectPendingStory(Editor editor, Story story) {
        return Response.status(Response.Status.OK).entity(storyService.rejectPendingStory(editor, story)).build();
    }

    @Path("/removeWriter")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeStoryByWriter(Writer writer, Story story) {
        return Response.status(Response.Status.OK).entity(storyService.removeStoryByWriter(writer, story)).build();
    }
    
}
