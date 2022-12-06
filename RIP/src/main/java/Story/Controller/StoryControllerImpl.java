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

    @Path("/search/categories/{reader}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStoriesByCategories(@PathParam("reader") String reader) {
        Reader r = new Reader();
        r.setUserID(Integer.parseInt(reader));
        return Response.status(Response.Status.OK).entity(storyService.searchStoriesByCategories(categoryService.getPreferredCategories(r))).build();
    }

    @Path("/search/categories/random/{categoryStr}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStoriesByRandomCategoriesChosen(@PathParam("categoryStr") String categoryStr) {
        List<Category> categories = new ArrayList<>();

        String[] categoryIDs = categoryStr.split(":");
        Category category = new Category();

        for (String catID : categoryIDs) {
            category.setCategoryID(Integer.parseInt(catID));
            categories.add(category);
        }

        return Response.status(Response.Status.OK).entity(storyService.searchStoriesByCategories(categories)).build();
    }

    @Path("/search/stories/{searchText}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchStoriesByTitleorAuthor(@PathParam("searchText") String searchText) {
        return Response.status(Response.Status.OK).entity(storyService.searchForStory(searchText)).build();
    }

    @Path("/viewByWriter/{writerID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewStoriesByWriter(@PathParam("writerID") Integer writerID) {
        User writer = new User();
        writer.setUserID(writerID);
        return Response.status(Response.Status.OK).entity(storyService.viewStoriesByWriter(writer)).build();
    }

    @Path("/viewLikedStories/{readerID}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewLikedStories(@PathParam("readerID") Integer readerID) {
        User reader = new User();
        reader.setUserID(readerID);
        return Response.status(Response.Status.OK).entity(storyService.getLikedStory(reader)).build();
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

    @Path("/getStory/{storyID}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response storySearch(@PathParam("storyID") String storySearch) {
        Story storyObj = new Story();
        storyObj.setStoryID(Integer.parseInt(storySearch));

        return Response.status(Response.Status.OK).entity(storyService.retrieveStory(storyObj)).build();
    }

    @Path("/search")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchForStory(String storyParameter) {
        return Response.status(Response.Status.OK).entity(storyService.searchForStory(storyParameter)).build();
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

    @Path("/getTop20StoriesForMonth/{month}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Response getTop20StoriesForMonth(@PathParam("month") String month) {
        return Response.status(Response.Status.OK).entity(storyService.getTop20RatedStoriesOfTheMonth(month)).build();
    }

    @Path("/getTop20StoriesForMonth")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTop20StoriesForMonth() {
        return Response.status(Response.Status.OK).entity(storyService.getTop20RatedStoriesOfTheMonth()).build();
    }

    @Path("/getRandomApprovedStories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRandomApprovedStories() {
        return Response.status(Response.Status.OK).entity(storyService.getRandomApprovedStories()).build();
    }

    @Path("/turnOffComments")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response turnOffComments(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.turnOffComments(story)).build();
    }

    @Path("/makeStoryOfTheDay")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response makeStoryOfTheDay(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.makeStoryOfTheDay(story)).build();
    }

    @Path("/getStoryOfTheDay")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoryOfTheDay() {
        return Response.status(Response.Status.OK).entity(storyService.getStoryOfTheDay()).build();
    }

    @Path("/story/block")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response blockStory(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.blockStory(story)).build();
    }

    @Path("/story/incrementViews")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response incrementViews(Story story) {
        return Response.status(Response.Status.OK).entity(storyService.incrementViews(story)).build();
    }
}
