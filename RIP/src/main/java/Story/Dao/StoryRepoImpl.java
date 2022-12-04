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
import User.Model.Writer;
import jakarta.ws.rs.core.Response;
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
        Story story = null;

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, "
                        + "writer, description, imagePath, body, isDraft, isActive, "
                        + "createdOn, allowComment, isApproved, views, likes, "
                        + "avgRating from story where isApproved = ? ORDER BY RAND() limit 23");

                ps.setInt(1, 1);
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

//                    Date createdOn = rs.getDate("createdOn");
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(createdOn);

                    boolean allowComment = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    story = new Story(storyID, title, writer, description,
                            imagePath, body, isDraft, isActive,
                            null, allowComment, isApproved,
                            views, likes, avgRating);
        

                    allApprovedStories.add(story);
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
        Story story = null;

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, "
                        + "writer, description, imagePath, body, isDraft, isActive, "
                        + "createdOn, allowComment, isApproved, views, likes, "
                        + "avgRating from story s inner join story_transaction st on s.storyid = st.story where storyid = story and isApproved = ? ");

                ps.setInt(1, 0);
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

                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    boolean allowComments = rs.getBoolean("allowComments");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    story = new Story(storyID, title, writer, description,
                            imagePath, body, isDraft, isActive,
                            calendar, allowComments, isApproved,
                            views, likes, avgRating);
                    allRejectedStories.add(story);
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

                ps = con.prepareStatement("select storyID, title, "
                        + "writer, description, imagePath, body, isDraft, isActive, "
                        + "allowComment, isApproved, views, likes, avgRating "
                        + "from story s inner join like_transaction lt on s.storyID = lt.story where lt.reader = ?");

                ps.setInt(1, reader.getUserID());
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

                    boolean allowComments = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    story = new Story(storyID, title, writer, description,
                            imagePath, body, isDraft, isActive,
                            null, allowComments, isApproved,
                            views, likes, avgRating);
                    readersLikesStories.add(story);
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
        Story storyObj;

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer, description, "
                        + "imagePath, body, isDraft, isActive , createdOn, allowComment, "
                        + "isApproved, views, likes, avgRating from story where writer = ?");

                ps.setInt(1, writer.getUserID());

                rs = ps.executeQuery();

                while (rs.next()) {

                    int storyID = rs.getInt("storyID");
                    String title = rs.getString("title");
                    String writer1 = rs.getString("writer");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("imagePath");
                    String body = rs.getString("body");
                    boolean isDraft = rs.getBoolean("isDraft");
                    boolean isActive = rs.getBoolean("isActive");

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(rs.getDate("createdOn"));

                    boolean allowComments = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    storyObj = new Story(storyID, title, writer1, description, imagePath, body, isDraft, isActive, null, allowComments, isApproved, views, likes, avgRating);
                    writerStories.add(storyObj);
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

        Story storyObj = new Story();
        List<Story> stories = new ArrayList<>();

        try {
            if (con != null) {
                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, "
                        + "body, isDraft, isActive, createdOn, allowComment, isApproved, views, likes, avgRating "
                        + "from story where isapproved = 0 and isdraft = 0");

                rs = ps.executeQuery();

                while (rs.next()) {

                    int storyID = rs.getInt("storyid");

                    String title = rs.getString("title");
                    String writer1 = rs.getString("writer");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("imagePath");
                    String body = rs.getString("body");
                    boolean isDraft = rs.getBoolean("isDraft");
                    boolean isActive = rs.getBoolean("isActive");

                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    boolean allowComments = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    storyObj = new Story(storyID, title, writer1, description, imagePath, body, isDraft, isActive, null, allowComments, isApproved, views, likes, avgRating);
                    stories.add(storyObj);
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
        Story storyObj;

        String more = "";
        try {
            if (categories.size() > 1) {
                for (int i = 1; i < categories.size(); i++) {
                    more += " or sc.category = ?";
                }
            }
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, writer, description, "
                        + "imagePath, body, isDraft, isActive, createdOn, allowComment, "
                        + "isApproved, views, likes, avgRating from story s "
                        + "inner join story_category sc on s.storyID = sc.story "
                        + "where sc.category = ?" + more + " ORDER BY RAND() limit 23");

                ps.setInt(1, categories.get(0).getCategoryID());

                if (categories.size() > 1) {
                    for (int i = 1; i < categories.size(); i++) {
                        ps.setInt(i + 1, categories.get(i).getCategoryID());
                    }
                }

                rs = ps.executeQuery();

                while (rs.next()) {

                    int storyID = rs.getInt("storyid");
                    String title = rs.getString("title");
                    String writer1 = rs.getString("writer");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("imagePath");
                    String body = rs.getString("body");
                    boolean isDraft = rs.getBoolean("isDraft");
                    boolean isActive = rs.getBoolean("isActive");

                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    boolean allowComments = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    storyObj = new Story(storyID, title, writer1, description, imagePath, body, isDraft, isActive, null, allowComments, isApproved, views, likes, avgRating);
                    storiesByCategory.add(storyObj);
                }
            }
        } finally {
            close();
        }
        return storiesByCategory;
    }

    @Override
    public Boolean createStory(Story story) throws SQLException {

        con = DBManager.getConnection();

        try {
            if (con != null) {

                ps = con.prepareStatement("insert into story (title, writer, description, imagePath, body) values (?, ?, ?, ?, ?)");
                ps.setString(1, story.getTitle());
                ps.setInt(2, Integer.parseInt(story.getWriter())); // We put in the writer id
                ps.setString(3, story.getDescription());
                ps.setString(4, story.getImagePath());
                ps.setString(5, story.getBody());




                rowsAffected = ps.executeUpdate();
            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }
    
    
    
    
    /*
    
       public Boolean createUser(User user) throws SQLException {

        con = DBManager.getConnection();

        try {
//            if (user instanceof Editor && con != null) {
//
//                ps = con.prepareStatement("insert into User (username, email, password, role) values (?, ?, ?, ?)");
//                ps.setString(1, user.getUsername());
//                ps.setString(2, user.getEmail());
//                ps.setString(3, user.getPassword());
//                ps.setInt(4, 3);
//                rowsAffected = ps.executeUpdate();
//
//            } else if (user instanceof Reader && con != null) {

            ps = con.prepareStatement("insert into User (username, email, password) values (?, ?, ?)");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            rowsAffected = ps.executeUpdate();
//            }
        } finally {
            close();
        }
        return rowsAffected == 1;
    }
    */
    
    
    
    
    
    
    
    
    

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
                    int storyID = rs.getInt("storyID");
                    String title = rs.getString("title");
                    String writer = rs.getString("writer");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("imagePath");
                    String body = rs.getString("body");
                    boolean isDraft = rs.getBoolean("isDraft");
                    boolean isActive = rs.getBoolean("isActive");

                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    boolean allowComments = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    storyObj = new Story(storyID, title, writer, description, imagePath, body,
                            isDraft, isActive, null, allowComments, isApproved, views, likes, avgRating);

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

                    ps = con.prepareStatement("update story set title = ?, description = ?, imagePath = ?,"
                            + "body = ? where storyID = ?");
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

    @Override//change the sql statement to getting the stories for that particular month - take out category
    public Map<String, Integer> getHighestRatedStoriesForMonth(String month) throws SQLException {

//        con = DBManager.getConnection();
//

//        Map<String, Integer> storyList = new HashMap<>();

//        List<Story> storyList = new ArrayList<>();

//
//        try {
//            if (con != null) {
//

//                //this sql sattement is wrong, no month is mentioned
//                ps = con.prepareStatement("select title,writer, description, imagePath, "

//                ps = con.prepareStatement("select storyID, title,writer, description, imagePath, "

//                        + "body, isDraft , isActive, createdOn, allowComment, isApproved, views, likes, "
//                        + " avg(rt.rating) as averageRating from Story s"
//                        + " inner join rating_transaction rt on s.storyID = rt.story "
//                        + "group by story "

//                        + "order by averageRating desc limit 20");

//                        + "order by averageRating desc limit 23");

//
//                while (rs.next()) {
//
//                    int storyID = rs.getInt("storyID");
//                    String title = rs.getString("title");
//                    String writer = rs.getString("writer");
//                    String description = rs.getString("description");
//                    String imagePath = rs.getString("imagePath");
//                    String body = rs.getString("body");
//                    boolean isDraft = rs.getBoolean("isDraft");
//                    boolean isActive = rs.getBoolean("isActive");
//
//                    Date createdOn = rs.getDate("createdOn");
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(createdOn);
//
//                    boolean allowComments = rs.getBoolean("allowComment");
//                    boolean isApproved = rs.getBoolean("isApproved");
//                    int views = rs.getInt("views");
//                    int likes = rs.getInt("likes");
//                    double avgRating = rs.getDouble("averageRating");
//
//                    Story story = new Story(storyID, title, writer, description,
//                            imagePath, body, isDraft, isActive,
//                            null, allowComments, isApproved,
//                            views, likes, avgRating);
//

//                    //storyList.add(story); commented out temporarily

//                    storyList.add(story);

//                }
//            }
//        } finally {
//            close();
//        }
//        return storyList;


                  //hardcoding
        Map<String, Integer> hCodeMap = new HashMap<>();
        
        String story1 = "frettel and nettel";
        String story2 = "becky and krekel";
        String story3 = "tekkle and mekkle";
        
        int a = 2;
        int b = 3;
        int c = 4;
        
        hCodeMap.put(story1, a);
        hCodeMap.put(story2, b);
        hCodeMap.put(story3, c);
        
        return hCodeMap;


//        con = DBManager.getConnection();

//        List<Story> allApprovedStories = new ArrayList<>();
//        Story story = null;

 //       try {
 //           if (con != null) {
//
//                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, body, isDraft , isActive, "
//                        + "createdOn, allowComment, isApproved, views, likes,  avg(rt.rating) as averageRating from Story s "
  //                      + "inner join rating_transaction rt on s.storyID = rt.story group by story order by averageRating desc limit 23");


//
//                rs = ps.executeQuery();
//
//                while (rs.next()) {
//
//                    int storyID = rs.getInt("storyID");
//                    String title = rs.getString("title");
//                    String writer = rs.getString("writer");
//                    String description = rs.getString("description");
//                    String imagePath = rs.getString("imagePath");
//                    String body = rs.getString("body");
//                    boolean isDraft = rs.getBoolean("isDraft");
//                    boolean isActive = rs.getBoolean("isActive");

//                    Date createdOn = rs.getDate("createdOn");
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(createdOn);

//                    boolean allowComment = rs.getBoolean("allowComment");
//                    boolean isApproved = rs.getBoolean("isApproved");
//                    int views = rs.getInt("views");
//                    int likes = rs.getInt("likes");
                   // double avgRating = rs.getDouble("averateRating");
//
//                    story = new Story(storyID, title, writer, description,
//                            imagePath, body, isDraft, isActive,
//                            null, allowComment, isApproved,
//                            views, likes, null);
//        
//
//                    allApprovedStories.add(story);
//                }
//            }
//        } finally {
//            close();
//        }
//        return allApprovedStories;

    }

    @Override
    public List<Story> searchForStory(String text) throws SQLException {

con = DBManager.getConnection();

        List<Story> allApprovedStories = new ArrayList<>();
        Story story = null;

        try {
            if (con != null) {

                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, body, isDraft, isActive, "
                        + "createdOn, allowComment, isApproved, views, likes, avgRating from story where title like '%"+text+"%' or writer like '%"+text+"%'");

                
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

                    boolean allowComment = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    story = new Story(storyID, title, writer, description,
                            imagePath, body, isDraft, isActive,
                            null, allowComment, isApproved,
                            views, likes, avgRating);
        

                    allApprovedStories.add(story);
                }
            }
        } finally {
            close();
        }
        return allApprovedStories;


    }

    @Override
    public List<Story> getStoriesForStoryOfTheDay() throws SQLException {

        con = DBManager.getConnection();

        Story storyObj = new Story();
        List<Story> stories = new ArrayList<>();

        con = DBManager.getConnection();

        try {
            if (con != null) {
//            ps = con.prepareStatement("select storyID, title, writer,description, imagePath, body, isDraft, isActive, createdOn, allowComment, isApproved, views, likes, avgRating from story where isApproved = 0 and isDraft = 0");

                ps = con.prepareStatement("select storyID, title, writer, description, imagePath, "
                        + "body, isDraft, isActive, createdOn, allowComment, isApproved, views, likes, avgRating "
                        + "from story where isApproved = 1 and isDraft = 0 ORDER BY RAND() limit 23");

                rs = ps.executeQuery();

                while (rs.next()) {

                    int storyID = rs.getInt("storyid");

                    String title = rs.getString("title");
                    String writer1 = rs.getString("writer");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("imagePath");
                    String body = rs.getString("body");
                    boolean isDraft = rs.getBoolean("isDraft");
                    boolean isActive = rs.getBoolean("isActive");

                    Date createdOn = rs.getDate("createdOn");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(createdOn);

                    boolean allowComments = rs.getBoolean("allowComment");
                    boolean isApproved = rs.getBoolean("isApproved");
                    int views = rs.getInt("views");
                    int likes = rs.getInt("likes");
                    double avgRating = rs.getDouble("avgRating");

                    storyObj = new Story(storyID, title, writer1, description,
                            imagePath, body, isDraft, isActive, null, allowComments,
                            isApproved, views, likes, avgRating);
                    stories.add(storyObj);
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

        int rowsAffected = 0;

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
