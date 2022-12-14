package User_Interactions.Comment.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Comment.Model.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentRepoImpl implements CommentRepo {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public Boolean createComment(Comment comment) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("insert into comment (commentBody, reader, story) values (?,?,?)");

                ps.setString(1, comment.getCommentBody());
                ps.setInt(2, comment.getReader().getUserID());
                ps.setInt(3, comment.getStory().getStoryID());

                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public List<Comment> getStoryComments(Story story) throws SQLException {

        con = DBManager.getConnection();
        List<Comment> storyComments = new ArrayList();

        try {
            if (con != null) {
                ps = con.prepareStatement("select commentID, commentBody, commentedOn, reader, story from comment where story = ?");
                ps.setInt(1, story.getStoryID());

                rs = ps.executeQuery();

                while (rs.next()) {
                    Reader reader = new Reader();
                    reader.setUserID(rs.getInt("reader"));

                    Comment comment = new Comment();
                    comment.setCommentBody(rs.getString("commentBody"));
                    comment.setReader(reader);

                    storyComments.add(comment);
                }
            }
        } finally {
            close();
        }
        return storyComments;
    }

    public void close() throws SQLException {

        con = DBManager.getConnection();
        if (ps != null) {
            ps.close();
        }
        if (rs != null) {
            rs.close();
        }
        if (con != null) {
            con.close();
        }
    }
}
