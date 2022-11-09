package Story.Dao;

import Category.Model.Category;
import Story.Model.Story;
import java.sql.SQLException;
import java.util.List;
import user.Reader.Model.Reader;
import user.Writer.Model.Writer;

public interface StoryRepo {

    List<Story> getApprovedStories() throws SQLException;

    List<Story> getRejectedStories() throws SQLException;

    List<Story> getLikedStories(Reader reader) throws SQLException;

    Boolean submitStory(Story story) throws SQLException;

    List<Story> getStoryByCategory(List<Category> categories) throws SQLException; 
    
    List<Story> getWriterStories(Writer writer) throws SQLException;
    
    List<Story> getPendingStories() throws SQLException;//not yet approved

}
