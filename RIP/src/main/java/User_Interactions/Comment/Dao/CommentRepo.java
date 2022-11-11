package User_Interactions.Comment.Dao;

import Story.Model.Story;
import User_Interactions.Comment.Model.Comment;
import java.sql.SQLException;
import java.util.List;

public interface CommentRepo {
    
    Boolean createComment(Comment comment) throws SQLException;
    
    List<Comment> getStoryComments(Story story) throws SQLException;
    
}