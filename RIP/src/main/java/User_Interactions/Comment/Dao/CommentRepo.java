package User_Interactions.Comment.Dao;

import Story.Model.Story;
import User_Interactions.Comment.Model.Comment;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author piete
 */
public interface CommentRepo {
    
    boolean createComment() throws SQLException;
    
    List<Comment> getStoryComments(Story story) throws SQLException;
    
}
