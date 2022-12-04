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
import java.util.HashMap;
import java.util.TreeMap;
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
        return Response.status(Response.Status.OK).entity(view_transaction_service.viewStory((Story) jsonObject.get("story"), (Reader) jsonObject.get("reader"))).build();
    }

    @Path("/AllStoryViewsInPeriod/{startDate}/{endDate}")
    @GET
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStoryViewsInPeriod(@PathParam("startDate")String startDate, @PathParam("endDate")String endDate) {
        return Response.status(Response.Status.OK).entity(view_transaction_service.getAllStoryViewsInPeriod(startDate, endDate)).build();


        //hardcoding - uncomment above to make it work again
//        HashMap<Story, Integer> jsonObject = new JSONObject();
//////        
//        Story s1 = new Story(); s1.setTitle("73 1st story ");
//        Story s2 = new Story(); s2.setTitle("2nd story " + startDate);
//        Story s3 = new Story(); s3.setTitle("3rd story ");
//        
//        int a = 5;
//        int b = 10;
//        int c = 15;
//        
//        jsonObject.put(s1, a);
//        jsonObject.put(s2, b);
//        jsonObject.put(s3, c);
//        return Response.status(Response.Status.OK).entity(jsonObject).build();
//        
        
        
        //return hardCodeMap;
        
        

        
          //return Response.status(Response.Status.OK).entity(hardCodeMap).build();
    }

}
