package Story.Controller;

import Category.Dao.CategoryRepoImpl;
import Category.Model.Category;
import Story.Dao.StoryRepoImpl;
import Story.Model.Story;
import Story.Service.StoryService;
import Story.Service.StoryServiceImpl;
import User.Model.Reader;
import User.Model.User;
import User.Model.Writer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.AbstractList;
import java.util.ArrayList;

import java.util.Calendar;

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
    public Response searchStoriesByCategories(Reader reader) {
        List<Category> categories = new ArrayList<>();
        categories = reader.getPreferredCategories();

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
        String goodStory = "Mellisa saved the story";
        return Response.status(Response.Status.OK).entity(storyService.saveStory(story)).build();
//        return Response.status(Response.Status.OK).entity(goodStory).build();
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
//        return Response.status(Response.Status.OK).entity(storyService.retrieveStory(story)).build();

        //Testing purposes below only
        Story storyObj = new Story();
        storyObj.setStoryID(420);
        storyObj.setTitle("DAO practice Title");
        storyObj.setAvgRating(6.0);
        storyObj.setWriter("DAO Pratice Author Tarun Sing");
        storyObj.setViews(69);
        storyObj.setLikes(20);
        storyObj.setDescription("DAO Practice Description");
        storyObj.setBody("DAO Practice Body");

//        return storyObj;
        return Response.status(Response.Status.OK).entity(storyObj).build();

    }

    @Path("/getStory/{storyID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response storySearch(@PathParam("storyID") String storySearch) {

//        writers = userService.writerSearch(writerSearch);

    
      Story storyObj = new Story();
      storyObj.setStoryID(Integer.parseInt(storySearch));
      
      storyObj = storyService.retrieveStory(storyObj);

        return Response.status(Response.Status.OK).entity(storyObj).build();
        

//        Story storyObj = new Story();
//        storyObj.setStoryID(Integer.parseInt(storySearch));
//
//        storyObj = storyService.retrieveStory(storyObj);
//        storyObj.setStoryID(420);
//        storyObj.setTitle("DAO practice Title");
//        storyObj.setAvgRating(8.0);
//        storyObj.setWriter("Controller Pratice Author Tarun Sing");
//        storyObj.setViews(30);
//        storyObj.setLikes(300);
//        storyObj.setDescription("ControllerPractice Description");
//        storyObj.setBody("DAO Practice Body");


//Story storyObj = new Story();
//storyObj.setStoryID(420);
//        storyObj.setTitle("DAO practice Title");
//        storyObj.setAvgRating(2.9);
//        storyObj.setWriter("DAO Pratice Author Tarun Sing");
//        storyObj.setDescription("DAO Practice Description");
//        storyObj.setBody("DAO Practice Body");
//        storyObj.setViews(504);
//        storyObj.setLikes(88);
//
//        return Response.status(Response.Status.OK).entity(storyObj).build();



    }

    @Path("/search")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForStory(String StoryParameter) {
        return Response.status(Response.Status.OK).entity(storyService.searchForStory(StoryParameter)).build();
    }

    @Path("/viewLikedStories/{readerID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewLikedStories(@PathParam("readerID") Integer readerID) {
        User reader = new User();
        reader.setUserID(readerID);
        List<Story> likedStories = storyService.getLikedStory(reader);
        return Response.status(Response.Status.OK).entity(likedStories).build();
    }

    @Path("/getPendingStories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPendingStories() {
        return Response.status(Response.Status.OK).entity(storyService.getPendingStories()).build();
    }

    @Path("/getStoriesForStoryOfTheDay")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoriesForStoryOfTheDay() {
        return Response.status(Response.Status.OK).entity(storyService.getStoriesForStoryOfTheDay()).build();
    }

    @Path("/getTop20StoriesForMonth")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTop20StoriesForMonth() {
        return Response.status(Response.Status.OK).entity(storyService.getTop20RatedStoriesOfTheMonth()).build();

    }
    
    @Path("/turnOffComments")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response turnOffComments(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.turnOffComments(story)).build();
//    String x = "test 3";
    }
}
