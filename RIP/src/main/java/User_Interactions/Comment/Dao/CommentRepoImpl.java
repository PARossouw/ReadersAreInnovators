package User_Interactions.Comment.Dao;

import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Comment.Model.Comment;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CommentRepoImpl extends JDBCConfig implements CommentRepo {

    @Override
    public Boolean createComment(Comment comment) throws SQLException {

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("insert into comment (commentBody, reader, story) values (?,?,?)");

            ps.setString(1, comment.getCommentBody());
            ps.setInt(2, comment.getReader().getUserID());
            ps.setInt(3, comment.getStory().getStoryID());

            rowsAffected = ps.executeUpdate();
        }
        closeConnection();
        return rowsAffected == 1;
    }

    @Override
    public List<Comment> getStoryComments(Story story) throws SQLException {

        List<Comment> storyComments = new ArrayList<>();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select commentID, commentBody, commentedOn, reader, story from comment where story = ?");
            ps.setInt(1, story.getStoryID());

            while (rs.next()) {
                Date createdOn = rs.getDate("dateAdded");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);

                Reader reader = new Reader();
                reader.setUserID(rs.getInt("reader"));

                storyComments.add(new Comment(rs.getInt("commentID"),
                        rs.getString("commentBody"),
                        calendar,
                        reader,
                        story));
            }
        }
        closeConnection();
        return storyComments;
    }
}