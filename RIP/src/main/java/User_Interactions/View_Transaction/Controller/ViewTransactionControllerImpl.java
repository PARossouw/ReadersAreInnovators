package User_Interactions.View_Transaction.Controller;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.View_Transaction.Dao.ViewTransactionRepoImpl;
import User_Interactions.View_Transaction.Service.ViewTransactionService;
import User_Interactions.View_Transaction.Service.ViewTransactionServiceImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/View")
public class ViewTransactionControllerImpl {

    private final ViewTransactionService view_transaction_service ;

    public ViewTransactionControllerImpl() {
        this.view_transaction_service = new ViewTransactionServiceImpl(new ViewTransactionRepoImpl());
    }
    
    @Path("/addToStory")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStory(Story story, Reader reader) {
        return Response.status(Response.Status.OK).entity(view_transaction_service.viewStory(story, reader)).build();
    }
    
}
