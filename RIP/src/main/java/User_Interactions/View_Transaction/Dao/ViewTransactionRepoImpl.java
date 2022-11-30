package User_Interactions.View_Transaction.Dao;

import DBManager.DBManager;
import Story.Model.Story;
import User.Model.Reader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ViewTransactionRepoImpl implements ViewTransactionRepo {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public Boolean createView(Story story, Reader reader) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("insert into view_transaction (reader, story) values (?,?");
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
    public Map<Story, Integer> getAllStoryViewsInPeriod(Calendar startDate, Calendar endDate) throws SQLException {

        con = DBManager.getConnection();
        Map<Story, Integer> allStoryViews = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer, description, "
                        + "imagePath, body, isDraft, isActive, createdOn, allowComment, isApproved, "
                        + "views, avgRating, likes, count(vt.story) as viewsInPeriod from story s "
                        + "inner join view_transaction vt on s.storyID = vt.story where vt.dateViewed "
                        + "between ? and ? order by count(vt.story) desc");

                ps.setDate(1, (Date) startDate.getTime());
                ps.setDate(2, (Date) endDate.getTime());
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

                    java.util.Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    boolean allowComments = rs.getBoolean("allowComments");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    allStoryViews.put(new Story(storyID, title, writer, description,
                            imagePath, body, isDraft, isActive,
                            calendar, allowComments, isApproved,
                            views, likes, avgRating), rs.getInt("viewsInPeriod"));
                }
            }
        } finally {
            close();
        }
        return allStoryViews;
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
