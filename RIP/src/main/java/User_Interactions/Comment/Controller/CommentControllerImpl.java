package User_Interactions.Comment.Controller;

import Story.Model.Story;
import User_Interactions.Comment.Model.Comment;
import User_Interactions.Comment.Service.CommentService;
import java.util.List;


public class CommentControllerImpl implements CommentController{
    
    private CommentService commentService;

    @Override
    public String commentOnAStory(Comment comment) {
        return commentService.commentOnAStory(comment);
    }

    @Override
    public List<Comment> getAllComments(Story story) {
        return commentService.getAllComments(story);
    }

}