package Story.Dao;

import Category.Model.Category;
import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import user.Editor.Model.Editor;
import user.Reader.Model.Reader;
import user.Writer.Model.Writer;
import User.Model.Reader;
import User.Model.Writer;


public class StoryRepoImpl extends JDBCConfig implements StoryRepo {

    @Override
    public List<Story> getApprovedStories() throws SQLException {
        
        List<Story> allApprovedStories = new ArrayList<>();
        Story story = null;
        
        if (getConnection() != null) {
            
            ps = getConnection().prepareStatement("select storyID, title, "
                    + "writer, description, imagePath, body, isDraft, isActive, "
                    + "createdOn, allowComments, isApproved, views, likes, "
                    + "avgRating from story where isApproved = ?");
            
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
            }
        }
        closeConnection();
        
        return allApprovedStories;
    }

    @Override
    public List<Story> getRejectedStories() throws SQLException {
        
        List<Story> allRejectedStories = new ArrayList<>();
        Story story = null;
        
        if (getConnection() != null) {
            
            ps = getConnection().prepareStatement("select storyID, title, "
                    + "writer, description, imagePath, body, isDraft, isActive, "
                    + "createdOn, allowComments, isApproved, views, likes, "
                    + "avgRating from story where isApproved = ?");
            
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
            }
        }
        closeConnection();
        
        return allRejectedStories;
    }

    @Override
    public List<Story> getLikedStories(Reader reader) throws SQLException {
        
        List<Story> readersLikesStories = new ArrayList<>();
        Story story = null;
        
        if (getConnection() != null) {
            
            ps = getConnection().prepareStatement("select storyID, title, "
                    + "writer, description, imagePath, body, isDraft, isActive, "
                    + "createdOn, allowComments, isApproved, views, likes, "
                    + "avgRating from story s inner join like_transaction lt on s.storyID = lt.story where lt.reader = ?");
            
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
            }
        }
        closeConnection();
        
        return readersLikesStories;
    }

    @Override
    public Boolean submitStory(Story story) throws SQLException {
        
        if (getConnection() != null) {
            ps = getConnection().prepareStatement("update story set isDraft = 0 where storyID = ?");
            ps.setInt(1, story.getStoryID());
            rowsAffected = ps.executeUpdate();
        }
        closeConnection();

        return rowsAffected == 1;
    }


    @Override
    public List<Story> getWriterStories(Writer writer) throws SQLException {

   
        List <Story> draftStories = new ArrayList<>();
        Story storyObj;

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select storyID, title, writer,description, imagePath, body, isDraft, isActive , createdOn, allowComments, isApproved, views, likes, avgRating from story where writer = ?");
            

            ps.setInt(1,writer.getUserID());
            ps.setInt(2,1);

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
     
                Date createdOn = rs.getDate("createdOn");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);
                
                boolean allowComments = rs.getBoolean("allowComments");
                boolean isApproved = rs.getBoolean("isApproved");
                int views = rs.getInt("views"); 
                int likes = rs.getInt("likes"); 
                double avgRating = rs.getDouble("avgRating");
                
                    
                 storyObj = new Story(storyID,title, writer1, description, imagePath, body, isDraft, isActive, calendar, allowComments, isApproved, views, likes, avgRating);
                 draftStories.add(storyObj);
            }
        }
        closeConnection();

        return draftStories;
    }
    
     @Override
        public List<Story> getPendingStories() throws SQLException {

   
        List <Story> pendingStories = new ArrayList<>();
        Story storyObj;

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select storyID, title, writer,description, imagePath, body, isDraft, isActive , createdOn, allowComments, isApproved, views, likes, avgRating from story where isApproved = ? ");
            ps.setInt(1,0);

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
                
                
                Date createdOn = rs.getDate("createdOn");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdOn);
                

                boolean allowComments = rs.getBoolean("allowComments");
                boolean isApproved = rs.getBoolean("isApproved");
                int views = rs.getInt("views"); 
                int likes = rs.getInt("likes"); 
                double avgRating = rs.getDouble("avgRating");
                
                    
                 storyObj = new Story(storyID,title, writer1, description, imagePath, body, isDraft, isActive, calendar, allowComments, isApproved, views, likes, avgRating);
                 pendingStories.add(storyObj);
            }
        }
        closeConnection();

        return pendingStories;
    }
    
         @Override
    public List<Story> getStoryByCategory(List<Category> categories) throws SQLException {
        List <Story> storiesByCategory = new ArrayList<>();
        Story storyObj;

        for(int i = 0; i< categories.size(); i++)
        {
        if (getConnection() != null) {
       
            ps = getConnection().prepareStatement("select storyID, title, "
                    + "writer, description, imagePath, body, isDraft, isActive, "
                    + "createdOn, allowComments, isApproved, views, likes, "
                    + "avgRating from story s inner join story_category sc on s.storyID = sc.story where sc.category = ?");
            
            

    
            
            ps.setInt(1,categories.get(i).getCategoryID());

            
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
                

                boolean allowComments = rs.getBoolean("allowComments");
                boolean isApproved = rs.getBoolean("isApproved");
                int views = rs.getInt("views"); 
                int likes = rs.getInt("likes"); 
                double avgRating = rs.getDouble("avgRating");
                
                
                
                
                 storyObj = new Story(storyID,title, writer1, description, imagePath, body, isDraft, isActive, calendar, allowComments, isApproved, views, likes, avgRating);
                 storiesByCategory.add(storyObj);
            }
        }
        }
        
        closeConnection();

        return storiesByCategory;
    }

    @Override
    public Boolean createStory(Story story) throws SQLException {
        
            Boolean createdStory = false;

            if (getConnection() != null) {

            ps = getConnection().prepareStatement("insert into story (title, writer, description, imagePath, body, isDraft, isActive, allowComment, isApproved, views, avgRating,likes) values (?, ?, ?, ?,?,?,?,?,?,?,?,?)");
            ps.setString(1, story.getTitle());
            ps.setString(2, story.getWriter());
            ps.setString(3, story.getDescription());
            ps.setString(4, story.getImagePath());
            ps.setString(5, story.getBody());
            ps.setBoolean(6, story.getIsDraft());
            ps.setBoolean(7, story.getIsActive());
            ps.setBoolean(8, story.getAllowComments());
            ps.setBoolean(9, story.getIsApproved());
            ps.setInt(10, story.getViews());
            ps.setDouble(11, story.getAvgRating());
            ps.setInt(12, story.getLikes());

            rowsAffected = ps.executeUpdate();

            createdStory = true;
            }    
    
           closeConnection();
           return createdStory ;

    }

    @Override
    public Story retrieveStory(Story story) throws SQLException {
    
        Story storyObj = new Story();

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select storyID, title, writer,description, "
                    + "imagePath, body, isDraft, isActive , createdOn, allowComments, "
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
                
                boolean allowComments = rs.getBoolean("allowComments");
                boolean isApproved = rs.getBoolean("isApproved");
                int views = rs.getInt("views"); 
                int likes = rs.getInt("likes"); 
                double avgRating = rs.getDouble("avgRating");
                    
                storyObj = new Story(storyID,title, writer, description, imagePath, body, 
                isDraft, isActive, calendar, allowComments, isApproved, views, likes, avgRating);

            }
        }
        closeConnection();

        return storyObj;

    }

    @Override
    public Boolean updateStory(Story story) throws SQLException {
        
        Boolean storyUpdated = false;
            if (getConnection() != null) 
            {
            ps = getConnection().prepareStatement("update story set title = ?, writer = ? ,description = ? , "
                    + "imagePath = ? , body = ? , isDraft = ?, isActive = ? , createdOn = ?, allowComments = ?, "
                    + "isApproved = ?, views = ?, likes = ?, avgRating = ?  where storyID = ? ");
            
            ps.setString(1, story.getTitle());
            ps.setString(2, story.getWriter());
            ps.setString(3, story.getDescription());
            ps.setString(4, story.getImagePath());
            ps.setString(5, story.getBody());
            ps.setBoolean(6, story.getIsDraft());
            ps.setBoolean(7, story.getIsActive());
            ps.setBoolean(8, story.getIsActive());
            ps.setBoolean(9, story.getAllowComments());
            ps.setBoolean(10, story.getIsApproved());
            ps.setInt(11, story.getViews());
            ps.setDouble(12, story.getAvgRating());
            ps.setInt(13, story.getLikes());
            ps.setInt(14, story.getStoryID());
            
            rs = ps.executeQuery();
            storyUpdated = true;
            
        }
        closeConnection();
        
    return storyUpdated;
    }

    @Override
    public Boolean deleteStory(Story story) throws SQLException {
        
        Boolean storyDeleted = false;
        
        if (getConnection() != null) 
            {
            ps = getConnection().prepareStatement("update story set isActive = ? where storyID = ? ");
                 
            ps.setInt(1, 0);
            ps.setInt(2, story.getStoryID());         
            rs = ps.executeQuery();
            storyDeleted = true;
       
        }
        closeConnection();
        return storyDeleted;
    
    }
}