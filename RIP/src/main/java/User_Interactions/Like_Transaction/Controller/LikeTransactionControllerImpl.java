package User_Interactions.Like_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Dao.LikeTransactionRepoImpl;
import User_Interactions.Like_Transaction.Service.LikeTransactionServiceImpl;
import java.util.Calendar;
import java.util.Map;
import User_Interactions.Like_Transaction.Service.LikeTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
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
        
        //Reader reader = (Reader) jsonObject.get("story");
        //Story story = (Story) jsonObject.get("story");
        Reader reader = new Reader();
        Story story = new Story();
        
        reader = mapper.convertValue(jsonObject.get("reader"), Reader.class);
        story = mapper.convertValue(jsonObject.get("story"), Story.class);
        
        
      //  String returnLike = "Like Thicc Bitches";
        
     //   return Response.status(Response.Status.OK).entity(returnLike).build();
        return Response.status(Response.Status.OK).entity(likeTransactionService.likeStory(reader, story)).build();
        
       // return Response.status(Response.Status.OK).entity(likeTransactionService.likeStory((Reader)jsonObject.get("reader"), (Story)jsonObject.get("story"))).build();
    }


    @Path("/allLikes")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllLikesInPeriod(Calendar month) {
        return Response.status(Response.Status.OK).entity(likeTransactionService.getAllLikesInPeriod(month)).build();
    }
}
