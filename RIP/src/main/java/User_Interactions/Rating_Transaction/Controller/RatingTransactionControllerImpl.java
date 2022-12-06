package User_Interactions.Rating_Transaction.Controller;

import Story.Dao.StoryRepoImpl;
import User_Interactions.Rating_Transaction.Dao.RatingTransactionRepoImpl;
import User_Interactions.Rating_Transaction.Service.RatingTransactionService;
import User_Interactions.Rating_Transaction.Service.RatingTransactionServiceImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response rateStory(String ratingInfo) {
        return Response.status(Response.Status.OK).entity(rating_transaction_service.rateStory(ratingInfo)).build();
    }

}
