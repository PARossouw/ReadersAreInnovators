package Story.Controller;

import Category.Model.Category;
import Story.Dao.StoryRepoImpl;
import Story.Model.Story;
import Story.Service.StoryService;
import Story.Service.StoryServiceImpl;
import User.Model.Writer;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/Story")
public class StoryControllerImpl {

    private final StoryService storyService;

    public StoryControllerImpl() {
        this.storyService = new StoryServiceImpl(new StoryRepoImpl());
    }

    @Path("/search/categories")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStoriesByCategories(List<Category> categories) {
        return Response.status(Response.Status.OK).entity(storyService.searchStoriesByCategories(categories)).build();
    }

    @Path("/viewByWriter")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStoriesByWriter(Writer writer) {
        return Response.status(Response.Status.OK).entity(storyService.viewStoriesByWriter(writer)).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveStory(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.saveStory(story)).build();
    }

    @Path("/submit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitCompletedStory(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.submitCompletedStory(story)).build();
    }

    @Path("/retrieve")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStory(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.retrieveStory(story)).build();
    }

    @Path("/search")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForStory(String StoryParameter) {
        return Response.status(Response.Status.OK).entity(storyService.searchForStory(StoryParameter)).build();
    }
}