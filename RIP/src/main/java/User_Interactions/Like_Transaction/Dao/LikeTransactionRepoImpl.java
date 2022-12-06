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

                ps = con.prepareStatement("select likeid from like_transaction where reader = ? and story = ?");
                ps.setInt(1, reader.getUserID());
                ps.setInt(2, story.getStoryID());
                rs = ps.executeQuery();
                
                if (rs.next()) {                    
                    return true;
                }
//                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return false;
    }

    @Override//supposed to get the total number of likes for each book - so you a map(key = story, value = amount of likes in that period)
    //getting top 20 ,most liked books of a certain period
    public Map<String, Integer> getAllLikesInPeriod(String month) throws SQLException {
        
        String [] time = month.split("-");

        con = DBManager.getConnection();
        Map<String, Integer> likeMap = new HashMap<>();

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, count(distinct lt.reader) as likes from story s inner "
                        + "join like_transaction lt on s.storyID = lt.story where month(likedOn) = ? and year(likedOn) = ? "
                        + "and isLiked = 1 group by storyId order by likes desc limit 20;");

                ps.setString(1, time[1]);
                ps.setString(2, time[0]);
                rs = ps.executeQuery();

                while (rs.next()) {

                    int storyID = rs.getInt("storyID");

                    likeMap.put(""+storyID, rs.getInt("likes"));

                    if (likeMap.size() == 20) {
                        break;
                    }
                }
            }
        } finally {
            close();
        }
        return likeMap;



        //hardcoding
//        Map<String, Integer> myMap = new HashMap<>();
//        String s1 = "storyID1";
//        String s2 = "storyID2";
//        String s3 = "storyID3";
//        
//        int i = 200;
//        int j = 400;
//        int k = 600;
//        
//        myMap.put(s1, i);
//        myMap.put(s2, j);
//        myMap.put(s3, k);
//        
//        return myMap;
        
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
