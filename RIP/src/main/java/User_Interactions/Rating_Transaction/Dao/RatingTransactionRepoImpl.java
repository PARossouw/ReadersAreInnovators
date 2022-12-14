package User_Interactions.Rating_Transaction.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Rating_Transaction.Model.RatingTransaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingTransactionRepoImpl implements RatingTransactionRepo {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public Boolean createRating(Story story, Reader reader, int rating) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("insert into rating_Transaction (rating, reader, story) values (?, ?, ?)");
                ps.setInt(1, rating);
                ps.setInt(2, reader.getUserID());
                ps.setInt(3, story.getStoryID());

                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Boolean getRating(Story story, Reader reader) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("select ratingID, reader, story from rating_transaction where story = ? and reader = ?");
                ps.setInt(1, story.getStoryID());
                ps.setInt(2, reader.getUserID());
                rs = ps.executeQuery();

                if (rs.next()) {
                    return true;
                }
            }
        } finally {
            close();
        }
        return false;
    }

    @Override
    public Boolean updateRating(Story story, Reader reader, int rating) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("update rating_Transaction set rating = ? where storyID = ? and reader = ?");
                ps.setInt(1, rating);
                ps.setInt(2, story.getStoryID());
                ps.setInt(3, reader.getUserID());

                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    public void close() throws SQLException {

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
