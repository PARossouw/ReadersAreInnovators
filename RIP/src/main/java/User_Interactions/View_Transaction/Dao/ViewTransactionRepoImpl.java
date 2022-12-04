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
    public Map<String, Integer> getAllStoryViewsInPeriod(String startDate, String endDate) throws SQLException {

        con = DBManager.getConnection();
        Map<String, Integer> allStoryViews = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyid, title, writer, count(vt.story) as viewsInPeriod "
                        + "from story s inner join view_transaction vt on s.storyID = vt.story "
                        + "where vt.dateViewed between ? and ? group "
                        + "by storyid order by count(vt.story) desc limit 10;");
                ps.setString(1, startDate);
                ps.setString(2, endDate);
                rs = ps.executeQuery();

                while (rs.next()) {

                    //String title = rs.getString("title");
                    int title = rs.getInt("storyID");
                    String writer = rs.getString("writer");

                    allStoryViews.put(title + "," + writer, rs.getInt("viewsInPeriod"));
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
