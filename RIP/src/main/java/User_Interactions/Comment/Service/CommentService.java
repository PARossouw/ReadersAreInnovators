package User_Interactions.Comment.Service;

import Story.Model.Story;
import User_Interactions.Comment.Model.Comment;
import java.util.List;

public interface CommentService {

    String commentOnAStory(Comment comment);

    List<Comment> getAllComments(Story story);

}
