package Category.Controller;

import Category.Dao.CategoryRepoImpl;
import Category.Model.Category;
import Category.Service.CategoryService;
import Category.Service.CategoryServiceImpl;
import Story.Model.Story;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.json.simple.JSONObject;

@Path("/Category")
public class CategoryControllerImpl{

    private final CategoryService categoryService;

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
        return Response.status(Response.Status.OK).entity(categoryService.addCategoriesToStory((List)jsonObject.get("categories"), (Story)jsonObject.get("story"))).build();
    }

    @Path("/topForMonth")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topCategoriesForMonth() {
        return Response.status(Response.Status.OK).entity(categoryService.topCategoriesForTheMonth()).build();
    }

}
