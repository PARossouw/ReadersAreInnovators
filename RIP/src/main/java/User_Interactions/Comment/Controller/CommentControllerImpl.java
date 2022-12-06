package User_Interactions.Comment.Controller;

import Story.Model.Story;
import User_Interactions.Comment.Dao.CommentRepoImpl;
import User_Interactions.Comment.Model.Comment;
import User_Interactions.Comment.Service.CommentService;
import User_Interactions.Comment.Service.CommentServiceImpl;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Comment")
public class CommentControllerImpl {

    private final CommentService commentService;
    private Story story;

    public CommentControllerImpl() {
        this.commentService = new CommentServiceImpl(new CommentRepoImpl());
    }

    @Path("/add")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response commentOnAStory(Comment comment) {
        return Response.status(Response.Status.OK).entity(commentService.commentOnAStory(comment)).build();
    }

    @Path("/getAll")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllComments(Story story) {
        return Response.status(Response.Status.OK).entity(commentService.getAllComments(story)).build();
    }

    @Path("/getAllComments/{storySearch}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoryComments(@PathParam("storySearch") String storySearch) {
        this.story = new Story();
        this.story.setStoryID(Integer.parseInt(storySearch));
        return Response.status(Response.Status.OK).entity(commentService.getAllComments(this.story)).build();
    }

}
