package Story.Dao;

import Category.Model.Category;
import Story.Model.Story;
import User.Model.User;
import java.sql.SQLException;
import java.util.List;
import user.Editor.Model.Editor;
import user.Reader.Model.Reader;
import user.Writer.Model.Writer;

public interface StoryRepo {

    //editor
    List<Story> getApprovedStories(Editor editor) throws SQLException;

    List<Story> getRejectedStories(Editor editor) throws SQLException;

    //Reader
    List<Story> getLikedStories(Reader reader) throws SQLException;

    Boolean submitStory(Story story) throws SQLException;
    
    List<Story> getStoryByCategory(List<Category> categories) throws SQLException; 
    
    List<Story> getDraftStories(Writer writer) throws SQLException;
    
    List<Story> getPendingStories() throws SQLException;//not yet approved
    

}
