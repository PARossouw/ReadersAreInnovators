package Story.Dao;

import Category.Model.Category;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.List;
import User.Model.User;

import User.Model.Writer;
import java.util.Map;


public interface StoryRepo {

    List<Story> getApprovedStories() throws SQLException;

    List<Story> getRejectedStories() throws SQLException;

    List<Story> getLikedStories(User reader) throws SQLException;

    Boolean submitStory(Story story) throws SQLException;

    List<Story> getStoryByCategory(List<Category> categories) throws SQLException; 
    
    List<Story> getWriterStories(User writer) throws SQLException;
    
    List<Story> getPendingStories() throws SQLException;
    
    Boolean deleteStory(Story story) throws SQLException;
    
    Boolean updateStory(Story story) throws SQLException ;
    
    Story retrieveStory(Story story) throws SQLException ; 
    
    Story createStory(Story story) throws SQLException ;
     
    Map<String, Integer> getHighestRatedStoriesForMonth(String month) throws SQLException;

    List<Story> searchForStory(String text) throws SQLException;

    List<Story> getStoriesForStoryOfTheDay() throws SQLException;

    Boolean turnOffComments(Story story) throws SQLException;

    List<Story> getHighestRatedStoriesForMonth()throws SQLException;

    Boolean blockStory(Story story) throws SQLException;

}
