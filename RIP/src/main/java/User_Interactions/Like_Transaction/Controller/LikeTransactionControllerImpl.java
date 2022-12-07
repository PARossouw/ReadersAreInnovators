package User_Interactions.Like_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Dao.LikeTransactionRepoImpl;
import User_Interactions.Like_Transaction.Service.LikeTransactionServiceImpl;
import User_Interactions.Like_Transaction.Service.LikeTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;

@Path("/Like")
public class LikeTransactionControllerImpl {

    private final LikeTransactionService likeTransactionService;
    ObjectMapper mapper = new ObjectMapper();

    public LikeTransactionControllerImpl() {
        this.likeTransactionService = new LikeTransactionServiceImpl(new LikeTransactionRepoImpl());
    }

    @Path("/likeStory")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response likeStory(JSONObject jsonObject) {
        Reader reader = mapper.convertValue(jsonObject.get("reader"), Reader.class);
        Story story = mapper.convertValue(jsonObject.get("story"), Story.class);
        return Response.status(Response.Status.OK).entity(likeTransactionService.likeStory(reader, story)).build();
    }

    @Path("/allLikes/{yearMonth}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLikesInPeriod(@PathParam("yearMonth") String month) {
        return Response.status(Response.Status.OK).entity(likeTransactionService.getAllLikesInPeriod(month)).build();
    }
}
