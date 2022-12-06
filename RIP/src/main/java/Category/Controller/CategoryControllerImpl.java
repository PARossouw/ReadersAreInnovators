package Category.Controller;

import Category.Dao.CategoryRepoImpl;
import Category.Service.CategoryService;
import Category.Service.CategoryServiceImpl;
import Story.Model.Story;
import User.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.json.simple.JSONObject;

@Path("/Category")
public class CategoryControllerImpl {

    private final CategoryService categoryService;
    private ObjectMapper mapper;

    public CategoryControllerImpl() {
        this.categoryService = new CategoryServiceImpl(new CategoryRepoImpl());
    }

    @Path("/displayAll")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response displayAllCategories() {
        return Response.status(Response.Status.OK).entity(categoryService.displayAllCategories()).build();
    }

    @Path("/addToStory")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCategoriesToStory(JSONObject jsonObject) {
        return Response.status(Response.Status.OK).entity(categoryService.addCategoriesToStory(
                (List) jsonObject.get("categories"), 
                mapper.convertValue(jsonObject.get("story"), Story.class))).build();
    }

    @Path("/topForMonth/{month}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topCategoriesForMonth(@PathParam("month") String month) {
        return Response.status(Response.Status.OK).entity(categoryService.topCategoriesForTheMonth(month)).build();
    }

    @Path("/preferredCategories/{readerID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPreferredCategories(@PathParam("readerID") Integer readerID) {
        User reader = new User();
        reader.setUserID(readerID);
        return Response.status(Response.Status.OK).entity(categoryService.getPreferredCategories(reader)).build();
    }
}
