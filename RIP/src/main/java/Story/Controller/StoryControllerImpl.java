package Story.Controller;

import Category.Dao.CategoryRepoImpl;
import Category.Model.Category;
import Story.Dao.StoryRepoImpl;
import Story.Model.Story;
import Story.Service.StoryService;
import Story.Service.StoryServiceImpl;
import User.Model.Reader;
import User.Model.Writer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONObject;

@Path("/Story")
public class StoryControllerImpl {

    private final StoryService storyService;
    private ObjectMapper mapper;

    public StoryControllerImpl() {
        this.storyService = new StoryServiceImpl(new StoryRepoImpl(), new CategoryRepoImpl());
    }

    @Path("/search/categories")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStoriesByCategories(JSONObject jsonObject) {
        List<Category> categories = new ArrayList<>();
        
        int size = mapper.convertValue( jsonObject.get("size"), Integer.class);

        for(int i = 0; i < size; i++){
            categories.add(mapper.convertValue(jsonObject.get(i), Category.class));
        }
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

    
    @Path("/viewLikedStories")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewLikedStories(Reader reader){
        return Response.status(Response.Status.OK).entity(storyService.getLikedStory(reader)).build();
    }
    

    @Path("/getFiveStoriesForStoryOfTheDay")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFiveStoriesForStoryOfTheDay() {
        return Response.status(Response.Status.OK).entity(storyService.getFiveStoriesForStoryOfTheDay()).build();

    }
    
    @Path("/getTop20StoriesForMonth")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTop20StoriesForMonth() {
        return Response.status(Response.Status.OK).entity(storyService.getTop20RatedStoriesOfTheMonth()).build();

    }
}
