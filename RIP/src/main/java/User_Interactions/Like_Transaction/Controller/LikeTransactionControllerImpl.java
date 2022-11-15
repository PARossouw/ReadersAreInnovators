package User_Interactions.Like_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Dao.LikeTransactionRepoImpl;
import User_Interactions.Like_Transaction.Service.LikeTransactionServiceImpl;
import java.util.Calendar;
import java.util.Map;
import User_Interactions.Like_Transaction.Service.LikeTransactionService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Like")
public class LikeTransactionControllerImpl {

    private final LikeTransactionService likeTransactionService;

    public LikeTransactionControllerImpl() {
        this.likeTransactionService = new LikeTransactionServiceImpl(new LikeTransactionRepoImpl());
    }

    @Path("/likeStory")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeStory(Reader reader, Story story) {
        return Response.status(Response.Status.OK).entity(likeTransactionService.likeStory(reader, story)).build();
    }

    @Path("/change")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response changeLike(Reader reader, Story story) {
        return Response.status(Response.Status.OK).entity(likeTransactionService.likeStory(reader, story)).build();
    }

    @Path("/allLikes")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLikesInPeriod(Calendar month) {
        return Response.status(Response.Status.OK).entity(likeTransactionService.getAllLikesInPeriod(month)).build();
    }
}
