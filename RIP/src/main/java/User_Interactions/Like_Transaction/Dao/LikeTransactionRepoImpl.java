package User_Interactions.Like_Transaction.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.Reader;
import User_Interactions.Like_Transaction.Model.LikeTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class LikeTransactionRepoImpl implements LikeTransactionRepo {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public Boolean createLike(Reader reader, Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("insert into like_Transaction (reader, story) values (?, ?)");
                ps.setInt(1, reader.getUserID());
                ps.setInt(2, story.getStoryID());

                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;

    }

    @Override
    public Boolean updateLike(Reader reader, Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("insert into like_Transaction (reader, story, isLiked) values (?, ?, IF( (select isLiked from like_Transaction where MAX(likeId) and reader = ? and story = ?) = 0, 1, 0))");
                ps.setInt(1, reader.getUserID());
                ps.setInt(2, story.getStoryID());
                ps.setInt(3, reader.getUserID());
                ps.setInt(4, story.getStoryID());

                rowsAffected = ps.executeUpdate();

            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Boolean getLike(Reader reader, Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("select likeid  from like_transaction where reader = ? and story = ?");
                ps.setInt(1, reader.getUserID());
                ps.setInt(2, story.getStoryID());
                rs = ps.executeQuery();
                
                while (rs.next()) {                    
                    LikeTransaction lt = new LikeTransaction();
                    lt.setLikeID(rs.getInt("likeid"));
                }
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override//supposed to get the total number of likes for each book - so you a map(key = story, value = amount of likes in that period)
    //getting top 20 ,most liked books of a certain period
    public Map<Story, Integer> getAllLikesInPeriod(Calendar month) throws SQLException {

        con = DBManager.getConnection();
        Map<Story, Integer> likeMap = new HashMap<>();

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, count(distinct lt.reader) as likes from story s "
                        + "inner join like_transaction lt on s.storyID = lt.story "
                        + "where month(likedOn) = ? and isLiked = 1 group by storyId order by likes desc");

                ps.setDate(1, (Date) month.getTime());
                rs = ps.executeQuery();

                while (rs.next()) {

                    int storyID = rs.getInt("storyID");
                    String title = rs.getString("title");
                    String writer = rs.getString("writer");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("imagePath");
                    String body = rs.getString("body");
                    boolean isDraft = rs.getBoolean("isDraft");
                    boolean isActive = rs.getBoolean("isActive");

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("createdOn"));

                    boolean allowComments = rs.getBoolean("allowComments");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    Story story = new Story(storyID, title, writer, description,
                            imagePath, body, isDraft, isActive,
                            calendar, allowComments, isApproved,
                            views, likes, avgRating);

                    likeMap.put(story, rs.getInt("likes"));

                    if (likeMap.size() == 20) {
                        break;
                    }
                }
            }
        } finally {
            close();
        }
        return likeMap;
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
