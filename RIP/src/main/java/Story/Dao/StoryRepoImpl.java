/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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


public class StoryRepoImpl extends JDBCConfig implements StoryRepo {

    @Override
    public List<Story> getApprovedStories(Editor editor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Story> getRejectedStories(Editor editor) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Story> getLikedStories(Reader reader) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean submitStory(Story story) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public List<Story> getDraftStories(Writer writer) throws SQLException {

   
        List <Story> draftStories = new ArrayList<>();
        Story storyObj;

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select storyID, title, writer,description, imagePath, body, isDraft, isActive , createdOn, allowComments, isApproved, views, likes, avgRating from story where writer = ? ");
            

            ps.setInt(1,writer.getUserID());

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
        close();

        return draftStories;
    }
    
     @Override
        public List<Story> getPendingStories() throws SQLException {

   
        List <Story> pendingStories = new ArrayList<>();
        Story storyObj;

        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select storyID, title, writer,description, imagePath, body, isDraft, isActive , createdOn, allowComments, isApproved, views, likes, avgRating from story where isApproved = 0 ");
            

          //  ps.setInt(1,writer.getUserID());

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
        close();

        return pendingStories;
    }
    
         @Override
    public List<Story> getStoryByCategory(List<Category> categories) throws SQLException {
        List <Story> storiesByCategory = new ArrayList<>();
        Story storyObj;

        for(int i = 0; i< categories.size(); i++)
        {
        if (getConnection() != null) {
            ps = getConnection().prepareStatement("select story from story_category where category = ? ");
            

            ps.setInt(1,categories.get(i).getCategoryID());

            
            rs = ps.executeQuery();

            while (rs.next()) {
                
                int storyID = rs.getInt("story");  
                   
                 storyObj = new Story(storyID);
                 storiesByCategory.add(storyObj);
            }
        }
        }
        
        close();

        return storiesByCategory;
    }
    
    
    
    
    
    
    
    
    
    
    
}
