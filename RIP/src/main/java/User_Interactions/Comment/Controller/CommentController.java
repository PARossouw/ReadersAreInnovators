package User_Interactions.Comment.Controller;

import Story.Model.Story;
import User_Interactions.Comment.Model.Comment;
import java.util.List;

/**
 *
 * @author pieter, ryan, tarun, anton
 */
public interface CommentController {

    String commentOnAStory(Comment comment);

    List<Comment> getAllComments(Story story);

}
