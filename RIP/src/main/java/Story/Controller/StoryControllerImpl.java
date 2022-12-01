package Story.Controller;

import Category.Dao.CategoryRepoImpl;
import Category.Model.Category;
import Category.Service.CategoryService;
import Category.Service.CategoryServiceImpl;
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
import java.util.List;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/Story")
public class StoryControllerImpl {

    private final StoryService storyService;
    private final CategoryService categoryService;
    private ObjectMapper mapper;

    public StoryControllerImpl() {
        this.storyService = new StoryServiceImpl(new StoryRepoImpl(), new CategoryRepoImpl());
        this.categoryService = new CategoryServiceImpl(new CategoryRepoImpl());
    }

    @Path("/search/categories/{reader}")//"/search/categories/{reader}"
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStoriesByCategories(@PathParam("reader") String reader) {//@PathParam("reader") String reader
        List<Category> categories = new ArrayList<>();
        
        Reader r = new Reader();
        r.setUserID(Integer.parseInt(reader));
        
        //works
        categories = categoryService.getPreferredCategories(r);

        //categories = reader.getPreferredCategories();
        List<Story> stories = new ArrayList<>();
        stories = storyService.searchStoriesByCategories(categories);

        return Response.status(Response.Status.OK).entity(stories).build();

        //hardcoding
//        List<Story> sts = new ArrayList<>();
//        Story story1 = new Story();
//        story1.setTitle(categories.get(0).getName());
//        sts.add(story1);
//        
//        
//        
//        Story story2 = new Story();
//        story2.setTitle("Pieter McJeter2");
//        sts.add(story2);
//        
//        Story story3 = new Story();
//        story3.setTitle("Pieter McJeter3");
//        sts.add(story3);
//        
//        return Response.status(Response.Status.OK).entity(sts).build();
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
        // String goodStory = "Mellisa saved the story";
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
