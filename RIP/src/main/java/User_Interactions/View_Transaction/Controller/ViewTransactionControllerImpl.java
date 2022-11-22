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
import java.util.Calendar;
import java.util.Map;
import org.json.simple.JSONObject;

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
    public Response viewStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(view_transaction_service.viewStory((Story)jsonObject.get("story"), (Reader) jsonObject.get("reader"))).build();
    }

    @Path("/AllStoryViewsInPeriod")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStoryViewsInPeriod(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(view_transaction_service.getAllStoryViewsInPeriod((Calendar)jsonObject.get("startDate"), (Calendar)jsonObject.get("endDate"))).build();
    }
    
    
}
