package User_Interactions.Rating_Transaction.Controller;

import Story.Dao.StoryRepoImpl;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Dao.RatingTransactionRepoImpl;
import User_Interactions.Rating_Transaction.Service.RatingTransactionService;
import User_Interactions.Rating_Transaction.Service.RatingTransactionServiceImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.json.simple.JSONObject;

@Path("/Rating")
public class RatingTransactionControllerImpl {

    private final RatingTransactionService rating_transaction_service;

    public RatingTransactionControllerImpl() {
        this.rating_transaction_service = new RatingTransactionServiceImpl(new RatingTransactionRepoImpl(), new StoryRepoImpl());
    }

    @Path("/rateStory")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response rateStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(rating_transaction_service.rateStory((Story)jsonObject.get("story"), (Reader)jsonObject.get("reader"), (Integer)jsonObject.get("rating"))).build();
    }

}
