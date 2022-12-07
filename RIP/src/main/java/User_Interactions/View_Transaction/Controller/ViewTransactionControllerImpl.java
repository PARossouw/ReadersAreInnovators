package User_Interactions.View_Transaction.Controller;

import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.View_Transaction.Dao.ViewTransactionRepoImpl;
import User_Interactions.View_Transaction.Service.ViewTransactionService;
import User_Interactions.View_Transaction.Service.ViewTransactionServiceImpl;
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

@Path("/View")
public class ViewTransactionControllerImpl {

    private final ViewTransactionService view_transaction_service;
    private ObjectMapper mapper;

    public ViewTransactionControllerImpl() {
        this.view_transaction_service = new ViewTransactionServiceImpl(new ViewTransactionRepoImpl());
    }

    @Path("/addToStory")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(view_transaction_service.viewStory(
                mapper.convertValue(jsonObject.get("story"), Story.class),
                mapper.convertValue(jsonObject.get("reader"), Reader.class))).build();
    }

    @Path("/AllStoryViewsInPeriod/{startDate}/{endDate}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStoryViewsInPeriod(@PathParam("startDate") String startDate, @PathParam("endDate") String endDate) {
        return Response.status(Response.Status.OK).entity(view_transaction_service.getAllStoryViewsInPeriod(startDate, endDate)).build();
    }

}
