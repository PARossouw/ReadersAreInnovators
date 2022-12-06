package Story.Dao;

import Category.Model.Category;
import DBManager.DBManager;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import User.Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class StoryRepoImpl implements StoryRepo {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;
    private Integer rowsAffected;

    @Override
    public List<Story> getApprovedStories() throws SQLException {

        con = DBManager.getConnection();

        List<Story> allApprovedStories = new ArrayList<>();

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, "
                        + "writer, description, imagePath, body, isDraft, isActive, "
                        + "createdOn, allowComment, isApproved, views, likes, "
                        + "avgRating from story where isApproved = ? ORDER BY RAND(), avgRating limit 15");

                ps.setInt(1, 1);
                rs = ps.executeQuery();

                while (rs.next()) {
//                    Date createdOn = rs.getDate("createdOn");
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(createdOn);

                    allApprovedStories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return allApprovedStories;
    }

    @Override
    public List<Story> getRejectedStories() throws SQLException {

        con = DBManager.getConnection();

        List<Story> allRejectedStories = new ArrayList<>();

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, writer, "
                        + "description, imagePath, body, isDraft, isActive, "
                        + "createdOn, allowComment, isApproved, views, likes, "
                        + "avgRating from story s inner join story_transaction st on s.storyid = st.story "
                        + "where storyid = story and isApproved = ? ");

                ps.setInt(1, 0);
                rs = ps.executeQuery();

                while (rs.next()) {
                    allRejectedStories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return allRejectedStories;
    }

    @Override
    public List<Story> getLikedStories(User reader) throws SQLException {

        con = DBManager.getConnection();

        List<Story> readersLikesStories = new ArrayList<>();
        Story story = null;

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, writer, "
                        + "description, imagePath, body, isDraft, isActive, "
                        + "allowComment, isApproved, views, likes, avgRating "
                        + "from story s inner join like_transaction lt on s.storyID = lt.story where lt.reader = ?");

                ps.setInt(1, reader.getUserID());
                rs = ps.executeQuery();

                while (rs.next()) {
                    readersLikesStories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return readersLikesStories;
    }

    @Override
    public Boolean submitStory(Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("update story set isDraft = 0 where storyID = ?");
                ps.setInt(1, story.getStoryID());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public List<Story> getWriterStories(User writer) throws SQLException {

        con = DBManager.getConnection();

        List<Story> writerStories = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer, description, "
                        + "imagePath, body, isDraft, isActive , createdOn, allowComment, "
                        + "isApproved, views, likes, avgRating from story where writer = ?");

                ps.setInt(1, writer.getUserID());

                rs = ps.executeQuery();

                while (rs.next()) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("createdOn"));

                    writerStories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return writerStories;
    }

    @Override
    public List<Story> getPendingStories() throws SQLException {

        con = DBManager.getConnection();

        List<Story> stories = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, body, "
                        + "isDraft, isActive, createdOn, allowComment, isApproved, views, likes, avgRating "
                        + "from story where isapproved = 0 and isdraft = 0 limit 15");

                rs = ps.executeQuery();

                while (rs.next()) {
                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    stories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return stories;
    }

    @Override
    public List<Story> getStoryByCategory(List<Category> categories) throws SQLException {

        con = DBManager.getConnection();
        List<Story> storiesByCategory = new ArrayList<>();


        try {

            if (con != null) {

                
                ps = con.prepareStatement("select storyID, title, writer, description, "
                        + "imagePath, body, isDraft, isActive, createdOn, allowComment, "
                        + "isApproved, views, likes, avgRating from story s "
                        + "inner join story_category sc on s.storyID = sc.story "
                        + "where sc.category = ? ORDER BY RAND() limit 23");

                for(Category c: categories){
                ps.setInt(1, c.getCategoryID());
                ps.addBatch();
                    
                }
                rs = ps.executeQuery();

            }

                while (rs.next()) {
                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    storiesByCategory.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            
        } finally {
            close();
        }
        return storiesByCategory;
        
        //hardcoding
//        List<Story> see = new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//        Story s = new Story();
//        s.setTitle("afeeaef");
//        s.setDescription("gsrarga");
//        s.setViews(856);
//        s.setAvgRating(5.0);
//            see.add(s);
//        }
//        return see;
        
        
        
    }

    @Override
    public Story createStory(Story story) throws SQLException {

        Story storyReturn = new Story();

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("insert into story (title, writer, description, imagePath, body, isdraft) values (?, ?, ?, ?, ?, ?)");
                ps.setString(1, story.getTitle());
                ps.setInt(2, Integer.parseInt(story.getWriter()));
                ps.setString(3, story.getDescription());
                ps.setString(4, story.getImagePath());
                ps.setString(5, story.getBody());
                ps.setBoolean(6, story.getIsDraft());

                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        storyReturn.setBody(story.getBody());
        storyReturn = getStoryID(storyReturn);
        return storyReturn;
    }

    @Override
    public Story retrieveStory(Story story) throws SQLException {

        con = DBManager.getConnection();

        Story storyObj = new Story();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer,description, "
                        + "imagePath, body, isDraft, isActive , createdOn, allowComment, "
                        + "isApproved, views, likes, avgRating from story where storyID = ? ");
                ps.setInt(1, story.getStoryID());

                rs = ps.executeQuery();

                if (rs.next()) {
                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    storyObj = new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating"));
                }
            }
        } finally {
            close();
        }
        return storyObj;
    }

    @Override
    public Boolean updateStory(Story story) throws SQLException {

        con = DBManager.getConnection();

        int rowsAffected = 0;
        try {

            if (con != null) {

                rowsAffected = 0;
                if (con != null) {

                    ps = con.prepareStatement("update story set title = ?, description = ?, imagePath = ?, body = ? where storyID = ?");
                    ps.setString(1, story.getTitle());
                    ps.setString(2, story.getDescription());
                    ps.setString(3, story.getImagePath());
                    ps.setString(4, story.getBody());
                    ps.setInt(5, story.getStoryID());

                    rowsAffected = ps.executeUpdate();
                }
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Boolean deleteStory(Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("update story set isActive = ? where storyID = ? ");

                ps.setInt(1, 0);
                ps.setInt(2, story.getStoryID());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Map<String, Double> getHighestRatedStoriesForMonth(String month) throws SQLException {

        String[] time = month.split("-");

        con = DBManager.getConnection();

        Map<String, Double> storyList = new HashMap<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyid, title, writer, "
                        + " avg(rt.rating) as averageRating from Story s"
                        + " inner join rating_transaction rt on s.storyID = rt.story "
                        + "where month(ratedOn) = ? and year(ratedOn) = ? group by story "
                        + "order by averageRating desc limit 20");

                ps.setString(1, time[1]);
                ps.setString(2, time[0]);
                rs = ps.executeQuery();

                while (rs.next()) {
                    //String title = rs.getString("title");
                    storyList.put(rs.getInt("storyID") + "," + rs.getString("writer"), rs.getDouble("averageRating"));
                }
            }
        } finally {
            close();
        }
        return storyList;
    }

    @Override
    public List<Story> getHighestRatedStoriesForMonth() throws SQLException {

        con = DBManager.getConnection();

        List<Story> highestRatedStories = new ArrayList<>();

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, body, isDraft , isActive, "
                        + "createdOn, allowComment, isApproved, views, likes, avgRating, avg(rt.rating) as averageRating from Story s "
                        + "inner join rating_transaction rt on s.storyID = rt.story group by story order by averageRating desc limit 20");

                rs = ps.executeQuery();

                while (rs.next()) {
                    highestRatedStories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return highestRatedStories;
    }

    @Override
    public List<Story> searchForStory(String text) throws SQLException {
        text = "%" + text + "%";
        con = DBManager.getConnection();

        List<Story> searchedStories = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, body, isDraft, s.isActive, "
                        + "createdOn, allowComment, isApproved, views, likes, avgRating from story s inner join user u on s.writer = u.userid "
                        + "where title like ? or u.username like ? limit 25");
                ps.setString(1, text);
                ps.setString(2, text);

                rs = ps.executeQuery();

                while (rs.next()) {
                    searchedStories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return searchedStories;

    }

    @Override
    public List<Story> getStoriesForStoryOfTheDay() throws SQLException {

        con = DBManager.getConnection();
        List<Story> stories = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, "
                        + "body, isDraft, isActive, createdOn, allowComment, isApproved, views, likes, avgRating "
                        + "from story where isApproved = 1 and isDraft = 0 ORDER BY RAND() limit 20");

                rs = ps.executeQuery();

                while (rs.next()) {
                    stories.add(new Story(rs.getInt("storyID"), rs.getString("title"),
                            rs.getString("writer"), rs.getString("description"),
                            rs.getString("imagePath"), rs.getString("body"),
                            rs.getBoolean("isDraft"), rs.getBoolean("isActive"),
                            null, rs.getBoolean("allowComment"), rs.getBoolean("isApproved"),
                            rs.getInt("views"), rs.getInt("likes"), rs.getDouble("avgRating")));
                }
            }
        } finally {
            close();
        }
        return stories;
    }

    @Override
    public Boolean turnOffComments(Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {
                ps = con.prepareStatement("UPDATE story SET allowcomment = IF(allowcomment != 0, 0, 1) WHERE storyid = ?;");
                ps.setInt(1, story.getStoryID());

                rowsAffected = ps.executeUpdate();;
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public Boolean blockStory(Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("update story set isActive = 0 where storyid = ?");
                ps.setInt(1, story.getStoryID());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }

    @Override
    public String incrementViews(Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("insert into view_transaction (reader, story) values (?, ?)");
                ps.setInt(1, Integer.parseInt(story.getWriter()));
                ps.setInt(2, story.getStoryID());
                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return "view incremented on " + story.getTitle();
    }

    private Story getStoryID(Story storyReturn) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyid from story where body = ?");
                ps.setString(1, storyReturn.getBody());
                rs = ps.executeQuery();

                if (rs.next()) {
                    storyReturn.setStoryID(rs.getInt("storyid"));
                }
            }
        } finally {
            close();
        }
        return storyReturn;
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
