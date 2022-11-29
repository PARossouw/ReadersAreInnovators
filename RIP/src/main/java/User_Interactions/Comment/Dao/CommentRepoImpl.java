package User_Interactions.Comment.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Comment.Model.Comment;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommentRepoImpl extends DBManager implements CommentRepo {

    @Override
    public Boolean createComment(Comment comment) throws SQLException {

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("insert into comment (commentBody, reader, story) values (?,?,?)");

            ps.setString(1, comment.getCommentBody());
            ps.setInt(2, comment.getReader().getUserID());
            ps.setInt(3, comment.getStory().getStoryID());

            rowsAffected = ps.executeUpdate();
        }
        close();
        return rowsAffected == 1;
    }

    @Override
    public List<Comment> getStoryComments(Story story) throws SQLException {
//
        List<Comment> storyComments = new ArrayList();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select commentID, commentBody, commentedOn, reader, story from comment where story = ?");
           // ps.setInt(1, story.getStoryID());
            ps.setInt(1, 4);
            
             rs = ps.executeQuery();

            while (rs.next()) {
//                Date createdOn = rs.getDate("dateAdded");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime(createdOn);

                Reader reader = new Reader();
                reader.setUserID(rs.getInt("reader"));
                
                Comment comment = new Comment();
                //comment.setCommentID(rs.getInt("commentID"));
                comment.setCommentBody(rs.getString("commentBody"));
                //comment.setReader(reader);
                //comment.setStory(story);
                //comment.setReader(rs.getInt("reader"));
                
                storyComments.add(comment);
                
                
            }
        }
        close();
        return storyComments;

//test Code below 
// List<Comment> allStoryComments = new ArrayList();
//            Comment testComment = new Comment();
//                testComment.setCommentBody("Good StoryLine ya bish Dao bro");
//
//                Comment testComment2 = new Comment();
//                testComment2.setCommentBody("Nice Plot twist servuce vir ");
//
//                Comment testComment3 = new Comment();
//                testComment3.setCommentBody("Long and insightful");
//
//                allStoryComments.add(testComment);
//                allStoryComments.add(testComment2);
//                allStoryComments.add(testComment3);
//            return allStoryComments;
////



    }
}