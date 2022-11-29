package User_Interactions.Comment.Controller;

import Story.Model.Story;
import User.Model.Writer;
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
import java.util.ArrayList;
import java.util.List;

@Path("/Comment")
public class CommentControllerImpl{
    
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
    
    
    //"/getAllComments/{storySearch}"
    @Path("/getAllComments/{storySearch}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response writerSearch(@PathParam("storySearch")String storySearch) {
        List<Comment> comments = new ArrayList();
        this.story = new Story();
        this.story.setStoryID(Integer.parseInt(storySearch));
        comments = commentService.getAllComments(this.story);
        return Response.status(Response.Status.OK).entity(comments).build();
        
//        
        
        
                // << Test Code 
//        List<Comment> allStoryComments = new ArrayList();
//            Comment testComment = new Comment();
//                testComment.setCommentBody("Good StoryLine ya bish");
//
//                Comment testComment2 = new Comment();
//                testComment2.setCommentBody("Nice Plot twist");
//
//                Comment testComment3 = new Comment();
//                testComment3.setCommentBody("Long and insightful");
//
//                allStoryComments.add(testComment);
//                allStoryComments.add(testComment2);
//                allStoryComments.add(testComment3);
//            //return allStoryComments;
//             return Response.status(Response.Status.OK).entity(allStoryComments).build();
        // >> End of Test code
       
    }
    
    
    

}