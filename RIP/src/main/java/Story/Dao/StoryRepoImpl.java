package Story.Dao;

import Category.Model.Category;
import JDBCConfig.JDBCConfig;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import user.Reader.Model.Reader;
import user.Writer.Model.Writer;


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
    public List<Story> getWritersDraftStories(Writer writer) throws SQLException {

   
        List <Story> draftStories = new ArrayList<>();
        Story storyObj;

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select storyID, title, writer,description, imagePath, body, isDraft, isActive , createdOn, allowComments, isApproved, views, likes, avgRating from story where writer = ? and isDraft = ? ");
            

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
}