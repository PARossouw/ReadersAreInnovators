package User_Interactions.Comment.Service;

import Story.Model.Story;
import User_Interactions.Comment.Dao.CommentRepo;
import User_Interactions.Comment.Model.Comment;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CommentServiceImpl implements CommentService{
    
    private final CommentRepo commentRepo;

    public CommentServiceImpl(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public String commentOnAStory(Comment comment) {
        
        try {
            if(comment == null || comment.getCommentBody().length() == 0){
                return "An empty comment was entered, please try again";//the textField should make it so this is impossible
            }
            if(commentRepo.createComment(comment)){
                
                return "Comment successfully added";
            }
            else{
                return "unsuccessful";
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return "unsuccessful";
        }
    }

    @Override
    public List<Comment> getAllComments(Story story) {
        
        try {
            return commentRepo.getStoryComments(story);
        } catch (SQLException ex) {
            Logger.getLogger(CommentServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}